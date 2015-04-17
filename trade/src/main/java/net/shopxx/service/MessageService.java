package net.shopxx.service;

import net.shopxx.bean.Pager;
import net.shopxx.entity.Member;
import net.shopxx.entity.Message;

/**
 * Service接口 - 消息
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  0173E6044D90E14E7C66F5208467F4F3
 * ============================================================================
 */

public interface MessageService extends BaseService<Message, String>{
	
	/**
	 * 根据Member、Pager获取会员收件箱分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 收件箱分页对象
	 */
	public Pager getMemberInboxPager(Member member, Pager pager);

	/**
	 * 根据Member、Pager获取会员发件箱分页对象
	 * 
	 * @param member
	 *            Member对象
	 *            
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 发件箱页对象
	 */
	public Pager getMemberOutboxPager(Member member, Pager pager);
	
	/**
	 * 根据Member、Pager获取会员草稿箱分页对象
	 * 
	 * @param member
	 *            Member对象
	 * 
	 * @param pager
	 *            Pager对象
	 *            
	 * @return 草稿箱分页对象
	 */
	public Pager getMemberDraftboxPager(Member member, Pager pager);
	
	/**
	 * 根据Pager获取管理员收件箱消息分页对象
	 * 
	 * @param pager
	 *            Pager对象
	 * 
	 * @return 收件箱分页对象
	 */
	public Pager getAdminInboxPager(Pager pager);
	
	/**
	 * 根据Pager获取管理员发件箱消息分页对象
	 * 
	 * @param pager
	 *            Pager对象
	 * 
	 * @return 发件箱分页对象
	 */
	public Pager getAdminOutboxPager(Pager pager);
	
	/**
	 * 根据Member获取未读消息数量
	 * 
	 * @param member
	 *            Member对象
	 * 
	 * @return 未读消息数量
	 */
	public Long getUnreadMessageCount(Member member);
	
	/**
	 * 获取管理员未读消息数
	 * 
	 * @return 未读消息数量
	 */
	public Long getUnreadMessageCount();
	
}