package com.mallcms.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.document.mongodb.MongoTemplate;

import com.mallcms.dao.ICategoriesDao;
import com.mallcms.domain.Categories;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 *分类同步 读取mongoDB 
 * @author rongyi11
 *
 */
public class CategoriesDaoImpl implements ICategoriesDao {
	private MongoTemplate mongoTemplate;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Categories> getCategoryByMallId(String mallId) {

		DBCollection category = mongoTemplate.getCollection("categories");

		DBCursor categories = category.find(new BasicDBObject("parent_ids",
				new ObjectId("51f9da1731d65584ab001f0f")));

		List<Categories> cateList = new ArrayList<Categories>();
		while (categories.hasNext()) {
			Categories cateEntry = new Categories();
			DBObject temp = categories.next();
			cateEntry.setApp_show(temp.get("app_show").toString()
					.equalsIgnoreCase("true") ? true : false);
			cateEntry.setId(temp.get("_id").toString());
			cateEntry.setName(temp.get("name").toString());
			if (temp.get("old_code") != null) {
				cateEntry.setOld_code(temp.get("old_code").toString());
			}

			if (temp.get("old_id") != null) {

				cateEntry.setOld_id(temp.get("old_id").toString());
			}
			cateEntry.setParent_id(temp.get("parent_id").toString());
			if (temp.get("position") != null) {
				cateEntry.setPosition(Integer.parseInt(temp.get("position")
						.toString()));
			}
			cateEntry.setUpdated_at(new Date(temp.get("updated_at").toString()));
			cateList.add(cateEntry);
			//System.out.println(temp.toString());
		}
		return cateList;
	}
}
