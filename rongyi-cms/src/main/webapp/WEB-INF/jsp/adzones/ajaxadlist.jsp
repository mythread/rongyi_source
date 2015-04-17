<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
<c:if test="${adList!=null}">
	<div class="ad-group">
	<c:if test="${adList!= null && fn:length(adList) != 0}">
	<c:forEach var="item" items="${adList}" varStatus="status">
	  <c:if test="${status.index>0 && status.index % 4 == 0}">
	  	</div>
	  	<div class="ad-group">
	  </c:if>
		<div class="ad" adZoneId='${adZoneId}' adId='${item.id}'>
			<c:if test="${item.synchStatus=='3'}">
				<div class="sync-error-msg">
					<div class="triangle-up"></div>
					<div>${item.synchMsg}</div>
				</div>
			</c:if>
			<div class="ad-title">
				<div>${item.name}</div>
				<div><fmt:formatDate value="${item.startTime}" pattern="yyyy.MM.dd" />-<fmt:formatDate value="${item.endTime}" pattern="yyyy.MM.dd" /></div>
			</div>
			<div class="ad-setting">
				
				<c:if test="${item.synchStatus=='1'}">
					<div class="ad-status sync-now" >
						<div><img src="../images/icon_check_now.png"></div>
						<div>审核中</div>
					</div>
				</c:if>
				<c:if test="${item.synchStatus=='2'}">
					<div class="ad-status sync-success" >
						<div><img src="../images/icon_check_success.png"></div>
						<div>审核已通过</div>
					</div>
				</c:if>
				<c:if test="${item.synchStatus=='3'}">
					<div class="ad-status sync-fail" >
						<div><img src="../images/icon_check_fail.png"></div>
						<div>审核未通过</div>
					</div>
				</c:if>
				
				<div class="ad-setting-btns">
						<div>
						<p>
						<a href="#" class="status-btn" adZoneId='${adZoneId}' adId='${item.id}' onStatus='${item.onStatus}'>
							<c:if test='${item.onStatus=="0"}'>
							<img src="../images/icon_disable.png">
							</c:if>
							<c:if test='${item.onStatus=="1"}'>
							<img src="../images/icon_enable.png">
							</c:if>
						</a>
						</p>
						<p>
						${item.onStatus=="1" ? "已启用" : "已关闭"}
						</p>
						</div>
					
						<div>
						<p>
						<a href="#" class="edit-btn"  adZoneId='${adZoneId}' adId='${item.id}'>
							<img src="../images/icon_config.png">
						</a>
						</p>
						<p>
						设置
						</p>
						</div>

						<div>
						<p>
						<a href="#" class="delete-btn" adZoneId='${adZoneId}' adId='${item.id}' synchStatus='${item.synchStatus}'>
							<img src="../images/icon_close.png">
						</a>
						</p>
						<p>
						删除
						</p>
						</div>
						
					
				</div>
			</div>
			<a href="#">
			<c:if test="${item.pictureUrl != null && item.pictureUrl != '-1'}">
				<img src="${item.pictureUrl}" class="main-img" />
			</c:if>
			<c:if test="${item.pictureUrl == '-1'}">
				<img src="${SHOW_IMG_PATH}/advertise/original/${item.picture}" class="main-img" />
			</c:if>
			</a>
		</div>
	</c:forEach>
	</c:if>
	</div>
</c:if>