package com.mallcms.dao;

import java.util.List;

import com.mallcms.domain.Floor;
import com.mallcms.domain.Mall;

public interface IMallDao {
	public Mall getMallById(String id);
	public List<Floor> getFloorByMallId(String id);
}
