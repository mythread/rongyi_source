package com.rongyi.cms.mapper;

import java.util.List;
import java.util.Map;

import com.rongyi.cms.bean.Shops;

public interface ShopsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shops record);

    int insertSelective(Shops record);

    Shops selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shops record);

    int updateByPrimaryKeyWithBLOBs(Shops record);

    int updateByPrimaryKey(Shops record);
    
    List<Map<String, Object>> queryLikeName(String name);

   	List<Shops> listPageSearchShopsByParam(Shops shops);
   	
   	List<Map<String,Object>> groupByShopsBySynchStatus();
	
	Shops selectByPid(Integer pid);
   	
   	List<Shops> listPageSearchShopsBySynchStatus(Shops shops);
   	
   	Shops selectByMongoId(String mongoId);
}