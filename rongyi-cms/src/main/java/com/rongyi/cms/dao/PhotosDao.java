package com.rongyi.cms.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.cms.bean.Photos;
import com.rongyi.cms.mapper.PhotosMapper;

@Repository
public class PhotosDao {

    @Autowired
    private PhotosMapper photosMapper;

    /**
     * 保存图片 2014.4.10 lim
     * 
     * @param photos
     * @return
     * @throws Exception
     */
    public int save(Photos photos) throws Exception {
        if (photos.getId() != null && photos.getId().intValue() > 0) {
            photosMapper.updateByPrimaryKeySelective(photos);
        } else {
            photosMapper.insert(photos);
        }
        int id = photos.getId() != null ? photos.getId() : 0;
        return id;
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ownerId", ownerId);
        map.put("ownerType", ownerType);
        return photosMapper.selectByOwnerIdAndType(map);
    }

    public List<Photos> selectAllByOwnerId(int ownerId, String ownerType) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ownerId", ownerId);
        map.put("ownerType", ownerType);
        return photosMapper.selectAllByOwnerId(map);
    }

    public Photos get(int id) throws Exception {
        return photosMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询新增或修改的图片
     */
    public List<Photos> listNewPhotosByOwnerId(Integer ownerId, String ownerType) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ownerId", ownerId);
        map.put("ownerType", ownerType);
        return photosMapper.listNewPhotosByOwnerId(map);
    }

    public Integer updateByPrimaryKeySelective(Photos photo) {
        return photosMapper.updateByPrimaryKeySelective(photo);
    }

    /**
     * 拷贝图片到新的草记录里面 当修改了商家记录的时候，会生成一条新的商家记录，需要把图片也拷一份过去 新拷贝的图片给老记录，把老的图片给新的记录 2014.4.11 lim
     * 
     * @param ownerId
     * @param ownerType
     * @param newOwnerId
     * @throws Exception
     */
    public void copyPic2NewOwner(int ownerId, String ownerType, int newOwnerId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ownerId", ownerId);
        map.put("ownerType", ownerType);
        List<Photos> list = photosMapper.selectByOwnerIdAndType(map);
        Photos newPhoto = new Photos();
        System.out.println("\n\n\nownerId>>>" + ownerId + ";newOwnerId>>>" + newOwnerId);
        int photoId = 0;
        if (list != null && list.size() > 0) {
            for (Photos p : list) {
                if (p != null) {
                    photoId = p.getId();
                    newPhoto = p;
                    newPhoto.setId(null);
                    photosMapper.insert(newPhoto);
                    p.setOwnerId(newOwnerId);
                    p.setId(photoId);
                    photosMapper.updateByPrimaryKeySelective(p);
                }
            }
        }
    }
}
