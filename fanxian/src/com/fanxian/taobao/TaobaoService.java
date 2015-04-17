package com.fanxian.taobao;

import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import com.fanxian.commons.lang.Argument;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.request.ItemGetRequest;
import com.taobao.api.response.ItemGetResponse;
import com.yue.commons.log.LoggerFactoryWrapper;
import com.yue.commons.security.HMacMD5;

public class TaobaoService {

    private static Logger                          logger       = LoggerFactoryWrapper.getLogger(TaobaoService.class);

    // private static final String ITEM_FIELDS = "detail_url,num_iid,title,nick,type,cid,seller_cids,"
    // + "props,input_pids,input_str,desc,pic_url,num,valid_thru,"
    // + "list_time,delist_time,stuff_status,location,price,"
    // + "post_fee,express_fee,ems_fee,has_discount,freight_payer,"
    // + "has_invoice,has_warranty,has_showcase,modified,increment,"
    // + "approve_status,postage_id,product_id,auction_point," + "item_img,"
    // + "property_alias,prop_img,sku,video,outer_id,is_virtual";
    private static final String                    ITEM_FIELDS  = "detail_url,num_iid,title,nick,type,price,pic_url";

    // 记录佣金的内存缓存map
    private static Map<String, TaobaokePriceCache> priceHashMap = new ConcurrentHashMap<String, TaobaokePriceCache>();

    // 过期时间10min
    private static int                             EXPIRED_TIME = 60 * 10 * 1000;

    // 操作缓存的时间
    private static long                            operateTime  = 0l;

    private static Map<String, TaobaokePriceCache> getPriceHashMap() {
        return priceHashMap;
    }

    /**
     * 根据淘宝商品id获取商品信息
     * 
     * @param authInfo 验证信息
     * @param iid 商品id
     * @param fileds 商品字段，为空时，取素有商品字段
     * @param useSession 是否使用session，卖家未登录时只能获得这个商品的公开数据，卖家登录后可以获取商品的所有数据
     * @return
     */
    public Item getItem(AuthInfo4TB authInfo, long iid, String fileds, boolean useSession) {
        if (iid <= 0) {
            return null;
        }
        TaobaoClient client = getTaobaoClient(authInfo);
        ItemGetRequest req = new ItemGetRequest();
        if (StringUtils.isEmpty(fileds)) {
            req.setFields(ITEM_FIELDS);
        } else {
            req.setFields(fileds);
        }
        req.setNumIid(iid);
        try {
            ItemGetResponse response;
            if (useSession) {
                response = client.execute(req, authInfo.getSessionKey());
            } else {
                response = client.execute(req);
            }
            return response.getItem();
        } catch (ApiException e) {
            logger.error(e.getErrMsg(), e);
        }
        return null;
    }

    /**
     * 从Authinfo4TB 创建一个TaobaoClient
     * 
     * @param authInfo4TB
     * @return
     */
    public TaobaoClient getTaobaoClient(AuthInfo4TB authInfo4TB) {
        return getTaobaoClient(authInfo4TB.getUrl(), authInfo4TB.getAppKey(), authInfo4TB.getAppSecret());
    }

    /**
     * 创建TaobaoClient对象
     * 
     * @param url
     * @param appkey
     * @param appSecret
     * @return
     */
    private TaobaoClient getTaobaoClient(String url, String appkey, String appSecret) {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, appSecret);
        return client;
    }

    /**
     * 计算淘宝签名
     * 
     * @param time
     * @return
     */
    public String getSign(long time) {
        StringBuilder sb = new StringBuilder(200);
        sb.append(TaobaoUtil.LINHUAQIAN_APP_SECRET).append("app_key").append(TaobaoUtil.LINHUAQIAN_APP_KEY);
        sb.append("timestamp").append(time).append(TaobaoUtil.LINHUAQIAN_APP_SECRET);

        try {
            byte[] hmacMd5Bytes = HMacMD5.getHmacMd5Bytes(TaobaoUtil.LINHUAQIAN_APP_SECRET.getBytes(),
                                                          sb.toString().getBytes());
            return new String(Hex.encodeHex(hmacMd5Bytes)).toUpperCase();
        } catch (Exception e) {
        }
        return StringUtils.EMPTY;
    }

    /**
     * <pre>
     * 获得淘宝客佣金，这里向“http://www.huadajie.com/”发请求
     * 花大街的佣金返还的是实际佣金的50%
     * @param url 淘宝商品url
     * @return 返回多少分
     * </pre>
     */
    public Integer getTaobaokeFanPrice(String url) {
        try {
            String encodeProductUrl = URLEncoder.encode(url, "utf-8");
            String reqUrl = "http://www.huadajie.com/index.php?mod=tao&act=view&q=" + encodeProductUrl + "&search=1";
            // 获取目标链接的Document
            Document doc = Jsoup.connect(reqUrl).get();
            Elements els = doc.getElementsByClass("price").eq(2);
            if (els.isEmpty()) {
                return null;
            }
            String priceHtml = els.first().html();
            // 解析价格
            int index = StringUtils.indexOf(priceHtml, "元");
            if (index == -1) {
                return null;
            }
            String price = StringUtils.substring(priceHtml, 0, index);
            float price2 = NumberUtils.toFloat(price) * 1.2f;
            Integer realFen = (int) (price2 * 100);
            if (!Argument.isPositive(realFen)) {
                realFen = getTaobaokeFanPrice(url);
            }
            return realFen;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从内存中获得佣金
     */
    public Integer getTaobaokeFanPriceFromCache(long id, String url) {
        String key = "taobao_" + id;
        Map<String, TaobaokePriceCache> cacheMap = getPriceHashMap();
        Integer realFen = null;
        if (!cacheMap.isEmpty()) {
            TaobaokePriceCache tbkPrice = cacheMap.get(key);
            if (tbkPrice != null) {
                realFen = tbkPrice.getPrice();

                // 清除缓存
                long currentTime = System.currentTimeMillis();
                if (currentTime > (operateTime + EXPIRED_TIME)) {
                    for (Map.Entry<String, TaobaokePriceCache> entry : cacheMap.entrySet()) {
                        String cacheKey = entry.getKey();
                        TaobaokePriceCache cacheValue = entry.getValue();
                        if (currentTime > (cacheValue.getCreateTime() + EXPIRED_TIME)) {
                            cacheMap.remove(cacheKey);
                        }
                    }
                    operateTime = currentTime;
                }
                return realFen;
            }
        }

        realFen = getTaobaokeFanPrice(url);
        if (realFen == null) {
            cacheMap.put(key, new TaobaokePriceCache(key, null));
        } else {
            cacheMap.put(key, new TaobaokePriceCache(key, realFen));
        }
        operateTime = System.currentTimeMillis();
        return realFen;
    }
}
