package com.rongyi.cms.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.Brands;
import com.rongyi.cms.bean.Categories;
import com.rongyi.cms.mapper.BrandsMapper;

@Repository
public class BrandsDao {

    @Autowired
    private BrandsMapper brandsMapper;

    public List<Brands> selectBrandsBySubCategoryId(Categories categories) {
        return brandsMapper.selectBrandsBySubCategoryId(categories);
    }

    public List<Brands> selectBrandsByParentCategoryId(Categories categories) throws Exception {
        return brandsMapper.selectBrandsByParentCategoryId(categories);
    }

    public List<Brands> listPageselectBrandsByParentCategoryId(Categories categories) throws Exception {
        return brandsMapper.listPageselectBrandsByParentCategoryId(categories);
    }
    
    
    public List<Brands> selectBrandsByCEName(Brands brands) throws Exception {
        return brandsMapper.selectBrandsByCEName(brands);
    }

    public Brands selectByPrimaryKey(Integer id) throws Exception {
        return brandsMapper.selectByPrimaryKey(id);
    }

    public int insert(Brands record) throws Exception {
        return brandsMapper.insert(record);
    }

    public boolean updateByPrimaryKeySelective(Brands brand) {
        return brandsMapper.updateByPrimaryKeySelective(brand) > 0;
    }

    public boolean verifyOK(HashMap<String, Object> map) {
        String outerId = (String) map.get("cmsOuterId");
        Integer reviewStatus = (Integer) map.get("reviewStatus");
        String mongodbId = (String) map.get("mongodbId");
        String memo = (String) map.get("memo");
        String picUrl = (String) map.get("imgUrl");

        Brands brand = new Brands();
        brand.setId(Integer.valueOf(outerId));
        brand.setSyhchStatus(reviewStatus);
        brand.setSyhchMsg(memo);
        brand.setMongoId(mongodbId);
        if (StringUtils.isNotEmpty(picUrl)) {
            brand.setIconUrl(picUrl);
        }
        return updateByPrimaryKeySelective(brand);

    }
}
