package com.rongyi.cms.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.CateLinkBrands;
import com.rongyi.cms.mapper.CateLinkBrandsMapper;

@Repository
public class CateLinkBrandsDao {
	
	@Autowired
	private CateLinkBrandsMapper cateLinkBrandsMapper;
	
	public  int insert(CateLinkBrands record)  throws Exception{
		return cateLinkBrandsMapper.insert(record);
	}
		
}
