package com.rongyi.mina.mongodao;

import org.springframework.stereotype.Repository;

/**
 * 操作mongoDB里的广告位
 * 
 * @author jiejie 2014年4月22日 下午12:18:26
 */
@Repository
public interface IAdZonesMongoDao {

    public void setDefaultPic(String advertMongoId, String adZoneId);
}
