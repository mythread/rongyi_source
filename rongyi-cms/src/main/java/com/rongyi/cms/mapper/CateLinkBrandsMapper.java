package com.rongyi.cms.mapper;

import java.util.List;

import com.rongyi.cms.bean.CateLinkBrands;

public interface CateLinkBrandsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CateLinkBrands record);

    int insertSelective(CateLinkBrands record);

    CateLinkBrands selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CateLinkBrands record);

    int updateByPrimaryKey(CateLinkBrands record);

    /**
     * 通过brandId查询
     */
    List<String> getCategoryIdsByBrandId(Integer brandId);
}
