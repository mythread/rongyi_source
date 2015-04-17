package com.rongyi.cms.mapper;

import java.util.List;
import java.util.Map;

import com.rongyi.cms.bean.Advertisements;

public interface AdvertisementsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisements record);

    int insertSelective(Advertisements record);

    Advertisements selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advertisements record);

    int updateByPrimaryKey(Advertisements record);
    
    List<Advertisements> selectAdListByAdZoneId(Advertisements record);
    
    List<Advertisements> queryIncludeDate(Map<String,Object> map);
    
    List<Map<String,Object>> groupSynchStatusByAdZoneId(String adZoneId);
    
    Advertisements selectByPid(Integer pid);
    
    Advertisements selectByDefaultPic(Integer defaultPic);
    /**
     * 将MAP里面包括的ID外的广告,都取消掉默认字段 =0
     * @param map
     * @return
     */
    int setDefaultAd(Map<String,Object> map);
    
    List<Advertisements> selectOnOffStatusAdListByMap(Map<String,Object> map);

    List<Advertisements> selectAdListByMap(Map<String,Object> map);
    /**
     * 更新PID
     * @param record
     * @return
     */
    int updatePidById(Advertisements record);
}