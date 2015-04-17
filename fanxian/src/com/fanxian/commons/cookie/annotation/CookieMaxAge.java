package com.fanxian.commons.cookie.annotation;

/**
 * @author wanghai 2011-11-3 上午10:52:54
 */
public final class CookieMaxAge {

    /**
     * 用于临时Cookie的MaxAge
     */
    public static final int TEMP        = -1;
    /**
     * 用于永久Cookie的MaxAge
     */
    public static final int FOREVER     = Integer.MAX_VALUE;

    /**
     * 用于删除Cookie
     */
    public static final int OUT_OF_DATE = 0;

    /**
     * 有效时间：一周
     */
    public static final int ONE_WEEK    = 60 * 60 * 24 * 7;

}
