package com.fanxian.web.fanxian.controller;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fanxian.biz.user.dataobject.UserDO;
import com.fanxian.commons.cookie.CookieNameEnum;
import com.fanxian.commons.cookie.manager.CookieManager;
import com.fanxian.commons.cookie.manager.CookieManagerLocator;
import com.fanxian.commons.ip.IPTools;
import com.fanxian.commons.lang.Argument;
import com.fanxian.taobao.AuthInfo4TB;
import com.fanxian.taobao.TaobaoUtil;
import com.fanxian.web.fanxian.fetch.TaobaoFetchProduct;
import com.fanxian.web.fanxian.webuser.FanxianWebUser;
import com.taobao.api.domain.Item;
import com.yue.commons.seine.web.annotations.ControllerAction;
import com.yue.commons.seine.web.servlet.result.Redirect;
import com.yue.commons.seine.web.servlet.result.View;
import com.yue.commons.seine.web.servlet.result.WebResult;

public class SearchController extends BaseController {

    /**
     * 查询返利
     * 
     * @param model
     * @param url
     * @return
     */
    @ControllerAction
    public WebResult search(Map<String, Object> model, String url) {
        // 1.解析淘宝商品的url，获取产品id
        TaobaoFetchProduct taobaoFetch = new TaobaoFetchProduct(url);

        taobaoFetch.resolver();
        if (!taobaoFetch.success()) {
            model.put("error", taobaoFetch.message());
            return new View("/home/home.htm");
        }
        // 根据淘宝ID获取淘宝的宝贝的详细信息
        AuthInfo4TB authInfo = TaobaoUtil.getLinHuaQianAppAuth();
        Item item = taobaoService.getItem(authInfo, taobaoFetch.getId(), null, false);
        if (item == null) {
            model.put("error", "该商品不存在！");
        } else {
            Integer price = taobaoService.getTaobaokeFanPriceFromCache(taobaoFetch.getId(), taobaoFetch.getSimpleUrl());
            model.put("item", item);
            model.put("id", taobaoFetch.getId());
            model.put("productUrl", taobaoFetch.getSimpleUrl());
            model.put("price", price);
        }
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        cookieManager.set(CookieNameEnum.pro_url, taobaoFetch.getSimpleUrl());
        
        //获得ip所在的区域
        String ip = getIpAddr(request);
        String ipText =   IPTools.getInstance(request.getServletContext().getRealPath("/") +IP_QQWRY_DAT).getAddress(ip);
        return new View("/home/home.htm");
    }

    /**
     * 跳到爱淘宝页面
     */
    @ControllerAction
    public WebResult goBuy(Map<String, Object> model, String identity, Integer price, String taobaokeUrl) {
        FanxianWebUser webUser = FanxianWebUser.getCurrentUser();
        Integer id = webUser.getUserId();
        if (!Argument.isPositive(id)) {
            model.put("buyError", "请先输入支付宝帐号!");
            return new View("/home/home.htm");
        }
        UserDO userDO = userService.getById(id);
        if (userDO == null) {
            model.put("buyError", "请正确输入支付宝帐号!");
            return new View("/home/home.htm");
        }
        String ip = getIpAddr(request);
        CookieManager cookieManager = CookieManagerLocator.get(request, response);
        String productUrl = cookieManager.get(CookieNameEnum.pro_url);
        userAndProService.insert(id, identity, productUrl, ip, price);
        String redirectUrl = null;
        if (StringUtils.isEmpty(taobaokeUrl)) {
            redirectUrl = productUrl;
        } else {
            redirectUrl = taobaokeUrl;
        }
        return new Redirect(redirectUrl);
    }
}
