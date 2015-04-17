package net.shopxx.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 实体类 - 会员等级
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  FD5096449E0A05EE91B6802622B8C184
 * ============================================================================
 */

@Entity
@Table(name="ry_memberrank")
public class MemberRank extends BaseEntity {

	private static final long serialVersionUID = 3599029355500655209L;

	private String name;// 等級名称
	private Double preferentialScale;// 优惠百分比
	private Integer point;// 所需积分
	private Boolean isDefault;// 是否为默认等级
	
	private Set<Member> memberSet;// 会员

	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Double getPreferentialScale() {
		return preferentialScale;
	}

	public void setPreferentialScale(Double preferentialScale) {
		this.preferentialScale = preferentialScale;
	}
	
	@Column(nullable = false, unique = true)
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@OneToMany(mappedBy = "memberRank", fetch = FetchType.LAZY)
	public Set<Member> getMemberSet() {
		return memberSet;
	}

	public void setMemberSet(Set<Member> memberSet) {
		this.memberSet = memberSet;
	}

}