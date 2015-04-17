package com.rongyi.monitor.biz.mongo.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.rongyi.monitor.biz.mongo.dao.IMallDao;

/**
 * 类IMallDaoImpl.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月7日 下午4:56:58
 */
@Repository
public class MallDaoImpl implements IMallDao {

    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String getMallName(String mallId) {
        DBCollection mall_zones = mongoTemplate.getCollection("zones");
        BasicDBObject queryDBObject = new BasicDBObject();
        queryDBObject.put("_type", "Mall");
        queryDBObject.put("_id", new ObjectId(mallId));
        DBCursor mallDB = mall_zones.find(queryDBObject);
        if (mallDB == null) {
            return null;
        }
        String name = null;
        while (mallDB.hasNext()) {
            DBObject obj = mallDB.next();
            name = obj.get("name").toString();
        }
        return name;
    }
}
