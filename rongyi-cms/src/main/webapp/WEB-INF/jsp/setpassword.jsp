<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理</title>
<link href="<%=request.getContextPath()%>/css/login/index.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		$("form").submit(function() {
			var newpassword = $("#input-newpassword").val();
			var relpassword = $("#input-relpassword").val();
			if(newpassword != relpassword) {
				alert("两次输入的密码不一致。");
				return false;
			}
		});
	});

</script>
</head>
<body onload='document.f.j_username.focus();'>
	
	<div class="main-container">
	
	<div id="header">
			<h1><site:config key="header.mall.name"/></h1>
			<h2>导购终端机内容管理</h2>
	</div>
	
	<div id="logincontainer">
		
		
			<h3>${message}</h3>
			<form action="../user/changePassword" method='POST'>
				<p>原密码</p>
				<div class="inputcontainer">
					<input type="password" id="oldpassword" name="oldpassword" placeholder="请输入原密码" />
				</div>
				<p>新密码</p>
				<div class="inputcontainer">
					<input type="password" id="input-newpassword" name="newpassword" placeholder="请输入新密码" />
				</div>
				<p>确认密码</p>
				<div class="inputcontainer">
					<input type="password" id="input-relpassword" name="relpassword" placeholder="请输入确认密码"/>
				</div>
				<input type="submit" value="修&nbsp;&nbsp;&nbsp;&nbsp;改" class="loginsubmit"/>
			</form>
		
		
	</div>
	
	
	</div>
	<div id="footer">
			Copyright © 2014 容易网 | Designed by www.rongyi.com 上海容易网电子商务有限公司
	</div>
</body>
</html>