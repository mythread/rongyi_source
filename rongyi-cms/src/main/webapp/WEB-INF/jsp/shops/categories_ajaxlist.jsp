<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="site" uri="http://rongyi.com/taglibs" %>
<c:forEach varStatus="ct" var="category" items="${cateList}">
<c:if test="${type=='1'}">
<button id="${category.id}" value="${category.name}"
		onclick="loadCategroyList('${category.id}','1')">${category.name}</button>
</c:if>
<c:if test="${type=='2'}">
<button id="${category.id}" value="${category.name}"
		onclick="loadBrandsList('${category.id}',2)">${category.name}</button>
</c:if>	
	<c:if test="${ct.index%10==0}">
		<br>
	</c:if>
</c:forEach>