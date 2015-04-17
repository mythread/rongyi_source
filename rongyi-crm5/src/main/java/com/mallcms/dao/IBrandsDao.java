package com.mallcms.dao;

import java.util.List;

import com.mallcms.domain.Brands;
import com.mallcms.domain.BrandsPojo;

public interface IBrandsDao {

    public List<Brands> getBrandsByMallId(String mallId);
    public Brands getBrandsByBrandId(String brandId);

    public String insert(BrandsPojo pojo);
}
