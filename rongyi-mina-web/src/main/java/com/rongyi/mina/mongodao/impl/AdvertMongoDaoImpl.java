package com.rongyi.mina.mongodao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.rongyi.mina.constant.Constant;
import com.rongyi.mina.mongodao.IAdvertMongoDao;

/**
 * @author jiejie 2014年4月15日 下午7:37:43
 */
@Repository
public class AdvertMongoDaoImpl implements IAdvertMongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void turnOn(String mongoId) {
        DBCollection adDBCol = mongoTemplate.getCollection("advertisements");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", mongoId);
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("on_status", Constant.OpenState.OPEN_STATE);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        adDBCol.update(queryCondition, updateSetValue, true, true);

    }

    @Override
    public void turnOff(String mongoId) {
        DBCollection adDBCol = mongoTemplate.getCollection("advertisements");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", mongoId);
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("on_status", Constant.OpenState.OPEN_STATE);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        adDBCol.update(queryCondition, updateSetValue, true, true);

    }

    @Override
    public void delete(String mongoId) {
        DBCollection adDBCol = mongoTemplate.getCollection("advertisements");
        adDBCol.remove(new BasicDBObject("_id", mongoId));
    }

    @Override
    public void setDefaultAd(Integer defaultVal, String mongoId) {
        DBCollection adDBCol = mongoTemplate.getCollection("advertisements");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", mongoId);
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("is_default", defaultVal);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        adDBCol.update(queryCondition, updateSetValue, true, true);
    }
}
