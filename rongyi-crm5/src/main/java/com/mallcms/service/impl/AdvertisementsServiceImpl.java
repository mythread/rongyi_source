package com.mallcms.service.impl;

import java.util.List;

import com.mallcms.dao.IAdvertisementsDao;
import com.mallcms.domain.Advertisements;
import com.mallcms.domain.AdvertisementsPojo;
import com.mallcms.service.IAdvertisementsService;

public class AdvertisementsServiceImpl implements IAdvertisementsService {

    private IAdvertisementsDao advertisementsDaoImpl;

    public IAdvertisementsDao getAdvertisementsDaoImpl() {
        return advertisementsDaoImpl;
    }

    public void setAdvertisementsDaoImpl(IAdvertisementsDao advertisementsDaoImpl) {
        this.advertisementsDaoImpl = advertisementsDaoImpl;
    }

    @Override
    public List<Advertisements> getAdvertisementsByMallId(String mall_id) {
        return advertisementsDaoImpl.getAdvertisementsByMallId(mall_id);
    }

    @Override
    public String insert2Mongodb(AdvertisementsPojo advert) {
        return advertisementsDaoImpl.insert(advert);
    }

	@Override
	public Advertisements getAdvertisementsById(String id) {
		return advertisementsDaoImpl.getAdvertisementsById(id);
	}

	@Override
	public String getAdzoneNameById(String id) {
		return advertisementsDaoImpl.getAdzoneById(id);
	}

}
