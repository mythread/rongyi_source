package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.MemberRankDao;
import net.shopxx.entity.MemberRank;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员分类
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  1FC43548A8F1563DB9E55475E0AE0011
 * ============================================================================
 */

@Repository
public class MemberRankDaoImpl extends BaseDaoImpl<MemberRank, String> implements MemberRankDao {
	
	public MemberRank getDefaultMemberRank() {
		String hql = "from MemberRank as memberRank where memberRank.isDefault = ?";
		MemberRank defaultMemberRank = (MemberRank) getSession().createQuery(hql).setParameter(0, true).uniqueResult();
		if(defaultMemberRank == null) {
			hql = "from MemberRank as memberRank order by memberRank.createDate asc";
			defaultMemberRank = (MemberRank) getSession().createQuery(hql).setMaxResults(1).uniqueResult();
		}
		return defaultMemberRank;
	}
	
	public MemberRank getMemberRankByPoint(Integer point) {
		String hql = "from MemberRank as memberRank where memberRank.point = ?";
		return (MemberRank) getSession().createQuery(hql).setParameter(0, point).uniqueResult();
	}
	
	public MemberRank getUpMemberRankByPoint(Integer point) {
		String hql = "from MemberRank as memberRank where memberRank.point <= ? order by memberRank.point desc";
		return (MemberRank) getSession().createQuery(hql).setParameter(0, point).setMaxResults(1).uniqueResult();
	}
	
	// 重写方法，保存时若对象isDefault=true，则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public String save(MemberRank memberRank) {
		if (memberRank.getIsDefault()) {
			String hql = "from MemberRank memberRank where memberRank.isDefault = ?";
			List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter(0, true).list();
			if (memberRankList != null) {
				for (MemberRank r : memberRankList) {
					r.setIsDefault(false);
				}
			}
		}
		return super.save(memberRank);
	}

	// 重写方法，更新时若对象isDefault=true，则设置其它对象isDefault值为false
	@Override
	@SuppressWarnings("unchecked")
	public void update(MemberRank memberRank) {
		if (memberRank.getIsDefault()) {
			String hql = "from MemberRank memberRank where memberRank.isDefault = ? and memberRank != ?";
			List<MemberRank> memberRankList = getSession().createQuery(hql).setParameter(0, true).setParameter(1, memberRank).list();
			if (memberRankList != null) {
				for (MemberRank r : memberRankList) {
					r.setIsDefault(false);
				}
			}
		}
		super.update(memberRank);
	}

}