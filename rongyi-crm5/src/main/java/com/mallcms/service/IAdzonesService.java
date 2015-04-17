package com.mallcms.service;

import java.util.List;

import com.mallcms.domain.Adzones;

public interface IAdzonesService {
	public List<Adzones> getAdzonesByMallId(String id);
}
