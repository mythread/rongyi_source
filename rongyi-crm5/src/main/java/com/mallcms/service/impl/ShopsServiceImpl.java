package com.mallcms.service.impl;

import java.util.List;

import com.mallcms.dao.IShopsDao;
import com.mallcms.domain.Shops;
import com.mallcms.domain.ShopsPojo;
import com.mallcms.service.IShopsService;

public class ShopsServiceImpl implements IShopsService {

    private IShopsDao shopsDaoImpl;

    public IShopsDao getShopsDaoImpl() {
        return shopsDaoImpl;
    }

    public void setShopsDaoImpl(IShopsDao shopsDaoImpl) {
        this.shopsDaoImpl = shopsDaoImpl;
    }

    @Override
    public List<Shops> getShopsByMallId(String mallId) {
        return shopsDaoImpl.getShopsByMallId(mallId);

    }
    
    @Override
    public Shops getShopById(String Id) {
    	
    	return shopsDaoImpl.getShopsById(Id);
    	
    }

    @Override
    public void updateShop(ShopsPojo shop) {
        shopsDaoImpl.update(shop);
    }

}
