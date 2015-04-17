package com.rongyi.cms.web.form;

import base.page.Pagination;

public class ShopForm {
	private String categoriesId;
	
	private Byte onStatus;
	
	private String synchStatus;
	
	private String searchParams;
	
	private Integer recommend;
	
	private String oldcategoryid;
	
	private String newcategoryid;
	
	private Pagination pagination;
	
	
	

	public String getOldcategoryid() {
		return oldcategoryid;
	}

	public void setOldcategoryid(String oldcategoryid) {
		this.oldcategoryid = oldcategoryid;
	}

	public String getNewcategoryid() {
		return newcategoryid;
	}

	public void setNewcategoryid(String newcategoryid) {
		this.newcategoryid = newcategoryid;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	
	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public String getCategoriesId() {
		return categoriesId;
	}

	public void setCategoriesId(String categoriesId) {
		this.categoriesId = categoriesId;
	}


	public String getSynchStatus() {
		return synchStatus;
	}

	public void setSynchStatus(String synchStatus) {
		this.synchStatus = synchStatus;
	}

	public String getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(String searchParams) {
		this.searchParams = searchParams;
	}

	public Byte getOnStatus() {
		return onStatus;
	}

	public void setOnStatus(Byte onStatus) {
		this.onStatus = onStatus;
	}
	
	
}
