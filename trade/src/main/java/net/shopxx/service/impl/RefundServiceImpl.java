package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.RefundDao;
import net.shopxx.entity.Refund;
import net.shopxx.service.RefundService;
import net.shopxx.util.SerialNumberUtil;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 退款
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  33368CD07557B60629179EF8EDB49808
 * ============================================================================
 */

@Service
public class RefundServiceImpl extends BaseServiceImpl<Refund, String> implements RefundService {
	
	@Resource
	private RefundDao refundDao;

	@Resource
	public void setBaseDao(RefundDao refundDao) {
		super.setBaseDao(refundDao);
	}
	
	public String getLastRefundSn() {
		return refundDao.getLastRefundSn();
	}
	
	public Refund getRefundByRefundSn(String refundSn) {
		return refundDao.getRefundByRefundSn(refundSn);
	}

	// 重写对象，保存时自动设置退款编号
	@Override
	public String save(Refund refund) {
		refund.setRefundSn(SerialNumberUtil.buildRefundSn());
		return super.save(refund);
	}

}