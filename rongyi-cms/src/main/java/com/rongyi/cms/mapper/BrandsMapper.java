package com.rongyi.cms.mapper;

import java.util.List;

import com.rongyi.cms.bean.Brands;
import com.rongyi.cms.bean.Categories;

public interface BrandsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Brands record);

    int insertSelective(Brands record);

    Brands selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Brands record);

    int updateByPrimaryKeyWithBLOBs(Brands record);

    int updateByPrimaryKey(Brands record);

    List<Brands> selectBrandsBySubCategoryId(Categories categories);

    List<Brands> selectBrandsByParentCategoryId(Categories categories);
    
    List<Brands> listPageselectBrandsByParentCategoryId(Categories categories);

    List<Brands> selectBrandsByCEName(Brands brands);

}
