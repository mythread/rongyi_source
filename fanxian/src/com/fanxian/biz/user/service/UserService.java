package com.fanxian.biz.user.service;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;

import com.fanxian.biz.common.cons.StatusEnum;
import com.fanxian.biz.common.tools.DefaultResult;
import com.fanxian.biz.common.tools.IResult;
import com.fanxian.biz.user.dao.interfaces.UserDao;
import com.fanxian.biz.user.dataobject.UserDO;
import com.fanxian.commons.lang.Argument;

public class UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public IResult<Integer> insertUser(String account, String cookieId) {
        if (StringUtils.isBlank(account)) {
            return DefaultResult.failResult("请输入支付宝帐号！");
        }
        account = StringUtils.trim(account);
        // 验证邮箱或手机是否合法
        if (!EmailValidator.getInstance().isValid(account) && !checkMobile(account)) {
            return DefaultResult.failResult("请输入正确的支付宝帐号！");
        }
        UserDO userDO = new UserDO();
        userDO.setStatus(StatusEnum.ENABLE.getValue());
        userDO.setAccount(account);
        userDO.setCookieId(cookieId);
        Integer id = userDao.insert(userDO);
        if (Argument.isPositive(id)) {
            return DefaultResult.successResult(id);
        } else {
            return DefaultResult.failResult("操作失败！");
        }
    }

    /**
     * 验证手机号是否合法
     * 
     * @param account
     * @return
     */
    private boolean checkMobile(String account) {
        return Pattern.matches("^1[0-9]{10}$", account);
    }

    /**
     * 通过帐号查询用户
     */
    public UserDO getByAccount(String account) {
        if (StringUtils.isEmpty(account)) {
            return null;
        }
        return userDao.getByAccount(StringUtils.trim(account));
    }

    public UserDO getById(Integer id) {
        if (!Argument.isPositive(id)) {
            return null;
        }
        return userDao.getById(id);
    }
}
