<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>活动列表 - Powered By ${systemConfig.systemName}</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>活动列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount} (共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="activity!list.action" method="post">
			<div class="operateBar">
				<input type="button" class="addButton" onclick="location.href='activity!add.action'" value="添加活动" />
				<label>查找:</label>
				<select name="pager.property">
					<option value="title" <#if pager.property == "title">selected="selected" </#if>>
						活动标题
					</option>
					<option value="id" <#if pager.property == "id">selected="selected" </#if>>
						活动id
					</option>
					<option value="buyerId" <#if pager.property == "buyerId">selected="selected" </#if>>
						买手id
					</option> 
					<option value="mallId" <#if pager.property == "mallId">selected="selected" </#if>>
						商场id
					</option>
				</select>
				<label class="searchText"><input type="text" name="pager.keyword" value="${pager.keyword!}" /></label><input type="button" id="searchButton" class="searchButton" value="" />
				<label>每页显示:</label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10" <#if pager.pageSize == 10>selected="selected" </#if>>
						10
					</option>
					<option value="20" <#if pager.pageSize == 20>selected="selected" </#if>>
						20
					</option>
					<option value="50" <#if pager.pageSize == 50>selected="selected" </#if>>
						50
					</option>
					<option value="100" <#if pager.pageSize == 100>selected="selected" </#if>>
						100
					</option>
				</select>
			</div>
			<table class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="id">id</span>
					</th>
					<th>
						<span class="sort" name="title">活动标题</span>
					</th>
					<th>
						<span class="sort" name="email">买手昵称</span>
					</th>
					<th>
						<span class="sort" name="name">商场名</span>
					</th>
					<th>
						<span class="sort" name="startDate">开始时间</span>
					</th>
					<th>
						<span class="sort" name="endDate">结束时间</span>
					</th>
					<th>
						<span class="sort" name="createDate">创建日期</span>
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
					<#-- ${activityService.load(list.id).title} -->
						<td>
							<input type="checkbox" name="ids" value="${list.id}" />
						</td>
						<td>
							${list.id}
						</td>
						<td>
							${list.title}
						</td>
						<td>
							买手昵称
						</td>
						<td>
							商场名
						</td>
						<td>
							<#if (list.startDate != null && list.startDate != "")!>
								<span>${list.startDate?string("yyyy-MM-dd HH:mm:ss")}</span>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td>
							<#if (list.endDate != null && list.endDate != "")!>
								<span>${list.endDate?string("yyyy-MM-dd HH:mm:ss")}</span>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td>
						<#if (list.createDate != null && list.createDate != "")!>
								<span>${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
							<#else>
								&nbsp;
							</#if>
						</td>
						<td>
						<#if list.activityStatus == "WAIT_RELEASE">
							<a href="activity!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						</td>
						</#if>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="activity!delete.action" value="删 除" disabled hidefocus="true" />
					<#include "/WEB-INF/template/admin/pager.ftl" />
				</div>
			<#else>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>
