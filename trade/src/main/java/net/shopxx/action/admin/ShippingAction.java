package net.shopxx.action.admin;

import javax.annotation.Resource;

import net.shopxx.entity.Shipping;
import net.shopxx.service.ShippingService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 发货
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  3C9F59418B032ED2F7727267615A9A24
 * ============================================================================
 */

@ParentPackage("admin")
public class ShippingAction extends BaseAdminAction {

	private static final long serialVersionUID = 3763457558863574061L;

	private Shipping shipping;

	@Resource
	private ShippingService shippingService;

	// 查看
	public String view() {
		shipping = shippingService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = shippingService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		try {
			shippingService.delete(ids);
		} catch (Exception e) {
			return ajaxJsonErrorMessage("删除失败，会员数据被引用！");
		}
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

}