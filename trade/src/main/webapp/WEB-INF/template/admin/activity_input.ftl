<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑活动 - Powered By ${systemConfig.systemName}</title>
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加<#else>编辑活动</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>activity!save.action<#else>activity!update.action</#if>" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
					 	活动标题:
					</th>
					<td>
						<input type="text" name="activity.title" class="formText {required: true}" value="${(activity.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						活动描述:
					</th>
					<td>
						<input type="text" name="activity.description" class="formText {required: true}" value="${(activity.description)!}" />
					</td>
					
				</tr>
				<tr>
					<th>
						选择商场名:
					</th>
					<td>
						<select name="activity.mallId">
							<option>请选择商场...</option>
							<option>1</option>
							<option>2</option>
							<#list productCategoryTreeList as list>
								<option value="${list.id}"<#if (list.id == product.productCategory.id)!> selected</#if>>
									${list.name}
								</option>
							</#list>
							
						</select>
					</td>
				</tr>
				<tr>
					<th>
						活动图片:
					</th>
					<td>
						<input type="file" name="activityPic" hidefocus="true" />
					</td>
				</tr>
				<tr>
					<th>开始时间：</th>
					<td>
						<input type="text" class="datetimepicker"  id="test" name="activity.startDateStr" readonly />
					</td>
				</tr>
				<tr>
					<th>结束时间：</th>
					<td>
						<input type="text" class="datetimepicker" name="activity.endDate" readonly />
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
<script type="text/javascript">
$(function(){
	console.debug($('.datetimepicker'))
	$('.datetimepicker').datepicker();
})
</script>
</html>