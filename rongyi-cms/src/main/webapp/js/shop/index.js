$(document).ready(function(){

	/**
	 * 事件代码
	 */
	
	loadShopList();
	
	// 启用、停用商家事件
	$("body").on("click",".enable-status",function() {
		var this_ = $(this);
		var shopId = this_.attr('shopId');
		var shopOnStatus=this_.attr('shopOnStatus');
		openOrCloseShop(shopId, shopOnStatus, function() {
			loadAajxListBySearchType();
		});
		return false;
	});
	
	// 推荐、取消推荐商家事件
	$("body").on("click",".recommend-status",function() {
		var this_ = $(this);
		var shopId = this_.attr('shopId');
		var shopRecommend =  this_.attr('shopRecommend');
		recomShop(shopId, shopRecommend, function(){
			loadAajxListBySearchType();
		});
		return false;
	});
	
	
	// 二级菜单显示
	$("#category-nav").mouseenter(function(){
		$("#category-second-nav").css("display", "block");
	});
	$("#category-nav").mouseleave(function(){
		$("#category-second-nav").css("display", "none");
	});
	$("#sync-nav").mouseenter(function(){
		$("#sync-second-nav").css("display", "block");
	});
	$("#sync-nav").mouseleave(function(){
		$("#sync-second-nav").css("display", "none");
	});
	$("#sync-second-nav,#category-second-nav").click(function() {
		$("#left-nav a").removeClass("selected");
		$(this).prev().addClass("selected");
		$(this).css("display", "none");
	});
});


function submitSuccess(data){
	$("#shoplist").html(data);
}


		
function loadAajxListBySearchType(){
	var type = $("#searchType").val();
	if(type==0){
		loadSynchStatus();
	}else{
		loadShopList();
	}
	
}
		

function loadShopList() {
	$("#searchType").val(1);
	$.ajax({
		type : "post",
		url : "../shops/ajaxSearchlist",
		data : $('#searchForm').serialize()
		
	}).done(function(data) {
		$("#shoplist").html(data);
		
	});
}

function categroyClick(id){
	$("#pageNum").val(1);
	$('.category_a').each(function(){
		$(this).removeClass("selected");
	});
	$('#category_'+id).addClass("selected");
	if(id!='all'){
		$("#categoriesId").val(id);
	}else {
		$("#categoriesId").val("");
	}
	
	loadShopList();
}

function onStatusClick(id){
	$("#pageNum").val(1);
	$('.shop_on_status').each(function(){
		$(this).removeClass('default-selecter-selected');
	});
	$('#shop_on_status_'+id).addClass('default-selecter-selected');
	if(id != 'all') {
		$("#onStatus").val(id);
	}else {
		$("#onStatus").val('');
	}
	loadShopList();	
}

function onRecommendClick(id){
	$("#pageNum").val(1);
	$('.shop_on_recommend').each(function(){
		$(this).removeClass('default-selecter-selected');
	});
	$('#shop_on_recommend_'+id).addClass('default-selecter-selected');
	if(id != 'all') {
		$("#recommend").val(id);
	}else {
		$("#recommend").val('');
	}
	 loadShopList();	
}


function onSearchClick(){
	$("#pageNum").val(1);
	var value= $("#searchParamsid").val();
	$("#searchParams").val(value);
	loadShopList();
}


function synchStatusClick(id){
	$("#pageNum").val(1);
	$('.syschstatus_a').each(function(){
		$(this).removeClass("selected");
	});
	$('#syschstatusid'+id).addClass("selected");
	if(id!='all'){
		$("#synchStatus").val(id);
	}else {
		$("#synchStatus").val("");
	}
	loadSynchStatus();
}

function loadSynchStatus(){
	$("#searchType").val(0);
	$.ajax({
		type : "post",
		url : "../shops/ajaxSynchlist",
		data : $('#synchForm').serialize()
		
	}).done(function(data) {
		$("#shoplist").html(data);
	});	
}

//回车事件
function keypress(){
        if(event.keyCode == "13")    
        {
        	onSearchClick();
        }
    };
