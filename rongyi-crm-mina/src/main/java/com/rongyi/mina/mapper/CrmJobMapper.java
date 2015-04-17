package com.rongyi.mina.mapper;

import java.util.List;

import com.rongyi.mina.bean.CrmJob;

/**
 * @author jiejie 2014年4月27日 下午1:27:04
 */
public interface CrmJobMapper {

    /**
     * 批量更新job 的status = 1
     * 
     * @param idList
     * @return
     */
    public Integer batchUpdateJobStatus(List<Integer> idList);

    /**
     * 查询未处理的job
     * 
     * @return
     */
    List<CrmJob> listUnHandleJob();

}
