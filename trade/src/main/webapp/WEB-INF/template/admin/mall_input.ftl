<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑商场 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加商场<#else>编辑商场</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>mall!save.action<#else>mall!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						商场名称:
					</th>
					<td>
						<input type="text" name="mall.name" class="formText {required: true}" value="${(productCategory.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>地区名</th>
					
				</tr>
				<tr>
					<th>
						商场图片:
					</th>
					<td>
						<input type="file" name="mall.pic" /><#if (mall.pic != null)!>&nbsp;&nbsp;&nbsp;<a href="${base}${mall.pic}" class="imagePreview" target="_blank">查看</a></#if>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>