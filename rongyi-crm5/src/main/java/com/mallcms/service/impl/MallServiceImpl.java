package com.mallcms.service.impl;

import java.util.List;

import com.mallcms.dao.IMallDao;
import com.mallcms.domain.Floor;
import com.mallcms.domain.Mall;
import com.mallcms.service.IMallService;

public class MallServiceImpl implements IMallService {
	private IMallDao mallDaoImpl;

	@Override
	public Mall getMallById(String id) {
		return mallDaoImpl.getMallById(id);
	}
	
	@Override
	public List<Floor> getFloorByMallId(String id) {
		return mallDaoImpl.getFloorByMallId(id);
	}

	public IMallDao getMallDaoImpl() {
		return mallDaoImpl;
	}

	public void setMallDaoImpl(IMallDao mallDaoImpl) {
		this.mallDaoImpl = mallDaoImpl;
	}
	
	

}
