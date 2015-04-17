<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
   <div class="info-block">
   		<s:if test="type==1">
   		<div class="block-body">
    		<div class="block-body-block">
    			<div class="block-body-title">当前内容</div>
    			<div class="block-body-body">
    				<div class="body-line">
    					<div class="body-line-title">广告位</div>
    					<div class="body-line-content">${mongoAdZonename}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">广告名称</div>
    					<div class="body-line-content">${mongoad.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">投放时间</div>
    					<div class="body-line-content">
    					<fmt:formatDate value="${mongoad.start_time}" type="date" dateStyle="medium"/>--
    					<fmt:formatDate value="${mongoad.end_time}" type="date" dateStyle="medium"/>
    					</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">广告素材</div>
    					<div class="body-line-content" style="height: 200px;">
    					<img alt="" src="${mongoad.picture}" height="200px" width="190px">
						</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">下方缩略图</div>
    					<div class="body-line-content" style="height: 200px;">
    					<img alt="" src="${mongoad.minPicture}" height="200px" width="190px">
						</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">链接地址</div>
    					<div class="body-line-content">
    					<c:if test="${mongoad.url==''}">无</c:if>
    					<c:if test="${mongoad.url!=''}">${mongoad.url}</c:if>
    					</div>
    					<div class="for-clear"></div>
    				</div>
    			</div>
    		</div>
    		<div class="block-body-block">
    			<div class="block-body-title">修改新内容</div>
    			<div class="block-body-body">
    				<div class="body-line">
    					<div class="body-line-title">广告位</div>
    					<div class="body-line-content">${adZonename}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">广告名称</div>
    					<div class="body-line-content">${adverPojo.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">投放时间</div>
    					<div class="body-line-content">
    					<fmt:formatDate value="${adverPojo.startTime}" type="date" dateStyle="medium"/>--
    					<fmt:formatDate value="${adverPojo.endTime}" type="date" dateStyle="medium"/>
    					</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">广告素材</div>
    					<div class="body-line-content" style="height: 200px;">
    					<img alt="" src="${adverPojo.picture}" height="200px" width="190px">
    					</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">下方缩略图</div>
    					<div class="body-line-content" style="height: 200px;">
    					<img alt="" src="${adverPojo.minPicture}" height="200px" width="190px">
    					</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">链接地址</div>
    					<c:if test="${adverPojo.shopUrl==''}">无</c:if>
    					<c:if test="${adverPojo.shopUrl!=''}">${adverPojo.shopUrl}</c:if>
    					<div class="for-clear"></div>
    				</div>
    			</div>
    		</div>
    		<div class="for-clear"></div>
    		<div class="btns-div">
    		<c:if test="${reviewStatus==1}">
	    		<input id="adverClick" type='button' value='审核通过' onclick="javascript:adverClick('${taskid}','${mallId}')"/>
	    		<input id="adverback" type='button' value='驳回'/>
	    	</c:if>
    		</div>
    		 
    	</div>
    	
    	</s:if>
   		
   		<s:if test="type==0">
   		<c:if test="${brandsPojo!=null}">
    			<div class="block-body-block-full">
    			<div class="block-body-title">新品牌提交</div>
    			<div class="block-body-body">
    				<div class="body-line">
    					<div class="body-line-title">品牌名</div>
    					<div class="body-line-content">${brandsPojo.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">品牌logo</div>
    					<img src="${brandsPojo.icon}" height="200px" width="190px"/>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">品牌分类</div>
    					<div class="body-line-content">${brandsPojo.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div style="margin-right: 20px;text-align: right;">
    					<label><input id="brandsCheck" type="checkbox" />确认以及生成新品牌</label>
    				</div>
    			</div>
    		</div>
    		</c:if>
    	<div class="block-body">
    		<div class="block-body-block">
    			<div class="block-body-title">当前内容</div>
    			<div class="block-body-body">
    				<div class="body-line">
    					<div class="body-line-title">商铺号</div>
    					<div class="body-line-content">${mongoShop.shop_number}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">商户名称</div>
    					<div class="body-line-content">${mongoShop.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">品牌&logo</div>
    					<div class="body-line-content">${mongoBrand.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<!-- <div class="body-line">
    					<div class="body-line-title">所属楼层</div>
    					<div class="body-line-content">3F</div>
    					<div class="for-clear"></div>
    				</div> -->
    				<div class="body-line">
    					<div class="body-line-title">用户标签</div>
    					<div class="body-line-content">${mongoShop.tags}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">营业时间</div>
    					<div class="body-line-content">${mongoShop.open_time}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">商家简介</div>
    					<div class="body-line-content" style="height:350px;white-space:normal;" title="${mongoShop.description}">${mongoShop.description}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title" style="width: 100%">商家图片</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-img">
    					<c:forEach var="photo" items="${mongoImgList}">
	    				<img src="${photo.file}" />
	    				</c:forEach>
    				</div>
    			</div>
    		</div>
    		<div class="block-body-block">
    			<div class="block-body-title">修改新内容</div>
    			<div class="block-body-body">
    				<div class="body-line">
    					<div class="body-line-title">商铺号</div>
    					<div class="body-line-content">${shopsPojo.shopNumber}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">商户名称</div>
    					<div class="body-line-content">${shopsPojo.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">品牌&logo</div>
    					<div class="body-line-content">${mongoBrand.name}</div>
    					<div class="for-clear"></div>
    				</div>
    				<!-- <div class="body-line">
    					<div class="body-line-title">所属楼层</div>
    					<div class="body-line-content">3F</div>
    					<div class="for-clear"></div>
    				</div> -->
    				<div class="body-line">
    					<div class="body-line-title">用户标签</div>
    					<div class="body-line-content">${shopsPojo.tags}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">营业时间</div>
    					<div class="body-line-content">${shopsPojo.openTime}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title">商家简介</div>
    					<div class="body-line-content" style="height:350px;white-space:normal;">${shopsPojo.description}</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<div class="body-line-title" style="width: 100%">商家图片</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-img">
	    				<c:forEach var="photo" items="${imglist}">
	    				<img src="${photo.file}" />
	    				</c:forEach>
    				</div>
    			</div>
    		</div>
    		<div class="for-clear"></div>
    		<div class="btns-div">
    		<c:if test="${reviewStatus==1}">
	    		<input id="shopClick" type='button' value='审核通过' onclick="javascript:shopClick('${taskid}','${mallId}','${shopsPojo.brandId}','${brandsPojo}');"/>
	    		<input id="shopback" type='button' value='驳回' />
	    	</c:if>
    		</div>
    	</div>
    	</s:if>
    </div>
    
     <div class="info-block" id="back" style="display: none;">
    	<div class="block-body" style="display: block;">
    	
    		<div class="block-body-block-full">
    			<div class="block-body-title">驳回理由</div>
    			<div class="block-body-body">
    				<div class="body-line">
    					<div class="body-line-title">驳回理由</div>
    					<div class="body-line-content">
    						<select style="width: 100%;border: 0px;outline: none;">
    							<option></option>
    							<option>不好</option>
    							<option>不好</option>
    							<option>不好</option>
    							<option>不好</option>
    						</select>
						</div>
    					<div class="for-clear"></div>
    				</div>
    				<div class="body-line">
    					<textarea style="width: 670px; height: 100px; border: 0px; outline: none;resize: none;" placeholder="请输入驳回理由…"></textarea>
    				</div>
    			</div>
    		</div>
    	
    		
    		<div class="for-clear"></div>
    		<div class="btns-div">
	    		<button onclick="sureback('${taskid}','${mallId}','${type}')">确定驳回</button>
	    		<button onclick="noback()">取消</button>
    		</div>
    	</div>
    </div>
<script type="text/javascript">

function downloadBrands(icon){
	$.ajax({
        url:"download.action?icon="+icon+"",
        success:function(data) {
        },
        error:function(){
           alert("添加失败");
    }
         
    });
}
 function shopClick(taskid,mallid,brandid,brandsPojo) {
		 var url="shopsSynchPass.action?taskId="+taskid+"&mallId="+mallid+"&brandId="+brandid+"";
		 if(brandsPojo!=''&&$("#brandsCheck").attr('checked')!='checked'){
			alert("请勾选确认新品牌提交!");
			return false; 
		 }
	 $.ajax({
         url:url,
         success:function(data) {
        	 $("#dd").window('close', true);
        	 window.location.reload();
         },
         error:function(){
            alert("添加失败");
     }
          
     });
 }
 
 function adverClick(taskid,mallid) {
	 $.ajax({
         url:"advertSynchPass.action?taskId="+taskid+"&mallId="+mallid+"",
         success:function(data) {
        	 $("#dd").window('close', true);
        	 window.location.reload();
         },
         error:function(){
            alert("添加失败");
     }
          
     });
 }
 
 
 $(document).ready(function() {
	$("#adverback,#shopback").click(function() {
		$("#back").css("display", "block");
	});
	
	$(".body-line-content select").change(function() {
		$("textarea").html($(".body-line-content select option:selected").text());
	});
});
 
 function sureback(taskid,mallid,type){
	 var showInfo=$("textarea").val();
	 var fail_url='';
	 if(showInfo==''){
		 alert("驳回信息不能为空！");
		 return false;
	 }
	 if(showInfo.length>256){
		 alert("驳回信息超出最大字数限制！");
		 return false;
	 }
	 var param = "taskId="+taskid+"&mallId="+mallid+"&showInfo="+encodeURIComponent(showInfo);
	 if(type==1){
		 
		 fail_url="advertSynchFail.action?" + param;
	 }
	 if(type==0){
		 
		 fail_url="shopsSynchFail.action?" + param;
	 }
	 console.debug(fail_url);
	 $.ajax({
         url:fail_url,
         success:function(data) {
        	 $("#dd").window('close', true);
        	 window.location.reload();
         },
         error:function(){
            alert("添加失败");
     }
     });
 }
 
 function noback(){
	 $(".body-line-content select").val('');
	 $("textarea").html('');
	 $("#back").css("display", "none");
 }
 </script>
