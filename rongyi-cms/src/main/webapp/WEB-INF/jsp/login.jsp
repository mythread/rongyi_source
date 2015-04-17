<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="site" uri="http://rongyi.com/taglibs" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理</title>
<link href="<%=request.getContextPath()%>/css/login/index.css" rel="stylesheet" type="text/css" />
</head>
<body onload='document.f.j_username.focus();'>
	
	<div class="main-container">
	
	<div id="header">
			<h1><site:config key="header.mall.name"/></h1>
			<h2>导购终端机内容管理</h2>
	</div>
	
	<div id="logincontainer">
		
		
		
			<form name='f' action="<%=request.getContextPath()%>/j_spring_security_check"	method='POST'>
				
				<p>用户名</p>
				<div class="inputcontainer">
					<input type="text" id="username" name="j_username" placeholder="请输入用户名" />
				</div>
				<c:if test="${not empty error}">
					<div class="errorblock">
						登录失败。原因：用户名或密码错误！
					</div>
				</c:if>
				<p>密码</p>
				<div class="inputcontainer">
					<input type="password" id="password" name="j_password" placeholder="请输入密码" />
				</div>
				<input type="submit" value="登&nbsp;&nbsp;&nbsp;&nbsp;录" class="loginsubmit" />
			</form>
		
		
	</div>
	
	
	</div>
	<div id="footer">
			Copyright © 2014 容易网 | Designed by www.rongyi.com 上海容易网电子商务有限公司
	</div>
</body>
</html>