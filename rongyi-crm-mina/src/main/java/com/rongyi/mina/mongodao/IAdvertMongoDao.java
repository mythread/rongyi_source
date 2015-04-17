package com.rongyi.mina.mongodao;

import org.springframework.stereotype.Repository;

/**
 * @author jiejie 2014年4月15日 下午7:37:22
 */
@Repository
public interface IAdvertMongoDao {

    /**
     * 开启广告
     */
    public void turnOn(String mongoId);

    /**
     * 关闭广告
     */
    public void turnOff(String mongoId);

    /**
     * 删除广告
     */
    public void delete(String mongoId);

    /**
     * 默认广告开启、关闭
     * 
     * @param defaultVal 0:不是 1:是
     */
    public void setDefaultAd(Integer defaultVal, String mongoId);

}
