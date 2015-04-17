<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<h1>Change Password</h1>
<h3>${message}</h3>
<form name='f' action="/user/changePassword" method="post" id="f">
	<table>
		<tr>
		<th>原密码：</th>
		<td>
		 <input id="oldpassword" name="oldpassword" size="20" maxlength="50" type="password"/>
		<td/>
		<tr/>
		<tr>
		<th>新密码：</th>
		<td>
		<input id="newpassword" name="newpassword" size="20" maxlength="50" type="password"/>
		<td/>
		<tr/>
		<tr>
		<th>确认密码：</th>
		<td>
		<input id="relpassword" name="relpassword" size="20" maxlength="50" type="password"/>
		<td/>
		<tr/>
		<tr>
		<td>
		 <input type="submit" value="修改密码" /> 
		<td/>
		<tr/>
	</table>
</form>
