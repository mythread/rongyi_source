package net.shopxx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 实体类 - 收货地址
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  1AC48FFD1F30E2FC6E5453A007CAD39D
 * ============================================================================
 */

@Entity
@Table(name="ry_receiver")
public class Receiver extends BaseEntity {

	private static final long serialVersionUID = 6667934590013089481L;
	
	public static final Integer MAX_RECEIVER_COUNT = 10;// 会员收货地址最大保存数，为null则无限制

	private String name;// 收货人姓名
	private String areaPath;// 收货地区路径
	private String address;// 地址
	private String phone;// 电话
	private String mobile;// 手机
	private String zipCode;// 邮编
	private Boolean isDefault;// 是否默认
	
	private Member member;// 会员

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, length = 10000)
	public String getAreaPath() {
		return areaPath;
	}

	public void setAreaPath(String areaPath) {
		this.areaPath = areaPath;
	}
	
	@Column(nullable = false, length = 5000)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	@Column(nullable = false)
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}