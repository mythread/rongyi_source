package com.mallcms.service;

import java.util.List;

import com.mallcms.domain.Advertisements;
import com.mallcms.domain.AdvertisementsPojo;

public interface IAdvertisementsService {

    public List<Advertisements> getAdvertisementsByMallId(String mall_id);
    public Advertisements getAdvertisementsById(String id);
    public String getAdzoneNameById(String id);

    /**
     * 向mongodb中插入数据
     */
    public String insert2Mongodb(AdvertisementsPojo advert);
}
