package com.mallcms.domain;

import java.util.Date;

public class Photo {
	private String mongo_id;
	private String file;
	private String owner_type;
	private String owner_mongo_id;
	private String position;
	private Date created_at;
	private Date updated_at;

	public String getMongo_id() {
		return mongo_id;
	}

	public void setMongo_id(String mongo_id) {
		this.mongo_id = mongo_id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getOwner_mongo_id() {
		return owner_mongo_id;
	}

	public void setOwner_mongo_id(String owner_mongo_id) {
		this.owner_mongo_id = owner_mongo_id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public String getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}

}
