package com.rongyi.monitor.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiejie 2014年6月10日 上午10:21:31
 */
public class TerminalCacheManager {

    private static Map<String, Long> cacheMap     = new HashMap<String, Long>();
    private static final int         EXPIRED_TIME = 60 * 60 * 1000;

    public static void setTerminalCache(String terminalId, Long time) {
        cacheMap.put(terminalId, time);
    }

    public static Long getCacheValue(String terminalId) {
        return cacheMap.get(terminalId);
    }

    /**
     * 是否过期
     * 
     * @return
     */
    public static boolean isExpired(String terminalId) {
        Long l = getCacheValue(terminalId);
        if (l == null) {
            // map中不存在该终端机ID信息
            return true;
        }
        if (System.currentTimeMillis() - l < EXPIRED_TIME) {
            return false;
        }
        return true;
    }

    /**
     * 清空map
     */
    public static void cleanAll() {
        if (!cacheMap.isEmpty()) {
            cacheMap.clear();
        }
    }
}
