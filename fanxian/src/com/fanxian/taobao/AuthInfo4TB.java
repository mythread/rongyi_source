package com.fanxian.taobao;

public class AuthInfo4TB {

    /**
     * 淘宝Open API app key
     */
    private String appKey;
    /**
     * 淘宝Open API app secret
     */
    private String appSecret;
    /**
     * 淘宝Open API session Key
     */
    private String sessionKey;
    /**
     * 淘宝Open API URL
     * 
     * <pre>
     * 容器地址：http://container.open.taobao.com/container
     * 调用接口提交地址：http://gw.api.taobao.com/router/rest
     * </pre>
     */
    private String url;

    /**
     * 缺省构建器
     */
    public AuthInfo4TB() {
    }

    /**
     * @param appKey
     * @param appSecret
     * @param sessionKey
     * @param url
     */
    public AuthInfo4TB(String appKey, String appSecret, String sessionKey, String url) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.sessionKey = sessionKey;
        this.url = url;
    }

    /**
     * @return the appKey
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * @param appKey the appKey to set
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * @return the appSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * @param appSecret the appSecret to set
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * @return the sessionKey
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * @param sessionKey the sessionKey to set
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
