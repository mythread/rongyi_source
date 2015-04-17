package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.MemberAttribute;

/**
 * Dao接口 - 会员属性
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  7A5E44F9B13AA8536A1A807D94A5B481
 * ============================================================================
 */

public interface MemberAttributeDao extends BaseDao<MemberAttribute, String> {

	/**
	 * 获取已启用的会员注册项.
	 * 
	 * @return 已启用的会员注册项集合.
	 */
	public List<MemberAttribute> getEnabledMemberAttributeList();

}
