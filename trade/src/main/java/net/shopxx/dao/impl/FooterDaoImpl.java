package net.shopxx.dao.impl;

import net.shopxx.dao.FooterDao;
import net.shopxx.entity.Footer;

import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 网页底部信息
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  BA46AA09A308786C17905F09A398AB80
 * ============================================================================
 */

@Repository
public class FooterDaoImpl extends BaseDaoImpl<Footer, String> implements FooterDao {

	public Footer getFooter() {
		return load(Footer.FOOTER_ID);
	}

}
