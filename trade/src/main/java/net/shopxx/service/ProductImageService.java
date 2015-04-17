package net.shopxx.service;

import java.io.File;

import net.shopxx.bean.ProductImage;


/**
 * Service接口 - 商品图片
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  89343F3383F923F8C0F63F568A7EC7A7
 * ============================================================================
 */

public interface ProductImageService {
	
	/**
	 * 生成商品图片（包括原图、大图、小图、缩略图）
	 * 
	 * @param uploadProductImageFile
	 *            上传图片文件
	 * 
	 */
	public ProductImage buildProductImage(File uploadProductImageFile);
	
	/**
	 * 根据ProductImage对象删除图片文件
	 * 
	 * @param productImage
	 *            ProductImage
	 * 
	 */
	public void deleteFile(ProductImage productImage);

}