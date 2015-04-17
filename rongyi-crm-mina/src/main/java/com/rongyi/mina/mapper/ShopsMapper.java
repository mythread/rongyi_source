package com.rongyi.mina.mapper;

import java.util.Map;

import com.rongyi.mina.bean.Shops;

/**
 * 类ShopsMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月5日 下午9:15:02
 */
public interface ShopsMapper {

    Integer insert(Shops shops);
    
    int updateByPrimaryKeySelective(Shops record);
    
    Shops selectByParam(Map<String,Object> map);
}
