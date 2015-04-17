<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<link href="../css/advertisement/adzonedetail.css" rel="stylesheet" type="text/css" />
<link href="../css/advertisement/layer.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/advertisement/adzonedetail.js"></script>
<script type="text/javascript" src="../js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
<script type="text/javascript" src="../js/datepicker/WdatePicker.js"></script>
<link rel="Stylesheet" href="../css/jquery.autocomplete.css" />

<script type="text/javascript" src="../js/advertisements.js"></script>
<script type="text/javascript" src="../js/util.js"></script> 
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
</head>
<body>
	<!-- header -->
    <%@include file="../top/top_ad.jsp" %>
	
	
	<div id="content">
		<div id="left-nav">
			<ul>
				<li id="sync-nav">
					<a href="#"  id="syncaid"  class="selected">审核状态</a>
					<div class="left-second-nav" id="sync-second-nav">
						<ul>
							<li><a id="syschstatusidall" href="#" class="syschstatus_a" onclick="synchStatusClick('${adZoneId}','all')">全部</a></li>
							<li><a id="syschstatusid2" href="#" class="syschstatus_a" onclick="synchStatusClick('${adZoneId}','2')">审核已通过(${adSynchMap['2']})</a></li>
							<li><a id="syschstatusid1" href="#" class="syschstatus_a" onclick="synchStatusClick('${adZoneId}','1')">审核中(${adSynchMap['1']})</a></li>
							<li><a id="syschstatusid3" href="#" class="syschstatus_a" onclick="synchStatusClick('${adZoneId}','3')">审核未通过(${adSynchMap['3']})</a></li>
						</ul>
					</div>
				</li>
				
				<li id="active-nav">
					<a href="#"  id="activeaid"   onclick="">活动状态</a>
					<div class="left-second-nav" id="active-second-nav">
						<ul>
							<li><a href="#"  onclick="onStatusClick('${adZoneId}','all')">全部</a></li>
							<li><a href="#"    onclick="onStatusClick('${adZoneId}','1')">已启用</a></li>
							<li><a href="#"    onclick="onStatusClick('${adZoneId}','0')">已停用</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
		
		<div id="right-content">
		<div id="show">
		
			<div>
				<span style="line-height: 50px;"><a  href="../adzones/index">广告活动管理</a> &gt; 广告列表</span>
				<a id="back-button" class="default-gray-btn-style" href="../adzones/index">返回</a>
			</div>
					
			<hr />
			
			<div id="ad-operation">
				
				<a id="create-ad" class="default-pink-btn-style" href="#" adZoneId="${adZoneId}">新建广告</a>
	
				<input  type='text' name="searchEndTime" id="searchEndTime" style="width:100px;" onfocus="WdatePicker({startDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'searchStartTime\')}',dateFmt:'yyyy-MM-dd'})" class="search_date"/>
				<span style="float: right;">~</span>
				<input  type='text' value="${searchStartTime}" name="searchStartTime" id="searchStartTime" style="width:100px;" onfocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd'})" class="search_date"/>
				
			</div>
			<div id="adlist">
			</div>
			
		</div>
		</div>
	</div>
	
	<!-- footer -->
    <%@include file="../footer/footer.jsp" %>

<script type="text/javascript">
jQuery(document).ready(function() {
	var adzonesid = '${adZoneId}';
	loadAdList(adzonesid);
	$('.search_date').blur(function(){
		loadAdList(adzonesid);
	});
});
</script>	
<input type="hidden" value="" name="synchStatus" id="synchStatus">
<input type="hidden" value="" name="onStatus" id="onStatus">
<div class="pop_edit_advertisements_div" style="display:none;"></div>
<div class="pop_view_advertisements_div" style="display:none;"></div>
</body>
</html>