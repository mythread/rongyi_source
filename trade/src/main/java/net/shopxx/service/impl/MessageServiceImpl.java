package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.dao.MessageDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Message;
import net.shopxx.service.MessageService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 消息
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  65B94A1C020124AC5E38C179CA8D1CD0
 * ============================================================================
 */

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {
	
	@Resource
	private MessageDao messageDao;
	
	@Resource
	public void setBaseDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
	}
	
	public Pager getMemberInboxPager(Member member, Pager pager) {
		return messageDao.getMemberInboxPager(member, pager);
	}
	
	public Pager getMemberOutboxPager(Member member, Pager pager) {
		return messageDao.getMemberOutboxPager(member, pager);
	}
	
	public Pager getMemberDraftboxPager(Member member, Pager pager) {
		return messageDao.getMemberDraftboxPager(member, pager);
	}
	
	public Pager getAdminInboxPager(Pager pager) {
		return messageDao.getAdminInboxPager(pager);
	}
	
	public Pager getAdminOutboxPager(Pager pager) {
		return messageDao.getAdminOutboxPager(pager);
	}
	
	public Long getUnreadMessageCount(Member member) {
		return messageDao.getUnreadMessageCount(member);
	}
	
	public Long getUnreadMessageCount() {
		return messageDao.getUnreadMessageCount();
	}

}