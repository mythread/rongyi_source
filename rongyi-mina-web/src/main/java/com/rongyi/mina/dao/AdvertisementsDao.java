package com.rongyi.mina.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.Advertisements;
import com.rongyi.mina.bean.BaseTaskImpl;
import com.rongyi.mina.mapper.AdvertisementsMapper;

/**
 * @author jiejie 2014年4月5日 下午9:25:22
 */
@Repository
public class AdvertisementsDao {
	private static final Logger LOG  = LoggerFactory.getLogger(AdvertisementsDao.class);
    @Autowired
    private AdvertisementsMapper advertMapper;

    public Integer insert(Advertisements advert) {
    	LOG.info("advert.getName()>>>"+advert.getName());
        return advertMapper.insert(advert);
    };
}
