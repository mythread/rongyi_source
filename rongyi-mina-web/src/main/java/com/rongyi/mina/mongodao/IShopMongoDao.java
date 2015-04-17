package com.rongyi.mina.mongodao;

import org.springframework.stereotype.Repository;

/**
 * 类IShopMongonDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月22日 上午11:37:23
 */
@Repository
public interface IShopMongoDao {

    /**
     * 店铺开启、关闭 <br/>
     * 0:关闭 1 :开启
     */
    public void turnOnOrOff(Integer onStatus, String mongodbId);

    /**
     * 店铺推荐、不推荐<br/>
     * 0 : 不推荐 1 :推荐
     */
    public void recommendOnOrOff(Integer recommend, String mongodbId);
}
