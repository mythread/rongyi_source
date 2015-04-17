package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.dao.OrderDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.service.OrderService;
import net.shopxx.util.SerialNumberUtil;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 订单
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  DD7076EB801A97081A271D1D0D7AF8F7
 * ============================================================================
 */

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {
	
	@Resource
	private OrderDao orderDao;

	@Resource
	public void setBaseDao(OrderDao orderDao) {
		super.setBaseDao(orderDao);
	}
	
	public String getLastOrderSn() {
		return orderDao.getLastOrderSn();
	}
	
	public Pager getOrderPager(Member member, Pager pager) {
		return orderDao.getOrderPager(member, pager);
	}
	
	public Long getUnprocessedOrderCount() {
		return orderDao.getUnprocessedOrderCount();
	}
	
	public Long getPaidUnshippedOrderCount() {
		return orderDao.getPaidUnshippedOrderCount();
	}

	// 重写对象，保存时自动设置订单编号
	@Override
	public String save(Order order) {
		order.setOrderSn(SerialNumberUtil.buildOrderSn());
		return super.save(order);
	}

}