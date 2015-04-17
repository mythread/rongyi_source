//$(document).ready(function() {
function loadAdvertisementsPageInit() {
	//验证日期
	/*$('.edit_advertisements_div').find('.verifyDate').focus(function(){
		$('.verifyDate').each(function(){
			var obj = $(this);
			obj.next('.tip').remove();
		});
	});*/
	/*$('.edit_advertisements_div').find('.verifyDate').blur(function(){
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		var adId = $(".adId").val();
		var adZoneId = $('.adZoneId').val();
		var obj = $(this);
		if(startTime!='' && endTime!='') {
			_advertisements.verifyDate(startTime, endTime, adId, adZoneId, function(data){
				obj.parent().append("<span class='tip' style='color:red'></span>");
				layer.msg(data.msg, 1, 1);
			});
		}
	});*/
	//绑提交FORM
	$(".submitAdForm").click(function(){
//		alert(">>>>>>"+$('#adForm').serializeObject());
		var adZoneId = $(".adZoneId").val();
		var adId = $(".adId").val();
		var adName = $(".adName").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var picture = $("#adPic").val();
		var hasMinPic = $(".hasMinPic:checked").val();
		var minPicture = $(".picPath").val();
		var hasLink = $(".hasLink:checked").val();
		var shopUrl = $("#shops_url").val();
//		var defaultAd = $(".defaultAd:checked").val();
		var onStatus = $(".onStatus:checked").val();
//		if(defaultAd != '1') {
//			defaultAd = '0';
//		}
		if(onStatus != '1') {
			onStatus = '0';
		}
//		alert($('#uploadImage').attr('src'));return false;
		var imgSrc = $('#uploadImage').attr('src');
//		alert(defaultAd + '<>' + onStatus);return false;
		var url2_ = "../advertisements/save";
		var paramsJson2_ = {"adZoneId":adZoneId};
		if(imgSrc.indexOf('http')>-1) {//未上传图片
			paramsJson2_['isUpdateImg'] = '0';
			paramsJson2_['picture'] = imgSrc;
		}else {
			paramsJson2_['picture'] = picture;
		}
		paramsJson2_['adId'] = adId;
		paramsJson2_['adName'] = adName;
		paramsJson2_['startTime'] = startTime;
		paramsJson2_['endTime'] = endTime;
//		paramsJson2_['picture'] = picture;
		paramsJson2_['hasMinPic'] = hasMinPic;
		paramsJson2_['minPicture'] = minPicture;
		paramsJson2_['hasLink'] = hasLink;
		paramsJson2_['shopUrl'] = shopUrl;
//		paramsJson2_['defaultAd'] = defaultAd;
		paramsJson2_['onStatus'] = onStatus;
		_util.ajaxSubmit(url2_,paramsJson2_,function(data) {
//			location.href = "../advertisements/edit?adZoneId="+data.adZoneId+"&adId="+data.adId;
			location.reload();
		});
		/*_util.ajaxSubmitForm('adForm',function(data) {
			//alert("发布成功！");
			location.href = "../advertisements/edit?adZoneId="+data.adZoneId+"&adId="+data.adId;
		});*/
	});
	//缩略图radio
	$(".hasMinPic").click(function() {
//		alert($(this).val());
		var t = $(this).val();
		if(t == 2) {
			$(".picPath").val("");
		}
	});
	
	$('#shops_name').focus(function(){
		if($(this).val() == '请输入商家名称') {
			$(this).val('');
		}
	});
	$('#shops_name').blur(function(){
		if($(this).val() == '') {
			$(this).val('请输入商家名称');
		}
	});
	
	 $("#shops_name").autocomplete("../advertisements/searchShops", {
		minChars: 1,
		matchCase:false,//不区分大小写
		autoFill: false,
		max: 10,
		parse: function(data) {
//			alert(data.status+data.data);
//			var data2 = eval('('+data+')'); 
			return $.map(eval(data.data), function(row) {
				return {
					data: row,
					value: row.id + row.name,
					result: row.name
				}
			});
		},
		formatItem: function(row, i, max,term) {
			return  row.name;
		},
		formatMatch: function(row, i, max) {
			return row.name;
		},
		formatResult: function(row) {
			return row.id;
		}
	 }).result(function(event, item) { 
		 $("#shops_url").val(item.id);
	 }); 
	 
	//选择图片之后马上触发上传事件
	$('#adImage').live({
		'change':function(ev){
			_advertisements.ajaxFileUpload();
			return false;
		}
	});
	$('#resizePic').live({
		'change':function(ev){
			_advertisements.uploadResizePic('resizePic');
			return false;
		}
	});
}

var _advertisements={
	viewLayer : -1,
	ajaxFileUpload:function(){
//		alert($('.edit_advertisements_div').find('#adImage').val());
		var fieldId_ = 'adImage';
		var picPath = '/advertise/original/';//上传的目录
		var maxSize = '1048576';//1024*1024=1M
		var picType = '.jpg.png';
		var paramsJson = {};
		paramsJson['picPath'] = picPath;
		paramsJson['maxSize'] = maxSize;
		paramsJson['picType'] = picType;
		paramsJson['width'] = '1032';
		paramsJson['height'] = '1344';
		_util.ajaxFileUploadPublic(fieldId_, paramsJson, function(data) {
			 $("img[id='uploadImage']").attr("src", _util.SHOW_IMG_PATH+picPath+data.fileName);
             $('#result').html(data.msg);  
             $('#adPic').val(data.fileName);
		});
	},
	pickDate:function(s, e){
		var startTime = $('#startTime').val();
		var endTime = $('#endTime').val();
		if(s == 's') {
			endTime = e;
		}
		if(e == 'e') {
			startTime = s;
		}
		var adId = $(".adId").val();
		var adZoneId = $('.adZoneId').val();
//		var obj = $(this);
		if(startTime!='' && endTime!='') {
			_advertisements.verifyDate(startTime, endTime, adId, adZoneId, function(data){
				if(data.status == 1) {
//					console.log('ok2');
				}else {
					layer.msg(data.msg, 1, 1);
//					console.log('no');
				}
			});
		}
//		return true;
	},
	verifyDate:function(startTime,endTime,adId,adZoneId,fn) {//验证开始时间和结束时间
		$.getJSON("../advertisements/verifyDate", { startTime: startTime, endTime: endTime, adId: adId,adZoneId:adZoneId }, function(data){
			fn && fn(data);
			return;
			/*if(data.status == 1) {
//				alert('ok');
			}else {
				fn && fn(data);
				return;
			}*/
		});
	},
	uploadResizePic:function(fieldId) {
		var fieldId_ = fieldId;
		var picPath = '/advertise/resize/';//上传的目录
		var maxSize = '10240';//10kb
		var picType = '.jpg.png';
		var paramsJson = {};
		paramsJson['picPath'] = picPath;
		paramsJson['maxSize'] = maxSize;
		paramsJson['picType'] = picType;
		paramsJson['width'] = '100';
		paramsJson['height'] = '100';
		_util.ajaxFileUploadPublic(fieldId, paramsJson, function(data) {
			$('.showImg').attr("src", _util.SHOW_IMG_PATH+picPath+data.fileName);  
//			$('#'+fieldId_).parents('tr').find('.info').html(data.msg);
			$('.picPath').val(data.fileName);
		});
	},
	//弹出新建/设置广告活动浮层
	popEditAdvertisements:function(adZoneId,adId) {
		$(".pop_edit_advertisements_div").load("../advertisements/edit", {adZoneId:adZoneId,adId:adId}, function(){
			var title_ = '新建广告活动';
			if(adId != '') {
				title_ = '设置广告活动';
			}
			  var i2 = $.layer({
				  type: 1,
				  title: title_,
				  closeBtn: false,
				  border : [5, 0.5, '#666', true],
				  offset: ['100px',''],
				  move: ['.juanmove', true],
				  area: ['720px','480px'],
				  page: {
					  html: "<div class='edit_advertisements_div'>"+$(".pop_edit_advertisements_div").html()+"</div>"
				  },
				  success: function(){
//					  alert($(".pop_edit_advertisements_div").html());
					  $(".pop_edit_advertisements_div").html("");
					  loadAdvertisementsPageInit();
					  $('.edit_advertisements_div').find('.cancelPop').click(function() {
						  layer.close(i2);
					  });
				  }
			  });
		});
	},
	//弹出查看广告活动浮层
	popViewAdvertisements:function(adZoneId,adId) {
		$(".pop_view_advertisements_div").load("../advertisements/view", {adZoneId:adZoneId,adId:adId}, function(){
			if($(".view_advertisements_div").html() != null) {
				$(".view_advertisements_div").html($(".pop_view_advertisements_div").html());
				$(".pop_view_advertisements_div").html("");
				  $('.view_advertisements_div').find('.cancelPop').click(function() {
					  layer.close(viewLayer);
				  });
				  $('.view_advertisements_div').find('.editAd').click(function() {
					  var adZoneId = $('.view_advertisements_div').find('.adZoneId').val();
					  var adId = $('.view_advertisements_div').find(".adId").val();
//					  var adId = $(this).attr("adId");
//					  var adZoneId = $(this).attr('adZoneId');
//					  alert(adZoneId+"<>"+ adId);
					  layer.close(viewLayer);
					  _advertisements.popEditAdvertisements(adZoneId, adId);
				  });
			}else {
				viewLayer = $.layer({
				  type: 1,
				  title: '查看广告素材',
				  area: ['720px', '520px'],
				  page: {
					  html: "<div class='view_advertisements_div'>"+$(".pop_view_advertisements_div").html()+"</div>"
				  },
				  success: function(){
					  $(".pop_view_advertisements_div").html("");
					  $('.view_advertisements_div').find('.cancelPop').click(function() {
						  layer.close(viewLayer);
					  });
					  $('.view_advertisements_div').find('.editAd').click(function() {
						  var adZoneId = $('.view_advertisements_div').find('.adZoneId').val();
						  var adId = $('.view_advertisements_div').find(".adId").val();
//						  var adId = $(this).attr("adId");
//						  var adZoneId = $(this).attr('adZoneId');
//						  alert(adZoneId+"<>"+ adId);
						  layer.close(viewLayer);
						  _advertisements.popEditAdvertisements(adZoneId, adId);
					  });
				  }
			  });
			}
		});
	}

}


