package com.rongyi.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.Advertisements;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.mapper.AdvertisementsMapper;

@Repository
public class AdvertisementsDao {

    private Logger               logger = Logger.getLogger(this.getClass());
    @Autowired
    private AdvertisementsMapper advertisementsMapper;

    public int save(Advertisements advertisements) throws Exception {
        if (advertisements.getId() == null) {
            advertisementsMapper.insert(advertisements);
        } else {
            updateByPrimaryKeySelective(advertisements);
        }
        // System.out.println("adId>>>>>"+advertisements.getId());
        return advertisements.getId() != null ? advertisements.getId() : 0;
    }

    public Advertisements get(int id) throws Exception {
        return advertisementsMapper.selectByPrimaryKey(id);
    }

    public List<Advertisements> selectByAdZoneId(Advertisements record) throws Exception {
        return advertisementsMapper.selectAdListByAdZoneId(record);
    }

    public Map<String, Integer> groupSynchStatusByAdZoneId(String adZoneId) {
        List<Map<String, Object>> maplist = advertisementsMapper.groupSynchStatusByAdZoneId(adZoneId);
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

    /**
     * 查询包括开始时间，结束时间的记录，若结束是0，则说明这段时间内还没有广告时间，可以插入表 2014.4.3 lim
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<Advertisements> queryIncludeDate(String startTime, String endTime, int id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        if (id > 0) {
            map.put("id", id);
        }
        return advertisementsMapper.queryIncludeDate(map);
    }

    /**
     * 更新PID 2014.4.4 LIM
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public int updatePid(int id, int pid) throws Exception {
        Advertisements record = new Advertisements();
        record.setId(id);
        record.setPid(pid);
        return advertisementsMapper.updatePidById(record);
    }

    public int updateByPrimaryKeySelective(Advertisements ad) {
        return advertisementsMapper.updateByPrimaryKeySelective(ad);
    }

    /**
     * 查询包括开始时间，结束时间的记录，若结束是0，则说明这段时间内还没有广告时间，可以插入表 2014.4.3 lim
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<Advertisements> queryIncludeDate(String startTime, String endTime, int id, int pid, String adZoneId)
                                                                                                                    throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        if (id > 0) {
            map.put("id", id);
        }
        if (pid > 0) {
            map.put("pid", pid);
        }
        map.put("adZoneId", adZoneId);
        return advertisementsMapper.queryIncludeDate(map);
    }

    /**
     * 更新记录 2014.4.4 LIM
     * 
     * @param advertisements
     * @throws Exception
     */
    public int update(Advertisements advertisements) throws Exception {
        return advertisementsMapper.updateByPrimaryKey(advertisements);
    }

    /**
     * 通过PID查找 2014.4.8 lim
     * 
     * @param pid
     * @return
     */
    public Advertisements selectByPid(Integer pid) {
        return advertisementsMapper.selectByPid(pid);
    }

    /**
     * 通过defaultPic查找
     */
    public Advertisements selectByDefaultPic(String adZoneId, Integer defaultPic) {
        return advertisementsMapper.selectByDefaultPic(defaultPic);
    }

    /**
     * 将ad_zone_id广告位下面的除ID外的都把默认字段设置为0
     * 
     * @param map
     * @return
     */
    public int setDefaultAd(int id, String ad_zone_id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("ad_zone_id", ad_zone_id);
        return advertisementsMapper.setDefaultAd(map);
    }

    /**
     * 查询加了时间段 2014.4.17.lim
     * 
     * @param adZoneId
     * @param synchStatus
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<Advertisements> selectAdListByMap(String adZoneId, Integer synchStatus, String startTime, String endTime)
                                                                                                                         throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("adZoneId", adZoneId);
        map.put("synchStatus", synchStatus);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return advertisementsMapper.selectAdListByMap(map);
    }

    /**
     * 当广告审核通过之后的处理， 更新原来的记录，若原来的记录的PID大于0（有之前的记录），则要把id=pid的记录删除掉 2014.4.21.lim
     * 
     * @param map
     * @throws Exception
     */
    public boolean verifyOK(Map<String, Object> map) throws Exception {
        String outerId = (String) map.get("cmsOuterId");
        Integer reviewStatus = (Integer) map.get("reviewStatus");
        String mongodbId = (String) map.get("mongodbId");
        String memo = (String) map.get("memo");
        String picUrl = (String) map.get("imgUrl");
        String minPicUrl = (String) map.get("minImgUrl");
        Advertisements tmp = get(Integer.valueOf(outerId));
        int i = 0;
        if (tmp != null) {
            // 判断广告的几种情况：
            logger.info("id>>>" + outerId + ";pid>>>" + tmp.getPid() + ";recordType>>>" + tmp.getRecordType());
            if(Constant.SynchState.SYNCHRONOUS_END == reviewStatus) {//同步完成,同步成功
            	// 广告
                Advertisements ad = new Advertisements();
                ad.setId(Integer.valueOf(outerId));
                ad.setSynchStatus(reviewStatus);
                ad.setSynchMsg(memo);
                ad.setMongodbId(mongodbId);
                ad.setPid(-1);
                ad.setRecordType((byte) 0);
                ad.setPictureUrl(picUrl);
                ad.setMinPictureUrl(minPicUrl);
                i = advertisementsMapper.updateByPrimaryKeySelective(ad);
                if (tmp.getPid() != null && tmp.getPid().intValue() > 0) {
                    Advertisements p = new Advertisements();
                    p.setId(tmp.getPid());
                    p.setDeleteStatus((byte) Constant.Common.YES);
                    // 删除掉之前的那条当前内容
                    advertisementsMapper.updateByPrimaryKeySelective(p);
                }
            }else if(Constant.SynchState.SYNCHRONOUS_FAIL == reviewStatus) {//同步失败
            	 Advertisements ad = new Advertisements();
                 ad.setId(Integer.valueOf(outerId));
                 ad.setSynchStatus(reviewStatus);
                 ad.setSynchMsg(memo);
                 i = advertisementsMapper.updateByPrimaryKeySelective(ad);
            }
        }
        return i > 0;
    }

    
	public List<Advertisements> selectOnOffStatusAdListByMap(String adZoneId,
			Integer onStatus, String startTime, String endTime)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adZoneId", adZoneId);
		map.put("onStatus", onStatus);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return advertisementsMapper.selectOnOffStatusAdListByMap(map);
	}
	
}
