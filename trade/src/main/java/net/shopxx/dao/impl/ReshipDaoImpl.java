package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.ReshipDao;
import net.shopxx.entity.Reship;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 退货
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  6624D83301C073E40676B792A192D5EE
 * ============================================================================
 */

@Repository
public class ReshipDaoImpl extends BaseDaoImpl<Reship, String> implements ReshipDao {
	
	@SuppressWarnings("unchecked")
	public String getLastReshipSn() {
		String hql = "from Reship as reship order by reship.createDate desc";
		List<Reship> reshipList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (reshipList != null && reshipList.size() > 0) {
			return reshipList.get(0).getReshipSn();
		} else {
			return null;
		}
	}

}