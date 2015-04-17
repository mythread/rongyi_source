package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.LogConfig;

/**
 * Dao接口 - 日志设置
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  C9EFB0719EC4C5405BF9AA770CDB3AC5
 * ============================================================================
 */

public interface LogConfigDao extends BaseDao<LogConfig, String> {

	/**
	 * 根据Action类名称获取LogConfig对象集合.
	 * 
	 * @param actionClassName
	 *            Action类名称
	 * @return LogConfig对象集合
	 */
	public List<LogConfig> getLogConfigList(String actionClassName);

}
