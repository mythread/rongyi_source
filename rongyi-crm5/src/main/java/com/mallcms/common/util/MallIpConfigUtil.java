package com.mallcms.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mallcms.domain.MallIpConfig;

/**
 * 获取商场的ip,port配置
 * 
 * @author jiejie 2014年4月24日 下午5:06:54
 */
public class MallIpConfigUtil {

    private static Map<String, MallIpConfig> configMap = new ConcurrentHashMap<String, MallIpConfig>();
}
