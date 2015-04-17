package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.dao.LogDao;
import net.shopxx.entity.Log;
import net.shopxx.service.LogService;

import org.springframework.stereotype.Service;

/**
 * Service实现类 - 日志
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  1F694A92A7CFA4B05E87616ADCA7D169
 * ============================================================================
 */

@Service
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

	@Resource
	public void setBaseDao(LogDao logDao) {
		super.setBaseDao(logDao);
	}

}
