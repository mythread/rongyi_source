package com.mallcms.dao;

import java.util.List;

import com.mallcms.domain.Photo;
import com.mallcms.domain.PhotosPojo;

public interface IPhotosDao {

    public List<Photo> getPhotosByMallId(String mallId);

    public List<Photo> getPhotoByOwnId(String mallId);

    /**
     * 向mongodb中插入photo的数据
     * 
     * @return
     */
    public String update(PhotosPojo photo);
}
