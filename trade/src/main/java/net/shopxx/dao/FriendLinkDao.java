package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.FriendLink;

/**
 * Dao接口 - 友情链接
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  C3D6CEBD9415FBD14A8F32042E622780
 * ============================================================================
 */

public interface FriendLinkDao extends BaseDao<FriendLink, String> {

	/**
	 * 获取所有图片友情链接集合;
	 * 
	 * @return 图片友情链接集合
	 * 
	 */
	public List<FriendLink> getPictureFriendLinkList();
	
	/**
	 * 获取所有文字友情链接集合;
	 * 
	 * @return 图片友情链接集合
	 * 
	 */
	public List<FriendLink> getTextFriendLinkList();

}
