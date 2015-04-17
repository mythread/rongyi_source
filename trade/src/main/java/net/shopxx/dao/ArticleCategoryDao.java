package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.ArticleCategory;

/**
 * Dao接口 - 文章分类
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  D693C9587F7A89A2474E4066ECEB93DE
 * ============================================================================
 */

public interface ArticleCategoryDao extends BaseDao<ArticleCategory, String> {
	
	/**
	 * 获取所有顶级文章分类集合;
	 * 
	 * @return 所有顶级文章分类集合
	 * 
	 */
	public List<ArticleCategory> getRootArticleCategoryList();
	
	/**
	 * 根据ArticleCategory对象获取所有父类集合，若无父类则返回null;
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ArticleCategory> getParentArticleCategoryList(ArticleCategory articleCategory);
	
	/**
	 * 根据ArticleCategory对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory);

}
