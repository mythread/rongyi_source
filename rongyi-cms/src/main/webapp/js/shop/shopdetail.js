$(document).ready(function(){

	/**
	 * 变量定义
	 */
	
	// 弹出层
	var layerWindow = null;
	
	// 弹出层的HTML
//	var titleLayerHtml = $("#title-layer").html();
//	var infoLayerHtml = $("#info-layer").html();
//	var imgLayerHtml = $("#img-layer").html();
//	var descriptionLayerHtml = $("#description-layer").html();
//	$("#title-layer").remove();
//	$("#info-layer").remove();
//	$("#img-layer").remove();
//	$("#description-layer").remove();
	
	/**
	 * 事件代码
	 */

	// 可编辑区域鼠标浮动到区域上，显示编辑按钮
	$(".detail-editable").mouseenter(function() {
		$(this).addClass("detail-editable-enable");
		$(this).find(".detail-editor-btn").addClass("detail-editor-btn-enable");
	});
	$(".detail-editable").mouseleave(function() {
		$(this).removeClass("detail-editable-enable");
		$(this).find(".detail-editor-btn").removeClass("detail-editor-btn-enable");
	});
	
	// 点击小图片切换大图片
	$(".detail-small-pic").click(function() {
		$(".detail-big-pic").attr("src", $(this).attr("src"));
	});
	
	// 点击编辑按钮弹出层
	$("#detail-head .detail-editor-btn").click(function() {
		/*layerWindow = $.layer({
	        type: 1,
	        title: '编辑logo及名称',
	        area: ['auto', 'auto'],
	        page: {html: titleLayerHtml}
	    });*/
		_shops.popLayer(1);
		return false;
	});
	$("#detail-infomation .detail-editor-btn").click(function() {
		/*layerWindow = $.layer({
	        type: 1,
	        title: '编辑商家信息',
	        area: ['auto', 'auto'],
	        page: {html: infoLayerHtml}
	    });*/
		_shops.popLayer(2);
		return false;
	});
	$("#detail-body-right .detail-editor-btn").click(function() {
		/*layerWindow = $.layer({
	        type: 1,
	        title: '编辑图片',
	        area: ['auto', 'auto'],
	        page: {html: imgLayerHtml}
	    });*/
		_shops.popLayer(3);
		return false;
	});
	$("#detail-footer .detail-editor-btn").click(function() {
		/*layerWindow = $.layer({
	        type: 1,
	        title: '编辑商家简介',
	        area: ['auto', 'auto'],
	        page: {html: descriptionLayerHtml}
	    });*/
		_shops.popLayer(4);
		return false;
	});

	// 弹出层关闭（取消）按钮
	$("body").on("click", ".layer-close", function() {
		layer.close(layerWindow);
		return false;
	});
});