<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<link href="../css/shop/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/shop/index.js"></script>
<script type="text/javascript" src="../js/shop/common.js"></script>
<script type="text/javascript" src="../js/page/pagination.js"></script>

</head>
<body>
	<%@include file="../top/top_shop.jsp" %>

	<div id="content">
	
		<div id="left-nav">
			<ul>
				<li id="category-nav">
					<a class="selected" href="#">类目</a>
					<div class="left-second-nav" id="category-second-nav">
						<ul>
							<li><a id="category_all" href="#" class="category_a" onclick="categroyClick('all')">全部</a></li>
							<c:forEach varStatus="ct" var="category" items="${cateList}">
								<li><a id="category_${category.id}" class="category_a" href="#" onclick="categroyClick('${category.id}')">${category.name}</a></li>
							</c:forEach>
						</ul>
					</div>
				</li>
				<li id="sync-nav">
					<a href="#">审核状态</a>
					<div class="left-second-nav" id="sync-second-nav">
						<ul>
							<li><a id="syschstatusidall" href="#" class="syschstatus_a" onclick="synchStatusClick('all')">全部</a></li>
							<li><a id="syschstatusid2" href="#" class="syschstatus_a" onclick="synchStatusClick('2')">审核已通过(${synchStatusMap['2']})</a></li>
							<li><a id="syschstatusid1" href="#" class="syschstatus_a" onclick="synchStatusClick('1')">审核中(${synchStatusMap['1']})</a></li>
							<li><a id="syschstatusid3" href="#" class="syschstatus_a" onclick="synchStatusClick('3')">审核未通过(${synchStatusMap['3']})</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
		
		<div id="right-content">			
		
			<div id="show">
			
				<div id="search-box">
					<div id="search-box-left">
						商家信息管理 &gt; 商家列表
					</div>
					<div id="search-box-right">
						<div>
							<input type="text" placeholder="请输入“店铺号”或“店铺名称”" id="searchParamsid" onkeydown="keypress()"/>
						</div>
						<div>
							<a href="#"  onclick="onSearchClick()">
								<img src="../images/icon_search.png" />
							</a>
						</div>
					</div>
				</div>
				
				<hr />
				
				<div id="filter-box">
					<span>活动状态：</span>
					<a class="shop_on_status default-selecter default-selecter-selected" href="#" id="shop_on_status_all" onclick="onStatusClick('all')">全部</a>
					<a class="shop_on_status default-selecter" href="#" id="shop_on_status_1" onclick="onStatusClick('1')">营业中</a>
					<a class="shop_on_status default-selecter" href="#" id="shop_on_status_0" onclick="onStatusClick('0')">已停业</a>
					<span class="split">|</span>
					<span>推荐情况：</span>
					<a class="shop_on_recommend default-selecter default-selecter-selected" href="#" id="shop_on_recommend_all" onclick="onRecommendClick('all')">全部</a>
					<a class="shop_on_recommend default-selecter" href="#" id="shop_on_recommend_1" onclick="onRecommendClick('1')">推荐</a>
					<a class="shop_on_recommend default-selecter" href="#" id="shop_on_recommend_0" onclick="onRecommendClick('0')">未推荐</a>
				</div>
				<div id="shoplist"></div>
			</div>
		</div>
	
	</div>
	
	<!-- 删选隐藏域 -->
	<div style="display: none;">
		<form action="../shops/ajaxSearchlist" id="searchForm">
			<input type="hidden" value="" name="categoriesId" id="categoriesId">
			<input type="hidden" value="" name="onStatus" id="onStatus"> 
			<input type="hidden" value="" name="recommend" id="recommend"> 
			<input type="hidden" value="" name="searchParams" id="searchParams">
		</form>
		<form action="../shops/ajaxSynchlist" id="synchForm">
		<input type="hidden" value="" name="synchStatus" id="synchStatus">
		</form>
		<input type="hidden" value="" name="searchType" id="searchType">	
	</div>


	<!-- footer -->
    <%@include file="../footer/footer.jsp" %>
	
</body>
</html>