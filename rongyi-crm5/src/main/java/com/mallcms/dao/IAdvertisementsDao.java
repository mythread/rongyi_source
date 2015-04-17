package com.mallcms.dao;

import java.util.List;

import com.mallcms.domain.Advertisements;
import com.mallcms.domain.AdvertisementsPojo;

public interface IAdvertisementsDao {

    public List<Advertisements> getAdvertisementsByMallId(String mall_id);

    public String insert(AdvertisementsPojo ad);

	public Advertisements getAdvertisementsById(String id);

	String getAdzoneById(String id);
}
