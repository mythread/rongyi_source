package com.rongyi.nasdaq.biz.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rongyi.nasdaq.biz.domain.HomePictureDO;
import com.rongyi.nasdaq.biz.mapper.HomePictureMapper;

/**
 * @author jiejie 2014年5月22日 下午2:27:21
 */
@Repository
public class HomePictureDao {

    @Autowired
    private HomePictureMapper pictureMapper;

    public List<HomePictureDO> listHomePic() {
        return pictureMapper.list();
    }
}
