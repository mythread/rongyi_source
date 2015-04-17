package com.mallcms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mallcms.dao.IPhotosDao;
import com.mallcms.domain.Photo;
import com.mallcms.domain.PhotosPojo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PhotoDaoImpl implements IPhotosDao {

    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<Photo> getPhotosByMallId(String mallId) {
        DBCollection shops = mongoTemplate.getCollection("shops");

        DBCursor shops_dbc = shops.find(new BasicDBObject("zone_ids", new ObjectId(mallId)));

        DBCollection photos = mongoTemplate.getCollection("photos");
        List<Photo> photosList = new ArrayList<Photo>();

        while (shops_dbc.hasNext()) {
            DBObject shops_temp = shops_dbc.next();
            DBCursor photos_dbc = photos.find(new BasicDBObject("owner_id", shops_temp.get("_id")));
            while (photos_dbc.hasNext()) {
                DBObject photos_temp = photos_dbc.next();
                Photo photoEntry = new Photo();
                photoEntry.setCreated_at(new Date(photos_temp.get("created_at").toString()));
                photoEntry.setFile(photos_temp.get("file").toString());
                photoEntry.setMongo_id(photos_temp.get("_id").toString());
                photoEntry.setOwner_type(photos_temp.get("owner_type").toString());
                photoEntry.setOwner_mongo_id(photos_temp.get("owner_id").toString());
                photoEntry.setPosition(photos_temp.get("position").toString());
                photoEntry.setUpdated_at(new Date(photos_temp.get("updated_at").toString()));
                photosList.add(photoEntry);
            }
        }
        return photosList;
    }

    @Override
    public String update(PhotosPojo photo) {
        DBCollection photosDbCol = mongoTemplate.getCollection("photos");
        BasicDBObject baseValue = new BasicDBObject();

        baseValue.put("owner_type", "Mall");
        baseValue.put("file", photo.getFile());
        baseValue.put("position", photo.getPosition());
        baseValue.put("created_at", new Date());
        baseValue.put("updated_at", new Date());

        if (StringUtils.isEmpty(photo.getMongoId())) {
            // 新增图片
            photosDbCol.insert(baseValue);
            return baseValue.getString("_id");
        } else {
            // 更新图片
            BasicDBObject queryCondition = new BasicDBObject();
            queryCondition.put("_id", new ObjectId(photo.getMongoId()));
            BasicDBObject updateSetValue = new BasicDBObject("$set", baseValue);
            photosDbCol.update(queryCondition, updateSetValue, true, true);
            return photo.getMongoId();
        }

    }

    public List<Photo> getPhotoByOwnId(String ownId) {
        String file_url = "http://rongyi.b0.upaiyun.com/system/photo/file/";
        List<Photo> photosList = new ArrayList<Photo>();
        DBCollection photos = mongoTemplate.getCollection("photos");
        DBCursor photos_dbc = photos.find(new BasicDBObject("owner_id", new ObjectId(ownId)));
        while (photos_dbc.hasNext()) {
            DBObject photos_temp = photos_dbc.next();
            Photo photoEntry = new Photo();
            photoEntry.setMongo_id(photos_temp.get("_id").toString());
            photoEntry.setCreated_at(new Date(photos_temp.get("created_at").toString()));
            photoEntry.setFile(file_url + (photoEntry.getCreated_at().getYear() + 1900) + "/"
                               + (photoEntry.getCreated_at().getMonth() + 1) + "/" + photoEntry.getMongo_id() + "/"
                               + photos_temp.get("file").toString());
            photoEntry.setMongo_id(photos_temp.get("_id").toString());
            photoEntry.setOwner_type(photos_temp.get("owner_type").toString());
            photoEntry.setOwner_mongo_id(photos_temp.get("owner_id").toString());
            photoEntry.setPosition(photos_temp.get("position").toString());
            photoEntry.setUpdated_at(new Date(photos_temp.get("updated_at").toString()));
            photosList.add(photoEntry);
        }

        return photosList;
    }
}
