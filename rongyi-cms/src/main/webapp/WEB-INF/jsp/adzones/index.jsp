<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<link href="../css/advertisement/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$(".adzone").mouseenter(function(){
		$(this).find(".adzone-border").css("display", "block");
	});
	$(".adzone").mouseleave(function(){
		$(this).find(".adzone-border").css("display", "none");
	});
});
</script>
</head>
<body>
	<!-- header -->
    <%@include file="../top/top_ad.jsp" %>


	<div id="content">
	
		<div id="left-nav">
			<ul>
				<li><a class="selected" href="#">首页</a></li>
			</ul>
		</div>
		
		<div id="right-content">
		
			<div id="show">
			
				<p style="line-height: 50px;margin: 0px;">广告活动管理 &gt; 首页 </p>
				
				<hr />
				
				<div class="for-triangle"></div>
				<p class="adzone-type">首页</p>
				
				<div class="adzone-group">
					
					<c:forEach var="adzonesList" items="${adzonesList}" varStatus="index">
						<div class="adzone">
							<div class="adzone-border"></div>
							<a href="../adzones/adlist?adZoneId=${adzonesList.id}">
								<img src="../images/setting.png">
							</a>
							
							<c:if test="${index.index==0}"><img src="<site:config key='advertisement.site.img.1'/>"></c:if>
							<c:if test="${index.index==1}"><img src="<site:config key='advertisement.site.img.2'/>"></c:if>
							<c:if test="${index.index==2}"><img src="<site:config key='advertisement.site.img.3'/>"></c:if>
							<c:if test="${index.index==3}"><img src="<site:config key='advertisement.site.img.4'/>"></c:if>
							
							<div class="adzone-index with-right-border <c:if test="${index.index==0}">now-selected</c:if>">
								<c:if test="${index.index==0}">
									<p>广告图</p>
									<div>1</div>
								</c:if>
							</div>
							<div class="adzone-index with-right-border <c:if test="${index.index==1}">now-selected</c:if>">
								<c:if test="${index.index==1}">
									<p>广告图</p>
									<div>2</div>
								</c:if>
							</div>
							<div class="adzone-index with-right-border <c:if test="${index.index==2}">now-selected</c:if>">
								<c:if test="${index.index==2}">
									<p>广告图</p>
									<div>3</div>
								</c:if>
							</div>
							<div class="adzone-index <c:if  test="${index.index==3}">now-selected</c:if>">
								<c:if test="${index.index==3}">
									<p>广告图</p>
									<div>4</div>
								</c:if>
							</div>
						</div>
						
					</c:forEach>
	
				</div>
				
			</div>
		
		</div>
		
	</div>
	
	<!-- footer -->
    <%@include file="../footer/footer.jsp" %>

</body>
</html>