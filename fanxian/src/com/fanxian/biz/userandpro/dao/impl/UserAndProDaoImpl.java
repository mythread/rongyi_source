package com.fanxian.biz.userandpro.dao.impl;

import com.fanxian.biz.userandpro.dao.interfaces.UserAndProDao;
import com.fanxian.biz.userandpro.dataobject.UserAndProDO;
import com.yue.commons.db.ibatis.YueBaseSqlMapClientDaoSupport;

public class UserAndProDaoImpl extends YueBaseSqlMapClientDaoSupport implements UserAndProDao {

    private static final String TABLE = "USERANDPRO.";

    public Integer insert(UserAndProDO userAndProDO) {
        return (Integer) getSqlMapClientTemplate().insert(TABLE + "insert", userAndProDO);
    }

}
