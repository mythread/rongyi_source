
/**
 * 2014.4.8 lim
 */
var x;
var _shops={
	popLayer:function(which) {//弹层
		var shopsId = $('.shopsId').val();
		if(which == 1) {
			//页面层例��
			var i = $.layer({
				type: 1,
				title: '编辑logo及名称',
		        area: ['auto', 'auto'],
				zIndex : 1,
				page: {
					html: "<div class='editBaseInfo'>"+$('.editBaseInfoDiv').html()+"</div>"
				},
				success: function(){
					var url2_ = "../shops/loadInfo";
					var paramsJson2_ = {"shopsId":shopsId};
					_util.ajaxSubmit(url2_,paramsJson2_,function(data) {
						
						//初始化数
						$('.editBaseInfo').find('.shops_name').val(data.shops.name);
						$('.editBaseInfo').find('.shops_logo').val(data.shops.brandId);
						$('.editBaseInfo').find('.shops_logo_name').val(data.shops.brands.name);
						$('.editBaseInfo').find('.shops_tags').val(data.shops.tags);
						$('.editBaseInfo').find('.shops_id').val(data.shops.id);
						//
						$('.editBaseInfo').find('.submitLayer').click(function(){
							var shopsId = $('.shopsId').val();
							var shopsName = $('.editBaseInfo').find('.shops_name').val();
							var shopsLogo = $('.editBaseInfo').find('.shops_logo').val();
							var shopsTags = $('.editBaseInfo').find('.shops_tags').val();
//						alert(shopsId+' '+shopsName+' '+shopsLogo+' '+shopsTags);
							var url_ = "../shops/saveBase";
							var paramsJson_ = {"shopsId":shopsId};
							paramsJson_['shopsName'] = encodeURIComponent(shopsName);
							paramsJson_['shopsLogo'] = encodeURIComponent(shopsLogo);
							paramsJson_['shopsTags'] = encodeURIComponent(shopsTags);
							_util.ajaxSubmit(url_,paramsJson_,function() {
								layer.close(i);
								location.reload();
							});
						});
						$('.editBaseInfo').find('.cancelLayer').click(function(){
							layer.close(i);
						});
					});
					
			    }
			});

		}else if(which == 2) {
			//页面层例
			var i22 = $.layer({
				type: 1,
				title: '编辑商家信息',
				area: ['auto', 'auto'],
				page: {
					html: "<div class='editOptionalInfo'>"+$('.editOptionalInfoDiv').html()+"</div>"
				},
				success: function(){
					var url2_ = "../shops/loadInfo";
					var paramsJson2_ = {"shopsId":shopsId};
					_util.ajaxSubmit(url2_,paramsJson2_,function(data) {
						//初始化数��
						$('.editOptionalInfo').find('.shops_openTime').val(data.shops.openTime);
//						$('.editOptionalInfo').find('.shops_telephone').val(data.shops.telephone);
//						$('.editOptionalInfo').find('.shops_description').val(data.shops.description);
						var tags = data.shops.tags;
						var tagArr = tags.split(' ');
						
						var tagHTML = "";
						for(var i=0; i<tagArr.length;i++) {
							tagHTML += "<div class='shop-tag shop-tag-text' tag='"+tagArr[i]+"'>"+tagArr[i]+"<img src='../images/icon_delete.png' alt='删除'></div>";
						}
						tagHTML += "<div class='shop-tag add-tag'><span>+</span><input type='text' /></div>";
						$('.editOptionalInfo').find('.shops-tag-div').html(tagHTML);
//						if(data.shops.recommend == 1) {
//							$('.editOptionalInfo').find("input[name='recommend'][value=1]").attr("checked",true);  
//						}else {
//							$('.editOptionalInfo').find("input[name='recommend'][value=0]").attr("checked",true);
//						}
						//
						$('.editOptionalInfo').find('.submitLayer').click(function(){
							var shopsId = $('.shopsId').val();
							var openTime = $('.editOptionalInfo').find('.shops_openTime').val();
							var shopsTags = '';//$('.shops_tags').val();//标签
							$('.editOptionalInfo').find('.shop-tag-text').each(function(){
								shopsTags += $(this).attr('tag') + ' ';
							});
//							var shops_tags = $('.editOptionalInfo').find('.shops_tags').val();
							if($.trim(openTime) == '') {
								layer.msg('营业时间不可为空!', 1, 1);
								return false;
							}
							if($.trim(shopsTags) == '') {
								layer.msg('商户标签不可为空!', 1, 1);
								return false;
							}
//							var telephone = $('.editOptionalInfo').find('.shops_telephone').val();
//							var description = $('.editOptionalInfo').find('.shops_description').val();
							
//							var recommend = $('.editOptionalInfo').find('input[name="recommend"]:checked').val();

							var url_ = "../shops/saveOptional";
							var paramsJson_ = {"shopsId":shopsId};
							paramsJson_['openTime'] = encodeURIComponent(openTime);
							paramsJson_['shopsTags'] = encodeURIComponent(shopsTags);
//							paramsJson_['telephone'] = encodeURIComponent(telephone);
//							paramsJson_['description'] = encodeURIComponent(description);
//							paramsJson_['recommend'] = encodeURIComponent(recommend);
							_util.ajaxSubmit(url_,paramsJson_,function() {
								layer.close(i22);
								location.reload();
							});
						});
						$('.editOptionalInfo').find('.cancelLayer').off().on({
							'click':function(ev){
								layer.close(i22);
							}
						});
					});
					
			    }
			});
		}else if(which == 4) {
			//页面层例
			var i = $.layer({
				type: 1,
				title: '编辑商家简介 ',
				area: ['auto', 'auto'],
				page: {
					html: "<div class='editDesc'>"+$('.editDescDiv').html()+"</div>"
				},
				success: function(){
					var url2_ = "../shops/loadInfo";
					var paramsJson2_ = {"shopsId":shopsId};
					_util.ajaxSubmit(url2_,paramsJson2_,function(data) {
						//初始化数��
						$('.editDesc').find('.shops_description').val(data.shops.description);
						$('.editDesc').find('.submitLayer').click(function(){
							var shopsId = $('.shopsId').val();
							var description = $('.editDesc').find('.shops_description').val();
							var url_ = "../shops/saveDesc";
							var paramsJson_ = {"shopsId":shopsId};
							paramsJson_['description'] = encodeURIComponent(description);
							_util.ajaxSubmit(url_,paramsJson_,function() {
								layer.close(i);
								location.reload();
							});
						});
						$('.editDesc').find('.cancelLayer').click(function(){
							layer.close(i);
						});
					});
					
			    }
			});
		}else if(which == 3) {
			var i = $.layer({
				type: 1,
				title: '编辑图片',
				closeBtn: false,
				border : [5, 0.5, '#666', true],
				offset: ['100px',''],
//				move: ['.juanmove', true],
				move : ['.xubox_title' , true],
				area: ['auto', 'auto'],
				page: {
					html: "<div class='editPic'>"+_shops.popUploadPicDiv()+"</div>"
				},
				success: function(){
					_editV.initPic();
					$('.editPic').find('.submitLayer').click(function(){
						var shopsId = $('.shopsId').val();
						var paramsJson_ = {"shopsId":shopsId};
						$('.editPic').find('.shops_pic').each(function(index){
							paramsJson_['shopsPic_'+(index+1)] = $(this).html();
							paramsJson_['shopsId_'+(index+1)] = $(this).parents('.upload-img-show').find('.shopsId').val();
							//$('#'+fieldId_).parents('tr').find('.shops_pic').attr('isUpdate','updated');  
							paramsJson_['isUpdate_'+(index+1)] = $(this).attr('isUpdate');
						});
						var url_ = "../shops/savePic";
						_util.ajaxSubmit(url_,paramsJson_,function() {
							layer.close(i);
							location.reload();
						});
					});
					$('.editPic').find('.cancelLayer').click(function(){
						$("#upload-img-layer").html($("#img-layer").html());
						layer.close(i);
					});
					
			    }
			});
		}
	},
	popUploadPicDiv:function() {
		var html_ = "";
		html_ +="<div id=\"img-layer\" class=\"editPic_\">";
		html_ +=$("#upload-img-layer").html();
		html_ +="</div>";
		$("#upload-img-layer").html('');
		return html_;
	},
	uploadShopsPic:function(fieldId) {
		var fieldId_ = fieldId;
		var picPath = '/shops/original/';//上传的目��
		var maxSize = '10485760';//1024*1024=1M
		var picType = '.jpg.png';
		var paramsJson = {};
		paramsJson['picPath'] = picPath;
		paramsJson['maxSize'] = maxSize;
		paramsJson['picType'] = picType;
		paramsJson['width'] = '640';
		paramsJson['height'] = '480';
		_util.ajaxFileUploadPublic(fieldId, paramsJson, function(data) {
			$('#'+fieldId_).parents('.layer-setting-input').find('.shops_pic').html(data.fileName);  
			$('#'+fieldId_).parents('.layer-setting-input').find('.shops_pic').attr('isUpdate','updated');
			$('#'+fieldId_).parents('.layer-setting-line').next('.layer-setting-check').find('.show_pic').attr('src',_util.SHOW_IMG_PATH+picPath+data.fileName);
//			$('#'+fieldId_).parents('.layer-setting-input').find('.upbtn').val('ok');
			
//			$('#'+fieldId_).parents('tr').find('.shops_pic').html(data.fileName);  
//			$('#'+fieldId_).parents('tr').find('.info').html(data.msg);
//			$('#'+fieldId_).parents('tr').find('.shops_pic').attr('isUpdate','updated');  
		});
	},
	loadView:function(shopsId) {
		var url2_ = "../shops/loadInfo";
		var paramsJson2_ = {"shopsId":shopsId};
		_util.ajaxSubmit(url2_,paramsJson2_,function(data) {
			//初始化数��
			$('.shops_name').val(data.shops.name);
			$('.shops_logo').val(data.shops.brandId);
			$('.shops_tags').val(data.shops.tags);
			$('.shops_openTime').val(data.shops.openTime);
			$('.shops_telephone').val(data.shops.telephone);
			$('.shops_description').val(data.shops.description);
			if(data.shops.recommend == 1) {
				$("input[name='recommend'][value=1]").attr("checked",true);  
			}else {
				$("input[name='recommend'][value=0]").attr("checked",true);
			}
			$(".detail-small-pic1").attr("src",data.pic_1);
			$(".detail-small-pic2").attr("src",data.pic_2);
			$(".detail-small-pic3").attr("src",data.pic_3);
			$(".detail-small-pic4").attr("src",data.pic_4);
		});
	}

};
var x;
var y;
var divtype=1;
var pageOFFstatus=1;
var _brands={
		popLayer:function(which) {//弹层
			 if(which == 1) {
				x = $.layer({
					type: 1,
					title: '选择品牌',
					closeBtn: false,
					border : [5, 0.5, '#666', true],
					move: ['.xubox_title', true],
					area: ['590px','600px'],
					zIndex : 1,
					page: {
						html: "<div class='selectBrands'>"+$('.selectBrandsDiv').html()+"</div>"
					},
					success: function(){
						divtype =1;
						$.ajax({
							url : "../brands/categorieslist",
							dataType: 'json',
							data : {
								categoryId:"",
								types:"1"
							},
							success: function(data){		
								var htmlstr = _brands.popCaregoryDiv(data.cateList,data.type);
								$('.selectBrands').find('.brand-class').html(htmlstr);
								return;
							}
						});
						$('.selectBrands').find('.cancelLayer').click(function(){
							layer.close(x);
						});
						
						
				    }
				});
			}else if(which == 2) {
				var y = $.layer({
					type: 1,
					title: '提交新品牌 ',
					closeBtn: false,
					border : [5, 0.5, '#666', true],
					move: ['.xubox_title', true],
					area: ['590px','600px'],
					page: {
						html: "<div class='newBrands'>"+$('.create-brand-layer').html()+"</div>"
					},
					success: function(){
						divtype =2;
						 layer.close(x);	
						 $.ajax({
								url : "../brands/categorieslist",
								data : {
									categoryId:"",
									types:"2"
								},
								success: function(data){		
									var htmlstr = _brands.popNBPCaregoryDiv(data.cateList,data.type);
									$('.newBrands').find("#create-brand-class").find("#parentbranddiv").html(htmlstr);
									return;
								}
							});
						$('.newBrands').find(".layer-setting-input").find("input:file[name='myfiles']").attr("id","brandslogo");
						
						$('.newBrands').find('#submitLayer').click(function(){
							
							
							var filename = $('.newBrands').find(".layer-setting-line").find("#fileinput").find('#filename').html();
							var logoname = $('.newBrands').find(".layer-setting-line").find("#logonames").find('#newbrandname').val();
							var categoryidstr ="";
			
							$('.newBrands').find(".layer-setting-check").find("#showarea").find(".selected").each(function(i){
								var ids= $(this).attr("id");
								if(i==0){
									categoryidstr +=ids;
								}else{
									categoryidstr +="#"+ids;
								}
							});
							

							var url = "../brands/createnewbrands";
							var params = {
									"filename":filename,
									"logoname":logoname,
									"categoryidstr":categoryidstr
									};
							//alert(categoryidstr);
							if(categoryidstr==""){
								layer.msg('请选择分类!', 1, 1);
								return false;
							}else if($.trim(logoname) == '') {
								layer.msg('品牌名不可为空!', 1, 1);
								return false;
							}else{
								$.ajax({
									url: url,
									type: 'POST',
									data:params,
									success: function(data){										
											$('.editBaseInfo').find('.shops_logo_name').val(logoname);
											$('.editBaseInfo').find('.shops_logo').val(data.newBrandsId);
											$('.editBaseInfo').find('.shops_logo_name').val(logoname);
											$('.editBaseInfo').find('.shops_logo').val(data.newBrandsId);
											 layer.close(y);									
											return;
									}
								});
	
							}
							
						});	
							
						

							$('.newBrands').find('#cancelLayer').click(function(){
								
								layer.close(y);
							});
							$('.newBrands').find('#returnlayer1').click(function(){
								layer.close(y);
								_brands.popLayer(1);
								
							});
							

				    }
				});
			}
		},
		uploadBrandsPic:function(fieldId) {
			var fieldObject =fieldId;
			var picPath = '/brands/original/';//上传的目��
			var maxSize = '153600';//150kb
			var picType = '.jpg.png';
			//alert("1asqw1212");
			var paramsJson = {};
			paramsJson['picPath'] = picPath;
			paramsJson['maxSize'] = maxSize;
			paramsJson['picType'] = picType;
			paramsJson['width'] = '500';
			paramsJson['height'] = '500';
			_util.ajaxFileUploadPublic(fieldObject, paramsJson, function(data) {
				//alert(data);
				//alert(data.fileName+"123123");
				
				$('.newBrands').find('#filename').html(data.fileName);  
				$('.newBrands').find('#fileinfo').html(data.msg);
				$('.newBrands').find('#newBrandsShowPic').attr('src',_util.SHOW_IMG_PATH+picPath+data.fileName);
				$('.newBrands').find('#filename').attr('isUpdate','updated');  
			});
		},
		popCaregoryDiv:function(jsonlist,categroyType) {
			var html_ = "";
			var loadTypeStr = "";
			
			html_ +="<ul>";
			html_ +="<li><a href=\"#\" id=\"all\" onclick='_brands.loadCategroyList(\'\');' class=\"selected\">全部</a></li>";
			 for(var o in jsonlist){ 
				 if(categroyType==1){
						loadTypeStr = "_brands.loadCategroyList('"+jsonlist[o].id+"'";
					}else if (categroyType==2){
						loadTypeStr = "_brands.loadBrandsList('"+jsonlist[o].id+"',2";
					}
				 html_ +="<li><a href='#' id='"+jsonlist[o].id+"'  onclick=\""+loadTypeStr+");\">";
				 html_ += jsonlist[o].name;
				 html_ +="</a></li>";
			    }  
			html_ +="</ul>";
			return html_;
		},
		popBrandsDiv:function(jsonlist) {
			var html_ = "";
			 for(var o in jsonlist){  
			html_ +="<div class='brand-div' id='"+jsonlist[o].id+"'><div class='brand-img'>";
			html_ += "<img src='"+jsonlist[o].iconUrl+"' onclick='_brands.selectBrandsid("+jsonlist[o].id+")';>";
			html_ += "</div><div  class='brand-title'>";
			html_ += jsonlist[o].name;	
			html_ +="</div></div>";
			} 
			return html_;
		},
		popNBPCaregoryDiv:function(jsonlist) {
			var html_ = "";	
			html_ +="<li><a href=\"#\" id=\"all\" onclick='_brands.loadNBSCategroyList(\'\');' class=\"selected\">全部</a></li>";
			 for(var o in jsonlist){ 
				loadTypeStr = "_brands.loadNBSCategroyList('"+jsonlist[o].id+"'";	
				 html_ +="<li><a href='#' id='"+jsonlist[o].id+"'  onclick=\"_brands.loadNBSCategroyList('"+jsonlist[o].id+"');\">";
				 html_ += jsonlist[o].name;
				 html_ +="</a></li>";
			    }  
			return html_;
		},		
		popNBSCaregoryDiv:function(jsonlist,pcate) {
			var html_ = "";
			html_ +="<div><label><input type='checkbox' id='"+pcate.id+"'   onclick=\"_brands.selectNewBrandsCategroyid('"+pcate.id+"','"+pcate.name+"');\"   class='pcate' />"+pcate.name+"</label></div>";
			 for(var o in jsonlist){ 
				 html_ +="<div><label><input type='checkbox' id='"+jsonlist[o].id+"'  onclick=\"_brands.selectNewBrandsCategroyid('"+jsonlist[o].id+"','"+jsonlist[o].name+"');\"  class='scate' />"+jsonlist[o].name+"</label></div>";
			    }
			return html_;
		},
		loadCategroyList:function(id) {
			$('.selectBrands').find('brand-class').find(".selected").removeClass("selected");
			$('.selectBrands').find('brand-class').find('#'+id).addClass("selected");
				$.ajax({
					type : "post",
					url : "../brands/categorieslist",
					dataType: 'json',
					data : {
						categoryId:id
					}
				}).done(function(data) {
						$('.selectBrands').find('.subbrand-class').html(_brands.popCaregoryDiv(data.cateList,data.type));
						_brands.loadBrandsList(id,1);
				});
		
		},
		loadNBSCategroyList:function(id) {
			$.ajax({
				type : "post",
				url : "../brands/categorieslist",
				dataType: 'json',
				data : {
					categoryId:id
				}
			}).done(function(data) {
				$('.newBrands').find(".layer-setting-check").find("#subbranddiv").html(_brands.popNBSCaregoryDiv(data.cateList,data.pcast));
				$('.newBrands').find(".layer-setting-check").find('#parentbranddiv').find(".selected").removeClass("selected");
				$('.newBrands').find(".layer-setting-check").find('#parentbranddiv').find('#'+id).addClass("selected");
				
				$('.newBrands').find(".layer-setting-check").find("#showarea").find(".selected").each(function(i){
					var ids= $(this).attr("id");
					$('.newBrands').find(".layer-setting-check").find("#subbranddiv").find("#"+ids).attr("checked","checked");
				});
				
				
			});
		},
		loadBrandsList:function(categoryId,categoryType) {
			if(categoryType == 2){
				$('.selectBrands').find('.subbrand-class').find(".selected").removeClass("selected");
				$('.selectBrands').find('.subbrand-class').find('#'+categoryId).addClass("selected");
			}else{
				$('.selectBrands').find('.brand-class').find(".selected").removeClass("selected");
				$('.selectBrands').find('.brand-class').find('#'+categoryId).addClass("selected");
			}
			$('.selectBrands').find('.brand-result').html('');
				var nScrollHight =0;
				var nScrollTop =0;
				var paging=1;
				var nDivHight = $('.selectBrands').find("#brand-result").height();
				pageOFFstatus = _brands.loadBrandsListPaging(categoryId,categoryType,paging);
				$('.selectBrands').find("#brand-result").scroll(function () {
					 nScrollHight = $(this)[0].scrollHeight;
			          nScrollTop = $(this)[0].scrollTop;
					
			          
			          if(nScrollTop + nDivHight >= nScrollHight){
			        	 paging++;
			        	_brands.loadBrandsListPaging(categoryId,categoryType,paging);
			             if(pageOFFstatus==0){
				        	$('.selectBrands').find("#brand-result").unbind('scroll');
				         }
			        	 
			            }
					  //alert(nScrollHight);
					  //alert(nScrollTop);
					});		
		},
		loadBrandsListPaging:function(categoryId,categoryType,paging){
			$.ajax({
				type : "post",
				url : "../brands/brandslist",
				dataType: 'json',
				data : {
					categoryId:categoryId,
					categoryType:categoryType,
					paging:paging
				}
			}).done(function(data) {
					var htmlstr = _brands.popBrandsDiv(data.brandsList);
					$('.selectBrands').find('.brand-result').append(htmlstr);
					pageOFFstatus = data.status;
			
			});
		},
		selectNewBrandsCategroyid:function(cateId,cateName){
			var checkArea = $('.newBrands').find(".layer-setting-check").find("#subbranddiv");
			var showArea = $('.newBrands').find(".layer-setting-check").find("#showarea");
			if(checkArea.find("#"+cateId).attr("checked")=="checked"){
				if(checkArea.find("#"+cateId).hasClass("scate")){
					if(checkArea.find(".pcate").attr("checked")!="checked"){
						checkArea.find(".pcate").attr("checked","checked");
						var pareid = checkArea.find(".pcate").attr("id");
						var pareName = checkArea.find(".pcate").parent().text();
						showArea.append('<li><a href="#" id="'+pareid+'" class="selected">'+pareName+'</a></li>');
					}
				}
				showArea.append('<li><a href="#" id="'+cateId+'" class="selected">'+cateName+'</a></li>');
			}else{
				showArea.find("#"+cateId).parent().remove();
				if(checkArea.find("#"+cateId).hasClass("pcate")){	
					checkArea.find("input[type='checkbox']:checked").each(function(){
						var subid = $(this).attr("id");
						showArea.find("#"+subid).parent().remove();
					});	
					checkArea.find("input").removeAttr("checked");
					
				}
			}						
		},
		selectBrandsid:function(brandsId){
			var brandsName =$('.selectBrands').find("#"+brandsId+".brand-div").find(".brand-title").text();
			//alert($('.editBaseInfo').html());
			$('.editBaseInfo').find('.shops_logo_name').val(brandsName);
			$('.editBaseInfo').find('.shops_logo').val(brandsId);
			layer.close(x);
		}

};



function searchBrands(){
	var searchStr =$('.selectBrands').find('#searchBrandsNameid').val();
	//alert(searchStr);
	if(searchStr!=""){
		$.ajax({
			type : "post",
			url : "../brands/searchbrands",
			dataType: 'json',
			data : {
				searchParam:searchStr
			}
		}).done(function(data) {
			$('.selectBrands').find('.brand-result').html(_brands.popBrandsDiv(data.brandsList));
		});
	}

}

function keypressSearch(){
    if(event.keyCode == "13")    
    {
    	searchBrands();
    }
}