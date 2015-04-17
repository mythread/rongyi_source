<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="site" uri="http://rongyi.com/taglibs" %>

<c:forEach varStatus="bt" var="brands" items="${brandsList}">
	<button id="${brands.id}" value="${brands.name}"
		onclick="selectBrandsid('${brands.id}')">${brands.name}</button>
	<c:if test="${bt.index%10==0}">
		<br>
	</c:if>
</c:forEach>