package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.DeliveryItemDao;
import net.shopxx.entity.DeliveryItem;
import net.shopxx.service.DeliveryItemService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 发货项
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  5CCDCA53AF8463D621530B1ADA0CE130
 * ============================================================================
 */

@Service
public class DeliveryItemServiceImpl extends BaseServiceImpl<DeliveryItem, String> implements DeliveryItemService {

	@Resource
	public void setBaseDao(DeliveryItemDao deliveryItemDao) {
		super.setBaseDao(deliveryItemDao);
	}

}