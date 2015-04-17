<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<div class="page">
	<s:if test="%{paging.totalPage>1}">
		<s:set name="prevPageNo" value="%{paging.currentPage-1}"></s:set>
		<s:set name="nextPageNo" value="%{paging.currentPage+1}"></s:set>
		<s:set name="totalPage" value="%{paging.totalPage}"></s:set>
		<s:if test="%{paging.currentPage!=1}">
			<s:if test="%{#url.indexOf('?')!=-1}">
				<a href="${ctx}/<s:property value="url"/>&currentPage=<s:property value="%{#prevPageNo}"/><s:property value="#positioning" />" title="上一页" class="pages-prvious pageIng">&lt;上一页</a>
			</s:if><s:else>
				<a href="${ctx}/<s:property value="url"/>?currentPage=<s:property value="%{#prevPageNo}"/><s:property value="#positioning" />" title="上一页" class="pages-prvious pageIng">&lt;上一页</a>
			</s:else>
		</s:if><s:else>
			<a href="javascript:void(0)" class="pages-prvious pageIng">&lt;上一页</a>
		</s:else>
		
		<s:iterator value="paging.noList" id="no">
			<s:if test='%{#no!=paging.currentPage}'>
				<s:if test="%{#url.indexOf('?')!=-1}">
					<a href="${ctx}/<s:property value="url"/>&currentPage=${no}<s:property value="#positioning" />" title="">${no}</a>
				</s:if><s:else>
					<a href="${ctx}/<s:property value="url"/>?currentPage=${no}<s:property value="#positioning" />" title="">${no}</a>
				</s:else>
			</s:if>
			<s:else>
				<s:if test='%{#no!="..."}'>
					<a class="active">${no}</a>
				</s:if>
				<s:else>
					<a >${no}</a>
				</s:else>
			</s:else>
		</s:iterator>
	    <s:if test="%{paging.currentPage != #totalPage}">
			<s:if test="%{#url.indexOf('?')!=-1}">
				<a href="${ctx}/<s:property value="url"/>&currentPage=<s:property value="%{#nextPageNo}"/><s:property value="#positioning" />" title="下一页" class="pages-next">下一页&gt;</a>
			</s:if>
			<s:else>
				<a href="${ctx}/<s:property value="url"/>?currentPage=<s:property value="%{#nextPageNo}"/><s:property value="#positioning" />" title="下一页" class="pages-next">下一页&gt;</a>
			</s:else>
		</s:if>
		<s:else>
			<a href="javascript:void(0)" title="下一页" class="pages-next">下一页&gt;</a>
		</s:else>
		<a class="pages-total">共${totalPage}页</a>
    </s:if>
</div>