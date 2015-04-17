package com.rongyi.mina.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.Brands;
import com.rongyi.mina.mapper.BrandsMapper;

/**
 * 类BrandsDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月5日 下午9:28:21
 */
@Repository
public class BrandsDao {

    @Autowired
    private BrandsMapper brandsMapper;

    public Integer insert(Brands brands) {
        return brandsMapper.insert(brands);
    };
}
