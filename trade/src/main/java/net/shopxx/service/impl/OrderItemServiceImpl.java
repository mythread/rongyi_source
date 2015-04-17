package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.OrderItemDao;
import net.shopxx.entity.OrderItem;
import net.shopxx.service.OrderItemService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 订单项
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  3CCC6CA8E9A226C1626A7887BB102AC6
 * ============================================================================
 */

@Service
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, String> implements OrderItemService {

	@Resource
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}

}