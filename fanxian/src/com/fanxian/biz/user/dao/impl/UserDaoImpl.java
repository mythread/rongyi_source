package com.fanxian.biz.user.dao.impl;

import com.fanxian.biz.user.dao.interfaces.UserDao;
import com.fanxian.biz.user.dataobject.UserDO;
import com.yue.commons.db.ibatis.YueBaseSqlMapClientDaoSupport;

public class UserDaoImpl extends YueBaseSqlMapClientDaoSupport implements UserDao {

    private static final String TABLE = "USER.";

    public Integer insert(UserDO userDO) {
        return (Integer) getSqlMapClientTemplate().insert(TABLE + "insert", userDO);
    }

    public UserDO getByAccount(String account) {
        return (UserDO) getSqlMapClientTemplate().queryForObject(TABLE + "getByAccount", account);
    }

    public UserDO getById(Integer id) {
        return (UserDO) getSqlMapClientTemplate().queryForObject(TABLE + "getById", id);
    }

}
