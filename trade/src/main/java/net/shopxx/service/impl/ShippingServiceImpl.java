package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.ShippingDao;
import net.shopxx.entity.Shipping;
import net.shopxx.service.ShippingService;
import net.shopxx.util.SerialNumberUtil;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 发货
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  EACE05E3DC84AC6892E51AD68CBADA74
 * ============================================================================
 */

@Service
public class ShippingServiceImpl extends BaseServiceImpl<Shipping, String> implements ShippingService {
	
	@Resource
	private ShippingDao shippingDao;

	@Resource
	public void setBaseDao(ShippingDao shippingDao) {
		super.setBaseDao(shippingDao);
	}
	
	public String getLastShippingSn() {
		return shippingDao.getLastShippingSn();
	}

	// 重写对象，保存时自动设置发货编号
	@Override
	public String save(Shipping shipping) {
		shipping.setShippingSn(SerialNumberUtil.buildShippingSn());
		return super.save(shipping);
	}

}