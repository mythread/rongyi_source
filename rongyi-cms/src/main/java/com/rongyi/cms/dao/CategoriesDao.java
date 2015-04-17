package com.rongyi.cms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.Categories;
import com.rongyi.cms.mapper.CateLinkBrandsMapper;
import com.rongyi.cms.mapper.CategoriesMapper;

@Repository
public class CategoriesDao {

    @Autowired
    private CategoriesMapper     categoriesMapper;

    @Autowired
    private CateLinkBrandsMapper cateLinkBrandsMapper;

    public List<Categories> selectAllParentCategories() throws Exception {
        return categoriesMapper.selectAllParentCategories();
    }

    public List<Categories> selectSubCategoriesByParentId(Categories record) throws Exception {
        return categoriesMapper.selectSubCategoriesByParentId(record);
    }
    public List<Categories> selectCategories()throws Exception{
		return categoriesMapper.selectAll();
	}

    public List<String> getCategoriesByBrandId(Integer brandId){
        return cateLinkBrandsMapper.getCategoryIdsByBrandId(brandId);
    }
    
    public Categories selectByPrimaryKey(String id)throws Exception{
    	return categoriesMapper.selectByPrimaryKey(id);
    }
 
	
}
