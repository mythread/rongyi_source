package net.shopxx.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * 实体类 - 导航
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  71F67C527A1B7EDF054BD395F75B7C4C
 * ============================================================================
 */

@Entity
@Table(name="ry_navigation")
public class Navigation extends BaseEntity {

	private static final long serialVersionUID = -7635757647887646795L;

	// 导航位置:顶部、中间、底部
	public enum Position {
		top, middle, bottom
	}

	private String name;// 名称
	private Position position;// 位置
	private String url;// 链接地址;
	private Boolean isVisible;// 是否显示
	private Boolean isBlankTarget;// 是否在新窗口中打开
	private Integer orderList;// 排序

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(nullable = false)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@Column(nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(nullable = false)
	public Boolean getIsBlankTarget() {
		return isBlankTarget;
	}

	public void setIsBlankTarget(Boolean isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
	}

	@Column(nullable = false)
	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	@Column(nullable = false)
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}

}