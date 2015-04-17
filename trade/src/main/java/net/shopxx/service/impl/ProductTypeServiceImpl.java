package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.ProductTypeDao;
import net.shopxx.entity.ProductType;
import net.shopxx.service.ProductTypeService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 商品类型
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  11AE7AEEC7428ABAA892EED06C4E877F
 * ============================================================================
 */

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, String> implements
		ProductTypeService {

	@Resource
	public void setBaseDao(ProductTypeDao productTypeDao) {
		super.setBaseDao(productTypeDao);
	}

}
