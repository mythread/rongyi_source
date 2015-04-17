package com.fanxian.commons.cookie;

import com.fanxian.commons.cookie.annotation.CookieKeyPolicy;

/**
 * @author wanghai 2011-8-9 下午12:28:54
 */
public enum CookieKeyEnum {

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // 顶级域的Cookie
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * cookieId
     */
    @CookieKeyPolicy(withinCooKieName = CookieNameEnum.fanxian_cookie_forever)
    cookie_id("f_c_id");

    private String key;

    private CookieKeyEnum(String cookieKey) {
        this.key = cookieKey;
    }

    public String getKey() {
        return key;
    }

    public static CookieKeyEnum getEnum(String key) {
        for (CookieKeyEnum cookieKey : values()) {
            if (cookieKey.getKey().equals(key)) {
                return cookieKey;
            }
        }
        return null;
    }

    public String toString() {
        return name();
    }

}
