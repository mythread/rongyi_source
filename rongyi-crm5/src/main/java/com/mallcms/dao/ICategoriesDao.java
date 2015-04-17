package com.mallcms.dao;

import java.util.List;

import com.mallcms.domain.Categories;


public interface ICategoriesDao {
	public List<Categories> getCategoryByMallId(String mallId);
}
