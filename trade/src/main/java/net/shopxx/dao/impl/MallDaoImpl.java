package net.shopxx.dao.impl;

import org.springframework.stereotype.Repository;

import net.shopxx.dao.MallDao;
import net.shopxx.entity.Mall;

@Repository
public class MallDaoImpl extends BaseDaoImpl<Mall, Integer> implements MallDao{

    public boolean isExistName(String name) {
        String hql = "from ry_mall  where name = ?" ;
        
        Mall  mall =  (Mall) getSession().createQuery(hql).setParameter(0, name).uniqueResult();
        if (mall != null) {
            return true;
        } else {
            return false;
        }
    }

}
