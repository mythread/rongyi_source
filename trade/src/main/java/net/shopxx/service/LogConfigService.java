package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.LogConfig;

/**
 * Service接口 - 日志设置
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  915A2A2F843137F904AA2D6AFAD31305
 * ============================================================================
 */

public interface LogConfigService extends BaseService<LogConfig, String> {

	/**
	 * 根据Action类名称获取LogConfig对象集合.
	 * 
	 * @param actionClassName
	 *            Action类名称
	 * @return LogConfig对象集合
	 */
	public List<LogConfig> getLogConfigList(String actionClassName);

}