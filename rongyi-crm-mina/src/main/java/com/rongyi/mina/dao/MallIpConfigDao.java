package com.rongyi.mina.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.MallIpConfig;
import com.rongyi.mina.mapper.MallIpConfigMapper;

/**
 * @author jiejie 2014年4月27日 下午1:29:32
 */
@Repository
public class MallIpConfigDao {

    @Autowired
    private MallIpConfigMapper configMapper;

    public List<MallIpConfig> list() {
        return configMapper.list();
    }
}
