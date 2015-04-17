package com.fanxian.biz.user.dao.interfaces;

import com.fanxian.biz.user.dataobject.UserDO;

public interface UserDao {

    UserDO getById(Integer id);

    Integer insert(UserDO userDO);

    UserDO getByAccount(String account);

}
