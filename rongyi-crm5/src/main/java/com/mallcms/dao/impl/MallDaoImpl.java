package com.mallcms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mallcms.dao.IMallDao;
import com.mallcms.domain.Floor;
import com.mallcms.domain.Mall;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 商城数据同步，读取mongodb
 * @author rongyi11
 *
 */
public class MallDaoImpl implements IMallDao {

    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mall getMallById(String id) {
        // TODO Auto-generated method stub
        DBCollection zones = mongoTemplate.getCollection("zones");

        DBObject mallOne = zones.findOne(new BasicDBObject("_id", new ObjectId(id)));

        Mall mall = new Mall();
        mall.setId(id);
        mall.setName(mallOne.get("name").toString());
        // System.out.println(mallOne.get("name").toString());
        return mall;
    }
    
    /**
     * 楼层初始化
     */
	@Override
	public List<Floor> getFloorByMallId(String id) {
		 DBCollection zones = mongoTemplate.getCollection("zones");
		 DBCursor floorColl=zones.find(new BasicDBObject("parent_id", new ObjectId(id)));
			List<Floor> floorsList=new ArrayList<Floor>();
			while (floorColl.hasNext()) {
				Floor floor=new Floor();
				DBObject tempFloor= floorColl.next();
				floor.setId(tempFloor.get("_id").toString());
				floor.setName(tempFloor.get("name").toString());
				floorsList.add(floor);
		    }
		return floorsList;
	}
}
