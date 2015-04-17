package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Article;
import net.shopxx.entity.ArticleCategory;

/**
 * Service接口 - 文章分类
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  F631FF5629197454FEED5CB4F84F0ED2
 * ============================================================================
 */

public interface ArticleCategoryService extends BaseService<ArticleCategory, String> {
	
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
	 * 根据Article对象获取所有父类集合，若无父类则返回null;
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<ArticleCategory> getParentArticleCategoryList(Article article);
	
	/**
	 * 根据ArticleCategory对象获取路径集合;
	 * 
	 * @return ArticleCategory集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryPathList(ArticleCategory articleCategory);
	
	/**
	 * 根据Article对象获取路径集合;
	 * 
	 * @return ArticleCategory集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryPathList(Article article);
	
	/**
	 * 根据ArticleCategory对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<ArticleCategory> getChildrenArticleCategoryList(ArticleCategory articleCategory);
	
	/**
	 * 根据Article对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<ArticleCategory> getChildrenArticleCategoryList(Article article);
	
	/**
	 * 获取文章分类树集合;
	 * 
	 * @return 文章分类树集合
	 * 
	 */
	public List<ArticleCategory> getArticleCategoryTreeList();

}