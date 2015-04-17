package com.rongyi.nasdaq.biz.dao;

import org.springframework.stereotype.Repository;

import com.rongyi.nasdaq.biz.domain.CaseDO;
import com.rongyi.nasdaq.biz.mapper.CaseMapper;

/**
 * @author jiejie 2014年5月22日 下午3:47:11
 */
@Repository
public class CaseDao extends BaseDao<CaseDO, CaseMapper> {

    /**
     * count总数
     */
    public Integer getTotalNum() {
        return e.getTotalNum();
    }

    public CaseDO getPrevCase(Integer id) {
        return e.getPrevCase(id);
    }

    public CaseDO getNextCase(Integer id) {
        return e.getNextCase(id);
    }
}
