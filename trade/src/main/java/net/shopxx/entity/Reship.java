package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import net.shopxx.util.SystemConfigUtil;

/**
 * 实体类 - 退货
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  B78A0828B86D9B288F5C60E3181EB7C1
 * ============================================================================
 */

@Entity
@Table(name="ry_reship")
public class Reship extends BaseEntity {

	private static final long serialVersionUID = 4439185740262484180L;
	
	private String reshipSn;// 退货编号
	private String deliveryTypeName;// 配送方式名称
	private String deliveryCorpName; // 物流公司名称
	private String deliverySn;// 物流编号
	private BigDecimal deliveryFee;// 物流费用
	private String shipName;// 退货人姓名
	private String shipArea;// 退货地区
	private String shipAreaPath;// 退货地区路径
	private String shipAddress;// 退货地址
	private String shipZipCode;// 退货邮编
	private String shipPhone;// 退货电话
	private String shipMobile;// 退货手机
	private String memo;// 备注
	
	private Order order;// 订单
	private DeliveryType deliveryType;// 配送方式
	private Set<DeliveryItem> deliveryItemSet;// 物流项
	
	@Column(updatable = false, nullable = false, unique = true)
	public String getReshipSn() {
		return reshipSn;
	}

	public void setReshipSn(String reshipSn) {
		this.reshipSn = reshipSn;
	}

	@Column(updatable = false, nullable = false)
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	
	@Column(updatable = false, nullable = false)
	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}
	
	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}
	
	@Column(updatable = false)
	public String getDeliverySn() {
		return deliverySn;
	}
	
	public void setDeliverySn(String deliverySn) {
		this.deliverySn = deliverySn;
	}
	
	@Column(updatable = false, nullable = false)
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}
	
	// 精度处理
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = SystemConfigUtil.getPriceScaleBigDecimal(deliveryFee);
	}
	
	@Column(updatable = false, nullable = false)
	public String getShipName() {
		return shipName;
	}
	
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	@Column(updatable = false, nullable = false)
	public String getShipArea() {
		return shipArea;
	}
	
	public void setShipArea(String shipArea) {
		this.shipArea = shipArea;
	}
	
	@Column(updatable = false)
	public String getShipAreaPath() {
		return shipAreaPath;
	}
	
	public void setShipAreaPath(String shipAreaPath) {
		this.shipAreaPath = shipAreaPath;
	}
	
	@Column(updatable = false, nullable = false)
	public String getShipAddress() {
		return shipAddress;
	}
	
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	
	@Column(updatable = false, nullable = false)
	public String getShipZipCode() {
		return shipZipCode;
	}
	
	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}
	
	@Column(updatable = false)
	public String getShipPhone() {
		return shipPhone;
	}
	
	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}
	
	@Column(updatable = false)
	public String getShipMobile() {
		return shipMobile;
	}
	
	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}
	
	@Column(updatable = false)
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}
	
	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	@OneToMany(mappedBy = "reship", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.DELETE })
	@OrderBy("createDate asc")
	public Set<DeliveryItem> getDeliveryItemSet() {
		return deliveryItemSet;
	}

	public void setDeliveryItemSet(Set<DeliveryItem> deliveryItemSet) {
		this.deliveryItemSet = deliveryItemSet;
	}

}