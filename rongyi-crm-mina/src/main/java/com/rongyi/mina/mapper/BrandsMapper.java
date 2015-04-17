package com.rongyi.mina.mapper;

import java.util.Map;

import com.rongyi.mina.bean.Brands;

/**
 * 类BrandMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月5日 下午9:05:10
 */
public interface BrandsMapper {

    Integer insert(Brands brands);

    /**
     * 查询为审核的品牌
     * 
     * @param map
     * @return
     */
    Brands selectByParam(Map<String, Object> map);

    Integer update(Brands brands);

}
