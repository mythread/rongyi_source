package com.mallcms.service.impl;

import java.util.List;

import com.mallcms.dao.ICategoriesDao;
import com.mallcms.domain.Categories;
import com.mallcms.service.ICategoriesService;

public class CategoriesServiceImpl implements ICategoriesService {

    private ICategoriesDao categoriesDaoImpl;

    public ICategoriesDao getCategoriesDaoImpl() {
        return categoriesDaoImpl;
    }

    public void setCategoriesDaoImpl(ICategoriesDao categoriesDaoImpl) {
        this.categoriesDaoImpl = categoriesDaoImpl;
    }

    @Override
    public List<Categories> getCategoryByMallId(String mallId) {
        // TODO Auto-generated method stub
        return categoriesDaoImpl.getCategoryByMallId(mallId);
    }

}
