package com.rongyi.mina.quartz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rongyi.mina.bean.CrmJob;
import com.rongyi.mina.bean.MallIpConfig;
import com.rongyi.mina.bean.Photos;
import com.rongyi.mina.client.MinaClient;
import com.rongyi.mina.constant.Constant;
import com.rongyi.mina.service.CmsTaskService;
import com.rongyi.mina.service.JobService;
import com.rongyi.mina.service.MallIpConfigService;
import com.rongyi.mina.service.ServiceFactoryBean;

/**
 * 定时执行job任务
 */
public class ExecutorsJob {

    private static final Logger        log                 = LoggerFactory.getLogger(ExecutorsJob.class);
    private static JobService          jobService          = ServiceFactoryBean.getJobService();
    private static MallIpConfigService mallIpConfigService = ServiceFactoryBean.getMallIpConfigService();
    private static CmsTaskService      cmsTaskService      = ServiceFactoryBean.getCmsTaskService();

    private static ExecutorService     executorService     = Executors.newFixedThreadPool(15);

    public void init() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int period = 1;
        final Runnable sentMsg = new Runnable() {

            @Override
            public void run() {
                log.info("-------------------CRM 客户端开始扫描未处理的job--------------");
                // 查询配置文件
                Map<String, MallIpConfig> mallMap = mallIpConfigService.mallIpConfigMap();
                if (mallMap == null || mallMap.isEmpty()) {
                    log.info("-----------数据库中未查询到所有商场的配置信息---------------");
                    return;
                }
                Map<String, ArrayList<HashMap<String, Object>>> msg = getMinaMsg();
                if (msg.size() > 0) {
                    for (Map.Entry<String, ArrayList<HashMap<String, Object>>> entry : msg.entrySet()) {
                        String mallId = entry.getKey();
                        MallIpConfig config = mallMap.get(mallId);
                        if (config == null) {
                            continue;
                        }
                        final String serverIp = config.getIp();
                        final int serverPort = config.getPort();
                        final ArrayList<HashMap<String, Object>> msgMap = entry.getValue();
                        executorService.execute(new Runnable() {

                            @Override
                            public void run() {
                                MinaClient client = new MinaClient(serverIp, serverPort, msgMap, true);
                            }
                        });
                    }
                }
            }
        };
        executor.scheduleAtFixedRate(sentMsg, 1, period, TimeUnit.MINUTES);

    }

    private List<Photos> listPhotos(CrmJob job) {
        String ownerType = "";
        Integer type = job.getType();
        if (type == 0) {
            ownerType = Constant.PhotosOwnerType.SHOP_TYPE;
            List<Photos> photoList = cmsTaskService.listPhotosByOwnerId(NumberUtils.toInt(job.getCmsOuterId()),
                                                                        ownerType, job.getMallId());
            return photoList;
        } else {
            return Collections.emptyList();
        }
    }

    private HashMap<String, Object> getJobMsgMap(CrmJob job) {
        if (job == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("crmJobId", job.getId());
        map.put("type", job.getType());
        map.put("cmsOuterId", job.getCmsOuterId());
        map.put("reviewStatus", job.getReviewStatus());
        map.put("mongodbId", job.getMongodbId());
        map.put("memo", job.getMemo());
        map.put("imgUrl", job.getPicUrl());
        map.put("minImgUrl", job.getMinPicUrl());
        setPhotoMsg(map, job);
        return map;
    }

    /**
     * 设置图片消息
     * 
     * @param map
     * @param job
     */
    private void setPhotoMsg(HashMap<String, Object> map, CrmJob job) {
        // 图片信息的消息
        List<Photos> photoList = listPhotos(job);
        if (photoList == null || photoList.isEmpty()) {
            return;
        }
        int i = 1;
        for (Photos p : photoList) {
            String key1 = "pic_" + i + "_cmsPhotoId";
            String key2 = "pic_" + i + "_fileUrl";
            String key3 = "pic_" + i + "_ownerMongoId";
            String key4 = "pic_" + i + "_mongoId";
            map.put(key1, p.getCmsPhotoId());
            map.put(key2, p.getFileUrl());
            map.put(key3, p.getOwnerMongoId());
            map.put(key4, p.getMongoId());
            i++;
        }

    }

    /**
     * 获得传递的消息
     */
    private synchronized Map<String, ArrayList<HashMap<String, Object>>> getMinaMsg() {
        log.info(">>>查询未处理的job记录");
        List<CrmJob> crmJobs = jobService.listUnHandleJob();
        if (crmJobs == null || crmJobs.isEmpty()) {
            return Collections.emptyMap();
        }
        log.info(" 获得传递的消息" + crmJobs.size());
        Map<String, ArrayList<HashMap<String, Object>>> map = new HashMap<String, ArrayList<HashMap<String, Object>>>();
        Iterator<CrmJob> it = crmJobs.iterator();
        while (it.hasNext()) {
            CrmJob job = it.next();
            String mallId = job.getMallId();
            if (!map.containsKey(mallId)) {
                ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                list.add(getJobMsgMap(job));
                map.put(mallId, list);
            } else {
                ArrayList<HashMap<String, Object>> existList = map.get(mallId);
                existList.add(getJobMsgMap(job));
                map.put(mallId, existList);
            }
        }
        return map;
    }
}
