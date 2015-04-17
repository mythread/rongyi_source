package com.rongyi.cms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.Param;
import com.rongyi.cms.mapper.ParamMapper;

@Repository
public class ParamDao {
	@Autowired
	private ParamMapper paramMapper;

	public List<Param> selectKeyBybizType(String bizType) throws Exception {
		return paramMapper.selectKeyBybizType(bizType);
	}
	
	/**
	 * 获取商铺楼层
	 * 2014.4.24.lim
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Param selectKeyBybizTypeAndServiceType(Map<String, Object> map) throws Exception {
		return paramMapper.selectKeyBybizTypeAndServiceType(map);
	}

}
