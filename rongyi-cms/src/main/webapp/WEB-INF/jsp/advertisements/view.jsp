<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="SHOW_IMG_PATH" value="<%=com.rongyi.cms.constant.Constant.PicPath.rootPath%>"/>
<div id="view-layer">
	<input type="hidden" name="adZoneId" class="adZoneId" value="${adZoneId}"/>
	<input type="hidden" name="id" value="${advertisements.id}" class="adId"/>
		<div class="navigation">
			<ul>
				<c:if test="${advertisements.pid==-1 && advertisements.recordType==1}">
			　　	<li><a class="selected" href="#">待同步内容</a></li>
			　　</c:if>
			　　<c:if test="${advertisements.pid==-1 && advertisements.recordType==0}">
			　　	<li><a class="selected" href="#">当前内容</a></li>
			　　</c:if>
			   <c:if test="${advertisements.pid>0 && advertisements.recordType==1}">
				<li><a href="#" onclick="_advertisements.popViewAdvertisements('${adZoneId}','${advertisements.pid}');">当前内容</a></li>
				<li><a class="selected" href="#">待同步内容</a></li>
			　　</c:if>
			   <c:if test="${advertisements.pid==0 && advertisements.recordType==0}">
				<li><a class="selected" href="#">当前内容</a></li>
				<li><a href="#" onclick="_advertisements.popViewAdvertisements('${adZoneId}','${sumAdId}');">待同步内容</a></li>
			　　</c:if>
			</ul>
		</div>

		<div class="layer-main">
			<div class="layer-photo">
				<c:if test="${advertisements.pictureUrl != null && advertisements.pictureUrl != '-1'}">
					<img src="${advertisements.pictureUrl}"/>
				</c:if>
				<c:if test="${advertisements.pictureUrl == '-1'}">
					<img src="${SHOW_IMG_PATH}/advertise/original/${advertisements.picture}"/>
				</c:if>
			</div>
			<div class="layer-settings">
				<div class="layer-setting-items" style="overflow-y:hidden;">
					<div class="layer-setting-line">
						<div class="layer-setting-title">广告位</div>
						<div class="layer-setting-input">${adZoneName}</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">广告名称</div>
						<div class="layer-setting-input">${advertisements.name}</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">投放日期</div>
						<div class="layer-setting-input">${startTime}  ~  ${endTime}</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">广告图</div>
						<div class="layer-setting-input">${advertisements.picture}</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">下方缩略图</div>
						<div class="layer-setting-input">${advertisements.minPicture}</div>
					</div>
					<div class="layer-setting-line">
						<c:if test="${shopName==null || shopName==''}">
							<div class="layer-setting-title">链接地址</div>
							<div class="layer-setting-input">无链接</div>
						</c:if>
						<c:if test="${shopName!=null && shopName!=''}">
							<div class="layer-setting-title">链接商家</div>
							<div class="layer-setting-input">${shopName}</div>
						</c:if>
					</div>
					<div class="layer-setting-line" style="margin-bottom:0px;">
						<div class="layer-setting-title">活动状态</div>
						<div class="layer-setting-input">
							${advertisements.onStatus=='1' ? '已启用' : '已停用'}
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="layer-buttons">
			<a href="#" class="default-black-btn-style cancelPop" id="layer-close">取消</a>
			<a href="#" class="default-pink-btn-style editAd" adZoneId="${adZoneId}" adId="${advertisements.pid}">编辑</a>
		</div>
	</div>
