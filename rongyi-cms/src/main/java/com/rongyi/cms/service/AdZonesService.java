package com.rongyi.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.AdZones;
import com.rongyi.cms.dao.AdZonesDao;

@Service
@Transactional
public class AdZonesService {

	@Autowired
	private AdZonesDao adZonesDao;

	public void save(AdZones adZones) throws Exception{
		adZonesDao.save(adZones);
	}
	
	public AdZones get(String id) throws Exception {
		return adZonesDao.get(id);
	}
	
	public List<AdZones> getAll() throws Exception{
		return adZonesDao.getAll();
	}
	
}
