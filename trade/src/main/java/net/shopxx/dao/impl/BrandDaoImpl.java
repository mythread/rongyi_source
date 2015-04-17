package net.shopxx.dao.impl;

import java.util.Set;

import net.shopxx.dao.BrandDao;
import net.shopxx.entity.Brand;
import net.shopxx.entity.Product;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 品牌
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository
public class BrandDaoImpl extends BaseDaoImpl<Brand, String> implements BrandDao {
	
	// 关联处理
	@Override
	public void delete(Brand brand) {
		Set<Product> productSet = brand.getProductSet();
		if (productSet != null) {
			for (Product product : productSet) {
				product.setBrand(null);
			}
		}
		super.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Brand brand = load(id);
		this.delete(brand);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Brand brand = load(id);
			this.delete(brand);
		}
	}
	
}