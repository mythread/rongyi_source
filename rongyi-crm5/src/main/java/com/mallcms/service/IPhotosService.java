package com.mallcms.service;

import java.util.List;

import com.mallcms.domain.Photo;
import com.mallcms.domain.PhotosPojo;

public interface IPhotosService {

    public List<Photo> getPhotosByMallId(String mallId);

    public List<Photo> getPhotosByOwnId(String OwnId);

    public String update2Mongodb(PhotosPojo photo);
}
