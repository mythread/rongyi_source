package com.rongyi.mina.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.mina.bean.Photos;
import com.rongyi.mina.mapper.PhotosMapper;

/**
 * 类PhotosDao.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月14日 下午1:57:42
 */
@Repository
public class PhotosDao {

    @Autowired
    private PhotosMapper photosMapper;

    public Integer insert(Photos photo) {
        return photosMapper.insert(photo);
    };

    public Integer delete(Integer ownerId, String type) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ownerId", ownerId);
        map.put("ownerType", type);
        return photosMapper.delete(map);
    }
}
