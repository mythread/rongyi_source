package com.rongyi.mina.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.CrmJob;
import com.rongyi.mina.mapper.CrmJobMapper;

/**
 * @author jiejie 2014年4月27日 下午1:27:55
 */
@Repository
public class CrmJobDao {

    @Autowired
    private CrmJobMapper crmJobMapper;

    public boolean batchUpdateJobStatus(List<Integer> idList) {
        return crmJobMapper.batchUpdateJobStatus(idList) > 0;
    }

    public List<CrmJob> listUnHandleJob() {
        return crmJobMapper.listUnHandleJob();
    }
}
