package com.mallcms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mallcms.dao.IShopsDao;
import com.mallcms.domain.Shops;
import com.mallcms.domain.ShopsPojo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 店铺同步，读取mongoDB
 * 
 * @author rongyi11
 */
public class ShopsDaoImpl implements IShopsDao {

    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Shops> getShopsByMallId(String mallId) {
        DBCollection shops = mongoTemplate.getCollection("shops");

        DBCursor shops_dbc = shops.find(new BasicDBObject("zone_ids", new ObjectId(mallId)));
        List<Shops> shopsList = new ArrayList<Shops>();
        while (shops_dbc.hasNext()) {
            Shops shopEntry = new Shops();
            DBObject temp = shops_dbc.next();
            if (temp.get("address") != null) {
                shopEntry.setAddress(temp.get("address").toString());
            }
            shopEntry.setBrand_id(temp.get("brand_id").toString());
            if (temp.get("description") != null) {
                shopEntry.setDescription(temp.get("description").toString());
            }
            shopEntry.setMongo_id(temp.get("_id").toString());
            shopEntry.setName(temp.get("name").toString());
            shopEntry.setOn_status(true);
            if (temp.get("shop_number") != null) {
                shopEntry.setShop_number(temp.get("shop_number").toString());
            }
            if (temp.get("tags") != null) {
                shopEntry.setTags(temp.get("tags").toString());
            }
            if (temp.get("telephone") != null) {
                shopEntry.setTelephone(temp.get("telephone").toString());
            }
            if (temp.get("zone_id") != null) {
                shopEntry.setZone_id(temp.get("zone_id").toString());
            }
            shopEntry.setUpdated_at(new Date(temp.get("updated_at").toString()));
            if (temp.get("recommend") == null) {
                shopEntry.setRecommend(0);
            }
            shopsList.add(shopEntry);
        }

        return shopsList;
    }

    @Override
    public void update(ShopsPojo shop) {
        DBCollection shopDbCol = mongoTemplate.getCollection("shops");
        BasicDBObject queryCondition = new BasicDBObject();
        queryCondition.put("_id", new ObjectId(shop.getMongoId()));
        BasicDBObject updatedValue = new BasicDBObject();
        updatedValue.put("address", shop.getAddress());
        updatedValue.put("brand_id", shop.getMongoBrandId());
        updatedValue.put("comment_count", shop.getCommentCount());
        updatedValue.put("description", shop.getDescription());
        updatedValue.put("name", shop.getName());
        updatedValue.put("telephone", shop.getTelephone());
        updatedValue.put("updated_at", shop.getUpdatedAt());
        updatedValue.put("tags", shop.getTags());
        updatedValue.put("business_hours", shop.getOpenTime());
        updatedValue.put("recommend", shop.getRecommend());
        BasicDBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        shopDbCol.update(queryCondition, updateSetValue, true, true);
    }

    @Override
    public Shops getShopsById(String id) {
        DBCollection shopDbCol = mongoTemplate.getCollection("shops");

        DBObject shopOne = shopDbCol.findOne(new BasicDBObject("_id", new ObjectId(id)));
        Shops shop = new Shops();
        shop.setMongo_id(shopOne.get("_id").toString());
        shop.setAddress(shopOne.get("address").toString());
        shop.setDescription(shopOne.get("description").toString());
        shop.setBrand_id(shopOne.get("brand_id").toString());
        if (shopOne.get("shop_number") != null) {
            shop.setShop_number(shopOne.get("shop_number").toString());
        }
        shop.setName(shopOne.get("name").toString());
        if (shopOne.get("open_time") != null) {

            shop.setOpen_time(shopOne.get("open_time").toString());
        }
        // shop.setTelephone(shopOne.get("telephone").toString());
        shop.setTags(shopOne.get("tags").toString());
        return shop;
    }
}
