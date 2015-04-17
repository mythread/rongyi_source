package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.PaymentDao;
import net.shopxx.entity.Payment;
import net.shopxx.service.PaymentService;
import net.shopxx.util.SerialNumberUtil;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 支付
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  84F252BF71A4D877C0285E8086FBE56D
 * ============================================================================
 */

@Service
public class PaymentServiceImpl extends BaseServiceImpl<Payment, String> implements PaymentService {
	
	@Resource
	private PaymentDao paymentDao;

	@Resource
	public void setBaseDao(PaymentDao paymentDao) {
		super.setBaseDao(paymentDao);
	}
	
	public String getLastPaymentSn() {
		return paymentDao.getLastPaymentSn();
	}
	
	public Payment getPaymentByPaymentSn(String paymentSn) {
		return paymentDao.getPaymentByPaymentSn(paymentSn);
	}

	// 重写对象，保存时自动设置支付编号
	@Override
	public String save(Payment payment) {
		payment.setPaymentSn(SerialNumberUtil.buildPaymentSn());
		return super.save(payment);
	}

}