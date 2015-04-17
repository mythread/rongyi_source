package com.rongyi.mina.mongodao.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.rongyi.mina.mongodao.IAdZonesMongoDao;

/**
 * 类IAdZonesMongoDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月22日 下午1:32:37
 */
@Repository
public class IAdZonesMongoDaoImpl implements IAdZonesMongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void setDefaultPic(String advertMongoId, String adZoneId) {
        DBCollection ad_zones = mongoTemplate.getCollection("ad_zones");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new ObjectId(adZoneId));
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("default_picture", advertMongoId);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        ad_zones.update(queryCondition, updateSetValue, true, true);

    }

}
