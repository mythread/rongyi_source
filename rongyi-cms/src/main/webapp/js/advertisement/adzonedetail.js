//$(document).ready(function(){
var _adzonedetail={
		initList:function() {
			
			/**
			 * 变量定义
			 */
			
			// 弹出层的HTML
			$("#layer").remove();
			
			// 弹出层
			var layerWindow = null;

			
			/**
			 * 初始化
			 */
			
			// 设置左侧菜单高度
			$("#left-nav").height($("#right-content").height());
			
			/**
			 * 事件代码
			 */
			
			// 二级菜单显示
			$("#active-nav").mouseenter(function(){
				$("#active-second-nav").css("display", "block");
			});
			$("#active-nav").mouseleave(function(){
				$("#active-second-nav").css("display", "none");
			});
			$("#sync-nav").mouseenter(function(){
				$("#sync-second-nav").css("display", "block");
			});
			$("#sync-nav").mouseleave(function(){
				$("#sync-second-nav").css("display", "none");
			});
			$("#sync-second-nav,#active-second-nav").click(function() {
				$("#left-nav a").removeClass("selected");
				$(this).prev().addClass("selected");
				$(this).css("display", "none");
			});
			
			// 鼠标浮动到广告上显示开启、默认、编辑、删除按钮事件
			$(".ad").mouseenter(function(){
				$(this).find(".ad-title").css("visibility", "hidden");
				$(this).find(".ad-setting").css("visibility", "visible");
				$(this).find(".sync-error-msg").css("visibility", "visible");
				$(this).css("border","2px solid #F97E76");
			});
			$(".ad").mouseleave(function(){
				$(this).find(".ad-title").css("visibility", "visible");
				$(this).find(".ad-setting").css("visibility", "hidden");
				$(this).find(".sync-error-msg").css("visibility", "hidden");
				$(this).css("border","2px solid #D7DCE0");
			});
			
			// 创建广告按钮事件
			$('#create-ad').off().on({
				'click':function(ev){
					var adZoneId = $(this).attr('adZoneId');
//					alert('ss'+adZoneId);
					_adzonedetail.selectAdToInfo(adZoneId,'');
					/*layerWindow = $.layer({
						type: 1,
						title: '新建广告活动', //不显示默认标题栏
						area: [layerWidth, layerHeight],
						page: {html: layerHtml}
					});*/
					return false;
				}
			});
			
			// 广告查看事件
			$(".ad").click(function() {
//				layerWindow = $.layer({
//			        type: 1,
//			        title: '查看广告素材',
//			        area: [layerWidth, '520px'],
//			        page: {html: viewLayerHtml}
//			    });
				var adZoneId = $(this).attr('adZoneId');
				var adId = $(this).attr('adId');
				_advertisements.popViewAdvertisements(adZoneId,adId);
				return false;
			});
			
			// 开启、关闭广告事件
			$(".status-btn").click(function() {
				var adZoneId = $(this).attr('adZoneId');
				var adId = $(this).attr('adId');
				var onStatus = $(this).attr('onStatus');
				var this_ = $(this);
				
				if(onStatus=='1'){
					onStatus =0;
				}else{
					onStatus =1;
				}
				
				_adzonedetail.openOrCloseAdToInfo(adZoneId, adId, onStatus, function(){
					
					if(onStatus == '1') {
						this_.attr("onStatus","1");
						this_.find("img").attr("src", "../images/icon_enable.png");
						this_.parent().next().html("已开启");
					}else {
						this_.attr("onStatus","0");
						this_.find("img").attr("src", "../images/icon_disable.png");
						this_.parent().next().html("已关闭");
						
					}

				});
				return false;
			});
			
			// 设置默认、非默认广告事件
			$(".default-btn").click(function() {
				if($(this).hasClass("is-default")) {
					$(this).html("默认");
					$(this).removeClass("is-default");
				} else {
					$(this).html("非默");
					$(this).addClass("is-default");
				}
				return false;
			});
			
			// 编辑广告事件
			$(".edit-btn").click(function() {
				var adZoneId = $(this).attr('adZoneId');
				var adId = $(this).attr('adId');
				_adzonedetail.selectAdToInfo(adZoneId,adId);
//				selectAdToInfo('${adZoneId}','${item.id}');
				/*layerWindow = $.layer({
					type: 1,
					title: '编辑广告活动', //不显示默认标题栏
					area: [layerWidth, layerHeight],
					page: {html: layerHtml}
				});*/
				return false;
			});
			
			// 删除广告事件
			$(".delete-btn").click(function() {
//				confirm("确认删除“" + $(this).parent().parent().parent().find(".ad-title").html()  + "”？");
				
				var adZoneId = $(this).attr('adZoneId');
				var adId = $(this).attr('adId');
				var synchStatus = $(this).attr('synchStatus');
				
				_adzonedetail.deleteAdToInfo(adZoneId, adId, synchStatus);
				return false;
			});
			
			// 弹出层->选择下方缩略图-使用其他图片、链接地址-商家页面事件
			$("body").on("click", "#layer-min-img-radio", function() {
				$("#layer-min-img-item").removeClass("display-none");
				$("#layer-min-img-check").removeClass("display-none");
			});
			$("body").on("click", "#layer-link-radio", function() {
				$("#layer-link-item").removeClass("display-none");
			});
			$("body").on("click", "#layer-no-min-img-radio", function() {
				$("#layer-min-img-item").addClass("display-none");
				$("#layer-min-img-check").addClass("display-none");
			});
			$("body").on("click", "#layer-no-link-radio", function() {
				$("#layer-link-item").addClass("display-none");
			});
			
			// 弹出层 -> 取消按钮事件
			$("body").on("click", "#layer-close", function() {
				layer.close(layerWindow);
				return false;
			});
		},
		selectAdToInfo:function(adZoneId, adId) {
			var paraAdIdStr = "";
			if(adId!=''){
				paraAdIdStr = "&adId=" + adId;
			}
			_advertisements.popEditAdvertisements(adZoneId,adId);
		},
		openOrCloseAdToInfo:function(adzonesid,adId,onstatus,fn) {
			$.ajax({
				type : "post",
				url : "../adzones/openOrCloseAd",
				data : {
					adZoneId : adzonesid,
					id : adId,
					onStatus : onstatus
				}
			}).done(function(data) {
				if(data=="success"){
					fn && fn();
				}
			}).fail(function() {
				alert("error");
			});
		},
		deleteAdToInfo:function(adzonesid,adId,synchstatus) {
			if(confirm("确定删除吗？"))
			$.ajax({
				type : "post",
				url : "../adzones/deleteAd",
				data : {
					adZoneId : adzonesid,
					id : adId,
					synchStatus : synchstatus
				}
			}).done(function(data) {
//				$("#adlist").html(data);
				loadAdList(adzonesid);
			}).fail(function() {
				alert("error");
			});
		}
}	
//});
function synchStatusClick(adzonesid, synchStatus){
	if(synchStatus != 'all'){
		$("#synchStatus").val(synchStatus);
	}else {
		$("#synchStatus").val("");
	}
	$("#onStatus").val("");
	loadAdList(adzonesid);
}

function onStatusClick(adzonesid, onStatus){
	if(onStatus != 'all'){
		$("#onStatus").val(onStatus);
	}else {
		$("#onStatus").val("");
	}
	$("#synchStatus").val("");
	loadAdList(adzonesid);
}

function loadAdList(adzonesid) {
	var synchStatus = $("#synchStatus").val();
	var startTime = $('#searchStartTime').val();
	var endTime = $('#searchEndTime').val();
	var onStatus = $('#onStatus').val();
	$.ajax({
		type : "post",
		url : "../adzones/ajaxadlist",
		data : {
			adZoneId : adzonesid,
			synchStatus : synchStatus,
			onStatus:onStatus,
			startTime : encodeURIComponent(startTime),
			endTime : encodeURIComponent(endTime)
		}
	}).done(function(data) {
		$("#adlist").html(data);
		_adzonedetail.initList();
	}).fail(function() {
		alert("error");
	});
}


function onclickNav(){
	
}

/* function defaltAd(adZoneId,synchStatus){
	var parasynchStatus =2;
	if(parasynchStatus!=''){
		
		parasynchStatus = "&synchStatus=" + parasynchStatus;
	}
		window.location.href = "../adzones/adlist?adZoneId="+adZoneId+parasynchStatus;
} */

	function closeFloat(){
	floatArea=document.getElementById("popup");
	floatArea.innerHTML="";
	floatArea.style.display="none";
	}
	
	
	function saveFloat(adZoneId){
	floatArea=document.getElementById("popup");
	floatArea.innerHTML="";
	floatArea.style.display="none";
	window.location.href = "../adzones/adlist?adZoneId="+adZoneId;
	}
	
	function defaultAd(adzonesid,adId,defaultPicture){
		$.ajax({
			type : "post",
			url : "/adzones/defaultAd",
			data : {
				adZoneId : adzonesid,
				id : adId,
				defaultPicture:defaultPicture
			}
		}).done(function(data) {
			if(data=="success"){
			var defaultAd=adId+"de";
			var val=$("#"+defaultAd).val();
			if(val=="默认"){
				$("#"+defaultAd).val("设为默认");
				
			}
			if(val=="设为默认"){
				$("#"+defaultAd).val("默认");
			}
			}
			if(data=="error"){
				alert("已存在默认广告");
			}
		}).fail(function() {
			alert("error");
		});
	}