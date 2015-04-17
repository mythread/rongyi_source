package com.rongyi.mina.mongodao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
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
        queryCondition.put("_id", new ObjectId(mongoId));
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("on_status", Constant.OpenState.OPEN_STATE);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        adDBCol.update(queryCondition, updateSetValue, true, true);
    }

    @Override
    public void turnOff(String mongoId) {
        DBCollection adDBCol = mongoTemplate.getCollection("advertisements");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new ObjectId(mongoId));
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("on_status", Constant.OpenState.CLOSE_STATE);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        adDBCol.update(queryCondition, updateSetValue, true, true);

    }

    @Override
    public void delete(String mongoId) {
        DBCollection adZonesDbCol = mongoTemplate.getCollection("ad_zones");
        DBCollection advertDBCol = mongoTemplate.getCollection("advertisements");

        DBObject advert = advertDBCol.findOne(new BasicDBObject("_id", new ObjectId(mongoId)));
        ArrayList<BasicDBObject> ad_zones = (ArrayList<BasicDBObject>) advert.get("ad_zone_ids");
        if (ad_zones == null) {
            return;
        }
        Object temp = ad_zones.get(0);
        DBObject adzone = adZonesDbCol.findOne(new BasicDBObject("_id", new ObjectId(temp.toString())));
        ArrayList<BasicDBObject> newAdvertisement = (ArrayList<BasicDBObject>) adzone.get("advertisement_ids");
        List<ObjectId> list = new ArrayList<ObjectId>();

        for (int i = 0; i < newAdvertisement.size(); i++) {
            Object object_cate = newAdvertisement.get(i);
            if (!object_cate.toString().equalsIgnoreCase(mongoId)) {
                list.add(new ObjectId(object_cate.toString()));
            }
        }
        ObjectId[] newAdvertisementString = list.toArray(new ObjectId[0]);

        DBObject updatedValue = new BasicDBObject();

        updatedValue.put("advertisement_ids", newAdvertisementString);

        DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        /**
         * updateCondition:更新条件 updateSetValue:设置的新值
         */
        adZonesDbCol.update(adzone, updateSetValue);

        advertDBCol.remove(new BasicDBObject("_id", new ObjectId(mongoId)));
    }

    @Override
    public void setDefaultAd(Integer defaultVal, String mongoId) {
        DBCollection adDBCol = mongoTemplate.getCollection("advertisements");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new ObjectId(mongoId));
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("is_default", defaultVal);
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        adDBCol.update(queryCondition, updateSetValue, true, true);
    }
}
