package com.fanxian.biz.userandpro.service;

import org.apache.commons.lang.StringUtils;

import com.fanxian.biz.common.cons.StatusEnum;
import com.fanxian.biz.userandpro.cons.AlipayStatusEnum;
import com.fanxian.biz.userandpro.dao.interfaces.UserAndProDao;
import com.fanxian.biz.userandpro.dataobject.UserAndProDO;

public class UserAndProService {

    private UserAndProDao userAndProDao;

    public void setUserAndProDao(UserAndProDao userAndProDao) {
        this.userAndProDao = userAndProDao;
    }

    /**
     * 插入数据
     */
    public Integer insert(Integer useId, String productId, String productUrl, String ip, Integer price) {
        if (StringUtils.isEmpty(productId) || StringUtils.isEmpty(productUrl)) {
            return null;
        }
        UserAndProDO up = new UserAndProDO();
        up.setStatus(StatusEnum.ENABLE.getValue());
        up.setIdentity(productId);
        up.setUserId(useId);
        up.setProductUrl(productUrl);
        up.setIp(ip);
        up.setAlipayStatus(AlipayStatusEnum.NO.getValue());
        up.setPrice(price);
        return userAndProDao.insert(up);
    }
}
