package net.shopxx.action.shop;

import javax.annotation.Resource;

import net.shopxx.service.MemberService;
import net.shopxx.service.MessageService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 前台Action类 - 会员中心
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  456CA553F200C7A4CFEC6788B8F51192
 * ============================================================================
 */

@ParentPackage("member")
public class MemberCenterAction extends BaseShopAction {

	private static final long serialVersionUID = -3568504222758246021L;
	
	@Resource
	MemberService memberService;
	@Resource
	MessageService messageService;
	
	// 会员中心首页
	public String index() {
		return "index";
	}

	// 获取未读消息数量
	public Long getUnreadMessageCount() {
		return messageService.getUnreadMessageCount(getLoginMember());
	}
	
}