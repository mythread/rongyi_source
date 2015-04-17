package com.rongyi.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.Photos;
import com.rongyi.cms.dao.PhotosDao;

@Service
@Transactional
public class PhotosService {

    @Autowired
    private PhotosDao photosDao;

    /**
     * 保存图片 2014.4.10 lim
     * 
     * @param photos
     * @return
     * @throws Exception
     */
    public int save(Photos photos) throws Exception {
        return photosDao.save(photos);
    }

    /**
     * 查找图片列表 2014.4.10 lim
     * 
     * @param ownerId
     * @param ownerType
     * @return
     * @throws Exception
     */
    public List<Photos> selectByOwnerIdAndType(int ownerId, String ownerType) throws Exception {
        return photosDao.selectByOwnerIdAndType(ownerId, ownerType);
    }

    public List<Photos> selectAllByOwnerId(int ownerId, String ownerType) throws Exception {
        return photosDao.selectAllByOwnerId(ownerId, ownerType);
    }

    public Photos get(int id) throws Exception {
        return photosDao.get(id);
    }

    /**
     * 查询更新或新增的图片
     */
    public List<Photos> listNewPhotos(Integer ownerId, String ownerType) {
        return photosDao.listNewPhotosByOwnerId(ownerId, ownerType);
    }

    public Integer updateByPrimaryKeySelective(Photos photo) {
        return photosDao.updateByPrimaryKeySelective(photo);
    }
}
