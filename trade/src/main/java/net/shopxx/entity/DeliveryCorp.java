package net.shopxx.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 实体类 - 物流公司
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  59CDA5BD112307CE31AA07638136F25A
 * ============================================================================
 */

@Entity
@Table(name="ry_deliverycorp")
public class DeliveryCorp extends BaseEntity {

	private static final long serialVersionUID = 10595703086045998L;

	private String name;// 名称
	private String url;// 网址
	private Integer orderList;// 排序
	
	private Set<DeliveryType> deliveryTypeSet;// 配送方式

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "defaultDeliveryCorp")
	public Set<DeliveryType> getDeliveryTypeSet() {
		return deliveryTypeSet;
	}

	public void setDeliveryTypeSet(Set<DeliveryType> deliveryTypeSet) {
		this.deliveryTypeSet = deliveryTypeSet;
	}

}