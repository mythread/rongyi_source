package com.fanxian.commons.cookie.annotation;

public enum CookieDomain {

    /**
     */
     DOT_LINHUAQIAN_COM(".linhuaqian.com");
//   DOT_LINHUAQIAN_COM(".yueji.com");

    private String domain;

    private CookieDomain(String cookieDomain) {
        this.domain = cookieDomain;
    }

    public String getDomain() {
        return domain;
    }

    public static CookieDomain getEnum(String domain) {
        for (CookieDomain cookieDomain : values()) {
            if (cookieDomain.getDomain().equals(domain)) {
                return cookieDomain;
            }
        }
        return null;
    }
}
