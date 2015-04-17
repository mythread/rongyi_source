<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@include file="../module/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><site:config key="header.mall.name"/>·导购终端机内容管理</title>
<link href="../css/main.css" rel="stylesheet" type="text/css" />

<link href="../css/shop/shopedit.css" rel="stylesheet" type="text/css" />
<link href="../css/shop/shopdetailedit.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/layer/layer.min.js"></script>
<script type="text/javascript" src="../js/shops.js"></script>  
<script type="text/javascript" src="../js/ajaxfileupload.js"></script>
<script type="text/javascript" src="../js/shop/common.js"></script>
<script type="text/javascript" src="../js/shop/shopedit.js"></script>
</head>
<body>
	<%@include file="../top/top_shop.jsp" %>

	<div id="content">
		<div id="show">
		
			<div class="top-title">
				<span><a href="../shops/list">商家信息管理 </a>&nbsp;&gt; &nbsp;详情页</span>
				<a href="../shops/list" class="default-gray-btn-style" id="back-btn">返回</a>
			</div>
			
			<hr />
			
			<div id="view-swith">
				
				<span>
					<a href="../shops/edit?shopsId=${shops.id}" class="default-selecter">可视化视图</a>
					<a href="../shops/editV?shopsId=${shops.id}" class="default-selecter default-selecter-selected">编辑视图</a>
				</span>

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
				
			</div>
			

				<div id="details" class="editBaseInfo">
					<input type="hidden" name="shopsId" class="shopsId" value="${shops.id}"/>
					<div class="edit-box">
						<div class="edit-box-title-div">
							<div class="edit-box-title">商家基本信息</div>
							<div class="edit-box-save"><a href="#" class="default-gray-btn-style" onclick="_editV.saveBase();">保存</a></div>
						</div>
						<hr />
						<div class="edit-box-body">
							<div class="layer-container">
								<div class="layer-settings">
								
									<div class="layer-setting-line">
										<div class="layer-setting-title">商铺号</div>
										<div class="layer-setting-input"><input type="text" value="${shops.shopNumber}" class="layer-setting-input-width" disabled="disabled"/></div>
									</div>
									
									<div class="layer-setting-line">
										<div class="layer-setting-title">商户名称</div>
										<div class="layer-setting-input"><input type="text" value="${shops.name}" class="layer-setting-input-width shops_name" /></div>
									</div>
									
									<div class="layer-setting-line-brand">
										<div class="layer-setting-line">
											<div class="layer-setting-title">品牌</div>
											<div class="layer-setting-input">
												<input type="text" class="shops_logo_name layer-setting-input-width" value="${shops.brands.name}"  disabled="disabled"/>
												<input type="hidden" value="${shops.brandId}" class="shops_logo"/>
											</div>
										</div>
										<a href="#"  onclick="_brands.popLayer(1);"  id="get-brand-btn" class="default-black-btn-style">选择品牌</a>
									</div>
									
									<div class="layer-setting-line" style="border: 0px;width: auto;">
										<div class="layer-setting-title">商户标签</div>
										<div class="layer-setting-input" style="height: auto;width: 350px;">
											<c:forEach items="${tagList}" var="tag">
												<div class="shop-tag shop-tag-text" tag="${tag}">${tag}<img src="../images/icon_delete.png" alt="删除"></div>
											</c:forEach>
											<div class="shop-tag add-tag"><span>+</span><input type="text" /></div>
										</div>
									</div>
									<div class="for-clear"></div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="edit-box">
						<div class="edit-box-title-div">
							<div class="edit-box-title">商家辅助信息</div>
							<div class="edit-box-save"><a href="#" class="default-gray-btn-style" onclick="_editV.saveOptional();">保存</a></div>
						</div>
						<hr />
						<div class="edit-box-body">
							<div class="layer-container">
								<div class="layer-settings">
								
									<div class="layer-setting-line">
										<div class="layer-setting-title">营业时间</div>
										<div class="layer-setting-input"><input type="text" value="${shops.openTime}" class="layer-setting-input-width shops_openTime" /></div>
									</div>
									
									<div class="layer-setting-line">
										<div class="layer-setting-title">商家电话</div>
										<div class="layer-setting-input"><input type="text" value="${shops.telephone}" class="layer-setting-input-width shops_telephone" /></div>
									</div>
									
									<div class="layer-setting-line">
										<div class="layer-setting-title">特别推荐</div>
										<div class="layer-setting-input">
											<label><input type="radio" name="recommend" class="shops_recommend" value="1" ${shops.recommend == 1 ? 'checked' : ''}/>推荐</label>
											<label><input type="radio" name="recommend" class="shops_recommend" value="0" ${shops.recommend == 1 ? '' : 'checked'}/>不推荐</label>
										</div>
									</div>
									
									<div class="layer-setting-line shops_description_line">
										<div class="layer-setting-title">商家简介</div>
										<textarea class="shops_description">${shops.description}</textarea>
									</div>
									<div class="textarea-info">不能超过160个字，还可以输入<span class="text-count">160</span>个字</div>
									
								</div>
							</div>
						</div>
					</div>
					
					<div class="edit-box editPic">
						<div class="edit-box-title-div">
							<div class="edit-box-title">商家图片</div>
							<div class="edit-box-save"><a href="#" class="default-gray-btn-style" onclick="_editV.savePic();">保存</a></div>
						</div>
						<hr />
						<div class="edit-box-body">
							<div class="layer-container">
								<div class="layer-settings">
								
									<div style="float: left;">
										<div class="layer-setting-line">
											<div class="layer-setting-title">商家图片</div>
											<div class="layer-setting-input"  style="line-height: 0px;">
												<input type="file" name="myfiles" id="shopsPic1" class="layer-setting-input-width" />
											</div>
										</div>

										<div class="img-upload-info">
										共X张，还能上传<span>Y</span>张
										</div>
									</div>
									<div class="layer-setting-check" style="float: left;width: 450px;margin-left: 50px;">
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
									
									<div class="upload-imgs">
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
							</div>
						</div>
					</div>
					
				</div>
			
			<div style="height: 40px;text-align: center;">
				<a href="#" class="default-pink-btn-style" id="preview-btn" style="padding-left: 40px;padding-right: 40px;" >预&nbsp;&nbsp;&nbsp;&nbsp;览</a>
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
						<input type="text" placeholder="检索品牌" id="searchBrandsNameid" onkeydown="keypressSearch();">
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
	//绑定beforeunload事件
	/* $(window).bind('beforeunload',function(){
// 		var t = _editV.pageHasChanged();
		return "您输入的内容尚未保存，确定离开此页面吗？";
	}); */
});

</script>	
</body>
</html>