package com.rongyi.mina.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.CrmJob;
import com.rongyi.mina.dao.CrmJobDao;

/**
 * 类JobService.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月27日 下午1:31:00
 */
@Repository
public class JobService {

    @Autowired
    private CrmJobDao crmJobDao;

    /**
     * 批量更新job 的status = 1
     * 
     * @param idList
     * @return
     */
    public boolean batchUpdateJobStatus(List<Integer> idList) {
        return crmJobDao.batchUpdateJobStatus(idList);
    }

    public List<CrmJob> listUnHandleJob() {
        return crmJobDao.listUnHandleJob();
    }
}
