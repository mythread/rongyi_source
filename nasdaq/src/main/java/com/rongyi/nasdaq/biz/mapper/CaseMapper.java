package com.rongyi.nasdaq.biz.mapper;

import com.rongyi.nasdaq.biz.domain.CaseDO;

/**
 * @author jiejie 2014年5月21日 下午3:38:25
 */
public interface CaseMapper extends BaseMapper<CaseDO> {

    /**
     * count总数
     */
    public Integer getTotalNum();

    /**
     * 获得上一条数据
     */
    public CaseDO getPrevCase(Integer id);

    /**
     * 获得下一条数据
     */
    public CaseDO getNextCase(Integer id);
}
