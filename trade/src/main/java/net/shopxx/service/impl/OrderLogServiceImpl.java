package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.OrderLogDao;
import net.shopxx.entity.OrderLog;
import net.shopxx.service.OrderLogService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 订单日志
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  99551A0E5292A6C03298AFBE162F1450
 * ============================================================================
 */

@Service
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, String> implements OrderLogService {

	@Resource
	public void setBaseDao(OrderLogDao orderLogDao) {
		super.setBaseDao(orderLogDao);
	}

}