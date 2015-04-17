package com.mallcms.service.impl;

import java.util.List;

import com.mallcms.dao.IBrandsDao;
import com.mallcms.domain.Brands;
import com.mallcms.domain.BrandsPojo;
import com.mallcms.service.IBrandsService;

public class BrandsServiceImpl implements IBrandsService {

    private IBrandsDao brandsDaoImpl;

    public IBrandsDao getBrandsDaoImpl() {
        return brandsDaoImpl;
    }

    public void setBrandsDaoImpl(IBrandsDao brandsDaoImpl) {
        this.brandsDaoImpl = brandsDaoImpl;
    }

    @Override
    public List<Brands> getBrandsByMallId(String mallId) {
        return brandsDaoImpl.getBrandsByMallId(mallId);
    }

    @Override
    public String insert(BrandsPojo pojo) {
        return brandsDaoImpl.insert(pojo);
    }

	@Override
	public Brands getBrandsBybrandId(String brandId) {
		return brandsDaoImpl.getBrandsByBrandId(brandId);
	}

}
