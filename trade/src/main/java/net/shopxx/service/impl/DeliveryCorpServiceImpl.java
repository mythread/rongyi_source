package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.DeliveryCorpDao;
import net.shopxx.entity.DeliveryCorp;
import net.shopxx.service.DeliveryCorpService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 物流公司
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  F20B6E705344AD3757A3740CDC66E896
 * ============================================================================
 */

@Service
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, String> implements DeliveryCorpService {

	@Resource
	public void setBaseDao(DeliveryCorpDao deliveryCorpDao) {
		super.setBaseDao(deliveryCorpDao);
	}

}
