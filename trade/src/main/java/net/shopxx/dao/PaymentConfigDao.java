package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.PaymentConfig;

/**
 * Dao接口 - 支付配置
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  632683C0954822B612AE218455A18E13
 * ============================================================================
 */

public interface PaymentConfigDao extends BaseDao<PaymentConfig, String> {

	/**
	 * 获取非预存款类型的支付配置
	 * 
	 * @return 支付配置
	 */
	public List<PaymentConfig> getNonDepositPaymentConfigList();
	
	/**
	 * 获取非预存款、线下支付方式的支付配置
	 * 
	 * @return 支付配置
	 */
	public List<PaymentConfig> getNonDepositOfflinePaymentConfigList();

}