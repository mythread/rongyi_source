<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
		<c:forEach var="shop" items="${shopList}">
			<div class="shop">
				<div class="shop-logo">
					<a href="../shops/view?shopsId=${shop.id}">
					<c:if test="${shop.brands.syhchStatus == 2}">
					<img src="${shop.brands.iconUrl}" />
					</c:if>
					<c:if test="${shop.brands.syhchStatus != 2}">
					<img src="${SHOW_IMG_PATH}/brands/original/${shop.brands.icon}" />
					</c:if>
					</a>
				</div>
				<div class="shop-info">
					<p><a href="../shops/view?shopsId=${shop.id}" title="${shop.name}">${shop.name}</a></p>
					<p>${shop.shopNumber}</p>
				</div>
				<div class="shop-sync-status">
					<c:if test="${shop.synchStatus=='1'}">
						<img src="../images/icon_check_now.png">
						<span class="shop-sync sync-now">审核中</span>
					</c:if>
					<c:if test="${shop.synchStatus=='2'}">
						<img src="../images/icon_check_success.png">
						<span class="shop-sync sync-success">审核已通过</span>
					</c:if>
					<c:if test="${shop.synchStatus=='3'}">
						<img src="../images/icon_check_fail.png" title="${shop.synchMsg}">
						<span class="shop-sync sync-fail" title="${shop.synchMsg}">审核未通过</span>
					</c:if>
				</div>
				<div class="shop-settings">
					<div>
					<p>
					<a href="../shops/edit?shopsId=${shop.id}">
						<img src="../images/icon_config.png" />
					</a>
					</p>
					<p>
					设置
					</p>
					</div>
					
					<div>
					<p>
					<a href="#" class="recommend-status ${shop.recommend=='1' ? 'is-recommend' : ''}" shopId='${shop.id}' shopRecommend="${shop.recommend=='1' ? '0' : '1'}">
						<c:if test="${shop.recommend=='1'}"><img src="../images/icon_enable.png" /></c:if><c:if test="${shop.recommend=='0'}"><img src="../images/icon_disable.png" /></c:if>
					</a>
					</p>
					<p>
					<c:if test="${shop.recommend=='1'}">已推荐</c:if><c:if test="${shop.recommend=='0'}">未推荐</c:if>
					</p>
					</div>
					
					<div>
					<p>
					<a href="#" class="enable-status ${shop.onStatus=='1' ? 'is-enable' : ''}" shopId='${shop.id}' shopOnStatus="${shop.onStatus=='1' ? '0' : '1'}">
						<c:if test="${shop.onStatus=='1'}"><img src="../images/icon_enable.png" /></c:if><c:if test="${shop.onStatus=='0'}"><img src="../images/icon_disable.png" /></c:if>
					</a>
					</p>
					<p>
					<c:if test="${shop.onStatus=='1'}">营业中</c:if><c:if test="${shop.onStatus=='0'}">已停业</c:if>
					</p>
					</div>
					
				</div>
				
			</div>
		</c:forEach>
<site:pagination pagination="${pagination}" searchFormId="synchForm" ajaxForm="true"/>
