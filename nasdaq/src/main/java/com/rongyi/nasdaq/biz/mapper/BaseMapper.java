package com.rongyi.nasdaq.biz.mapper;

import java.util.List;
import java.util.Map;

/**
 * 类BaseMapper.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年5月21日 下午6:52:24
 */
public interface BaseMapper<T> {

    /**
     * 根据ID查找
     * 
     * @param id
     * @return
     */
    public T getById(Integer id);

    /**
     * 分页查询
     * 
     * @param map
     * @return
     */
    public List<T> listPagination(Map<String, Object> map);

    /**
     * 查询所有或limit 的几条数据
     * 
     * @param map
     * @param limitSize == null 则查所有
     */
    public List<T> list(Map<String, Object> map);

}
