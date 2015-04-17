<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<c:set var="SHOW_IMG_PATH" value="<%=com.rongyi.cms.constant.Constant.PicPath.rootPath%>"/>
	<div id="layer">
	<form id="adForm" name="form1" action="../advertisements/save" method="post">
	<input type="hidden" name="adZoneId" value="${adZoneId}" class="adZoneId"/>
	<input type="hidden" name="id" value="${advertisements.id}" class="adId"/>

		<div class="layer-main">
			<div class="layer-photo">
				<c:choose>
				     <c:when test="${advertisements.pictureUrl != null && advertisements.pictureUrl != '-1'}">
				     	<img id="uploadImage" alt="预览" src="${advertisements.pictureUrl}"/>
				     </c:when>
					 <c:when test="${advertisements.pictureUrl == '-1'}">
						<img id="uploadImage" alt="预览" src="${SHOW_IMG_PATH}/advertise/original/${advertisements.picture}"/>
					 </c:when>
				     <c:otherwise>
				     	<img id="uploadImage" alt="预览" src=""/>
				     </c:otherwise>
				  </c:choose>
			</div>
			<div class="layer-settings">
				<div class="layer-setting-items">
					<div class="layer-setting-line">
						<div class="layer-setting-title">广告位</div>
						<div class="layer-setting-input"><input type="text" class="layer-setting-input-width" value="${adZoneName}" disabled="disabled"/></div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">广告名称</div>
						<div class="layer-setting-input"><input type="text" class="layer-setting-input-width adName" name="adName" value="${advertisements.name}"/></div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">投放开始日期</div>
						<div class="layer-setting-input">
						<input  type='text' name="startTime" id="startTime" value="${startTime}" style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-%d',minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',onpicking:function(dp){ _advertisements.pickDate(dp.cal.getNewDateStr(),'e');}})" class="layer-setting-input-width verifyDate"/>
						</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">投放结束日期</div>
						<div class="layer-setting-input">
						<input  type='text' name="endTime" id="endTime" value="${endTime}" style="width:150px;" onfocus="WdatePicker({startDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd',onpicking:function(dp){ _advertisements.pickDate('s',dp.cal.getNewDateStr()); }})" class="layer-setting-input-width verifyDate"/>
						</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">广告素材</div>
						<div class="layer-setting-input" style="line-height: 0px;">
							<input type="file" id="adImage" name="myfiles" class="layer-setting-input-width" style="width:150px;height: 32px;"/>
	          				<input type="hidden" name="picture" id="adPic" value="${advertisements.picture}"/>
						</div>
					</div>
					<div class="layer-setting-check">
						<div>素材形式：图片素材</div>
						<div>素材格式：JPG、PNG</div>
						<div>素材宽高：1032 * 1344 px</div>
						<div>大小限制：1M</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">下方缩略图</div>
						<div class="layer-setting-input">
							<label><input type="radio" class="hasMinPic" name="hasMinPic" value="1" ${hasMinPic=='1' ? 'checked' : ''} id="layer-no-min-img-radio" />将广告图缩略</label>
							<label><input type="radio" class="hasMinPic" name="hasMinPic" value="2" ${hasMinPic=='2' ? 'checked' : ''} id="layer-min-img-radio" />使用其他图片</label>
						</div>
					</div>
					<div class="layer-setting-line ${hasMinPic=='1' ? 'display-none' : ''}" id="layer-min-img-item">
						<div class="layer-setting-title"></div>
						<div class="layer-setting-input" style="line-height: 0px;"><input type="file" class="layer-setting-input-width" name="myfiles" id="resizePic" style="width:150px;height: 32px;"/>
<!-- 						<input type="button" value="上传" onclick="_advertisements.uploadResizePic('resizePic')"/> -->
						</div>
        				<input type='hidden' class='picPath' name='minPicture' value="${advertisements.minPicture}"/>
					</div>
					<div class="layer-setting-check ${hasMinPic=='1' ? 'display-none' : ''}" id="layer-min-img-check">
						<div style="float: left;width: 60%">
							<div>素材形式：图片素材</div>
							<div>素材格式：JPG、PNG</div>
							<div>素材宽高：100 * 100 px</div>
							<div>大小限制：10kb</div>
						</div>
						<div style="float: left;">
						<img class="showImg" src="${SHOW_IMG_PATH}/advertise/resize/${advertisements.minPicture}" alt="预览" style="width:100px;height:100px;"/>
						</div>
						<div class="for-clear"></div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">链接地址</div>
						<div class="layer-setting-input">
							<label><input type="radio" class="hasLink" name="hasLink" value="0" ${shopName!='' ? '' : 'checked'} id="layer-no-link-radio" />无链接</label>
							<label><input type="radio" class="hasLink" name="hasLink" value="1" ${shopName!='' ? 'checked' : ''} id="layer-link-radio" />商家页面</label>
					        <input type="hidden" name="shopUrl" id="shops_url" value="${advertisements.shopUrl}"/>
						</div>
					</div>
					<div class="layer-setting-line ${shopName!='' ? '' : 'display-none'}" id="layer-link-item">
						<div class="layer-setting-title"></div>
						<div class="layer-setting-input">
							<input type="text" name="shopName" id="shops_name" value="${shopName!='' ? shopName : '请输入商家名称'}" class="layer-setting-input-width" />
						</div>
					</div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">活动状态</div>
						<div class="layer-setting-input">
							<label><input type="radio" name="onStatus" class="onStatus" value="1" ${advertisements.onStatus=='1' ? 'checked' : ''}/>启用</label>
							<label><input type="radio" name="onStatus" class="onStatus" value="0" ${advertisements.onStatus!='1' ? 'checked' : ''}/>停用</label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="layer-buttons">
			<a href="#" class="default-black-btn-style cancelPop" id="layer-close">取消</a>
			<a href="#" class="default-pink-btn-style submitAdForm">发布</a>
		</div>
	</form>
	</div>
