package com.rongyi.mina.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.Shops;
import com.rongyi.mina.mapper.ShopsMapper;

/**
 * 类ShopsDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月5日 下午9:33:58
 */
@Repository
public class ShopsDao {

    @Autowired
    private ShopsMapper shopsMapper;

    public Integer insert(Shops shops) {
        return shopsMapper.insert(shops);
    }
}
