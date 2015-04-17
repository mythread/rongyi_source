package com.rongyi.cms.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.Advertisements;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.dao.AdvertisementsDao;

@Service
@Transactional
public class AdvertisementsService {

    @Autowired
    private AdvertisementsDao advertisementsDao;

    public AdvertisementsDao getDao() {
        return advertisementsDao;
    }

    public Advertisements get(int id) throws Exception {
        return advertisementsDao.get(id);
    }

    public List<Advertisements> selectByAdZoneId(Advertisements record) throws Exception {
        return advertisementsDao.selectByAdZoneId(record);
    }

    public Map<String, Integer> getSynchStatusByAdZoneId(String adZoneId) {
        Map<String, Integer> map = advertisementsDao.groupSynchStatusByAdZoneId(adZoneId);
        if (map != null && map.size() > 0) {
            for (int i = 1; i <= 3; i++) {
                if (!map.containsKey(i + "")) {
                    map.put(i + "", 0);
                }
            }
        } else {
            map.put("1", 0);
            map.put("2", 0);
            map.put("3", 0);
        }
        return map;
    }

    public boolean updateByPrimaryKeySelective(Advertisements ad) {
        if (ad == null) {
            return false;
        }
        return advertisementsDao.updateByPrimaryKeySelective(ad) > 0;
    }

    /**
     * 查询包括开始时间，结束时间的记录，若结束是0，则说明这段时间内还没有广告时间，可以插入表��
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<Advertisements> queryIncludeDate(String startTime, String endTime, int id) throws Exception {
        return getDao().queryIncludeDate(startTime, endTime, id);
    }

    /**
     * 删除广告投放
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public void delete(int id) {

        try {
            Advertisements ad = get(id);
            ad.setDeleteStatus((byte) 1);
            advertisementsDao.update(ad);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除广告投放
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public void openOrClose(int id) {

        try {
            Advertisements ad = get(id);
            Byte onStatus = ad.getOnStatus();
            if (onStatus == 1) {
                ad.setOnStatus((byte) 0);
            }
            if (onStatus == 0) {
                ad.setOnStatus((byte) 1);
            }
            advertisementsDao.update(ad);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 1.新增 2.修改 a:修改草记录（只有一条草记录），b:修改已审核通过的记录（只有一条审核通过的记录），c:修改有审核通过记录的草记录 2014.4.4 limin
     * 
     * @param advertisements
     * @throws Exception
     */
    public int save(Advertisements advertisements) throws Exception {
        int id = 0;
        if (advertisements.getId() != null && advertisements.getId().intValue() > 0) {// 修改
            id = advertisements.getId();
            if (advertisements.getPid() == -1 && advertisements.getRecordType() == 1) {// 修改草记录（只有一条草记录��
                // 只修改原来的记录，直接修��
                Date d = new Date();
                advertisements.setUpdatedAt(d);
                advertisements.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);// 同步中
                getDao().update(advertisements);
                id = advertisements.getId();
            } else if (advertisements.getPid() == -1 && advertisements.getRecordType() == 0) {// 修改已审核通过的记录（只有一条审核通过的记录）
                // 原来的记录pid=0 新加一条修改的草记��
                int adId = advertisements.getId();// 老记录ID
                // 用老记录ID去表里查一下是否有记录存在，防止意外情况出现
                Advertisements newAd = advertisementsDao.selectByPid(adId);
                if (newAd == null) {
                    newAd = new Advertisements();
                }
                newAd.setAdZoneId(advertisements.getAdZoneId());
                newAd.setEndTime(advertisements.getEndTime());
                newAd.setMinPicture(advertisements.getMinPicture());
                newAd.setMongodbId(advertisements.getMongodbId());
                newAd.setName(advertisements.getName());
                newAd.setPicture(advertisements.getPicture());
                newAd.setShopUrl(advertisements.getShopUrl());
                newAd.setStartTime(advertisements.getStartTime());
                newAd.setDeleteStatus(Constant.DeleteState.DELETE_NO);// 未删��
                newAd.setOnStatus(advertisements.getOnStatus());// 开��
                newAd.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);// 同步��
                newAd.setDefaultPicture(Constant.Common.NO);// 新建的时候都是非默认的
                // newAd.setMongodbId(null);
                newAd.setSynchMsg("");
                newAd.setPid(adId);
                newAd.setRecordType((byte) 1);
                Date d = new Date();
                newAd.setCreatedAt(d);
                newAd.setUpdatedAt(d);
                newAd.setPictureUrl("-1");
                newAd.setMinPictureUrl("-1");
                // 新增，老记录修改PID=0
                getDao().updatePid(adId, 0);
                id = advertisementsDao.save(newAd);
            } else if (advertisements.getPid() > 0 && advertisements.getRecordType() == 1) {// 修改有审核通过记录的草记录
                // 只修改原来的记录，直接修��
                Date d = new Date();
                advertisements.setUpdatedAt(d);
                advertisements.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);
                getDao().update(advertisements);
                id = advertisements.getId();
            }
        } else {// 新增
            advertisements.setDeleteStatus(Constant.DeleteState.DELETE_NO);// 未删��
            // advertisements.setOnStatus(Constant.OpenState.OPEN_STATE);// 开��
            advertisements.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);// 同步��
            advertisements.setMongodbId(null);
            advertisements.setSynchMsg("");
            advertisements.setPid(-1);
            advertisements.setRecordType((byte) 1);
            Date d = new Date();
            advertisements.setUpdatedAt(d);
            advertisements.setCreatedAt(d);
            advertisements.setDefaultPicture(Constant.Common.NO);
            advertisements.setPictureUrl("-1");
            advertisements.setMinPictureUrl("-1");
            // 新增记录
            id = advertisementsDao.save(advertisements);
        }
        /*
         * if(advertisements.getDefaultPicture()!=null && Constant.Common.YES==advertisements.getDefaultPicture()) {
         * //默认广告在一个广告位下面只能有一个,所以要把其他的取消掉[新建的广告,不能设置默认] advertisementsDao.setDefaultAd(id,
         * advertisements.getAdZoneId()); }
         */
        return id;
    }

    /**
     * 查询包括开始时间，结束时间的记录，若结果是0，则说明这段时间内还没有广告时间，可以插入表��
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<Advertisements> queryIncludeDate(String startTime, String endTime, int id, int pid, String adZoneId)
                                                                                                                    throws Exception {
        return getDao().queryIncludeDate(startTime, endTime, id, pid, adZoneId);
    }

    /**
     * 通过PID查找 2014.4.8 lim
     * 
     * @param pid
     * @return
     */
    public Advertisements selectByPid(Integer pid) {
        return getDao().selectByPid(pid);
    }

    /**
     * 开启关闭广告投放
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public int openOrClose(Advertisements ad) {

        try {
            int update = advertisementsDao.update(ad);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取默认广告
     */
    public Advertisements getDefauldAd(String adZoneId, Integer defaultPic) {

        return getDao().selectByDefaultPic(adZoneId, defaultPic);
    }

    /**
     * 设置默认广告
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public int defaultAd(Advertisements ad) {

        try {
            /*
             * Advertisements defauldAd = getDefauldAd(ad.getAdZoneId(),1); defauldAd.setDefaultPicture(0);
             * advertisementsDao.update(defauldAd);
             */
            Integer defaultPicture = ad.getDefaultPicture();
            if (defaultPicture == 0) {
                ad.setDefaultPicture(1);
            }
            if (defaultPicture == 1) {
                ad.setDefaultPicture(0);
            }
            int update = advertisementsDao.update(ad);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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
        return advertisementsDao.selectAdListByMap(adZoneId, synchStatus, startTime, endTime);
    }

    /**
     * @param adZoneId
     * @param onStatus
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public List<Advertisements> selectOnOffStatusAdListByMap(String adZoneId, Integer onStatus, String startTime,
                                                             String endTime) throws Exception {
        return advertisementsDao.selectOnOffStatusAdListByMap(adZoneId, onStatus, startTime, endTime);
    }
}
