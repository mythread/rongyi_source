package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.ProductAttribute;
import net.shopxx.entity.ProductType;

/**
 * Dao接口 - 商品属性
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  4D051F4491A9917665E897135777FB7C
 * ============================================================================
 */

public interface ProductAttributeDao extends BaseDao<ProductAttribute, String> {
	
	/**
	 * 获取已启用的商品属性.
	 * 
	 * @return 已启用的商品属性集合.
	 */
	public List<ProductAttribute> getEnabledProductAttributeList();
	
	/**
	 * 根据商品类型获取已启用的商品属性.
	 * 
	 * @return 已启用的商品属性集合.
	 */
	public List<ProductAttribute> getEnabledProductAttributeList(ProductType productType);
	
	/**
	 * 根据商品类型、商品名称查找，若不存在则返回null
	 * 
	 * @param productType
	 *            商品类型
	 * 
	 * @param name
	 *            商品属性名称 
	 * 
	 */
	public ProductAttribute getProductAttribute(ProductType productType, String name);

}