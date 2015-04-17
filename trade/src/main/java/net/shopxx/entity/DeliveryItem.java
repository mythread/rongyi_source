package net.shopxx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Bean类 - 物流项
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  DE21DFA17B86CB2B9C558AB35759D2E1
 * ============================================================================
 */

@Entity
@Table(name="ry_deliveryitem")
public class DeliveryItem extends BaseEntity {
	
	private static final long serialVersionUID = -6783787752984851646L;
	
	private String productSn;// 商品货号
	private String productName;// 商品名称
	private String productHtmlFilePath;// 商品HTML静态文件路径
	private Integer deliveryQuantity;// 物流数量
	
	private Product product;// 商品
	private Shipping shipping;// 发货
	private Reship reship;// 退货
	
	@Column(updatable = false, nullable = false)
	public String getProductSn() {
		return productSn;
	}
	
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
	
	@Column(updatable = false, nullable = false)
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Column(nullable = false, updatable = false)
	public String getProductHtmlFilePath() {
		return productHtmlFilePath;
	}
	
	public void setProductHtmlFilePath(String productHtmlFilePath) {
		this.productHtmlFilePath = productHtmlFilePath;
	}
	
	@Column(updatable = false, nullable = false)
	public Integer getDeliveryQuantity() {
		return deliveryQuantity;
	}

	public void setDeliveryQuantity(Integer deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Shipping getShipping() {
		return shipping;
	}
	
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public Reship getReship() {
		return reship;
	}

	public void setReship(Reship reship) {
		this.reship = reship;
	}
	
}