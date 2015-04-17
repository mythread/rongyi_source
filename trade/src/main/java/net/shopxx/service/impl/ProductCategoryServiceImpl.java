package net.shopxx.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.dao.ProductCategoryDao;
import net.shopxx.entity.Product;
import net.shopxx.entity.ProductCategory;
import net.shopxx.service.ProductCategoryService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 商品分类
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  9C63255F0E5AAEE7DD3D83FB323FC00D
 * ============================================================================
 */

@Service
public class ProductCategoryServiceImpl extends BaseServiceImpl<ProductCategory, String> implements
		ProductCategoryService {

	@Resource
	private ProductCategoryDao productCategoryDao;
	
	@Resource
	public void setBaseDao(ProductCategoryDao productCategoryDao) {
		super.setBaseDao(productCategoryDao);
	}

	@Cacheable(modelId = "caching")
	public List<ProductCategory> getRootProductCategoryList() {
		List<ProductCategory> rootProductCategoryList = productCategoryDao.getRootProductCategoryList();
		if (rootProductCategoryList != null) {
			for (ProductCategory rootProductCategory : rootProductCategoryList) {
				Hibernate.initialize(rootProductCategory);
			}
		}
		return rootProductCategoryList;
	}
	
	@Cacheable(modelId = "caching")
	public List<ProductCategory> getParentProductCategoryList(ProductCategory productCategory) {
		List<ProductCategory> parentProductCategoryList = productCategoryDao.getParentProductCategoryList(productCategory);
		if (parentProductCategoryList != null) {
			for (ProductCategory parentProductCategory : parentProductCategoryList) {
				Hibernate.initialize(parentProductCategory);
			}
		}
		return parentProductCategoryList;
	}
	
	public List<ProductCategory> getParentProductCategoryList(Product product) {
		ProductCategory productCategory = product.getProductCategory();
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.addAll(this.getParentProductCategoryList(productCategory));
		productCategoryList.add(productCategory);
		return productCategoryList;
	}
	
	public List<ProductCategory> getProductCategoryPathList(ProductCategory productCategory) {
		List<ProductCategory> productCategoryPathList = new ArrayList<ProductCategory>();
		productCategoryPathList.addAll(this.getParentProductCategoryList(productCategory));
		productCategoryPathList.add(productCategory);
		return productCategoryPathList;
	}
	
	public List<ProductCategory> getProductCategoryPathList(Product product) {
		ProductCategory productCategory = product.getProductCategory();
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.addAll(this.getParentProductCategoryList(productCategory));
		productCategoryList.add(productCategory);
		return productCategoryList;
	}
	
	@Cacheable(modelId = "caching")
	public List<ProductCategory> getChildrenProductCategoryList(ProductCategory productCategory) {
		List<ProductCategory> childrenProductCategoryList = productCategoryDao.getChildrenProductCategoryList(productCategory);
		if (childrenProductCategoryList != null) {
			for (ProductCategory childrenProductCategory : childrenProductCategoryList) {
				Hibernate.initialize(childrenProductCategory);
			}
		}
		return childrenProductCategoryList;
	}
	
	public List<ProductCategory> getChildrenProductCategoryList(Product product) {
		ProductCategory productCategory = product.getProductCategory();
		List<ProductCategory> productCategoryList = getChildrenProductCategoryList(productCategory);
		if (productCategoryList == null) {
			productCategoryList = new ArrayList<ProductCategory>();
		}
		productCategoryList.add(productCategory);
		return productCategoryList;
	}
	
	@Cacheable(modelId = "caching")
	public List<ProductCategory> getProductCategoryTreeList() {
		List<ProductCategory> allProductCategoryList = this.getAll();
		return recursivProductCategoryTreeList(allProductCategoryList, null, null);
	}
	
	// 递归父类排序分类树
	private List<ProductCategory> recursivProductCategoryTreeList(List<ProductCategory> allProductCategoryList, ProductCategory p, List<ProductCategory> temp) {
		if (temp == null) {
			temp = new ArrayList<ProductCategory>();
		}
		for (ProductCategory productCategory : allProductCategoryList) {
			ProductCategory parent = productCategory.getParent();
			if ((p == null && parent == null) || (productCategory != null && parent == p)) {
				temp.add(productCategory);
				if (productCategory.getChildren() != null && productCategory.getChildren().size() > 0) {
					recursivProductCategoryTreeList(allProductCategoryList, productCategory, temp);
				}
			}
		}
		return temp;
	}

	@Override
	@Cacheable(modelId = "caching")
	public List<ProductCategory> getAll() {
		List<ProductCategory> allProductCategory = productCategoryDao.getAll();
		if (allProductCategory != null) {
			for (ProductCategory productCategory : allProductCategory) {
				Hibernate.initialize(productCategory);
			}
		}
		return allProductCategory;
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(ProductCategory productCategory) {
		productCategoryDao.delete(productCategory);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		productCategoryDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		productCategoryDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(ProductCategory productCategory) {
		return productCategoryDao.save(productCategory);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(ProductCategory productCategory) {
		productCategoryDao.update(productCategory);
	}

}