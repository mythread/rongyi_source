package net.shopxx.dao.impl;

import java.util.Set;

import net.shopxx.dao.MemberDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  AC8DDA6F41F51B7BA7B541180E9FE7F7
 * ============================================================================
 */

@Repository
public class MemberDaoImpl extends BaseDaoImpl<Member, String> implements MemberDao {

	@SuppressWarnings("unchecked")
	public boolean isExistByUsername(String username) {
		String hql = "from Member members where lower(members.username) = lower(?)";
		Member member = (Member) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
		if (member != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Member getMemberByUsername(String username) {
		String hql = "from Member members where lower(members.username) = lower(?)";
		return (Member) getSession().createQuery(hql).setParameter(0, username).uniqueResult();
	}
	
	// 关联处理
	@Override
	public void delete(Member member) {
		Set<Order> orderSet = member.getOrderSet();
		if (orderSet != null) {
			for (Order order : orderSet) {
				order.setMember(null);
			}
		}
		super.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Member member = load(id);
		this.delete(member);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Member member = load(id);
			this.delete(member);
		}
	}

}