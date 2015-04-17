package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Navigation;

/**
 * Dao接口 - 导航
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  8AB0DD49D172E6AD636A61B24245294C
 * ============================================================================
 */

public interface NavigationDao extends BaseDao<Navigation, String> {

	/**
	 * 获取顶部Navigation对象集合（只包含isVisible=true的对象）
	 * 
	 * @return Navigation对象集合
	 * 
	 */
	public List<Navigation> getTopNavigationList();
	
	/**
	 * 获取中间Navigation对象集合（只包含isVisible=true的对象）
	 * 
	 * @return Navigation对象集合
	 * 
	 */
	public List<Navigation> getMiddleNavigationList();
	
	/**
	 * 获取底部Navigation对象集合（只包含isVisible=true的对象）
	 * 
	 * @return Navigation对象集合
	 * 
	 */
	public List<Navigation> getBottomNavigationList();
	
}
