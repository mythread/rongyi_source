package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Area;

/**
 * Dao接口 - 地区
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  C0C633DBE1BACF5A05979923F6AD164F
 * ============================================================================
 */

public interface AreaDao extends BaseDao<Area, String> {
	
	/**
	 * 获取所有顶级地区集合;
	 * 
	 * @return 所有顶级地区集合
	 * 
	 */
	public List<Area> getRootAreaList();
	
	/**
	 * 根据Area对象获取所有上级地区集合，若无上级地区则返回null;
	 * 
	 * @return 上级地区集合
	 * 
	 */
	public List<Area> getParentAreaList(Area area);
	
	/**
	 * 根据Area对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Area> getChildrenAreaList(Area area);

	/**
	 * 根据地区名称及父类ID查询在其子类中是否唯一(parentId为null则查询根地区是否唯一)
	 * 
	 * @param id
	 *         父类ID
	 * 
	 */
	public Boolean isNameUnique(String parentId, String oldName, String newName);
	
}