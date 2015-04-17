package com.rongyi.nasdaq.biz.mapper;

import com.rongyi.nasdaq.biz.domain.PartnerDO;

/**
 * @author jiejie 2014年5月21日 下午3:51:18
 */
public interface PartnerMapper extends BaseMapper<PartnerDO> {

    /**
     * count总数
     */
    public Integer getTotalNum();
}
