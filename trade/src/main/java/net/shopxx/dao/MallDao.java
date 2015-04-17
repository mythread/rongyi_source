package net.shopxx.dao;

import net.shopxx.entity.Mall;

public interface MallDao extends BaseDao<Mall, Integer> {

    public boolean isExistName(String name);
}
