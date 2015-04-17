package com.mallcms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mallcms.dao.IAdvertisementsDao;
import com.mallcms.domain.Advertisements;
import com.mallcms.domain.AdvertisementsPojo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/**
 * 广告位 读取mongoDB
 * 
 * @author rongyi11
 */
public class AdvertisementsDaoImpl implements IAdvertisementsDao {

    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<Advertisements> getAdvertisementsByMallId(String mall_id) {

        DBCollection ad_zones = mongoTemplate.getCollection("ad_zones");

        DBCursor adzones = ad_zones.find(new BasicDBObject("zone_owner_id", new ObjectId(mall_id)));

        DBCollection advertisements = mongoTemplate.getCollection("advertisements");

        List<Advertisements> adverList = new ArrayList<Advertisements>();
        while (adzones.hasNext()) {
            DBObject ad_temp = adzones.next();
            DBCursor adver_dbc = advertisements.find(new BasicDBObject("ad_zone_ids", ad_temp.get("_id")));
            System.out.println(adver_dbc);
            while (adver_dbc.hasNext()) {
                Advertisements adverEntry = new Advertisements();
                adverEntry.setAd_zones_id(ad_temp.get("_id").toString());
                DBObject adver_temp = adver_dbc.next();
                adverEntry.setCreated_at(new Date(adver_temp.get("created_at").toString()));
                adverEntry.setEnd_time(new Date(adver_temp.get("end_time").toString()));
                adverEntry.setMall_id(mall_id);
                adverEntry.setMongo_id(adver_temp.get("_id").toString());
                adverEntry.setName(adver_temp.get("name").toString());
                adverEntry.setOn_status(true);
                adverEntry.setPicture(adver_temp.get("picture").toString());
                adverEntry.setStart_time(new Date(adver_temp.get("start_time").toString()));
                adverEntry.setUpdated_at(new Date(adver_temp.get("updated_at").toString()));
                adverEntry.setUrl(adver_temp.get("url").toString());
                adverList.add(adverEntry);
            }
        }

        return adverList;
    }

    /**
     * 广告详情数据更新添加处理
     */
    @Override
    public String insert(AdvertisementsPojo ad) {

        DBCollection adDbCol = mongoTemplate.getCollection("advertisements");
        BasicDBObject doc = new BasicDBObject();
        doc.put("created_at", ad.getCreatedAt());
        doc.put("start_time", ad.getStartTime());
        doc.put("end_time", ad.getEndTime());
        doc.put("updated_at", ad.getUpdatedAt().getTime());
        doc.put("name", ad.getName());
        doc.put("url", ad.getShopUrl());
        doc.put("picture", ad.getPicture());
        doc.put("ad_zone_ids", new Object[] { new ObjectId(ad.getAdZoneId()) });
        doc.put("on_status", ad.getOnStatus());
        if (ad.getMongodbId() == null) {// 判断时候为新增，新增则添加
            WriteResult result = adDbCol.insert(doc);
            // 更新表
            DBCollection adZonesDbCol = mongoTemplate.getCollection("ad_zones");
            DBObject adverOne = adZonesDbCol.findOne(new BasicDBObject("_id", new ObjectId(ad.getAdZoneId())));
            ArrayList<BasicDBObject> newAdvertisement = (ArrayList<BasicDBObject>) adverOne.get("advertisement_ids");
            Object[] newAdvertisementString = new Object[newAdvertisement.size() + 1];

            for (int i = 0; i < newAdvertisement.size(); i++) {
                Object object_cate = newAdvertisement.get(i);
                newAdvertisementString[i] = object_cate.toString();
            }
            newAdvertisementString[newAdvertisementString.length - 1] = doc.getString("_id");
            // newAdvertisement.add(e);
            DBObject updateCondition = new BasicDBObject();
            // where _id=id
            updateCondition.put("_id", new ObjectId(ad.getAdZoneId()));
            DBObject updatedValue = new BasicDBObject();

            updatedValue.put("advertisement_ids", newAdvertisementString);

            DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
            /**
             * updateCondition:更新条件 updateSetValue:设置的新值
             */
            adZonesDbCol.update(updateCondition, updateSetValue);
            return doc.getString("_id");
        } else {
            DBObject updateCondition = new BasicDBObject();
            // where _id=id
            updateCondition.put("_id", new ObjectId(ad.getMongodbId()));
            /**
             * updateCondition:更新条件 doc:设置的新值
             */
            DBObject updateSetValue = new BasicDBObject("$set", doc);
            adDbCol.update(updateCondition, updateSetValue);
            return ad.getMongodbId().toString();
        }
    }

    @Override
    public Advertisements getAdvertisementsById(String id) {
        DBCollection adverDbCol = mongoTemplate.getCollection("advertisements");
        String url = "http://rongyi.b0.upaiyun.com/system/advertisement/picture/";
        DBObject adverOne = adverDbCol.findOne(new BasicDBObject("_id", new ObjectId(id)));

        Advertisements adver = new Advertisements();
        if (adverOne != null) {
            List AdZoneIdList = (List) adverOne.get("ad_zone_ids");
            adver.setAd_zones_id(AdZoneIdList.get(0).toString());
            adver.setName(adverOne.get("name").toString());
            adver.setPicture(url + id + "/" + adverOne.get("picture").toString());
            if (adverOne.get("minPicture") != null) {
                adver.setPicture(url + id + "/" + adverOne.get("minPicture").toString());
            }
            adver.setStart_time(new Date(adverOne.get("start_time").toString()));
            adver.setEnd_time(new Date(adverOne.get("end_time").toString()));
            adver.setUrl(adverOne.get("url").toString());
        }
        return adver;
    }

    @Override
    public String getAdzoneById(String id) {
        DBCollection adverDbCol = mongoTemplate.getCollection("ad_zones");
        DBObject adverOne = adverDbCol.findOne(new BasicDBObject("_id", new ObjectId(id)));

        return adverOne.get("name").toString();
    }

}
