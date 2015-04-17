package net.shopxx.action.admin;

import javax.annotation.Resource;

import net.shopxx.entity.Payment;
import net.shopxx.service.PaymentService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 支付
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  3D84C91974390915B764F72E0D09A3AA
 * ============================================================================
 */

@ParentPackage("admin")
public class PaymentAction extends BaseAdminAction {

	private static final long serialVersionUID = -4276446217262552805L;

	private Payment payment;

	@Resource
	private PaymentService paymentService;

	// 查看
	public String view() {
		payment = paymentService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = paymentService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		paymentService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}