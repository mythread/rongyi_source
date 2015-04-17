package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.CartItemDao;
import net.shopxx.entity.CartItem;
import net.shopxx.service.CartItemService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 品牌
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  B29936B2E4DBE58A5D88D80B8580D7A5
 * ============================================================================
 */

@Service
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, String> implements CartItemService {

	@Resource
	public void setBaseDao(CartItemDao cartItemDao) {
		super.setBaseDao(cartItemDao);
	}

}