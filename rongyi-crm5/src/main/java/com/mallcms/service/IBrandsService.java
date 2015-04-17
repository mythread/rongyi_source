package com.mallcms.service;

import java.util.List;

import com.mallcms.domain.Brands;
import com.mallcms.domain.BrandsPojo;

public interface IBrandsService {

    public List<Brands> getBrandsByMallId(String mallId);
    public Brands getBrandsBybrandId(String brandId);

    public String insert(BrandsPojo pojo);
}
