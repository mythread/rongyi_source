package com.fanxian.commons.cookie;

import org.apache.commons.lang.StringUtils;

import com.fanxian.commons.cookie.annotation.CookieDomain;
import com.fanxian.commons.cookie.annotation.CookieMaxAge;
import com.fanxian.commons.cookie.annotation.CookieNamePolicy;

/**
 * 一个Cookie组，其实就是Servlet 中一个Cookie项，他的Name就是Servelet Cookie中的Name
 */
public enum CookieNameEnum {

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // 顶级域的Cookie
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @CookieNamePolicy(domain = CookieDomain.DOT_LINHUAQIAN_COM, isSimpleValue = true, maxAge = CookieMaxAge.TEMP)
    fanxian_cookie_userid("f_uid"),

    /**
     * 商品url
     */
    @CookieNamePolicy(domain = CookieDomain.DOT_LINHUAQIAN_COM, isSimpleValue = true, maxAge = CookieMaxAge.TEMP)
    pro_url("f_pu"),

    /**
     * 永久Cookie
     */
    @CookieNamePolicy(domain = CookieDomain.DOT_LINHUAQIAN_COM, maxAge = CookieMaxAge.FOREVER)
    fanxian_cookie_forever("f_cf");

    private String cookieName;

    private CookieNameEnum(String cookieName) {
        this.setCookieName(cookieName);
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String toString() {
        return name();
    }

    public static CookieNameEnum getEnum(String name) {
        for (CookieNameEnum cookieNameEnum : values()) {
            if (StringUtils.equals(name, cookieNameEnum.getCookieName())) return cookieNameEnum;
        }
        return null;
    }

}
