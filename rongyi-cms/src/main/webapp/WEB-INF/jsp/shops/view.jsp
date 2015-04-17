<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理-商家详情</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />
<link href="../css/shop/shopdetail.css" rel="stylesheet" type="text/css" />

<!-- 世贸国际广场专用样式 -->
<link href="../css/shop/shopdetail-shimao.css" rel="stylesheet" type="text/css" />

<c:if test="${isedit == 1}">
<!-- 下面这个样式用于切换“查看商家详情”、“可视化视图编辑”页 -->
<link href="../css/shop/shopdetailedit.css" rel="stylesheet" type="text/css" />
</c:if>

<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../js/shop/shopdetail.js"></script>
<script type="text/javascript" src="../js/shops.js"></script>  
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
<script type="text/javascript" src="../js/shop/common.js"></script>
<script type="text/javascript" src="../js/shop/shopedit.js"></script>
</head>
<body>
	<%@include file="../top/top_shop.jsp" %>

	<div id="content">
		<div id="show">
		<input type="hidden" name="shopsId" class="shopsId" value="${shops.id}"/>
			<div class="top-title">
				<span><a href="../shops/list">商家信息管理</a> &nbsp;&gt; &nbsp;详情</span>
				<a href="../shops/list" class="default-gray-btn-style" id="back-btn">返回</a>
			</div>
			
			<hr />
			
			<div id="view-swith">
			
				<c:if test="${isedit == 1}">
					<span>
						<a href="../shops/edit?shopsId=${shops.id}" class="default-selecter default-selecter-selected">可视化视图</a>
						<a href="../shops/editV?shopsId=${shops.id}" class="default-selecter">编辑视图</a>
					</span>
				</c:if>
				<c:if test="${isedit != 1}">
					<span>
						<c:if test="${shops.pid==-1 && shops.recordType==1}">
						　　	<a href="../shops/view?shopsId=${shops.id}" class="default-selecter default-selecter-selected">待同步内容 </a>
						</c:if>
						<c:if test="${shops.pid==-1 && shops.recordType==0}">
						　　	<a href="../shops/view?shopsId=${shops.id}" class="default-selecter default-selecter-selected">当前内容</a>
						</c:if>
						<c:if test="${shops.pid>0 && shops.recordType==1}">
							<a href="../shops/view?shopsId=${shops.pid}" class="default-selecter">当前内容</a>
							<a href="#" class="default-selecter default-selecter-selected">待同步内容 </a>
						</c:if>
						<c:if test="${shops.pid==0 && shops.recordType==0}">
							<a href="#" class="default-selecter default-selecter-selected">当前内容</a>
							<a href="../shops/view?shopsId=${sumAdId}" class="default-selecter">待同步内容 </a>
						</c:if>
					</span>
				</c:if>
			
				<c:if test="${isedit == 1}">
				
					<div class="status-radio">
						<p>
							<a href="#" class="recommend-status ${shops.recommend=='1' ? 'is-recommend' : ''}" shopId='${shops.id}' shopRecommend="${shops.recommend==1 ? '0' : '1'}">
								<c:if test="${shops.recommend==1}"><img src="../images/icon_enable.png" /></c:if><c:if test="${shops.recommend==0}"><img src="../images/icon_disable.png" /></c:if>
							</a>
						</p>
						<p>
							<c:if test="${shops.recommend==1}">已推荐</c:if><c:if test="${shops.recommend==0}">未推荐</c:if>
						</p>
					</div>
					
					<div class="status-radio">
						<p>
							<a href="#" class="enable-status ${shops.onStatus=='1' ? 'is-enable' : ''}" shopId='${shops.id}' shopOnStatus="${shops.onStatus==1 ? '0' : '1'}">
								<c:if test="${shops.onStatus==1}"><img src="../images/icon_enable.png" /></c:if><c:if test="${shops.onStatus==0}"><img src="../images/icon_disable.png" /></c:if>
							</a>
						</p>
						<p>
							<c:if test="${shops.onStatus==1}">营业中</c:if><c:if test="${shops.onStatus==0}">已停业</c:if>
						</p>
					</div>
				</c:if>
				
			</div>
			
			<div>
				<img src="<site:config key='mall.shop.img'/>" style="width: 100%">
			</div>
			
			<div id="view-content">
				
				<div id="details">
					
					<div id="detail-head" class="detail-editable">
						<div id="detail-logo">
							<c:if test="${shops.brands.iconUrl == '-1'}">
								<img src="${SHOW_IMG_PATH}/brands/original/${shops.brands.icon}" />
							</c:if>
							<c:if test="${shops.brands.iconUrl != '-1'}">
								<img src="${shops.brands.iconUrl}" />
							</c:if>
						</div>
						<div id="detail-title">
							${shops.name}
						</div>
						<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
					</div>
					
					<hr />
					
					<div id="detail-body">
					
						<div id="detail-body-left">
							<div id="detail-address">
								<span><c:if test="${floor!=null}">${floor.value}</c:if></span>${shops.shopNumber}
							</div>
							
							<div id="detail-infomation" class="detail-editable">
								<p>标签：${shops.tags}</p>
								<p>营业时间：${shops.openTime}</p>
								<p>
									商铺评价：
									<span class="star-enable">★</span>
									<span class="star-enable">★</span>
									<span class="star-enable">★</span>
									<span class="star-disable">★</span>
									<span class="star-disable">★</span>
								</p>
								<p><a href="http://www.rongyi.com">http://www.rongyi.com</a></p>
								<p><img src="../images/code.png"></p>
								<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
							</div>
						</div>
						
						<div id="detail-body-right" class="detail-editable">
							<img src="${pic}" class="detail-big-pic" />
							<img src="" class="detail-small-pic detail-small-pic1 detail-small-pic-selected" />
							<img src="" class="detail-small-pic detail-small-pic2" />
							<img src="" class="detail-small-pic detail-small-pic3" />
							<img src="" class="detail-small-pic detail-small-pic4" />
							<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
						</div>
						
					</div>
					
					<div id="detail-footer"  class="detail-editable">
						<div id="detail-description">
							${shops.description}
						</div>
						<a href="#" class="default-btn-style detail-editor-btn">编辑</a>
					</div>
					
				</div>
				
			</div>
			
		</div>
	</div>
	
	<!-- 下面是一些弹出层HTML，会在页面加载后移除 -->
	<div id="title-layer" class="editBaseInfoDiv" style="display:none;">
		<div class="layer-container">
			<div class="layer-settings">
				<div class="layer-setting-line">
					<div class="layer-setting-title">商户名称</div>
					<div class="layer-setting-input"><input type="text" name="name" class="layer-setting-input-width shops_name" />
						<input type="hidden" name="id" class="shops_id"/>
					</div>
				</div>
				<div class="layer-setting-line-brand">
					<div class="layer-setting-line">
						<div class="layer-setting-title">品牌</div>
						<div class="layer-setting-input">
							<input type="text" name="logo" class="shops_logo_name layer-setting-input-width" disabled="disabled"/>
							<input type="hidden" name="logo" class="shops_logo" />
							<input type="hidden" name="tags" class="shops_tags"/>
						</div>
					</div>
					<a href="#" onclick="_brands.popLayer(1);" id="get-brand-btn" class="default-black-btn-style">选择品牌</a>
				</div>
			</div>
			<div class="layer-buttons">
				<a href="#" class="default-black-btn-style layer-close cancelLayer">取消</a>
				<a href="#" class="default-pink-btn-style submitLayer">发布</a>
			</div>
		</div>
	</div>
	
	<div id="info-layer" class="editOptionalInfoDiv" style="display:none;">
		<div class="layer-container">
			<div class="layer-settings">
				<div class="layer-setting-line" style="border: 0px;width: auto;">
					<div class="layer-setting-title">商户标签</div>
					<div class="layer-setting-input shops-tag-div" style="height: auto;width: 350px;">
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag">女装<img src="../images/icon_delete.png"></div>
						<div class="shop-tag add-tag"><span>+</span><input type="text" /></div>
					</div>
				</div>
				<div class="for-clear"></div>
				
				<div class="layer-setting-line">
					<div class="layer-setting-title">营业时间</div>
					<div class="layer-setting-input"><input type="text" name="openTime" class="layer-setting-input-width shops_openTime" /></div>
				</div>
			</div>
			<div class="layer-buttons">
				<a href="#" class="default-black-btn-style layer-close cancelLayer">取消</a>
				<a href="#" class="default-pink-btn-style submitLayer">发布</a>
			</div>
		</div>
	</div>
	
	<div id="description-layer" class="editDescDiv" style="display:none;">
		<div class="layer-container">
			<div id="description-textarea-div">
				<textarea id="description-textarea" class="shops_description"></textarea>
				<div class="textarea-info">不能超过160个字，还可以输入<span class="text-count">160</span>个字</div>
			</div>
		</div>
		<div class="layer-buttons">
			<a href="#" class="default-black-btn-style layer-close cancelLayer">取消</a>
			<a href="#" class="default-pink-btn-style submitLayer">发布</a>
		</div>
	</div>
	
	
	
	<div id="upload-img-layer" style="display:none;">
		<div class="layer-container">
			<div class="layer-settings">
			
				<div>
					<div class="layer-setting-line">
						<div class="layer-setting-title">商家图片</div>
						<div class="layer-setting-input" style="line-height: 0px;">
							<input type="file" name="myfiles" id="shopsPic1" class="layer-setting-input-width" />
						</div>
					</div>
					<div class="img-upload-info">
					共3张，还能上传<span>1</span>张
					</div>
				</div>
				<div class="layer-setting-check" style="width: 525px; clear: both;">
					<div class="layer-setting-check-item">
						素材形式：图片素材
						<!-- <img src="../images/icon_not_pass.png" /> -->
					</div>
					<div class="layer-setting-check-item">
						素材格式：JPG、PNG
						<!-- <img src="../images/icon_not_pass.png" /> -->
					</div>
					<div class="for-clear"></div>
					<div class="layer-setting-check-item">
						素材宽高：640 * 480 px
						<!-- <img src="../images/icon_pass.png" /> -->
					</div>
					<div class="layer-setting-check-item">
						大小限制：不限
						<!-- <img src="../images/icon_pass.png" /> -->
					</div>
					<div class="for-clear"></div>
				</div>
				
				<div class="for-clear"></div>
				
				<div class="upload-imgs" style="height: 400px;">
					<div class="upload-img-show">
						<img class="upload-img-close-btn" src="../images/icon_close.png" style="display:none;"/>
						<img alt="预览" class="show_pic" src="../images/addition.png" />
						<input type="hidden" value="" name="shopsId" class="shopsId" />
						<span class="shops_pic" style="display:none;"></span>
					</div>
					<div class="upload-img-show">
						<img class="upload-img-close-btn" src="../images/icon_close.png" style="display:none;"/>
						<img alt="预览" class="show_pic" src="../images/addition.png" />
						<input type="hidden" value="" name="shopsId" class="shopsId" />
						<span class="shops_pic" style="display:none;"></span>
					</div>
					<div class="upload-img-show">
						<img class="upload-img-close-btn" src="../images/icon_close.png" style="display:none;"/>
						<img alt="预览" class="show_pic" src="../images/addition.png" />
						<input type="hidden" value="" name="shopsId" class="shopsId" />
						<span class="shops_pic" style="display:none;"></span>
					</div>
					<div class="upload-img-show">
						<img class="upload-img-close-btn" src="../images/icon_close.png" style="display:none;"/>
						<img alt="预览" class="show_pic" src="../images/addition.png" />
						<input type="hidden" value="" name="shopsId" class="shopsId" />
						<span class="shops_pic" style="display:none;"></span>
					</div>
				</div>
				
			</div>
			<div class="for-clear"></div>
			<div class="layer-buttons">
				<a href="#" class="default-black-btn-style layer-close cancelLayer">取消</a>
				<a href="#" class="default-pink-btn-style submitLayer">保存</a>
			</div>
		</div>
	</div>
	
		<div id="brand-layer" class="selectBrandsDiv"    style="display:none;" >
	
		<div  class="brand-top">
			<a href="#" class="default-selecter" onclick="_brands.popLayer(2);">提交新品牌</a>
			<a href="#" class="default-selecter default-selecter-selected">选择品牌</a>
		</div>

		<div class="layer-container">
			<div class="layer-settings">
			
				
				<div id="brand-search-box">
					<div>
						<input type="text" placeholder="检索品牌" id="searchBrandsNameid"  onkeydown="keypressSearch();">
					</div>
					<div>
						<a href="#" onclick="searchBrands();">
							<img src="../images/icon_search.png">
						</a>
					</div>
				</div>

				<div class="brand-class">
					
				</div>
				<div class="for-clear" style="height: 10px;"></div>
				<!--<hr/>  设计图上没有根据二级品牌选择的功能
				 <div class="subbrand-class">
					
				</div> -->
				
				<div id="brand-result" class="brand-result">
				</div>
			</div>	
			<div class="layer-buttons">
				<a href="#" class="default-black-btn-style layer-close cancelLayer">取消</a>
			</div>
		
		</div>
	</div>
	
	<div id="create-brand-layer" class="create-brand-layer"  style="display:none;">
	
		<div class="brand-top">
			<a href="#" class="default-selecter default-selecter-selected">提交新品牌</a>
			<a href="#" class="default-selecter" id="returnlayer1">选择品牌</a>
		</div>
		
		<div class="layer-container">
			<div class="layer-settings">
				<div class="layer-setting-line">
					<div class="layer-setting-title">新品牌名称</div>
					<div id="logonames" class="layer-setting-input"><input type="text" id="newbrandname" class="layer-setting-input-width" />
					</div>
					
				</div>
				
				
				<div class="layer-setting-line-file">
					<div class="layer-setting-line">
						<div class="layer-setting-title">新品牌logo</div>
						<div class="layer-setting-input" id="fileinput" style="line-height: 0px;">
							<input type="file" id="myfiles" name="myfiles" class="layer-setting-input-width" />
							<span id="filename" class="brand_pic" style="display:none;"></span><div id="fileinfo" class='info'></div>
						</div>
					</div>
					<a href="#" class="default-black-btn-style" onclick="_brands.uploadBrandsPic('brandslogo')">上传</a>
				</div>

				<div class="layer-setting-check">
				
					<div style="float: left;">
						<div>素材形式：图片素材</div>
						<div>素材格式：JPG、PNG</div>
						<div>素材宽高：500 * 500 px</div>
						<div>大小限制：150kb</div>
					</div>
					<img alt="预览" class="show_pic" id="newBrandsShowPic" src="" width="82px" height="82px"/>
					<div class="for-clear"></div>
				</div>
				
				<div style="margin-left: 20px;font-size: 16px;">品牌分类</div>
				
				
				<div id="create-brand-class" class="layer-setting-check">
					<div class="brand-class" >
						<ul id="parentbranddiv">
						
						</ul>
					</div>
					<div class="for-clear"></div>
					<hr />
					<div id="subbranddiv" class ="create-brand-sub-class" >
						
					</div>
					<div class="for-clear"></div>
					<hr />
					<div>
						已选择：
						<div class="brand-class">
							<ul  id="showarea">
								
							</ul>
						</div>
					</div>
					<div class="for-clear"></div>
				</div>
			</div>
			<div class="layer-buttons">
				<a href="#" class="default-black-btn-style layer-close" id="cancelLayer">取消</a>
				
				<a href="#" class="default-pink-btn-style"  id="submitLayer">保存</a>
			</div>
		</div>
	</div>
	
	<!-- footer -->
    <%@include file="../footer/footer.jsp" %>
	
<script type="text/javascript">
$(document).ready(function(){
	_shops.loadView('${shops.id}');
	$('.operator_').click(function(){
// 		alert($(this).prop("checked"));
		if($(this).val() == 'onStatus') {
			if($(this).prop("checked")){
				openOrCloseShop('${shops.id}', '1');
			}else {
				openOrCloseShop('${shops.id}', '0');
			}
		}else if($(this).val() == 'recommend') {
			if($(this).prop("checked")){
				recomShop('${shops.id}', '1');
			}else {
				recomShop('${shops.id}', '0');
			}
		}
	});
});

</script>
</body>
</html>