package com.rongyi.cms.mapper;

import java.util.List;

import com.rongyi.cms.bean.AdZones;

public interface AdZonesMapper {
    int deleteByPrimaryKey(String id);

    int insert(AdZones record);

    int insertSelective(AdZones record);

    AdZones selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AdZones record);

    int updateByPrimaryKey(AdZones record);
    
    List<AdZones> selectAll();
}