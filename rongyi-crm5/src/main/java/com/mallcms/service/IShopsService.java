package com.mallcms.service;

import java.util.List;

import com.mallcms.domain.Shops;
import com.mallcms.domain.ShopsPojo;

public interface IShopsService {

    public List<Shops> getShopsByMallId(String mallId);

    public void updateShop(ShopsPojo shop);

	Shops getShopById(String Id);
}
