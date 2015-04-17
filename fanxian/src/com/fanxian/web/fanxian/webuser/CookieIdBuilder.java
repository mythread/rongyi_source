package com.fanxian.web.fanxian.webuser;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.fanxian.commons.cookie.CookieKeyEnum;
import com.fanxian.commons.cookie.manager.CookieManager;

public class CookieIdBuilder {

    private static Lock lock = new ReentrantLock();

    /**
     * 创建cookieId 并 写入cookie中
     * 
     * @param cookieManager
     * @return
     */
    public static String createCookieId(CookieManager cookieManager) {
        try {
            lock.lock();
            String cookieId = getCookieId(cookieManager);
            if (cookieId == null) {
                cookieId = create();
                cookieManager.set(CookieKeyEnum.cookie_id, cookieId);
            }
            return cookieId;
        } catch (Exception e) {
            return null;
        } finally {
            lock.unlock();
        }
    }

    public static String getCookieId(CookieManager cookieManager) {
        return cookieManager.get(CookieKeyEnum.cookie_id);
    }

    public static String create() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }
}
