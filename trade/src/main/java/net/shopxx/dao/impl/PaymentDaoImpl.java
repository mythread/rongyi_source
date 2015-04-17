package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.PaymentDao;
import net.shopxx.entity.Payment;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 支付
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  F9A10498A79B1352A22264FA2960B2E5
 * ============================================================================
 */

@Repository
public class PaymentDaoImpl extends BaseDaoImpl<Payment, String> implements PaymentDao {
	
	@SuppressWarnings("unchecked")
	public String getLastPaymentSn() {
		String hql = "from Payment as payment order by payment.createDate desc";
		List<Payment> paymentList =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		if (paymentList != null && paymentList.size() > 0) {
			return paymentList.get(0).getPaymentSn();
		} else {
			return null;
		}
	}
	
	public Payment getPaymentByPaymentSn(String paymentSn) {
		String hql = "from Payment as payment where payment.paymentSn = ?";
		return (Payment) getSession().createQuery(hql).setParameter(0, paymentSn).uniqueResult();
	}

}