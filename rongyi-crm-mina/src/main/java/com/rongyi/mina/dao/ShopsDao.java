package com.rongyi.mina.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.Shops;
import com.rongyi.mina.mapper.ShopsMapper;

/**
 * 类ShopsDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月5日 下午9:33:58
 */
@Repository
public class ShopsDao {

    @Autowired
    private ShopsMapper shopsMapper;

    public Integer insert(Shops shops) {
        return shopsMapper.insert(shops);
    }
    
    /**
     * 更新数据
     * @param record
     * @return
     * @throws Exception
     */
    public int update(Shops record) throws Exception {
    	return shopsMapper.updateByPrimaryKeySelective(record);
	}
    
    /**
     * 查询是否有待处理的商铺
     * 2014.4.23.lim
     * @param mallId
     * @param cmsOuterId
     * @return
     * @throws Exception
     */
    public Shops selectByParam(String mallId, String cmsOuterId) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("mallId", mallId);
    	map.put("cmsOuterId", cmsOuterId);
    	return shopsMapper.selectByParam(map);
	}
}
