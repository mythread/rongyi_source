package com.rongyi.cms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.AdZones;
import com.rongyi.cms.mapper.AdZonesMapper;

@Repository
public class AdZonesDao {

	@Autowired
	private AdZonesMapper adZonesMapper;

	public void save(AdZones adZones) throws Exception {
		adZonesMapper.insert(adZones);
	}
	
	public AdZones get(String id) throws Exception {
		return adZonesMapper.selectByPrimaryKey(id);
	}
	
	public List<AdZones> getAll() throws Exception{
		return adZonesMapper.selectAll();
	}
}
