package net.shopxx.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 实体类 - 品牌
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  57610AD84D0AD8B8CD2894FA77AC348C
 * ============================================================================
 */


@Entity
@Table(name="ry_brand")
public class Brand extends BaseEntity {

	private static final long serialVersionUID = -6109590619136943215L;

	private String name;// 名称
	private String logo;// Logo图片路径
	private String url;// 网址
	private String introduction;// 介绍
	private Integer orderList;// 排序
	private String engName;//品牌英文名
	private Set<Product> productSet;// 商品

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

	@Column(length = 10000)
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}

    
    public String getEngName() {
        return engName;
    }

    
    public void setEngName(String engName) {
        this.engName = engName;
    }

}