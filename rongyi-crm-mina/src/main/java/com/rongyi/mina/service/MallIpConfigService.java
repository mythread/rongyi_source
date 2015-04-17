package com.rongyi.mina.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.MallIpConfig;
import com.rongyi.mina.dao.MallIpConfigDao;

/**
 * 类MallIpConfigService.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月27日 下午1:32:41
 */

@Repository
public class MallIpConfigService {

    @Autowired
    private MallIpConfigDao configDao;

    /**
     * key:mallId;value:MallIpConfig
     * 
     * @return
     */
    public Map<String, MallIpConfig> mallIpConfigMap() {
        List<MallIpConfig> list = configDao.list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        Map<String, MallIpConfig> result = new HashMap<String, MallIpConfig>();
        for (MallIpConfig config : list) {
            if (StringUtils.isNotEmpty(config.getMallId())) {
                result.put(config.getMallId(), config);
            }
        }
        return result;
    }

}
