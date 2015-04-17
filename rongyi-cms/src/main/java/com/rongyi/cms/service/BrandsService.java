package com.rongyi.cms.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.Brands;
import com.rongyi.cms.bean.CateLinkBrands;
import com.rongyi.cms.bean.Categories;
import com.rongyi.cms.dao.BrandsDao;

@Service
@Transactional
public class BrandsService {

    @Autowired
    private BrandsDao brandsDao;
	@Autowired
	private CateLinkBrandsService cateLinkBrandsService;
	@Autowired
	private PhotosService  photosService;

	public List<Brands> selectBrandsBySubCategoryId(Categories categories)  throws Exception{
        return brandsDao.selectBrandsBySubCategoryId(categories);
    }

	public List<Brands> selectBrandsByParentCategoryId(Categories categories) throws Exception{
        return brandsDao.selectBrandsByParentCategoryId(categories);
    }
	
	 public List<Brands> listPageselectBrandsByParentCategoryId(Categories categories) throws Exception {
	        return brandsDao.listPageselectBrandsByParentCategoryId(categories);
	 }

	public  List<Brands> selectBrandsByCEName(Brands brands)  throws Exception{
        return brandsDao.selectBrandsByCEName(brands);
    }

	public Brands selectByPrimaryKey(Integer id)  throws Exception{
        return brandsDao.selectByPrimaryKey(id);
	}
	
	public int insert(Brands brands,String categoryIds )  throws Exception{
		String[] categoryidArray ={};
		if(!StringUtils.isBlank(categoryIds)){
			categoryidArray = categoryIds.split("#");
		}
		brandsDao.insert(brands);
		int brandsId = brands.getId();
		for(int i=0;i<categoryidArray.length;i++){
			CateLinkBrands  cateLinkBrands = new CateLinkBrands();
			cateLinkBrands.setBrandsId(brandsId);
			cateLinkBrands.setCategoriesId(categoryidArray[i]);
			cateLinkBrandsService.insert(cateLinkBrands);
		}	
		return brandsId;
    }

    public boolean updateByPrimaryKeySelective(Brands brand) {
        return brandsDao.updateByPrimaryKeySelective(brand);
    }
}
