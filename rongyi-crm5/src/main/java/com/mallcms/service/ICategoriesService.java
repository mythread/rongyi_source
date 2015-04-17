package com.mallcms.service;

import java.util.List;

import com.mallcms.domain.Categories;

public interface ICategoriesService {
	public List<Categories> getCategoryByMallId(String mallId);
}
