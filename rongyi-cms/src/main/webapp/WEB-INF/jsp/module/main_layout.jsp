<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body>
	<div style="width: 1000px;">
		<tiles:insertAttribute name="top" />
	</div>
	<div style="width: 1000px; height: 600px;">
		<tiles:insertAttribute name="body" />
	</div>
	<div style="width: 1000px;">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>
