package com.mallcms.dao;

import java.util.List;

import com.mallcms.domain.Shops;
import com.mallcms.domain.ShopsPojo;

public interface IShopsDao {

    public List<Shops> getShopsByMallId(String mallId);
    public Shops getShopsById(String Id);

    /**
     * 更新商铺信息
     * 
     * @param shop
     */
    public void update(ShopsPojo shop);
}
