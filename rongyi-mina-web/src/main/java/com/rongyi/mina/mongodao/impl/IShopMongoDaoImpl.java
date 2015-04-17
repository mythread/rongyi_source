package com.rongyi.mina.mongodao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.rongyi.mina.mongodao.IShopMongoDao;

/**
 * @author jiejie 2014年4月22日 下午1:34:48
 */
@Repository
public class IShopMongoDaoImpl implements IShopMongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void turnOnOrOff(Integer onStatus, String mongodbId) {
        DBCollection shops = mongoTemplate.getCollection("shops");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", mongodbId);
        BasicDBObject updatedValue = new BasicDBObject();
        Integer valid = 0;
        if (onStatus != null && onStatus != 1) {
            valid = 2;
        }
        updatedValue.put("valid", valid);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        shops.update(queryCondition, updateSetValue, true, true);
    }

    @Override
    public void recommendOnOrOff(Integer recommend, String mongoDbId) {
        DBCollection shops = mongoTemplate.getCollection("shops");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", mongoDbId);
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("recommend", recommend);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        shops.update(queryCondition, updateSetValue, true, true);
    }

}
