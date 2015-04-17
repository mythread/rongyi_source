<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
	href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
	src="../../js/datagrid-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
	src="../../js/locale/easyui-lang-<%=(String) session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	function ajaxMallInfo() {
		var F = $('#dbinfo');
		jQuery.post("/jsp/mallcms/initMallInfo.action", F.serialize(),
				function(res) {
					if (res == '0') {
						alert("ok");
					} else {
						alert("failed");
					}
				});
	}
	function ajaxAdvertisementsInfo() {
		var F = $('#dbinfo');
		jQuery.post("/jsp/mallcms/initAdvertisementsInfo.action",
				F.serialize(), function(res) {

					if (res == '0') {
						alert("ok");
					} else {
						alert("failed");
					}
				});
	}
	function ajaxCategoriesInfo() {
		var F = $('#dbinfo');
		jQuery.post("/jsp/mallcms/initCategoriesInfo.action", F.serialize(),
				function(res) {

					if (res == '0') {
						alert("ok");
					} else {
						alert("failed");
					}
				});
	}
	function ajaxBrandsInfo() {
		var F = $('#dbinfo');
		jQuery.post("/jsp/mallcms/initBrandsInfo.action", F.serialize(),
				function(res) {

					if (res == '0') {
						alert("ok");
					} else {
						alert("failed");
					}
				});
	}
	function ajaxShopsInfo() {
		var F = $('#dbinfo');
		jQuery.post("/jsp/mallcms/initShopsInfo.action", F.serialize(),
				function(res) {
					if (res == '0') {
						alert("ok");
					} else {
						alert("failed");
					}
				});
	}
	function ajaxAdzonesInfo() {
		var F = $('#dbinfo');
		jQuery.post("/jsp/mallcms/initAdzonesInfo.action", F.serialize(),
				function(res) {
					if (res == '0') {
						alert("ok");
					} else {
						alert("failed");
					}
				});
	}
	function ajaxPhotosInfo() {
		var F = $('#dbinfo');
		jQuery.post("/jsp/mallcms/initPhotosInfo.action", F.serialize(),
				function(res) {
					if (res == '0') {
						alert("ok");
					} else {
						alert("failed");
					}
				});
	}
</script>
</head>
<body>
	<div id="page-wrap">
		<s:include value="../header.jsp" />
		<s:include value="../menu.jsp" />
		<div id="feature">
			<s:include value="../navigation.jsp" />
			<div id="feature-content">
				<table>
					<tr>
						<form id="dbinfo">
							<td>商城ID:</td>
							<td><input type="txt" value="51f9d7f731d6559b7d00015f"
								name="mallId"></input></td>
							<td>数据库IP地址:</td>
							<td><input type="txt" value="127.0.0.1" name="dbIp"></input></td>
							<td>用户名:</td>
							<td><input type="txt" value="root" name="dbUser"></td>
							<td>密码:</td>
							<td><input type="txt" value="123456" name="dbPassword"></td>
						</form>
					</tr>
					<tr>
						<td>1</td>
						<td colspan="4">商城信息初始化</td>
						<td colspan="2"><input type="button" onclick="ajaxMallInfo()"
							value="初始化"></input></td>
					</tr>
					<tr>
						<td>2</td>
						<td colspan="4">品牌初始化</td>
						<td colspan="2"><input type="button"
							onclick="ajaxBrandsInfo()" value="初始化"></input></td>
					</tr>
					<tr>
						<td>3</td>
						<td colspan="4">广告位初始化</td>
						<td colspan="2"><input type="button"
							onclick="ajaxAdzonesInfo()" value="初始化"></input></td>
					</tr>
					<tr>
						<td>4</td>
						<td colspan="4">广告详情初始化</td>
						<td colspan="2"><input type="button"
							onclick="ajaxAdvertisementsInfo()" value="初始化"></input></td>
					</tr>
					<tr>
						<td>5</td>
						<td colspan="4">商铺初始化</td>
						<td colspan="2"><input type="button"
							onclick="ajaxShopsInfo()" value="初始化"></input></td>
					</tr>
					<tr>
						<td>6</td>
						<td colspan="4">分类初始化</td>
						<td colspan="2"><input type="button"
							onclick="ajaxCategoriesInfo()" value="初始化"></input></td>
					</tr>
					<tr>
						<td>7</td>
						<td colspan="4">店铺图片初始化</td>
						<td colspan="2"><input type="button"
							onclick="ajaxPhotosInfo()" value="初始化"></input></td>
					</tr>
				</table>
			</div>
		</div>
		<s:include value="../footer.jsp" />
	</div>
</body>
</html>
