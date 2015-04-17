package com.rongyi.cms.bean;

import com.rongyi.cms.interfaces.PageInterface;

import base.page.Pagination;

public class BaseBean implements PageInterface {
	public Pagination pagination;
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}


	

}
