package com.mallcms.service.impl;

import java.util.List;

import com.mallcms.dao.IPhotosDao;
import com.mallcms.domain.Photo;
import com.mallcms.domain.PhotosPojo;
import com.mallcms.service.IPhotosService;

public class PhotosServiceImpl implements IPhotosService {

    private IPhotosDao photoDaoImpl;

    public IPhotosDao getPhotoDaoImpl() {
        return photoDaoImpl;
    }

    public void setPhotoDaoImpl(IPhotosDao photoDaoImpl) {
        this.photoDaoImpl = photoDaoImpl;
    }

    @Override
    public List<Photo> getPhotosByMallId(String mallId) {
        return photoDaoImpl.getPhotosByMallId(mallId);
    }

    public String update2Mongodb(PhotosPojo photo) {
        return photoDaoImpl.update(photo);
    }

    @Override
    public List<Photo> getPhotosByOwnId(String OwnId) {
        return photoDaoImpl.getPhotoByOwnId(OwnId);
    }

}
