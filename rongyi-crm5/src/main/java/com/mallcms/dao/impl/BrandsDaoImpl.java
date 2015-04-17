package com.mallcms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mallcms.dao.IBrandsDao;
import com.mallcms.domain.Brands;
import com.mallcms.domain.BrandsPojo;
import com.mallcms.domain.Shops;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/**
 * 品牌同步
 * 
 * @author rongyi11
 */
public class BrandsDaoImpl implements IBrandsDao {

    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<Brands> getBrandsByMallId(String mallId) {
        DBCollection brands = mongoTemplate.getCollection("brands");

        DBCursor brands_dbc = brands.find();

        // int temp1=brands.find().count();
        // System.out.println(temp1);

        List<Brands> brandsList = new ArrayList<Brands>();
        while (brands_dbc.hasNext()) {
            Brands brand = new Brands();
            DBObject temp = brands_dbc.next();
            if (temp.get("cname") != null) {
                brand.setCname(temp.get("cname").toString());
            }
            if (temp.get("description") != null) {
                brand.setDescription(temp.get("description").toString());
            }
            if (temp.get("ename") != null) {
                brand.setEname(temp.get("ename").toString());
            }
            if (temp.get("name") != null) {
                brand.setName(temp.get("name").toString());
            }
            if (temp.get("tag") != null) {
                brand.setTag(temp.get("tag").toString());
            }
            if (temp.get("telephone") != null) {
                brand.setTelephone(temp.get("telephone").toString());
            }
            if (temp.get("icon") != null) {
                brand.setIcon(temp.get("icon").toString());
            }
            brand.setMongo_id(temp.get("_id").toString());
            brand.setUpdated_at(new Date(temp.get("updated_at").toString()));
            if (temp.get("category_ids") != null) {
                @SuppressWarnings("unchecked")
                ArrayList<BasicDBObject> versi = (ArrayList<BasicDBObject>) temp.get("category_ids");
                // System.out.println(versi.get(0));
                brand.setCategories(versi);
            }
            brandsList.add(brand);
        }

        return brandsList;
    }

    @Override
    public String insert(BrandsPojo pojo) {
        DBCollection brandsDBCol = mongoTemplate.getCollection("brands");
        BasicDBObject obj = new BasicDBObject();
        obj.put("name", pojo.getName());
        obj.put("tags", pojo.getTags());
        obj.put("category_ids", pojo.getCategoryIds().split(","));
        obj.put("updated_at", new Date());
        WriteResult result = brandsDBCol.insert(obj);
        return obj.getString("_id");
    }

	@Override
	public Brands getBrandsByBrandId(String brandId) {
		DBCollection shopDbCol = mongoTemplate.getCollection("brands");

        DBObject brandsOne = shopDbCol.findOne(new BasicDBObject("_id", new ObjectId(brandId)));
        Brands bradns = new Brands();
        if(brandsOne!=null){
        if(brandsOne.get("icon")!=null){
        	bradns.setIcon(brandsOne.get("icon").toString());
        }
        if(brandsOne.get("name")!=null){
        	bradns.setName(brandsOne.get("name").toString());
        }
        if(brandsOne.get("tags")!=null){
        	bradns.setTag(brandsOne.get("tags").toString());
        }
        
        }
        return bradns;
	}
}
