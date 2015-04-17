package com.rongyi.cms.mapper;

import java.util.List;
import java.util.Map;

import com.rongyi.cms.bean.Photos;

public interface PhotosMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Photos record);

    int insertSelective(Photos record);

    Photos selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Photos record);

    int updateByPrimaryKey(Photos record);

    List<Photos> selectByOwnerIdAndType(Map<String, Object> map);

    List<Photos> selectAllByOwnerId(Map<String, Object> map);

    /**
     * 查询新增或修改的图片
     */
    List<Photos> listNewPhotosByOwnerId(Map<String, Object> map);
}
