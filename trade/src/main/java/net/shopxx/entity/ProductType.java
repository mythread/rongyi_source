package net.shopxx.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OrderBy;

/**
 * 实体类 - 商品类型
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  9205E70ECB8E9EA3CF1669CBB8C6FCAF
 * ============================================================================
 */

@Entity
@Table(name="ry_producttype")
public class ProductType extends BaseEntity {

	private static final long serialVersionUID = -6173231303962800416L;

	private String name;// 类型名称
	
	private List<ProductAttribute> productAttributeList;// 商品属性
	private Set<Product> productSet;// 商品

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productType")
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy(clause = "orderList asc")
	public List<ProductAttribute> getProductAttributeList() {
		return productAttributeList;
	}

	public void setProductAttributeList(List<ProductAttribute> productAttributeList) {
		this.productAttributeList = productAttributeList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productType")
	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}
	
	// 获得已启用的商品属性
	@Transient
	public List<ProductAttribute> getEnabledProductAttributeList() {
		if (productAttributeList == null) {
			return null;
		}
		List<ProductAttribute> enabledProductAttributeList = new ArrayList<ProductAttribute>();
		for (ProductAttribute productAttribute : productAttributeList) {
			if (productAttribute.getIsEnabled()) {
				enabledProductAttributeList.add(productAttribute);
			}
		}
		return enabledProductAttributeList;
	}

}