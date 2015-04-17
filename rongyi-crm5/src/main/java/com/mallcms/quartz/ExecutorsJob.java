package com.mallcms.quartz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gcrm.service.IBaseService;
import com.mallcms.Constant.CmsConstant;
import com.mallcms.domain.CrmJob;
import com.mallcms.domain.MallIpConfig;
import com.mallcms.domain.PhotosPojo;
import com.mina.client.MinaClient;

/**
 * 定时执行job任务
 * 
 * @author jiejie 2014年4月9日 下午2:56:27
 */
public class ExecutorsJob {

    private static final Logger        log = LoggerFactory.getLogger(ExecutorsJob.class);
    private IBaseService<CrmJob>       baseService;
    private IBaseService<PhotosPojo>   photoService;
    private IBaseService<MallIpConfig> mallConfigService;

    public void setMallConfigService(IBaseService<MallIpConfig> mallConfigService) {
        this.mallConfigService = mallConfigService;
    }

    public void setBaseService(IBaseService<CrmJob> baseService) {
        this.baseService = baseService;
    }

    public void setPhotoService(IBaseService<PhotosPojo> photoService) {
        this.photoService = photoService;
    }

    /**
     * <pre>
     * 1.查询配置文件
     * 2.getMinaMsg 静态数据队列
     * 3.client 多线程(线程池)
     * </pre>
     */
    public void init() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        int period = 1;
        final Runnable sentMsg = new Runnable() {

            @Override
            public void run() {
                log.warn("-------------------CRM 客户端开始扫描未处理的job--------------");
                Map<String, ArrayList<HashMap<String, Object>>> msg = getMinaMsg();
                log.warn("-------------CRM 客户端扫面到未处理的job个数【" + msg.size() + "】-----------");
                if (msg.size() > 0) {
                    // 查询配置文件
                    for (Map.Entry<String, ArrayList<HashMap<String, Object>>> entry : msg.entrySet()) {
                        String mallId = entry.getKey();
                        MallIpConfig config = getMallConfig(mallId);
                        String serverIp = config.getIp();
                        int serverPort = config.getPort();
                        ArrayList<HashMap<String, Object>> msgMap = entry.getValue();
                        MinaClient client = new MinaClient(serverIp, serverPort, msgMap, true);
                    }
                }
            }
        };
        executor.scheduleAtFixedRate(sentMsg, 1, period, TimeUnit.MINUTES);

    }

    /**
     * 查询未处理的job记录
     */
    private List<CrmJob> listUnHandleJob() {
        String hql = "from CrmJob where jobStatus = 0";
        List<CrmJob> jobs = baseService.findByHQL(hql);
        return jobs;
    }

    /**
     * 查询新增的图片
     * 
     * @return
     */
    private List<PhotosPojo> listNewPhotos(CrmJob job) {
        String ownerType = "";
        Integer type = job.getType();
        if (type == 0) {
            ownerType = CmsConstant.PhotosOwnerType.SHOP_TYPE;
            String hql = "from PhotosPojo where status = 0 and ownerId = '" + job.getCmsOuterId()
                         + "' and ownerType = '" + ownerType + "' and mallId = '" + job.getMallId() + "'";
            List<PhotosPojo> photoList = photoService.findByHQL(hql);
            return photoList;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 查询商场ip 端口配置
     * 
     * @return
     */
    private MallIpConfig getMallConfig(String mallId) {
        if (StringUtils.isEmpty(mallId)) {
            return null;
        }
        String hql = "from MallIpConfig where mallId = '" + mallId + "'";
        List<MallIpConfig> mallConfigs = mallConfigService.findByHQL(hql);
        if (mallConfigs == null || mallConfigs.isEmpty()) {
            return null;
        }
        return mallConfigs.get(0);
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
        // 图片信息的消息内容
        List<PhotosPojo> photoList = listNewPhotos(job);
        if (photoList == null || photoList.isEmpty()) {
            return;
        }
        int i = 1;
        for (PhotosPojo p : photoList) {
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
    private Map<String, ArrayList<HashMap<String, Object>>> getMinaMsg() {
        log.warn(" 获得传递的消息");
        List<CrmJob> crmJobs = listUnHandleJob();
        if (crmJobs == null || crmJobs.isEmpty()) {
            return Collections.emptyMap();
        }
        log.warn(" 获得传递的消息" + crmJobs.size());
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
