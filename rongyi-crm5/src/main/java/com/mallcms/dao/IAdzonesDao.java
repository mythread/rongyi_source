package com.mallcms.dao;

import java.util.List;

import com.mallcms.domain.Adzones;

public interface IAdzonesDao {
	public List<Adzones> getAdzonesByMallId(String id);
}
