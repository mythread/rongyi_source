package com.fanxian.commons.cookie.manager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取CookieManager
 * 
 * @author wanghai 2011-9-6 下午9:24:19
 */
public class CookieManagerLocator {

    private static final String key  = "_cookiemanager_";
    private static final Lock   LOCK = new ReentrantLock();

    public static CookieManager get(HttpServletRequest request, HttpServletResponse response) {
        // 大多数情况
        CookieManager cookieManager = (CookieManager) request.getAttribute(key);
        if (cookieManager != null) {
            return cookieManager;
        }
        // 第一次需要创建
        try {
            LOCK.lock();
            if (cookieManager == null) {
                cookieManager = new DefaultCookieManager(request, response);
                request.setAttribute(key, cookieManager);
            }
            return cookieManager;
        } finally {
            LOCK.unlock();
        }

    }
}
