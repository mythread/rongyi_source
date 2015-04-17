package com.rongyi.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.CateLinkBrands;
import com.rongyi.cms.dao.CateLinkBrandsDao;

@Service
@Transactional
public class CateLinkBrandsService {
	@Autowired
	private CateLinkBrandsDao cateLinkBrandsDao;
	
	public  int insert(CateLinkBrands record)  throws Exception{
		return cateLinkBrandsDao.insert(record);		
	}
}
