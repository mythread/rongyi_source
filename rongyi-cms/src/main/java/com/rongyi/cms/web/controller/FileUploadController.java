package com.rongyi.cms.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import base.config.PropertyConfigurer;
import base.util.character.JsonUtil;
import base.util.file.FileUtil;

import com.rongyi.cms.constant.Constant;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

	private Logger logger = Logger.getLogger(this.getClass());
	private final static int MAX_SIZE = 10485760;//10MB
	@Autowired
	private PropertyConfigurer propertyConfigurer;
	/**
	 * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file"
	 * name="myfiles"/>
	 * 上传文件完毕后返回给前台[0`filepath],1表示上传成功(后跟上传后的文件路径),0表示失败(后跟失败描述)
	 */
	@RequestMapping(value = "/file")
	public String upload(@RequestParam MultipartFile[] myfiles, String paramsJson, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 使用在异步JSON里面
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (paramsJson == null) {
			resultMap.put("msg", "参数为NULL，请关闭再重试！");
			resultMap.put("status", 0);
			String json = JsonUtil.getJSONString(resultMap);
			logger.info(json);
			responseJson(response, json);
			return null;
		}
		int maxSize_ = MAX_SIZE;
		Map<String, Object> paramsMap = JsonUtil.getMapFromJson(paramsJson);
		String picPath = (String) paramsMap.get("picPath");
		String maxSize = (String) paramsMap.get("maxSize");
		String picType = (String) paramsMap.get("picType");
		String width = (String) paramsMap.get("width");
		String height = (String) paramsMap.get("height");
		if(maxSize!=null && StringUtils.isNotBlank(maxSize.trim())) {
			maxSize_ = Integer.parseInt(maxSize.trim());
			if(maxSize_ > MAX_SIZE) {
				maxSize_ = MAX_SIZE;
			}
		}
		
		// 可以在上传文件的同时接收其它参数
		logger.info("收到用户的文件上传请求");
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
		// 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
//		String realPath = request.getSession().getServletContext().getRealPath(File.separator + Constant.PicPath.rootPath + File.separator);
		String realPath = propertyConfigurer.getProperty(Constant.Common.IMAGE_ROOT_DIR).toString();
		if (picPath != null && StringUtils.isNotBlank(picPath.trim())) {
			realPath += picPath.trim();
		}
		logger.info("realPath>>>" + realPath);
		// 上传文件的原名(即上传前的文件名字)
		String originalFilename = null;
		// 新文件名
		String newName = null;
		// 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		// 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		// 上传多个文件时,前台表单中的所有<input
		// type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				resultMap.put("status", 0);
				resultMap.put("errorType", -1);
				resultMap.put("msg", "请选择文件后上传！");
				String json = JsonUtil.getJSONString(resultMap);
				logger.info(json);
				responseJson(response, json);
				return null;
			} else {
				originalFilename = myfile.getOriginalFilename();
				newName = myfile.getOriginalFilename();
				logger.info("文件原名: " + originalFilename);
				logger.info("文件名称: " + myfile.getName());
				logger.info("文件长度: " + myfile.getSize());
				if (myfile.getSize() >= maxSize_) {
					resultMap.put("status", 0);
					resultMap.put("errorType", 1);
					resultMap.put("msg", "文件大小超过了最大限制！");
					String json = JsonUtil.getJSONString(resultMap);
					logger.info(json);
					responseJson(response, json);
					return null;
				}
				logger.info("文件类型: " + myfile.getContentType());
				String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
				if (".exe".indexOf(fileType.toLowerCase()) > -1) {
					resultMap.put("status", 0);
					resultMap.put("errorType", 3);
					resultMap.put("msg", "文件类型不对！");
					String json = JsonUtil.getJSONString(resultMap);
					logger.info(json);
					responseJson(response, json);
					return null;
				}
				if(picType!=null && StringUtils.isNotBlank(picType.trim())) {
					if (picType.indexOf(fileType.toLowerCase()) == -1) {
						resultMap.put("status", 0);
						resultMap.put("errorType", 3);
						resultMap.put("msg", "文件类型不对！");
						String json = JsonUtil.getJSONString(resultMap);
						logger.info(json);
						responseJson(response, json);
						return null;
					}
				}
				if(StringUtils.isNotBlank(width) && StringUtils.isNotBlank(height)) {
					int width_ = Integer.parseInt(width);
					int height_ = Integer.parseInt(height);
					BufferedImage sourceImg =ImageIO.read(myfile.getInputStream());   
			        logger.info("宽："+sourceImg.getWidth());  
			        logger.info("高："+sourceImg.getHeight());  
			        if(sourceImg != null) {
			        	if(sourceImg.getWidth() != width_ || sourceImg.getHeight() != height_) {
			        		resultMap.put("status", 0);
			        		resultMap.put("errorType", 2);
							resultMap.put("msg", "素材宽高:"+sourceImg.getWidth()+"*"+sourceImg.getHeight()+"px,不符合要求!");
							String json = JsonUtil.getJSONString(resultMap);
							logger.info(json);
							responseJson(response, json);
							return null;
			        	}
			        }
				}
				logger.info("========================================");
				try {
					// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
					// 此处也可以使用Spring提供的MultipartFile.transferTo(File
					// dest)方法实现文件的上传
					if (originalFilename.indexOf(".") > -1) {
						newName = FileUtil.generateFileName() + originalFilename.substring(originalFilename.indexOf("."), originalFilename.length());
					}
					FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, newName));
				} catch (IOException e) {
					logger.info("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
					e.printStackTrace();
					resultMap.put("status", 0);
					resultMap.put("errorType", -1);
					resultMap.put("msg", "文件上传失败，请重试！");
					String json = JsonUtil.getJSONString(resultMap);
					logger.info(json);
					responseJson(response, json);
					return null;
				}
			}
		}
		// 此时在Windows下输出的是[D:\Develop\apache-tomcat-6.0.36\webapps\AjaxFileUpload\\upload\愤怒的小鸟.jpg]
		// logger.info(realPath + "\\" + originalFilename);
		// 此时在Windows下输出的是[/AjaxFileUpload/upload/愤怒的小鸟.jpg]
		// logger.info(request.getContextPath() + "/upload/" +
		// originalFilename);
		// 不推荐返回[realPath + "\\" + originalFilename]的值
		// 因为在Windows下<img src="file:///D:/aa.jpg">能被firefox显示,而<img
		// src="D:/aa.jpg">firefox是不认的
		resultMap.put("status", 1);
		resultMap.put("fileName", newName);
		resultMap.put("msg", "上传成功");
		String json = JsonUtil.getJSONString(resultMap);
		logger.info(json);
		responseJson(response, json);
		return null;
	}

	/**
	 * 向客户端输出指定格式的文��
	 * 
	 * @param response
	 *            响应
	 * @param text
	 *            要发的内��
	 * @param contentType
	 *            发送的格式
	 */
	public void doPrint(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().print(text);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.info("向客户端输出时发生异常 " + e.getMessage());
		}
	}

	/**
	 * 直接输出json
	 */
	public void responseJson(HttpServletResponse response, String json) {
		doPrint(response, json, "text/html;charset=UTF-8");
	}

}
