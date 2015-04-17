package com.fanxian.web.fanxian.fetch;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.fanxian.web.common.util.UrlAnalysisUtil;

public class TaobaoFetchProduct implements IFetchProductResult {

    private static final String TAOBAO_C     = "http://item.taobao.com/item.htm";
    private static final String TMALL        = "http://detail.tmall.com/item.htm";
    // 聚划算
    private static final String JUHUASUAN    = "http://detail.ju.taobao.com/home.htm";

    private String              sourceUrl;
    private String              simpleUrl;                                            // http://item.taobao.com/item.htm?id=11111
    private boolean             success;
    private String              message;
    private long                id;
    private String              IDENTITY_KEY = "id";

    public TaobaoFetchProduct(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    private boolean resolverUrl() {
        // 检查是否是合法的淘宝url
        if (!checkUrl()) {
            return false;
        }
        Map<String, String> paramsMap = UrlAnalysisUtil.getParamsMap(sourceUrl);
        if (paramsMap.isEmpty()) {
            return false;
        }
        String str = paramsMap.get(IDENTITY_KEY);
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        long longvalue = NumberUtils.toLong(str, 0);
        if (longvalue <= 0) {
            return false;
        }
        id = longvalue;
        success = true;
        simpleUrl = paramsMap.get("reqUrl") + "?id=" + id;
        return true;
    }

    @Override
    public void resolver() {
        if (StringUtils.isBlank(sourceUrl)) {
            setErrorMessage("请输入淘宝商品的url！");
            return;
        }
        if (!resolverUrl()) {
            StringBuilder sb = new StringBuilder();
            sb.append("【").append(sourceUrl).append("】不是合法的淘宝商品的url");
            setErrorMessage(sb.toString());
            return;
        }
    }

    private boolean checkUrl() {
        if (StringUtils.startsWith(sourceUrl, TAOBAO_C) || StringUtils.startsWith(sourceUrl, TMALL)
            || StringUtils.startsWith(sourceUrl, JUHUASUAN)) {
            return true;
        }
        return false;
    }

    public void setErrorMessage(String message) {
        this.success = false;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSimpleUrl() {
        return simpleUrl;
    }

    public void setSimpleUrl(String simpleUrl) {
        this.simpleUrl = simpleUrl;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String message() {
        return message;
    }

}
