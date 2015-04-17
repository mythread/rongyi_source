package com.rongyi.cms.mapper;

import java.util.List;
import java.util.Map;

import com.rongyi.cms.bean.Param;

public interface ParamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Param record);

    int insertSelective(Param record);

    Param selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Param record);

    int updateByPrimaryKey(Param record);
    
    List<Param> selectKeyBybizType(String bizType);
    
    Param selectKeyBybizTypeAndServiceType(Map<String, Object> map);
}