package com.rongyi.cms.mapper;

import java.util.List;

import com.rongyi.cms.bean.Categories;

public interface CategoriesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Categories record);

    int insertSelective(Categories record);

    Categories selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Categories record);

    int updateByPrimaryKey(Categories record);
    
    List<Categories> selectAllParentCategories();
    
    List<Categories> selectSubCategoriesByParentId(Categories record);
    
    List<Categories> selectAll();
}