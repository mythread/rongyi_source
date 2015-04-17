package net.shopxx.common;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;

import sun.misc.BASE64Decoder;

/**
 * SHOP++ 初始化
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  F493F4F9639AB767EED179DBF919AD85
 * ============================================================================
 */

public class Initializing implements InitializingBean {
	
	private String keyFile = "key";
	
	@Resource
	private ServletContext servletContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (servletContext != null) {
			BASE64Decoder bASE64Decoder = new BASE64Decoder();
            keyFile = new String(bASE64Decoder.decodeBuffer(keyFile + "A=="));
            Method readKey = Class.forName("net.shopxx.common.Key").getMethod("readKeyFile", String.class);
            /*String content = (String) readKey.invoke(null, servletContext.getRealPath(keyFile));
            servletContext.setAttribute(new String(bASE64Decoder.decodeBuffer("U0hPUFhYX0tFWQ==")), content);*/
		}
	}

	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

}