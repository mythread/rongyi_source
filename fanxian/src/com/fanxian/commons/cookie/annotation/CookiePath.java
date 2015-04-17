package com.fanxian.commons.cookie.annotation;

public enum CookiePath {
    /**
     * 泛子目录
     */
    ROOT("/"),

    ADMIN("/admin");

    private String path;

    private CookiePath(String path) {
        this.setPath(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static CookiePath getEnum(String path) {
        for (CookiePath cookiePath : values()) {
            if (cookiePath.getPath().equals(path)) return cookiePath;
        }
        return null;
    }

}
