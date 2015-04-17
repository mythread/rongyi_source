/*
 * Copyright 2011-2016 YueJi.com All right reserved. This software is the confidential and proprietary information of
 * YueJi.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with YueJi.com.
 */
package com.fanxian.commons.udas;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.yue.commons.nisa.service.ConfigServiceLocator;
import com.yue.commons.udas.impl.CacheEnum;
import com.yue.commons.udas.impl.UdasServiceImpl;
import com.yue.commons.udas.interfaces.UdasService;

/**
 * @author 轰天雷 2013-7-17 下午04:27:10
 */
public class UdasServiceFactory {

    private static final String       CONFIGKEY        = "SA_memcached.url";
    private static UdasServiceFactory instance         = new UdasServiceFactory();
    private Map<String, UdasService>  map              = new HashMap<String, UdasService>();
    private boolean                   useInnerMemCache = false;

    private UdasServiceFactory() {
        useInnerMemCache = ConfigServiceLocator.getCongfigService().getKV("B_INNER_CACHE_USE", false);
    }

    public static UdasServiceFactory getInstance() {
        return instance;
    }

    public UdasService getUdasService(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        UdasService udasService = map.get(name);
        if (udasService != null) {
            return udasService;
        }
        UdasServiceImpl impl;
        if (useInnerMemCache) {
            impl = new UdasServiceImpl(CacheEnum.inner_mem, name, CONFIGKEY);
            impl.init();
        } else {
            impl = new UdasServiceImpl(CacheEnum.remote_memcached, name, CONFIGKEY);
            impl.init();
        }
        map.put(name, impl);
        return impl;
    }
}
