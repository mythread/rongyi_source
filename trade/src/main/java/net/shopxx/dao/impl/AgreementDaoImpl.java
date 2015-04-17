package net.shopxx.dao.impl;

import net.shopxx.dao.AgreementDao;
import net.shopxx.entity.Agreement;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 会员注册协议
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  D5FBB56980C60A9D4D676BC334C9E5F6
 * ============================================================================
 */

@Repository
public class AgreementDaoImpl extends BaseDaoImpl<Agreement, String> implements AgreementDao {

	public Agreement getAgreement() {
		return load(Agreement.AGREEMENT_ID);
	}

}