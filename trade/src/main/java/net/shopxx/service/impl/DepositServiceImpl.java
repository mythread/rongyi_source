package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.dao.DepositDao;
import net.shopxx.entity.Deposit;
import net.shopxx.entity.Member;
import net.shopxx.service.DepositService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 预存款记录
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  622F01BF112F3C630E9962A378F07787
 * ============================================================================
 */

@Service
public class DepositServiceImpl extends BaseServiceImpl<Deposit, String> implements DepositService {

	@Resource
	private DepositDao depositDao;
	
	@Resource
	public void setBaseDao(DepositDao depositDao) {
		super.setBaseDao(depositDao);
	}
	
	public Pager getDepositPager(Member member, Pager pager) {
		return depositDao.getDepositPager(member, pager);
	}

}