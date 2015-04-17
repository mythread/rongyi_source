package com.rongyi.mina.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.Advertisements;
import com.rongyi.mina.mapper.AdvertisementsMapper;

/**
 * @author jiejie 2014年4月5日 下午9:25:22
 */
@Repository
public class AdvertisementsDao {
	private static final Logger LOG  = LoggerFactory.getLogger(AdvertisementsDao.class);
    @Autowired
    private AdvertisementsMapper advertMapper;

    /**
     * 如果当前有一条待审核的记录,则只更新数据,否则插入新记录;
     * @param advert
     * @return
     */
    public Integer insert(Advertisements advert) {
    	LOG.info("advert.getName()>>>"+advert.getName());
        return advertMapper.insert(advert);
    }
    
    /**
     * 更新数据
     * @param record
     * @return
     * @throws Exception
     */
    public int update(Advertisements record) throws Exception {
    	return advertMapper.updateByPrimaryKeySelective(record);
	}

    /**
     * 查询是否有待处理的广告
     * 2014.4.23.lim
     * @param mallId
     * @param cmsOuterId
     * @return
     * @throws Exception
     */
    public Advertisements selectByParam(String mallId, String cmsOuterId) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("mallId", mallId);
    	map.put("cmsOuterId", cmsOuterId);
    	return advertMapper.selectByParam(map);
	}
}
