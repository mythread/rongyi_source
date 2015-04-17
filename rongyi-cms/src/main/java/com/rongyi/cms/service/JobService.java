package com.rongyi.cms.service;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import mina.client.MinaClient;

// import mina.client.MinaClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import base.config.PropertyConfigurer;

import com.rongyi.cms.bean.Brands;
import com.rongyi.cms.bean.Job;
import com.rongyi.cms.bean.Param;
import com.rongyi.cms.bean.Photos;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.constant.JobConstant;
import com.rongyi.cms.dao.AdvertisementsDao;
import com.rongyi.cms.dao.BrandsDao;
import com.rongyi.cms.dao.JobDao;
import com.rongyi.cms.dao.ShopsDao;

/**
 * @author jiejie 2014���上�32:47
 */
@Repository
@Transactional
public class JobService {

    private static final Logger log              = LoggerFactory.getLogger(JobService.class);
    private static final String MALL_ID          = "param_mallId";
    private static final String MALL_NAME        = "param_mallName";
    private static final String CMS_ID           = "cmsId";
    private static final String JOB_ID           = "jobId";
    private static final String JOB_TYPE         = "type";
    private static final String JOB_CREATE_DATE  = "gmtJobCreate";
    private static final String JOB_ACTION_TYPE  = "actionType";
    private static final String CATEGORY_ID_LIST = "categoryIdList";
    private static final String OPERATE_TYPE     = "operateType";
    // private static final String SERVER_IP = "192.168.1.130";//192.168.1.130
    // private static final int PORT = 8991;

    @Autowired
    private JobDao              jobDao;

    @Autowired
    private AdvertisementsDao   advertDao;

    @Autowired
    private ShopsDao            shopsDao;

    @Autowired
    private PhotosService       photoService;

    @Autowired
    private CategoriesService   categoryService;

    @Autowired
    private PropertyConfigurer  propertyConfigurer;
    @Autowired
    private ParamService        paramService;

    @Autowired
    private BrandsDao           brandsDao;

    @PostConstruct
    public void init() {
        // 轮询未处理的job，并调用client发消�
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int period = 1;
        final Runnable sentMsg = new Runnable() {

            public void run() {
                log.info("---------------开始扫描数据库job记录--------------");
                ArrayList<HashMap<String, Object>> msg = getMsgResult();
                log.info("---------------扫描到数据库job记录【" + msg.size() + "】条--------------");
                if (msg.size() > 0) {
                    String serverIP = propertyConfigurer.getProperty(Constant.Common.MINA_SERVER_IP).toString();
                    int port = Integer.parseInt(propertyConfigurer.getProperty(Constant.Common.MINA_SERVER_PORT).toString());
                    MinaClient client = new MinaClient(serverIP, port, msg, true);
                    log.info("----------------执行完毕-----------------");
                }
            }
        };
        executor.scheduleAtFixedRate(sentMsg, 1, period, TimeUnit.MINUTES);
    }

    /**
     * 插入新的job记录
     * 
     * @param type :JobConstant.JobType
     * @param cmsId : 商铺、广告、品牌的主键ID
     * @param operateType JobConstant.OperateType ;0:需要审�..
     */
    public Integer insertJob(int type, String cmsId, int operateType) {
        if (StringUtils.isEmpty(cmsId)) {
            return null;
        }
        // 检查未处理的job
        Job j = getUnHandleJob(type, cmsId);
        if (j != null) {
            if (operateType != j.getOperateType()) {
                updateOperateType(j.getId(), operateType);
            } else {
                updateJobTime(j.getId());
            }
            return null;
        } else {
            Job job = new Job();
            job.setCmsId(cmsId);
            job.setJobStatus(JobConstant.JobStatus.UN_HANDLE);
            job.setActionType(JobConstant.ActionType.ADD_ACTION);
            job.setType(type);
            job.setOperateType(operateType);
            return jobDao.insert(job);
        }
    }

    /**
     * 查询未处理的job
     */
    public Job getUnHandleJob(Integer type, String cmsId) {
        return jobDao.getUnHandleJob(type, cmsId);
    }

    public Integer updateJobTime(Integer id) {
        return jobDao.updateJobTime(id);
    }

    public Integer updateOperateType(Integer id, Integer operateType) {
        return jobDao.updateOperateType(id, operateType);
    }

    /**
     * 查询未处理的job
     */
    public List<Job> listUnHandleJob() {
        return jobDao.listUnHandleJob();
    }

    /**
     * 批量更新job状�
     */
    public Integer batchUpdateJobStatus(List<Integer> jobIds) {
        return jobDao.batchUpdateJobStatus(jobIds);
    }

    /**
     * 获得传递消息的hashMap�
     */
    private ArrayList<HashMap<String, Object>> getMsgResult() {
        Param mall = paramService.getMallID();
        ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        if (mall == null) {
            log.info("-----------未查询到对应的商场配置信息------------");
            return result;
        }

        List<Job> jobs = listUnHandleJob();
        if (jobs != null && jobs.size() > 0) {
            for (Job j : jobs) {
                log.info("mallName:" + mall.getValue() + " job>>>type:" + j.getType() + ";cmsId:" + j.getCmsId());
                Object cmsObj = getObjectByCmsId(j);
                if (cmsObj == null) {
                    continue;
                }
                HashMap<String, Object> msgMap = new HashMap<String, Object>();
                HashMap<String, Object> map = getBeanValueMap(cmsObj);
                if (map == null) {
                    continue;
                }
                msgMap.putAll(map);

                msgMap.put(MALL_ID, mall.getServiceType());
                msgMap.put(MALL_NAME, mall.getValue());
                msgMap.put(OPERATE_TYPE, j.getOperateType());
                msgMap.put(JOB_ID, j.getId());
                msgMap.put(JOB_TYPE, j.getType());
                msgMap.put(JOB_ACTION_TYPE, j.getActionType());
                msgMap.put(JOB_CREATE_DATE, j.getGmtcreate().getTime());
                msgMap.put(CMS_ID, j.getCmsId());
                if (cmsObj instanceof Brands) {
                    List<String> categoryIdList = categoryService.getCategoryIdList(((Brands) cmsObj).getId());
                    if (categoryIdList != null && categoryIdList.size() > 0) {
                        msgMap.put(CATEGORY_ID_LIST, StringUtils.join(categoryIdList, ","));
                    }
                }

                // 获取图片
                List<Photos> photoList = listPhotos(j);
                if (photoList != null && photoList.size() > 0) {
                    photo2MsgMap(msgMap, photoList);
                }
                result.add(msgMap);
            }
        }
        return result;
    }

    /**
     * photo信息转为消息
     */
    private void photo2MsgMap(HashMap<String, Object> msgMap, List<Photos> photoList) {
        int i = 1;
        for (Photos p : photoList) {
            String key1 = "minaPicId_" + i;
            msgMap.put(key1, p.getId());
            String key2 = "minaPicFileUrl_" + i;
            msgMap.put(key2, p.getFileUrl());
            String key3 = "minaPicFile_" + i;
            msgMap.put(key3, p.getFile());
            String key4 = "minaPicOwnerId_" + i;
            msgMap.put(key4, p.getOwnerId());
            String key5 = "minaPicOwnerType_" + i;
            msgMap.put(key5, p.getOwnerType());
            String key6 = "minaPicPosition_" + i;
            msgMap.put(key6, p.getPosition());
            String key7 = "minaPicOwnerMongoId_" + i;
            msgMap.put(key7, p.getOwnerMongoId());
            String key8 = "minaPicMongoId_" + i;
            msgMap.put(key8, p.getMongoId());
            String key9 = "minaPicGmtCreate_" + i;
            msgMap.put(key9, p.getCreatedAt().getTime());
            String key10 = "minaPicDeleteStatus_" + i;
            msgMap.put(key10, p.getDeleteStatus());
            ++i;
        }
    }

    private Object getObjectByCmsId(Job job) {
        try {
            int type = job.getType();
            if (type == 0) {
                // 商铺信息
                return shopsDao.get(Integer.valueOf(job.getCmsId()));
            } else if (type == 1) {
                // 广告
                return advertDao.get(Integer.valueOf(job.getCmsId()));
            } else if (type == 2) {
                // 品牌
                return brandsDao.selectByPrimaryKey(Integer.valueOf(job.getCmsId()));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 获得photos图片
     */
    private List<Photos> listPhotos(Job job) {
        // 获得商铺photos图片
        try {
            int type = job.getType();
            if (type == 0) {
                return photoService.selectAllByOwnerId(Integer.valueOf(job.getCmsId()), "Shop");
            }

        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 获得javabean属性的key-value �
     */
    private HashMap<String, Object> getBeanValueMap(Object obj) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            PropertyDescriptor[] props = Introspector.getBeanInfo(obj.getClass(), Object.class).getPropertyDescriptors();
            if (props != null) {
                for (int i = 0; i < props.length; i++) {
                    Class<?> propType = props[i].getPropertyType();
                    if (propType.isInstance(new Date())) {
                        Date date = (Date) props[i].getReadMethod().invoke(obj);
                        if (date != null) {
                            map.put(props[i].getName(), date.getTime());
                        }
                    } else {
                        map.put(props[i].getName(), props[i].getReadMethod().invoke(obj));
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }

    /**
     * 更新商铺、广告、品牌的同步状�
     * 
     * @throws Exception
     */
    public boolean updateSynchStatus(HashMap<String, Object> map) throws Exception {
        if (map == null || map.isEmpty()) {
            return false;
        }
        Integer type = (Integer) map.get("type");
        if (type == null) {
            return false;
        }
        String outerId = (String) map.get("cmsOuterId");
        Integer reviewStatus = (Integer) map.get("reviewStatus");
        String mongodbId = (String) map.get("mongodbId");
        log.info("接收到crm客户端发送过来的消息>>> 更新商铺、广告、品牌的同步");
        log.info("outerId>>>" + outerId + ";type>>>" + type + ";mongodbId>>>" + mongodbId + ";reviewStatus>>>"
                 + reviewStatus);
        if (type == 0) {
            // 商铺
            updatePhotoByMsg(map);
            return shopsDao.verifyOK(map);
        } else if (type == 1) {
            // 广告
            return advertDao.verifyOK(map);
        } else if (type == 2) {
            // 品牌
            return brandsDao.verifyOK(map);
        }
        return false;
    }

    /**
     * 通过消息更新photo�
     */
    private void updatePhotoByMsg(HashMap<String, Object> map) {
        if (map.get("pic_1_cmsPhotoId") == null) {
            return;
        }
        for (int i = 1;; i++) {
            String key1 = "pic_" + i + "_cmsPhotoId";
            String key2 = "pic_" + i + "_fileUrl";
            String key3 = "pic_" + i + "_ownerMongoId";
            String key4 = "pic_" + i + "_mongoId";
            Integer photoId = (Integer) map.get(key1);
            if (photoId == null) {
                break;
            }
            String fileUrl = (String) map.get(key2);
            String ownerMongoId = (String) map.get(key3);
            String mongoId = (String) map.get(key4);
            log.info("---------更新photo表信息：id:" + photoId + ",fileUrl:" + fileUrl + ",ownerMongoId:" + ownerMongoId
                     + ",mongoId:" + mongoId);
            Photos photo = new Photos();
            photo.setId(photoId);
            photo.setFileUrl(fileUrl);
            photo.setOwnerMongoId(ownerMongoId);
            photo.setMongoId(mongoId);
            photoService.updateByPrimaryKeySelective(photo);
        }
    }
}
