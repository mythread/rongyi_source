package com.rongyi.cms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.Categories;
import com.rongyi.cms.dao.CategoriesDao;
import com.rongyi.cms.web.form.CategoryVO;

@Service
@Transactional
public class CategoriesService {

    @Autowired
    private CategoriesDao categoriesDao;

	public List<Categories> selectAllParentCategories() throws Exception {
        return categoriesDao.selectAllParentCategories();
    }

	public List<Categories> selectSubCategoriesByParentId(Categories record) throws Exception {
        return categoriesDao.selectSubCategoriesByParentId(record);
    }

	public List<CategoryVO> selectCategories() throws Exception {
		List<Categories> list = categoriesDao.selectCategories();
		Map<String, CategoryVO> mapvo = new HashMap<String, CategoryVO>();
		List<CategoryVO> volist = new ArrayList<CategoryVO>();
		CategoryVO categoryVO =null;
		for (Categories cate : list) {
			if (cate.getParentId().equals("-1")) {
				categoryVO = new CategoryVO();
				categoryVO.setId(cate.getId());
				categoryVO.setName(cate.getName());
				mapvo.put(cate.getId(), categoryVO);
			}
		}


		for (Categories cate : list) {
			String pid = cate.getParentId();
			if (mapvo.containsKey(pid)) {
				categoryVO = new CategoryVO();
				categoryVO.setId(cate.getId());
				categoryVO.setName(cate.getName());
				if (mapvo.get(pid).getList() == null) {
					List<CategoryVO> mapvolist = new ArrayList<CategoryVO>();
					mapvo.get(pid).setList(mapvolist);
				}
				mapvo.get(pid).getList().add(categoryVO);
			}
		}
		 for(Entry<String, CategoryVO> entry:mapvo.entrySet()){
			 volist.add(entry.getValue());
		 }
		return volist;
	}
	
	 public List<String> getCategoryIdList(Integer brandId){
        return categoriesDao.getCategoriesByBrandId(brandId);
    }
	 
	 public Categories selectByPrimaryKey(String id)throws Exception{
		 return categoriesDao.selectByPrimaryKey(id);
	 }
}
