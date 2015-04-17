package com.rongyi.mina.mapper;

import java.util.Map;

import com.rongyi.mina.bean.Photos;

/**
 * @author jiejie 2014年4月14日 下午1:29:02
 */
public interface PhotosMapper {

    Integer insert(Photos photo);

    /**
     * 将status 值置为1
     * 
     * @param ownerId
     * @param type
     * @return
     */
    Integer delete(Map<String, Object> map);
}
