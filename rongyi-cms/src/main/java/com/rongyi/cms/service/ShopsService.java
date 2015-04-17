package com.rongyi.cms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.exception.ServiceException;
import base.util.character.JsonUtil;

import com.rongyi.cms.bean.Shops;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.dao.PhotosDao;
import com.rongyi.cms.dao.ShopsDao;

@Service
@Transactional
public class ShopsService {

    private Logger    logger = Logger.getLogger(this.getClass());
    @Autowired
    private ShopsDao  shopsDao;
    @Autowired
    private PhotosDao photosDao;

    public ShopsDao getDao() {
        return shopsDao;
    }

    public boolean updateByPrimaryKeySelective(Shops shops) {
        if (shops == null) {
            return false;
        }
        return shopsDao.updateByPrimaryKeySelective(shops) > 0;
    }

    /**
     * 查询商家,返回JSON格式
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public String queryLikeName(String name) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (name != null && StringUtils.isNotBlank(name.trim())) {
            List<Map<String, Object>> list = getDao().queryLikeName(name.trim());
            resultMap.put("data", list);
            resultMap.put("status", 1);
            if (list != null && list.size() > 0) {
                resultMap.put("msg", "ok");
            } else {
                resultMap.put("msg", "none");
            }
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "none");
        }
        String json = JsonUtil.getJSONString(resultMap);
        return json;
    }

    /*
     * public void save(Shops shops) throws Exception{ getDao().save(shops); }
     */

    public Shops get(Integer id) throws Exception {
        return getDao().get(id);
    }

    public List<Shops> searchShopsByParam(Shops shops) throws ServiceException {
        try {
            return shopsDao.searchShopsByParam(shops);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Integer> groupByShopsBySynchStatus() throws Exception {
        Map<String, Integer> map = shopsDao.groupByShopsBySynchStatus();
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

    public List<Shops> searchShopsBySynchStatus(Shops shops) throws Exception {
        return shopsDao.searchShopsBySynchStatus(shops);
    }

    /**
     * 开启关闭广告投�
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public int openOrClose(Shops shop) {

        try {
            int update = shopsDao.update(shop);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 开启关闭广告投�
     * 
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public int recom(Shops shop) {

        try {
            int update = shopsDao.update(shop);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 1.新增 2.修改 a:修改草记录（只有一条草记录），b:修改已审核通过的记录（只有一条审核通过的记录），c:修改有审核通过记录的草记录 2014.4.9 limin
     * 
     * @param shops
     * @throws Exception
     */
    public int save(Shops shops) throws Exception {
        int id = 0;
        if (shops.getId() != null && shops.getId().intValue() > 0) {// 修改
            id = shops.getId();
            if (shops.getPid() == -1 && shops.getRecordType() == 1) {// 修改草记录（只有一条草记录�
                // 只修改原来的记录，直接修�
                Date d = new Date();
                shops.setUpdatedAt(d);
                shops.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);
                getDao().update(shops);
                id = shops.getId();
            } else if (shops.getPid() == -1 && shops.getRecordType() == 0) {// 修改已审核通过的记录（只有一条审核通过的记录）
                // 原来的记录pid=0 新加一条修改的草记�
                int shopsId = shops.getId();// 老记录ID
                // 查一下是否存在PID=SHOPSID的记录
                Shops newShops = shopsDao.selectByPid(shopsId);
                if (newShops == null) {
                    newShops = new Shops();
                }
                newShops.setAddress(shops.getAddress());
                newShops.setAverageConsumption(shops.getAverageConsumption());
                newShops.setBrandId(shops.getBrandId());
                newShops.setCommentCount(shops.getCommentCount());
                newShops.setCoordinate(shops.getCoordinate());
                newShops.setDescription(shops.getDescription());
                newShops.setDoorCoordinate(shops.getDoorCoordinate());
                newShops.setLocation(shops.getLocation());
                newShops.setMongoId(null);
                newShops.setName(shops.getName());
                newShops.setOldCode(shops.getOldCode());
                newShops.setOldId(shops.getOldId());
                newShops.setOnStatus(Constant.OpenState.OPEN_STATE);// 开�
                newShops.setOpenTime(shops.getOpenTime());
                newShops.setPid(shopsId);
                newShops.setRank(shops.getRank());
                newShops.setRecommend(shops.getRecommend());
                newShops.setRecordType(1);
                newShops.setShopNumber(shops.getShopNumber());
                newShops.setShopType(shops.getShopType());
                newShops.setSlug(shops.getSlug());
                newShops.setSubtitle(shops.getSubtitle());
                newShops.setSynchMsg("");
                newShops.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);
                newShops.setTags(shops.getTags());
                newShops.setTelephone(shops.getTelephone());
                newShops.setTerminalPosition(shops.getTerminalPosition());
                newShops.setTerminalShop(shops.getTerminalShop());
                newShops.setDeleteStatus((byte) Constant.Common.NO);
                newShops.setMongoId(shops.getMongoId());
                newShops.setMongoBrandId(shops.getMongoBrandId());
                Date d = new Date();
                newShops.setUpdatedAt(d);
                newShops.setZoneId(shops.getZoneId());
                // 新增，老记录修改PID=0
                getDao().updatePid(shopsId, 0);
                id = shopsDao.save(newShops);
                // 将图片也拷一份出�
                photosDao.copyPic2NewOwner(shopsId, Constant.Photo.OwnerType1, id);
                logger.info("id>>>>>" + id);
            } else if (shops.getPid() > 0 && shops.getRecordType() == 1) {// 修改有审核通过记录的草记录
                // 只修改原来的记录，直接修�
                Date d = new Date();
                shops.setUpdatedAt(d);
                shops.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);
                getDao().update(shops);
                id = shops.getId();
            }
        } else {// 新增
            shops.setOnStatus(Constant.OpenState.OPEN_STATE);// 开�
            shops.setSynchStatus(Constant.SynchState.SYNCHRONOUS_ING);// 同步�
            shops.setMongoId(null);
            shops.setSynchMsg("");
            shops.setPid(-1);
            shops.setRecordType(1);
            Date d = new Date();
            shops.setUpdatedAt(d);
            // 新增记录
            id = shopsDao.save(shops);
        }
        return id;
    }

    /**
     * 通过PID查找 2014.4.9 lim
     * 
     * @param pid
     * @return
     */
    public Shops selectByPid(Integer pid) {
        return getDao().selectByPid(pid);
    }

    /**
     * 2014.4.28 limin mongoid查询商家
     * 
     * @param mongoId
     * @return
     * @throws Exception
     */
    public Shops selectByMongoId(String mongoId) throws Exception {
        return getDao().selectByMongoId(mongoId);
    }
}
