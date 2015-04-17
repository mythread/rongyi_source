package com.rongyi.nasdaq.biz.dao;

import org.springframework.stereotype.Repository;

import com.rongyi.nasdaq.biz.domain.PartnerDO;
import com.rongyi.nasdaq.biz.mapper.PartnerMapper;

/**
 * 类PartnerDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月22日 下午4:02:56
 */
@Repository
public class PartnerDao extends BaseDao<PartnerDO, PartnerMapper> {

    /**
     * count总数
     */
    public Integer getTotalNum() {
        return e.getTotalNum();
    };
}
