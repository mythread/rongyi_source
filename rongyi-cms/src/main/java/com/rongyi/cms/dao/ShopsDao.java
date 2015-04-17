package com.rongyi.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.Shops;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.mapper.ShopsMapper;

@Repository
public class ShopsDao {

    private Logger      logger = Logger.getLogger(this.getClass());
    @Autowired
    private ShopsMapper shopsMapper;

    /**
     * 查询商家
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> queryLikeName(String name) throws Exception {
        return shopsMapper.queryLikeName(name);
    }

    public List<Shops> searchShopsByParam(Shops shops) throws Exception {
        return shopsMapper.listPageSearchShopsByParam(shops);
    }

    public int updateByPrimaryKeySelective(Shops shop) {
        return shopsMapper.updateByPrimaryKeySelective(shop);
    }

    public int save(Shops shops) throws Exception {
        if(shops.getId() == null) {
        	shopsMapper.insert(shops);
        }else {
        	update(shops);
        }
        return shops.getId() != null ? shops.getId() : 0;
    }

    public Shops get(Integer id) throws Exception {
        return shopsMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新记录 2014.4.9 LIM
     * 
     * @param shops
     * @throws Exception
     */
    public int update(Shops shops) throws Exception {
        return shopsMapper.updateByPrimaryKeySelective(shops);
    }

    public Map<String, Integer> groupByShopsBySynchStatus() throws Exception {
        List<Map<String, Object>> maplist = shopsMapper.groupByShopsBySynchStatus();
        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        if (maplist.size() > 0) {
            for (Map<String, Object> map : maplist) {
                String key = map.get("ss").toString();
                Integer value = Integer.valueOf(map.get("counts").toString());
                resultMap.put(key, value);
            }
        }
        return resultMap;
    }

    public List<Shops> searchShopsBySynchStatus(Shops shops) throws Exception {
        return shopsMapper.listPageSearchShopsBySynchStatus(shops);
    }

    /**
     * 更新PID 2014.4.9 LIM
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public int updatePid(int id, int pid) throws Exception {
        Shops record = new Shops();
        record.setId(id);
        record.setPid(pid);
        return shopsMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 通过PID查找 2014.4.9 lim
     * 
     * @param pid
     * @return
     */
    public Shops selectByPid(Integer pid) {
        return shopsMapper.selectByPid(pid);
    }

    /**
     * 当商家审核通过之后的处理， 更新原来的记录，若原来的记录的PID大于0（有之前的记录），则要把id=pid的记录删除掉 2014.4.21.lim
     * 
     * @param map
     * @throws Exception
     */
    public boolean verifyOK(Map<String, Object> map) throws Exception {
        String outerId = (String) map.get("cmsOuterId");
        Integer reviewStatus = (Integer) map.get("reviewStatus");
        String mongodbId = (String) map.get("mongodbId");
        String memo = (String) map.get("memo");
        Shops tmp = get(Integer.valueOf(outerId));
        int i = 0;
        if (tmp != null) {
            // 判断商家的几种情况：
            logger.info("id>>>" + outerId + ";pid>>>" + tmp.getPid() + ";recordType>>>" + tmp.getRecordType());
            if (Constant.SynchState.SYNCHRONOUS_END == reviewStatus) {
            	// 商家
                Shops shop = new Shops();
                shop.setId(Integer.valueOf(outerId));
                shop.setSynchStatus(reviewStatus);
                shop.setSynchMsg(memo);
                shop.setMongoId(mongodbId);
                shop.setPid(-1);
                shop.setRecordType(0);
                i = shopsMapper.updateByPrimaryKeySelective(shop);
                if (tmp.getPid() != null && tmp.getPid().intValue() > 0) {
                    Shops p = new Shops();
                    p.setId(tmp.getPid());
                    p.setDeleteStatus((byte) Constant.Common.YES);
                    // 删除掉之前的那条当前内容
                    shopsMapper.updateByPrimaryKeySelective(p);
                }
            }else if (Constant.SynchState.SYNCHRONOUS_FAIL == reviewStatus) {
            	Shops shop = new Shops();
                shop.setId(Integer.valueOf(outerId));
                shop.setSynchStatus(reviewStatus);
                shop.setSynchMsg(memo);
                i = shopsMapper.updateByPrimaryKeySelective(shop);
            }
            
        }
        return i > 0;
    }
    /**
     * 2014.4.28 limin 
     * mongoid查询商家
     * @param mongoId
     * @return
     * @throws Exception
     */
    public Shops selectByMongoId(String mongoId) throws Exception {
    	return shopsMapper.selectByMongoId(mongoId);
    }
}
