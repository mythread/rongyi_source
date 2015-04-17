package net.shopxx.action.admin;

import javax.annotation.Resource;

import net.shopxx.entity.Reship;
import net.shopxx.service.ReshipService;

import org.apache.struts2.convention.annotation.ParentPackage;

/**
 * 后台Action类 - 退货
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  8EAAC961EFD4ACBAA255A8B63695E97B
 * ============================================================================
 */

@ParentPackage("admin")
public class ReshipAction extends BaseAdminAction {

	private static final long serialVersionUID = -571777768643219736L;

	private Reship reship;

	@Resource
	private ReshipService reshipService;

	// 查看
	public String view() {
		reship = reshipService.load(id);
		return VIEW;
	}

	// 列表
	public String list() {
		pager = reshipService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		try {
			reshipService.delete(ids);
		} catch (Exception e) {
			return ajaxJsonErrorMessage("删除失败，会员数据被引用！");
		}
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Reship getReship() {
		return reship;
	}

	public void setReship(Reship reship) {
		this.reship = reship;
	}

}