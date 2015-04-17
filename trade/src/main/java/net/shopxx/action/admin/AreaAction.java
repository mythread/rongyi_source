package net.shopxx.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.shopxx.entity.Area;
import net.shopxx.service.AreaService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 鍚庡彴Action绫�- 鍦板尯
 * ============================================================================
 *  銆�
 * ----------------------------------------------------------------------------
 *  銆�
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  AECC1B974FB9EE5128F3B8A118CCEC03
 * ============================================================================
 */

@ParentPackage("admin")
public class AreaAction extends BaseAdminAction {

	private static final long serialVersionUID = 6254431866456845575L;

	private Area area;
	private String parentId;
	private Area parent;
	private List<Area> areaList = new ArrayList<Area>();

	@Resource
	private AreaService areaService;

	// 鏄惁宸插瓨鍦╝jax楠岃瘉
	public String checkName() {
		String oldName = getParameter("oldValue");
		String parentId = getParameter("parentId");
		String newName = area.getName();
		if (areaService.isNameUnique(parentId, oldName, newName)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 娣诲姞
	public String add() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = areaService.load(parentId);
		}
		return INPUT;
	}

	// 缂栬緫
	public String edit() {
		area = areaService.load(id);
		parent = area.getParent();
		return INPUT;
	}

	// 鍒楄〃
	public String list() {
		if (StringUtils.isNotEmpty(parentId)) {
			parent = areaService.load(parentId);
			areaList = new ArrayList<Area>(parent.getChildren());
		} else {
			areaList = areaService.getRootAreaList();
		}
		return LIST;
	}

	// 鍒犻櫎
	public String delete() {
		areaService.delete(id);
		return ajaxJsonSuccessMessage("鍒犻櫎鎴愬姛锛�");
	}

	// 淇濆瓨
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "鍦板尯鍚嶇О涓嶅厑璁镐负绌�")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		String newName = area.getName();
		if (!areaService.isNameUnique(parentId, null, newName)) {
			addActionError("鍦板尯鍚嶇О宸插瓨鍦�");
			return ERROR;
		}
		if (StringUtils.isNotEmpty(parentId)) {
			area.setParent(areaService.load(parentId));
		} else {
			area.setParent(null);
		}
		areaService.save(area);
		redirectionUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}

	// 鏇存柊
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "area.name", message = "鍦板尯鍚嶇О涓嶅厑璁镐负绌�")
		}
	)
	public String update() {
		Area persistent = areaService.load(id);
		Area parent = persistent.getParent();
		if (parent != null) {
			parentId = parent.getId();
		}
		if (!areaService.isNameUnique(parentId, persistent.getName(), area.getName())) {
			addActionError("鍦板尯鍚嶇О宸插瓨鍦�");
			return ERROR;
		}
		persistent.setName(area.getName());
		areaService.update(persistent);
		redirectionUrl = "area!list.action?parentId=" + parentId;
		return SUCCESS;
	}
	
	// 鏍规嵁鍦板尯Path鍊艰幏鍙栦笅绾у湴鍖篔SON鏁版嵁
	public String ajaxChildrenArea() {
		String path = getParameter("path");
		if (StringUtils.contains(path,  Area.PATH_SEPARATOR)) {
			id = StringUtils.substringAfterLast(path, Area.PATH_SEPARATOR);
		} else {
			id = path;
		}
		List<Area> childrenAreaList = new ArrayList<Area>();
		if (StringUtils.isEmpty(id)) {
			childrenAreaList = areaService.getRootAreaList();
		} else {
			childrenAreaList = new ArrayList<Area>(areaService.load(id).getChildren());
		}
		List<Map<String, String>> optionList = new ArrayList<Map<String, String>>();
		for (Area area : childrenAreaList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", area.getName());
			map.put("value", area.getPath());
			optionList.add(map);
		}
		JSONArray jsonArray = JSONArray.fromObject(optionList);
		return ajaxJson(jsonArray.toString());
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

}