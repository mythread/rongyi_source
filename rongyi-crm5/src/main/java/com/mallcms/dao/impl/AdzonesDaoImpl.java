package com.mallcms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mallcms.dao.IAdzonesDao;
import com.mallcms.domain.Adzones;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 广告位同步，读取mongoDB
 * @author rongyi11
 *
 */
public class AdzonesDaoImpl implements IAdzonesDao {
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Adzones> getAdzonesByMallId(String id) {
		DBCollection  ad_zones=mongoTemplate.getCollection("ad_zones");
		
		DBCursor adzones=ad_zones.find(new BasicDBObject("zone_owner_id", new ObjectId(id)));
		List<Adzones> adzonesList=new ArrayList<Adzones>();
				
		while (adzones.hasNext()) {
			Adzones adzone=new Adzones();
			DBObject temp= adzones.next();
			adzone.setId(temp.get("_id").toString());
			adzone.setName(temp.get("name").toString());
			adzone.setAdvertisement_ids(temp.get("advertisement_ids").toString());
			adzone.setDescription(temp.get("description").toString());
			adzone.setPosition("1");
			adzone.setUpdated_at(new Date(temp.get("updated_at").toString()));
			adzone.setZone_owner_id(id);
			adzone.setZone_owner_type(temp.get("zone_owner_type").toString());
			adzone.setCreated_at(new Date(temp.get("created_at").toString()));
			if(temp.get("holder")!=null){
				if(temp.get("holder").toString().equalsIgnoreCase("0")){
					adzone.setOwner_type(false);
				}
			}else{
				adzone.setOwner_type(true);
			}
			adzonesList.add(adzone);
	    }
		
				
		return adzonesList;
	}
}
