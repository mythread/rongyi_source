package com.rongyi.mina.mapper;

import java.util.Map;

import com.rongyi.mina.bean.Advertisements;

/**
 * 类AdvertisementsMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月4日 下午6:28:19
 */
public interface AdvertisementsMapper {

    Integer insert(Advertisements advertisements);
    
    int updateByPrimaryKeySelective(Advertisements record);
    
    Advertisements selectByParam(Map<String,Object> map);
}
