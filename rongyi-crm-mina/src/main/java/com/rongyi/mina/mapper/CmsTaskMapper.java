package com.rongyi.mina.mapper;

import java.util.Map;

import com.rongyi.mina.bean.CmsTask;

/**
 * 类CmsTaskMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月4日 下午2:26:07
 */
public interface CmsTaskMapper {

    Integer insert(CmsTask task);

    int updateGmtModifiedById(Map<String, Object> map);

    /**
     * 查询
     * 
     * @param map
     * @return
     */
    CmsTask selectExistCmsTask(Map<String, Object> map);

}
