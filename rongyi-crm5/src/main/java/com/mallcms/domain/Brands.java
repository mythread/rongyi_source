package com.mallcms.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;

public class Brands {
	private String mongo_id; //品牌广告位id
	private String name; //名称
	private Date updated_at; 
	private String cname; //中文名
	private String ename; //英文名
	private String description; //描述信息
	private String icon; //图标
	private String tag; //标签
	private String telephone; //电话
	private List<BasicDBObject> categories=new ArrayList<BasicDBObject>();
	
	public List<BasicDBObject> getCategories() {
		return categories;
	}
	public void setCategories(List<BasicDBObject> categories) {
		this.categories = categories;
	}
	public String getMongo_id() {
		return mongo_id;
	}
	public void setMongo_id(String mongo_id) {
		this.mongo_id = mongo_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
