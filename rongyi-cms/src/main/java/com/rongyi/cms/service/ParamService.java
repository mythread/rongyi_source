package com.rongyi.cms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rongyi.cms.bean.Param;
import com.rongyi.cms.constant.Constant;
import com.rongyi.cms.dao.ParamDao;

@Service
@Transactional
public class ParamService {

    @Autowired
    private ParamDao paramDao;

    /**
     * 通过业务类型取得相关数据
     */
    public List<Param> selectKeyBybizType(String bizType) throws Exception {
        return paramDao.selectKeyBybizType(bizType);
    }

    /**
     * 取得商场mongoId
     */
    public Param getMallID() {
        try {

            List<Param> list = paramDao.selectKeyBybizType(Constant.Paramtable.PARAM_TABLE_BIZTYPE_MALL);
            Param param = null;
            if (list != null && list.size() > 0) {
                param = list.get(0);
            }
            return param;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取商铺楼层 2014.4.24.lim
     * 
     * @param map
     * @return
     * @throws Exception
     */
    public Param getFloor(String serviceType) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bizType", Constant.Paramtable.PARAM_TABLE_BIZTYPE_BUILD);
        map.put("serviceType", serviceType);
        return paramDao.selectKeyBybizTypeAndServiceType(map);
    }

}
