package com.mallcms.service.impl;

import java.util.List;

import com.mallcms.dao.IAdzonesDao;
import com.mallcms.domain.Adzones;
import com.mallcms.service.IAdzonesService;

public class AdzonesServiceImpl implements IAdzonesService {
	private IAdzonesDao adzonesDaoImpl;

	public IAdzonesDao getAdzonesDaoImpl() {
		return adzonesDaoImpl;
	}

	public void setAdzonesDaoImpl(IAdzonesDao adzonesDaoImpl) {
		this.adzonesDaoImpl = adzonesDaoImpl;
	}

	@Override
	public List<Adzones> getAdzonesByMallId(String id) {
		return adzonesDaoImpl.getAdzonesByMallId(id);
	}

	
	
}
