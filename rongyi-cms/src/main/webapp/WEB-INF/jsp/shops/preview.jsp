<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理-商家详情</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<link href="../css/shop/shopdetail.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/shop/shopdetail.js"></script>
</head>
<body>
	<%@include file="../top/top_shop.jsp" %>

	<div id="content">
		<div id="show">
		<input type="hidden" name="shopsId" class="shopsId" value="${shops.id}"/>
			<div class="top-title">
				<span><a href="../shops/list">商家信息管理</a> &nbsp;&gt; &nbsp;<a href="../shops/editV?shopsId=${shops.id}">编辑视图</a> &nbsp;&gt; &nbsp;预览</span>
				<a href="../shops/editV?shopsId=${shops.id}" class="default-gray-btn-style" id="back-btn">返回</a>
			</div>
			
			<hr />
			
			<div id="view-swith">
			</div>
			
			<div>
				<img src="<site:config key='mall.shop.img'/>" style="width: 100%">
			</div>
			
			<div id="view-content">
				
				<div id="details">
					
					<div id="detail-head" class="detail-editable">
						<div id="detail-logo">
							<c:if test="${shops.brands.iconUrl == '-1'}">
								<img src="${SHOW_IMG_PATH}/brands/original/${shops.brands.icon}" />
							</c:if>
							<c:if test="${shops.brands.iconUrl != '-1'}">
								<img src="${shops.brands.iconUrl}" />
							</c:if>
						</div>
						<div id="detail-title">
							${shops.name}
						</div>
						<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
					</div>
					
					<hr />
					
					<div id="detail-body">
					
						<div id="detail-body-left">
							<div id="detail-address">
								<span><c:if test="${floor!=null}">${floor.value}</c:if></span>${shops.shopNumber}
							</div>
							
							<div id="detail-infomation" class="detail-editable">
								<p>标签：${shops.tags}</p>
								<p>营业时间：${shops.openTime}</p>
								<p>
									商铺评价：
									<span class="star-enable">★</span>
									<span class="star-enable">★</span>
									<span class="star-enable">★</span>
									<span class="star-disable">★</span>
									<span class="star-disable">★</span>
								</p>
								<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
							</div>
						</div>
						
						<div id="detail-body-right" class="detail-editable">
							<img src="${shopsPic_0}" class="detail-big-pic" />
							<img src="${shopsPic_0}" class="detail-small-pic detail-small-pic1 detail-small-pic-selected" />
							<img src="${shopsPic_1}" class="detail-small-pic detail-small-pic2" />
							<img src="${shopsPic_2}" class="detail-small-pic detail-small-pic3" />
							<img src="${shopsPic_3}" class="detail-small-pic detail-small-pic4" />
							<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
						</div>
						
					</div>
					
					<div id="detail-footer"  class="detail-editable">
						<div id="detail-description">
							${shops.description}
						</div>
						<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
					</div>
					
				</div>
				
			</div>
			<div style="height: 40px;text-align: center;padding-top: 20px;">
				<a href="javascript:self.close();" class="default-black-btn-style" style="padding-left: 40px;padding-right: 40px;" >关&nbsp;&nbsp;&nbsp;&nbsp;闭</a>
			</div>
		</div>
	</div>
	
	<!-- footer -->
    <%@include file="../footer/footer.jsp" %>
</body>
</html>