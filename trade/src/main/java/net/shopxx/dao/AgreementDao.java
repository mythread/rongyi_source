package net.shopxx.dao;

import net.shopxx.entity.Agreement;

/**
 * Dao接口 - 会员注册协议
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  AD6A2E1229B83B3D983EBEB49F0BC7E0
 * ============================================================================
 */
public interface AgreementDao extends BaseDao<Agreement, String> {

	/**
	 * 获取Agreement对象
	 * 
	 * @return Agreement对象
	 * 
	 */
	public Agreement getAgreement();

}