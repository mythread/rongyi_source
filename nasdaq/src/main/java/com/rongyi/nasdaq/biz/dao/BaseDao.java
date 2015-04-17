package com.rongyi.nasdaq.biz.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.rongyi.nasdaq.biz.mapper.BaseMapper;

/**
 * @author jiejie 2014年5月21日 下午6:37:35
 */

public class BaseDao<T, E> {

    @Autowired
    protected E e;

    /**
     * 根据ID查询对象
     */
    public T getById(Integer id) {
        return ((BaseMapper<T>) e).getById(id);
    }

    /**
     * 分页查询
     * 
     * @param map
     * @return
     */
    public List<T> listPagination(Integer startRecordIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startRecordIndex", startRecordIndex);
        map.put("pageSize", pageSize);
        return ((BaseMapper<T>) e).listPagination(map);
    }

    /**
     * 查询所有或limit 的几条数据
     * 
     * @param map
     * @param limitSize == null 则查所有
     */
    public List<T> list(Integer limitSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limitSize", limitSize);
        return ((BaseMapper<T>) e).list(map);
    }
}
