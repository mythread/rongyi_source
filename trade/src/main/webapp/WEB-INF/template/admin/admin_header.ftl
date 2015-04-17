﻿<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心 -${systemConfig.systemName}</title>
<meta name="Author" content="Rongyi Team" />
<meta name="Copyright" content="rongyi" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/header.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	DD_belatedPNG.fix(".headerLogo");
</script>
<![endif]-->
</head>
<body class="header">
	<div class="body">
		<div class="headerLogo"></div>
		<div class="headerTop">
			<div class="headerLink">
				<span class="welcome">
					<strong><@sec.authentication property="name" /></strong>&nbsp;您好!&nbsp;
				</span>
				<a href="admin!index.action" target="mainFrame">后台首页</a>|
			</div>
		</div>
		<div class="headerBottom">
			<div class="headerMenu">
				<div class="menuLeft"></div>
				<ul>
	            	<li><a href="menu!product.action" target="menuFrame" hidefocus="true">商品管理</a></li>
	                <li><a href="menu!order.action" target="menuFrame" hidefocus="true">订单管理</a></li>
			<li><a href="menu!activity.action" target="menuFrame" hidefocus="true">活动管理</a></li>
	                <li><a href="menu!member.action" target="menuFrame" hidefocus="true">会员管理</a></li>
	                <li><a href="menu!admin.action" target="menuFrame" hidefocus="true">管理员</a></li>
			<li><a href="menu!comment.action" target="menuFrame" hidefocus="true">评论管理</a></li>
	            </ul>
	            <div class="menuRight"></div>
			</div>
			<div class="userInfo">
				<a href="admin_profile!edit.action" target="mainFrame">
					<span class="profileIcon">&nbsp;</span>个人资料
				</a>
				<a href="${base}/admin/logout" target="_top">
					<span class="logoutIcon">&nbsp;</span>退出&nbsp
				</a>
			</div>
		</div>
	</div>
</body>
</html>