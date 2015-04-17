<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div id="header">
	<div class="header-title">
		<h1><site:config key="header.mall.name"/></h1>
		<h2>导购终端机内容管理</h2>
	</div>
	<div id="nav">
		<ul>
			<li><a href="../adzones/index">广告活动管理</a></li>
			<li><a class="selected" href="../shops/list">商家信息管理</a></li>
		</ul>
	</div>
	<span class="header-user"><a href="../j_spring_security_logout">退出</a></span>
	<span class="header-user"><a href="../user/setPasswd">修改密码</a></span>
	<span class="header-user">欢迎登陆，<security:authentication property="Principal.name" /></span>
</div>