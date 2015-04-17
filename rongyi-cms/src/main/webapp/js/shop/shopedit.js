$(document).ready(function(){

	_editV.hasChanged = -1;
	/**
	 * 变量定义
	 */
	
	// 弹出层
	var layerWindow = null;
	
	// 弹出层的HTML
	///var brandLayerHtml = $("#brand-layer").html();
	//var createBrandLayerHtml = $("#create-brand-layer").html();
	//$("#brand-layer").remove();
	//$("#create-brand-layer").remove();
	
	/**
	 * 事件代码
	 */
	
	
	// 添加、删除标签事件
	$("body").on("click", ".add-tag", function() {
		$(this).find("input").css("display", "inline");
		$(this).find("span").css("display", "none");
		$(this).find("input").focus();
	});
	$("body").on("blur", ".add-tag input", function() {
		$(this).parents(".add-tag").find("span").css("display", "inline");
		$(this).css("display", "none");
		var input = $(this).val();
		$(this).val("");
		if(input != null && input != '') {
			var newLabel = "<div class=\"shop-tag  shop-tag-text\" tag='"+input+"'>" + input + "<img src=\"../images/icon_delete.png\"></div>";
			$(this).parents(".add-tag").before(newLabel);
		}
	});
	$("body").on("keypress", ".add-tag input", function(event) {
		if(event.keyCode == "13"){ 
			var input = $(this).val();
			if(input != null && input != '') {
				var newLabel = "<div class=\"shop-tag  shop-tag-text\" tag='"+input+"'>" + input + "<img src=\"../images/icon_delete.png\"></div>";
				$(this).parents(".add-tag").before(newLabel);
			}
			$(this).val("");
			$(this).blur();
		}
	});
	$("body").on("click", ".shop-tag img", function() {
		$(this).parent().remove();
	});
	
	// 弹出层关闭（取消）按钮
	$("body").on("click", ".layer-close", function() {
		layer.close(layerWindow);
		return false;
	});

	
	//图片赋值
	var mytime1 = setInterval(function(){
		var shopsId = $('.shopsId').val();
		if(typeof(shopsId) != undefined && shopsId!='') {
			clearInterval(mytime1);
			_editV.initPic();
		}
	},300);
	//商家简介
	// 文本框字数统计，展示剩余字数
	$("body").on("keyup", ".layer-settings textarea,#description-textarea", function() {
		getRestWordCount(this);
	});
	$("body").on("change", ".layer-settings textarea,#description-textarea", function() {
		getRestWordCount(this);
	});
	function getRestWordCount(textareaElement) {
		var max = 160;
		var content = $(textareaElement).val();
		var length = content.length;
		var rest = max - length;
		if(rest < 0){
			content = content.substr(0, max);
			$(textareaElement).val(content);
			layer.msg("超过字数限制，多出的字将被截断！", 1, 1);
			rest = 0;
		}
		$(".textarea-info span").html(rest);
	}
	
	var mytime2 = setInterval(function(){
		if($(".shops_description").val().length > 0) {
			clearInterval(mytime2);
			$(".text-count").text(160-$(".shops_description").val().length);
		}
	},300);
	//选择图片之后马上触发上传事件
	$('#shopsPic1').live({
		'change':function(ev){
			_editV.uploadShopsPic();
			return false;
		}
	});
	$("body").on("click",".recommend-status",function() {
		var this_ = $(this);
		var shopId = this_.attr('shopId');
		var shopRecommend =  this_.attr('shopRecommend');
		recomShop(shopId, shopRecommend, function(){
			if(shopRecommend == 1) {
				this_.find('img').attr('src',_editV.icon_enable);
				this_.parent('p').next('p').html('已推荐');
				this_.attr('shopRecommend',0)
			}else {
				this_.find('img').attr('src',_editV.icon_disable);
				this_.parent('p').next('p').html('未推荐');
				this_.attr('shopRecommend',1)
			}
		});
		return false;
	});
	// 启用、停用商家事件
	$("body").on("click",".enable-status",function() {
		var this_ = $(this);
		var shopId = this_.attr('shopId');
		var shopOnStatus=this_.attr('shopOnStatus');
		openOrCloseShop(shopId, shopOnStatus, function() {
			if(shopOnStatus == 1) {
				this_.find('img').attr('src',_editV.icon_enable);
				this_.parent('p').next('p').html('营业中');
				this_.attr('shopOnStatus',0)
			}else {
				this_.find('img').attr('src',_editV.icon_disable);
				this_.parent('p').next('p').html('已停业');
				this_.attr('shopOnStatus',1)
			}
		});
		return false;
	});
	//预览
	$('#preview-btn').off().on({
		'click':function(ev){
			var shopsId = $('.shopsId').val();
			var shopsName = $('.shops_name').val();//商家名称
			var brandId = $('.shops_logo').val();//品牌ID
			var shopsTags = '';//$('.shops_tags').val();//标签
			$('.shop-tag-text').each(function(){
				shopsTags += $(this).attr('tag') + ' ';
			});
			var shops_openTime = $('.shops_openTime').val();
			var shops_telephone = $('.shops_telephone').val();
			var shops_recommend = $('.shops_recommend:checked').val();
			var shops_description = $('.shops_description').val();
			var paramsJson_ = {};
			$('.upload-imgs').find('.show_pic').each(function(index){
				if($(this).attr('src').indexOf('addition.png') == -1) {
					paramsJson_['shopsPic_'+index] = $(this).attr('src');
				}
			});
			var url_ = "../shops/preview";
			paramsJson_['shopsId'] = shopsId;
			paramsJson_['shopsName'] = encodeURIComponent(shopsName);
			paramsJson_['shopsLogo'] = encodeURIComponent(brandId);
			paramsJson_['shopsTags'] = encodeURIComponent(shopsTags);
			paramsJson_['openTime'] = encodeURIComponent(shops_openTime);
			paramsJson_['telephone'] = encodeURIComponent(shops_telephone);
			paramsJson_['recommend'] = encodeURIComponent(shops_recommend);
			paramsJson_['description'] = encodeURIComponent(shops_description);
			paramsJson_['timeStamp_'] = new Date().getTime();
//			window.open(url_+'?paramsJson='+JSON.stringify(paramsJson_));
			_util.createForm(url_, JSON.stringify(paramsJson_), '_blank')
			return false;
		}
	});
	//删除标签 
//	$('.del_tag_').off().on({
//		'click':function(ev){
//			$(this).parent('.shop-tag').remove();
//			return false;
//		}
//	});
	/*$('input').bind('input propertychange change', function() {
		_editV.pageHasChanged();
	});
	$('textarea').bind('input propertychange', function() {
		_editV.pageHasChanged();
	});*/
});

var _editV = {
	hasChanged:-1,//判断是否有修改过页面上的数据
	shopPicPosition:-1,//记忆点击的位置，好插入图片
	picInfo:'共X张，还能上传<span>Y</span>张',//共X张，还能上传<span>Y</span>张
	icon_enable:'../images/icon_enable.png',
	icon_disable:'../images/icon_disable.png',
	saveBase:function() {//保存商家基本信息
		var shopsId = $('.shopsId').val();
		var shops_name = $('.shops_name').val();
		var shops_logo = $('.shops_logo').val();
		var shops_tags = "";//$('.shops_tags').val();
		$('.shop-tag-text').each(function(){
			shops_tags += $(this).attr('tag') + ' ';
		});
		if($.trim(shops_name) == '') {
			layer.msg('商户名称不可为空!', 1, 1);
			return false;
		}
		if($.trim(shops_logo) == '') {
			layer.msg('品牌不可为空!', 1, 1);
			return false;
		}
		if($.trim(shops_tags) == '') {
			layer.msg('商户标签不可为空!', 1, 1);
			return false;
		}
		var url_ = "../shops/saveBase";
		var paramsJson_ = {"shopsId":shopsId};
		paramsJson_['shopsName'] = encodeURIComponent(shops_name);
		paramsJson_['shopsLogo'] = encodeURIComponent(shops_logo);
		paramsJson_['shopsTags'] = encodeURIComponent(shops_tags);
		paramsJson_['timeStamp_'] = new Date().getTime();
		_util.ajaxSubmit(url_,paramsJson_,function() {
			layer.msg('保存成功', 1, 1);
			setTimeout("location.reload();", 1000 );
			
		});
	},
	saveOptional:function() {//保存商家辅助信息
		var shopsId = $('.shopsId').val();
		var shops_openTime = $('.shops_openTime').val();
		var shops_telephone = $('.shops_telephone').val();
		var shops_recommend = $('.shops_recommend:checked').val();
		var shops_description = $('.shops_description').val();
		if($.trim(shops_openTime) == '') {
			layer.msg('营业时间不可为空!', 1, 1);
			return false;
		}
		if($.trim(shops_telephone) == '') {
			layer.msg('商家电话不可为空!', 1, 1);
			return false;
		}
		if($.trim(shops_description) == '') {
			layer.msg('商家简介不可为空!', 1, 1);
			return false;
		}
		var url_ = "../shops/saveOptional";
		var paramsJson_ = {"shopsId":shopsId};
		paramsJson_['openTime'] = encodeURIComponent(shops_openTime);
		paramsJson_['telephone'] = encodeURIComponent(shops_telephone);
		paramsJson_['recommend'] = encodeURIComponent(shops_recommend);
		paramsJson_['description'] = encodeURIComponent(shops_description);
		paramsJson_['timeStamp_'] = new Date().getTime();
		_util.ajaxSubmit(url_,paramsJson_,function() {
			layer.msg('保存成功', 1, 1);
			setTimeout("location.reload();", 1000 );
		});
	},
	savePic:function() {//保存图片
		var shopsId = $('.shopsId').val();
		var paramsJson_ = {"shopsId":shopsId};
		$('.editPic').find('.shops_pic').each(function(index){
			paramsJson_['shopsPic_'+(index+1)] = $(this).html();
			paramsJson_['shopsId_'+(index+1)] = $(this).parents('.upload-img-show').find('.shopsId').val();
			//$('#'+fieldId_).parents('tr').find('.shops_pic').attr('isUpdate','updated');  
			paramsJson_['isUpdate_'+(index+1)] = $(this).attr('isUpdate');
		});
		var url_ = "../shops/savePic";
		paramsJson_['timeStamp_'] = new Date().getTime();
		_util.ajaxSubmit(url_,paramsJson_,function() {
			layer.msg('保存成功', 1, 1);
			setTimeout("location.reload();", 1000 );
		});
	},
	initPic:function() {//初始图片
		var url2_ = "../shops/loadInfo";
		var shopsId = $('.shopsId').val();
		var paramsJson2_ = {"shopsId":shopsId};
		_util.ajaxSubmit(url2_,paramsJson2_,function(data) {
			//初始化数据
			$('.upload-imgs').find('.show_pic').eq(0).attr('src',data.pic_1);
			$('.upload-imgs').find('.show_pic').eq(1).attr('src',data.pic_2);
			$('.upload-imgs').find('.show_pic').eq(2).attr('src',data.pic_3);
			$('.upload-imgs').find('.show_pic').eq(3).attr('src',data.pic_4);
			
			$('.upload-imgs').find('.upload-img-show').eq(0).find('.shopsId').val(data.id_1);
			$('.upload-imgs').find('.upload-img-show').eq(1).find('.shopsId').val(data.id_2);
			$('.upload-imgs').find('.upload-img-show').eq(2).find('.shopsId').val(data.id_3);
			$('.upload-imgs').find('.upload-img-show').eq(3).find('.shopsId').val(data.id_4);
			
			$('.upload-imgs').find('.shops_pic').eq(0).html(data.fileName_1);
			$('.upload-imgs').find('.shops_pic').eq(1).html(data.fileName_2);
			$('.upload-imgs').find('.shops_pic').eq(2).html(data.fileName_3);
			$('.upload-imgs').find('.shops_pic').eq(3).html(data.fileName_4);
			
			var id_ = 0;
			$('.upload-imgs').find('.upload-img-show').each(function(index) {
//				console.log(index);
				id_ = $(this).find('.shopsId').val();
				if(id_ > 0) {
					$(this).find('.upload-img-close-btn').show();
				}else {
					$(this).find('.upload-img-close-btn').hide();
					$(this).find('.show_pic').attr('src','../images/addition.png');
				}
			});
			_editV.removePic();
			_editV.addPic();
			_editV.shopPicInfo();
		});
	},
	uploadShopsPic:function() {
		var index_ = _editV.findShowPosition();
		console.log('空位置>>>'+index_);
		if(index_ == -1) {
			alert('无空图片位置了哦！');
			return false;
		}
		var fieldId = 'shopsPic1';
		var picPath = '/shops/original/';//上传的目录
		var maxSize = '10485760';//1024*1024=1M
		var picType = '.jpg.png';
		var paramsJson = {};
		paramsJson['picPath'] = picPath;
		paramsJson['maxSize'] = maxSize;
		paramsJson['picType'] = picType;
		paramsJson['width'] = '640';
		paramsJson['height'] = '480';
		_util.ajaxFileUploadPublic(fieldId, paramsJson, function(data) {
			$('.upload-imgs').find('.show_pic').eq(index_).attr('src',_util.SHOW_IMG_PATH+picPath+data.fileName);
			$('.upload-imgs').find('.shops_pic').eq(index_).html(data.fileName);
			$('.upload-imgs').find('.shops_pic').eq(index_).attr('isUpdate','updated');
			$('.upload-imgs').find('.upload-img-show').eq(index_).find('.upload-img-close-btn').show();
			_editV.removePic();
			_editV.shopPicInfo();
		});
	},
	findShowPosition:function() {//查找显示图片的位置
		var index_ = -1;
		var first = true;
		if(_editV.shopPicPosition > -1) {
			index_ = _editV.shopPicPosition;
		}else {
			$('.upload-imgs').find('.upload-img-show').each(function(index) {
				if($(this).find('.shops_pic').html() == '' && first) {
					index_ = index;
					first = false;
				}
			});
		}
		return index_;
	},
	removePic:function() {//删除图片功能
		$('.upload-imgs').find('.upload-img-close-btn').off().on({
			'click':function(ev){
				if(confirm("删除图片无法恢复您确定要删除吗？")) {
					$(this).parent('.upload-img-show').find('.show_pic').attr('src','../images/addition.png');
//					$(this).parent('.upload-img-show').find('.shopsId').val('');
					$(this).parent('.upload-img-show').find('.shops_pic').html('');
					$(this).parent('.upload-img-show').find('.shops_pic').attr('isUpdate','delete');
					$(this).hide();
					_editV.addPic();
					_editV.shopPicInfo();
				}
				return false;
			}
		});
	},
	addPic:function() {//点击图片弹出上传对话框
		$('.upload-imgs').find('.show_pic').off().on({
			'click':function(ev){
				if($(this).attr('src').indexOf('addition.png') > -1) {
					_editV.shopPicPosition = $('.upload-imgs').find('.show_pic').index(this);
//					console.log('>>>>>'+$('.upload-imgs').find('.show_pic').index(this));
					$("#shopsPic1").click();  
				}
				return false;
			}
		});
	},
	shopPicInfo:function() {
		var picNum = 4;
		var picNumDesc = _editV.picInfo;
		$('.upload-imgs').find('.upload-img-show').each(function(index) {
//			console.log(">>>"+$(this).find('.shops_pic').html());
			if($(this).find('.shops_pic').html() == '') {
				picNum--;
			}
		});
		$('.img-upload-info').html(picNumDesc.replace('X',picNum).replace('Y',4-picNum));
	},
	pageHasChanged:function() {//页面有修改的时候调用；
		_editV.hasChanged = 1;
		var shopsId = $('.shopsId').val();
		var shops_name = $('.shops_name').val();
		var shops_logo = $('.shops_logo').val();
		var shops_tags = "";
		$('.shop-tag-text').each(function(){
			shops_tags += $(this).attr('tag') + ' ';
		});
		var shops_openTime = $('.shops_openTime').val();
		var shops_telephone = $('.shops_telephone').val();
		var shops_recommend = $('.shops_recommend:checked').val();
		var shops_description = $('.shops_description').val();
		var url_ = "../shops/verifyChanged";
		var paramsJson_ = {"shopsId":shopsId};
		paramsJson_['shopsName'] = encodeURIComponent(shops_name);
		paramsJson_['shopsLogo'] = encodeURIComponent(shops_logo);
		paramsJson_['shopsTags'] = encodeURIComponent(shops_tags);
		paramsJson_['openTime'] = encodeURIComponent(shops_openTime);
		paramsJson_['telephone'] = encodeURIComponent(shops_telephone);
		paramsJson_['recommend'] = encodeURIComponent(shops_recommend);
		paramsJson_['description'] = encodeURIComponent(shops_description);
		$('.editPic').find('.shops_pic').each(function(index){
			paramsJson_['shopsPic_'+(index+1)] = $(this).html();
			paramsJson_['shopsId_'+(index+1)] = $(this).parents('.upload-img-show').find('.shopsId').val();
			paramsJson_['isUpdate_'+(index+1)] = $(this).attr('isUpdate');
		});
		paramsJson_['timeStamp_'] = new Date().getTime();
		_util.ajaxSubmit_(url_,paramsJson_,function(data) {
			if(data.status==1){
				return;
			}else {
				console.log(data.msg);
				return '您输入的内容尚未保存，确定离开此页面吗？';
			}
		});
		
		console.log('has changed3'+new Date().getTime());
		
	}
}