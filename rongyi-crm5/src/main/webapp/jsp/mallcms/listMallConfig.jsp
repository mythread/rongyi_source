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
$(function(){
	$('#id_addConfig').click(function(){
		var F = $('#mallConfig');
		jQuery.post('/jsp/mallcms/addConfig.action',F.serialize(),function(res){
			var jsonObj = jQuery.parseJSON(res);
			alert(jsonObj.msg);
			if(jsonObj.result == 0){
				window.location.href = "/jsp/mallcms/listMallIp.action";
			}
		});
	});
	
})
</script>
</head>
<body>
	<div id="page-wrap">
		<s:include value="../header.jsp" />
		<s:include value="../menu.jsp" />
		<div id="feature">
			<div id="feature-content">
			<table>
				<tr>
						<td>
						<form id="mallConfig">
							<td>商城ID:</td>
							<td><input type="txt" value="" name="mallId"></input></td>
							<td>域名</td>
							<td><input type="txt" value="" name="domain"></input></td>
							<td>服务器IP地址:</td>
							<td><input type="txt" value="" name="ip"></input></td>
							<td>端口号:</td>
							<td><input type="txt" value="" name="port"></td>
						</form>
						</td>
						<td>
						<input type="button" id="id_addConfig" value="添加"/>
						</td>
					</tr>
			</table>
			<table style="width:60%">
					<tr>
						<td>序号</td>
						<td>商场ID</td>
						<td>域名</td>
						<td>IP</td>
						<td>端口号</td>
					</tr>
		<s:if test="mallConfigList">
		<s:iterator value="mallConfigList" status="st" id="config">
				<tr>
					<td>
					<s:property value="#config.id" />
					</td>
					<td ><s:property value="#config.mallId" /></td>
					<td ><s:property value="#config.domain" /></td>
					<td><s:property value="#config.ip" /></td>
					<td><s:property value="#config.port" /></td>
				</tr>
		</s:iterator>
		</s:if>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
