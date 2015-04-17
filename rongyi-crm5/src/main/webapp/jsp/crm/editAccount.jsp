<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
  href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />

<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
	
	function ajaxSave(){
			var F = $('#addObjectForm');
			jQuery.post("/jsp/crm/saveAccount.action",F.serialize(),function(res){
				var jsonObj = jQuery.parseJSON(res);
				if(jsonObj.result == 1){
					if(jsonObj.data == "exist"){
						$('#id_continueSave').show();
					}
				}	
					$('#id_tipsMsg').text(jsonObj.msg);
					$('#id_tips_box').show();
					if(jsonObj.result == 0){
						setTimeout(function(){
							window.location.href = "/jsp/crm/editAccount.action";
						},1000);
					}
			});
	}
	
	function closeTips(){
		$('#id_tips_box').hide();
	}
	
	//继续强制添加
	function continueSave(){
		var F = $('#addObjectForm');
		var param = F.serialize() + "&goon=true";
		jQuery.post("/jsp/crm/saveAccount.action",param,function(res){
			var jsonObj = jQuery.parseJSON(res);
				if(jsonObj.result == 1){
					if(jsonObj.data == "exist"){
						$('#id_continueSave').show();
					}
				}else{
					$('#id_continueSave').hide();
				}
				$('#id_tipsMsg').text(jsonObj.msg);
				$('#id_tips_box').show();
				if(jsonObj.result == 0){
					setTimeout(function(){
						window.location.href = "/jsp/crm/editAccount.action";
					},1000);
				}
		});
	}
	
	function beforeSubmit(){
		var advert_level = $('#advert_level').val();
		var mallbd_level = $('#mallbd_level').val();
		var typeID = $('#typeID').val();
		var floors = $('#floors').val();
		var mall_circle = $('#mall_circle').val();
		var address = $('#address').val();
	    	if(advert_level==""){
	    	alert('广告等级不能为空!');
	    	return false;
	    	}
	    	if(mallbd_level==""){
	    	alert('商场BD等级不能为空！');
	    	return false;
	    	}
	    	if(typeID==""){
	    	alert('商场属性不能为空！');
	    	return false;
	    	}
	    	if(floors==""){
	    	alert('楼层不能为空！');
	    	return false;
	    	}
	    	if(mall_circle==""){
	    	alert('商圈不能为空！');
	    	return false;
	    	}
	    	if(address==""){
	    	alert('商场地址不能为空！');
	    	return false;
	    	}
	    	return true;
	    	}
	function saveClose() {
		if(beforeSubmit()){
		baseSaveClose("Account");
		}
	}
	
	function cancel() {
		baseCancel("Account");
	}

	function selectAreaValue(){
		var proval=$("#areaProvince option:selected").text();
		var cityval=$("#areaCity option:selected").text();
		var areaval=$("#areas option:selected").text();
		if($("#areaProvince").changed){
			
		}
		alert(proval);
		alert(cityval);
		alert(areaval);
		
	}
	
	function billMap() {
	    address = $("input[name='account.bill_country']").val() + " "
				+ $("input[name='account.bill_state']").val() + " "
				+ $("input[name='account.bill_city']").val() + " "
				+ $("input[name='account.bill_street']").val();
	    locale = '<%=(String)session.getAttribute("locale")%>'
	    if (locale == "zh_CN"){
		    window.open("http://ditu.google.com/?ie=UTF8&hl=zh-CN&q=" + address,
				"_blank");
	    } else {
			window.open("https://maps.google.com/maps?q=" + address,
				"_blank");	    	
	    }
	}

	function shipMap() {
	    address = $("input[name='account.ship_country']").val() + " "
				+ $("input[name='account.ship_state']").val() + " "
				+ $("input[name='account.ship_city']").val() + " "
				+ $("input[name='account.ship_street']").val();
	    locale = '<%=(String)session.getAttribute("locale")%>'
	    if (locale == "zh_CN"){
		    window.open("http://ditu.google.com/?ie=UTF8&hl=zh-CN&q=" + address,
				"_blank");
	    } else {
			window.open("https://maps.google.com/maps?q=" + address,
				"_blank");	    	
	    }
	}
	
	function copyAddress() {
		if ($('#copy_checkbox').attr('checked')) {
			$("input[name='account.ship_street']").attr("value",
					$("input[name='account.bill_street']").val());
			$("input[name='account.ship_street']").attr("disabled", "disabled");
			$("input[name='account.ship_city']").attr("value",
					$("input[name='account.bill_city']").val());
			$("input[name='account.ship_city']").attr("disabled", "disabled");
			$("input[name='account.ship_state']").attr("value",
					$("input[name='account.bill_state']").val());
			$("input[name='account.ship_state']").attr("disabled", "disabled");
			$("input[name='account.ship_postal_code']").attr("value",
					$("input[name='account.bill_postal_code']").val());
			$("input[name='account.ship_postal_code']").attr("disabled",
					"disabled");
			$("input[name='account.ship_country']").attr("value",
					$("input[name='account.bill_country']").val());
			$("input[name='account.ship_country']")
					.attr("disabled", "disabled");
		} else {
			$("input[name='account.ship_street']").removeAttr("disabled");
			$("input[name='account.ship_city']").removeAttr("disabled");
			$("input[name='account.ship_state']").removeAttr("disabled");
			$("input[name='account.ship_postal_code']").removeAttr("disabled");
			$("input[name='account.ship_country']").removeAttr("disabled");
		}
	}

	$(document).ready(
			
			function() {
				$('#ownerID').combogrid('setValue',
						'<s:property value="assignedToID"/>');
				$('#ownerID').combogrid('setText',
						'<s:property value="assignedToText"/>');
				$('#createDate').datebox('setValue', '<s:property value="createDate"/>');
				if ($("#seleteIDs").val() != "") {
					$("input:checkbox[name=massUpdate]")
							.css("display", 'block');
					$('#tt').tabs('close', '<s:text name='tab.relations'/>');
				}
				if ($("#id").val() == "") {
					$('#tt').tabs('close', '<s:text name='tab.relations'/>');
					$('#tt').tabs('close', '指派记录');
					if ($("#seleteIDs").val() == "") {
						$("#addObjectForm").form('validate');
					}
				}
				if ($("#saveFlag").val() == "true") {
					$.messager.show({
						title : '<s:text name="message.title" />',
						msg : '<s:text name="message.save" />',
						timeout : 5000,
						showType : 'slide'
					});
					$("#saveFlag").val("");
				}
				
				
				$('#tt').tabs({
					onSelect:function(title){
				        if (title == "<s:text name='tab.relations'/>"){
							 var params = {
							   id : $("#id").val()
							 };
							  $.ajax({
							    type: "POST",
							    url: "getAccountRelationCounts.action",
							    data: params,
							    dataType:"text", 
							    success: function(json){  
							      var obj = $.parseJSON(json);  
							      $("#contactNumber").html(obj.contactNumber); 
							      $("#opportunityNumber").html(obj.opportunityNumber); 
							      $("#leadNumber").html(obj.leadNumber); 
							      $("#accountNumber").html(obj.accountNumber); 
							      $("#documentNumber").html(obj.documentNumber); 
							      $("#caseNumber").html(obj.caseNumber);
							      $("#taskNumber").html(obj.taskNumber);
							    },
							    error: function(json){
							      alert("json=" + json);
							      return false;
							    }
							  });				        	
				        }
				    }
				});
				
				$("#up-down-btn").click(function(){
					var tab2 = $('#shop-more-info');
					tab2.toggle();
					var dis = tab2.css('display');
					if(dis != 'none' && dis != ''){
						$("#up-down-btn span").text('点击收起');
					}else{
						$("#up-down-btn span").text('点击展开');
					}
				});
				
				    initAreaSelect();
			});
			
	
	function initProvince(){
	$.ajax({
		type:'post',
		url:'areaCodeActionProvince.action',
		cache:false,
		async:false,
		dataType:'text',
		success:function(data) {
			$("#provineceDiv").html(data);
			initCity();
		}
	});
}

	
function initCity(){
	$("#areaProvince").change(function(){
		$("#areaDiv").html("");
		var val = $(this).find("option:selected").val();
		if(val!=0){
			$.ajax({
				type:'post',
				url:'areaCodeActionCity.action',
				data:'areacode='+val,
				cache:false,
				async:false,
				dataType:'text',
				success:function(data) {
					$("#cityDiv").html(data);
					initArea();
					var proval=$("#areaProvince option:selected").text();
					if(proval!="请选择"){
						$("#accountProvice").val(proval);
					}
					
				}
			});
		}else{
			$("#cityDiv").html("");
			$("#areaDiv").html("");
		}
	});
}


function initArea(){
	$("#areaCity").change(function(){
		var val = $(this).find("option:selected").val();
		if(val!=0){
			$.ajax({
				type:'post',
				url:'areaCodeActionArea.action',
				data:'areacode='+val,
				cache:false,
				async:false,
				dataType:'text',
				success:function(data) {
					$("#areaDiv").html(data);
					var cityval=$("#areaCity option:selected").text();
					if(cityval!="请选择"){
						$("#accountCity").val(cityval);
					}
				}
			});
		}else{
			$("#areaDiv").html("");
		}
		$("#areas").change(function(){
			var areaval=$("#areas option:selected").text();
			if(areaval!="请选择"){
				$("#accountDistrict").val(areaval);
			}
		});
	});

}
function initAreaSelect(){
	initProvince();
	initCity();
	initArea();
	var proval = $("#accountProvice").val();
	var cityval = $("#accountCity").val();
	var areaval = $("#accountDistrict").val();
	if(proval!=""){
		 $("#areaProvince").find("option:contains('"+proval+"')").attr("selected",true); 
		 $("#areaProvince").trigger("change");
	}
	if(cityval!=""){
		 $("#areaCity").find("option:contains('"+cityval+"')").attr("selected",true);
		 $("#areaCity").trigger("change");
	}
	if(areaval!=""){
		 $("#areas").find("option:contains('"+areaval+"')").attr("selected",true);
	}
}

</script>
</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <div id="shortcuts" class="headerList">
        <b style="white-space: nowrap; color: #444;"><s:text
            name="title.action" />:&nbsp;&nbsp;</b> <span> <span
          style="white-space: nowrap;"> <a id="save_accept_btn"
            href="#" class="easyui-linkbutton"
            iconCls="icon-save-accept" onclick="ajaxSave()" plain="true"><s:text
                name="button.save" /></a>
        </span> <span> <%-- <span style="white-space: nowrap;"> <a
              id="save_go_btn" href="#" class="easyui-linkbutton"
              iconCls="icon-save-go" onclick="saveClose()" plain="true"><s:text
                  name="button.saveClose" /></a>
          </span> --%>
           <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="cancel()" plain="true"><s:text
                  name="button.cancel" /></a>
          </span> <%-- <s:if test="account!=null && account.id!=null">
              <span style="white-space: nowrap;"><a
                href="javascript:void(0)" id="mtmt"
                class="easyui-menubutton"
                data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
                    name='menu.toolbar.more.title' /></a>
                <div id="mtm1" style="width: 150px;">
                  <div data-options="iconCls:'icon-import'"
                    onClick="openwindow2('/crm/showChangeLogPage.action?entity=Account&recordID=' + '<s:property value="account.id" />',750,500)">
                    <s:text name='menu.item.changeLog.title' />
                  </div>
                </div> </span>
            </s:if>
        </span> --%>
      </div>

		<div class="item span-60" >
		<div class="item-btn">
		</div>
			<div class="item-title">商场信息<sapn style="margin-left:10px;color:red">(带*的是必填字段)</sapn></div>
				<form id="addObjectForm" validate="true">
					 <s:hidden id="id" name="account.id" value="%{account.id}" />
			          <s:hidden id="saveFlag" name="saveFlag" />
			          <s:hidden name="relationKey" id="relationKey"
			            value="%{relationKey}" />
			          <s:hidden name="relationValue" id="relationValue"
			            value="%{relationValue}" />
			          <s:hidden id="seleteIDs" name="seleteIDs" value="%{seleteIDs}" />
			          <s:hidden id="createdBy" name="createdBy" />
			          <s:hidden id="createdOn" name="createdOn" />
			          <s:hidden id="updatedBy" name="updatedBy" />
			          <s:hidden id="updatedOn" name="updatedOn" />
		<table style="padding: 10px;" cellspacing="10" cellpadding="0"
            width="100%">
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.name.label"></s:text>：</label>
               </td>
              <td class="td-value">
              	<input name="account.name" class="easyui-validatebox record-value" data-options="required:true" value="<s:property value="account.name" />" /><span style="color:red">*</span>
                </td>
              <td class="td-label"><label class="record-label">跟进人：</label></td>
              <td class="td-value">
              <select id="ownerID"
                class="easyui-combogrid record-value" name="ownerID"
                style="width: 180px;"
                data-options="  
					            panelWidth:520,  
					            idField:'id',  
					            textField:'name',  
					            url:'<s:url action="listvalidator" namespace="/jsp/system"/>',
		                        loadMsg: '<s:text name="datagrid.loading" />',
		                        pagination : true,
		                        pageSize: 10,
		                        pageList: [10,30,50],
				                fit: true,
					            mode:'remote',
					            columns:[[  
					                {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
					                {field:'name',title:'姓名',width:100},  
					                {field:'title',title:'<s:text name="entity.title.label" />',width:120},  
					                {field:'department',title:'<s:text name="entity.department.label" />',width:100},
					                {field:'status.name',title:'<s:text name="entity.status.label" />',width:100}   
					            ]]  
					        ">
              </select><span style="color:red">*</span></td>
            </tr>
            <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.advert_level.label"></s:text>：</label></td>
                  <td class="td-value">
                  <select name="account.advert_level" id="advert_level" style="width:178px">
                  		<option value="">--选择广告等级--</option>
                  		<option value="B" <s:if test="advertLevel == \"B\"" >selected </s:if>>B</option>
                  		<option value="A" <s:if test="advertLevel == \"A\"" >selected </s:if>>A</option>
                  		<option value="S" <s:if test="advertLevel == \"S\"" >selected </s:if>>S</option>
                  		<option value="SS" <s:if test="advertLevel == \"SS\"" >selected </s:if>>SS</option>
                  </select>  
                    </td>
                 <td class="td-label"><label class="record-label"><s:text
                        name="entity.mallbd_level.label"></s:text>：</label></td>
                  <td class="td-value">
                  	<select name="account.mallbd_level" id="mallbd_level" style="width:178px">
                  		<option value="">--商场BD等级--</option>
                  		<option value="C" <s:if test="mallLevel == \"C\"" >selected </s:if>>C</option>
                  		<option value="B" <s:if test="mallLevel == \"B\"" >selected </s:if>>B</option>
                  		<option value="A" <s:if test="mallLevel == \"A\"" >selected </s:if>>A</option>
                  		<option value="S" <s:if test="mallLevel == \"S\"" >selected </s:if>>S</option>
                  	</select>
                   </td>
                </tr>
                 <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="menu.account_type.title"></s:text>：</label></td>
                  <td class="td-value" >
                   <select name="typeID" id="typeID" style="width:178px">
                  		<option value="">--商场类型--</option>
                  		<option value="1" <s:if test="typeID == 1" >selected </s:if>>商圈购物中心</option>
                  		<option value="2" <s:if test="typeID == 2" >selected </s:if>>社区购物中心</option>
                  		<option value="3" <s:if test="typeID == 3" >selected </s:if>>综合型百货</option>
                  		<option value="4" <s:if test="typeID == 4" >selected </s:if>>纯百货</option>
                  	</select><span style="color:red">*</span>
                                    </td>
                <td class="td-label"><label class="record-label">楼层：</label></td>   
				  <td class="td-value">
				  	<input class="easyui-validatebox record-value" placeholder="填写数字" type="text" name="account.floors" id="floors" value="<s:property value="account.floors"/>" />层/区
                  </td>
                </tr>       
                <tr>
                  <td class="td-label"><label class="record-label">商圈名：</label></td>
                  <td class="td-value">
                    <input class="easyui-validatebox record-value" type="text" name="account.mall_circle"  id="mall_circle" value="<s:property value="account.mall_circle"/>" /><span style="color:red">*</span>
                    </td>
                 <td class="td-label"><label class="record-label">商场电话：</label></td>   
				  <td class="td-value">
				  	<input class="easyui-validatebox record-value" type="text" name="account.office_phone"  value="<s:property value="account.office_phone"/>" />
                  </td>
                </tr>
                <tr>
               	 <td class="td-label"><label class="record-label">区域：</label></td>   
				  <td class="td-value" colspan="3">
				  	<input type="hidden" id="accountProvice" name="account.province"  value="<s:property value="account.province" />" />
                    <input type="hidden" id="accountCity" name="account.city" value="<s:property value="account.city"/>" />
                    <input  type="hidden" id="accountDistrict" name="account.district"  value="<s:property value="account.district"/>" />
                    <div style="float:left;" id="provineceDiv"></div><div style="float:left;" id="cityDiv"></div><div style="float:left;" id="areaDiv"></div>
                    
                  </td>
                </tr>
                <tr>
                	<td class="td-label"><label class="record-label">商场地址：</label></td>   
				 	 <td colspan="3" class="td-value">
				  		<input class="easyui-validatebox record-value" size="60" type="text" name="account.address" id="address" value="<s:property value="account.address"/>" /><span style="color:red">*</span>
                  	</td> 
                 </tr>  
              </table> 
              <div id="up-down-btn" class="updownicon"><b class="icon_l"></b><span>点击展开</span><b class="icon_r"></b></div>
              <table style="padding: 10px; display:none" cellspacing="10" cellpadding="0"
            width="100%" id="shop-more-info" >
               	 <tr>
                  	<td class="td-label"><label class="record-label">营业面积：</label></td>   
				 	 <td class="td-value">
				  		<input class="easyui-validatebox record-value" type="text" name="account.mall_acreage"  value="<s:property value="account.mall_acreage"/>" />
                  		<span>万平方米</span>
                  	</td>
                  	<td class="td-label"><label class="record-label">商户数量：</label></td>   
				 	 <td class="td-value">
				  		<input class="easyui-validatebox record-value" type="text" name="account.merchant_num"  value="<s:property value="account.merchant_num"/>" />
                  		<span>家</span>
                  	</td>
                </tr> 
                  <tr>         
                  	<td class="td-label"><label class="record-label">日均人流量：</label></td>   
				 	 <td class="td-value">
				  		<input class="easyui-validatebox record-value" type="text" name="account.day_people_flow" value="<s:property value="account.day_people_flow"/>" />
                  		<span>万人</span>
                  	</td>
                  	<td class="td-label"><label class="record-label">峰值人流量：</label></td>   
				 	 <td class="td-value">
				  		<input class="easyui-validatebox record-value" type="text" name="account.peak_people_flow" value="<s:property value="account.peak_people_flow"/>" />
                  		<span>万人</span>
                  	</td>
                </tr>  
               <tr>
                  	<td class="td-label"><label class="record-label">年销售额：</label></td>   
				 	 <td class="td-value">
				  		<input class="easyui-validatebox record-value" type="text" name="account.year_sales"  value="<s:property value="account.year_sales"/>" />
                  		<span>万人民币</span>
                  	</td>
                  	<td class="td-label"></td> 
                  	<td class="td-label"></td> 
                </tr>  
                  <tr>
                    <td class="td-label"><label class="record-label">备注：</label></td>   
				 	 <td class="td-value" colspan="4">
				  		<textarea style="margin:0px;width:425px;height:85px" name="account.memo"><s:property value="account.memo"/></textarea>
                  	</td>
                 </tr>  
               </table>   
			</form>
		</div>
		
	<s:if test="id">
		<div class="item span-38">
			<div class="item-title">跟进分析</div>
		<s:if test="recordVO">
			<table style="padding: 10px;" cellspacing="10" cellpadding="0"
            width="100%" >
			<tr>
				<td class="td-width">
				<label class="record-label">当前跟进人：</label>
				<label class="record-label"><s:property value="recordVO.assignToName" /></label>
               </td>
               <td class="td-width">
               </td>
			</tr>
			<tr>
				<td class="td-width">
				<label class="record-label">客户意向：</label>
				<label class="record-label"><s:property value="recordVO.intentName" /></label>
               </td>
               <td class="td-width">
				<label class="record-label">拜访情况：</label>
				<label class="record-label"><s:property value="recordVO.visitName" /></label>
               </td>
			</tr>
			
			<tr>
				<td class="td-width">
				<label class="record-label">创建时间：</label>
				<label class="record-label"><s:property value="recordVO.createDate" /></label>
               </td>
               <td class="td-width">
				<label class="record-label">跟踪<s:property value="recordVO.days" />天</label>
               </td>
			</tr>
			<tr>
				<td class="td-width">
				<label class="record-label">次数统计：</label>
				<label class="record-label"><s:property value="recordVO.getTotalVisitNum()" /></label>
               </td>
               <td class="td-width">
				<label class="record-label">电话次数：</label>
				<label class="record-label"><s:property value="recordVO.phoneVisitNum" /></label>&nbsp;&nbsp;&nbsp;
				<label class="record-label">上门次数：</label>
				<label class="record-label"><s:property value="recordVO.dropVisitNum" /></label>
               </td>
			</tr>
			</table>
		</s:if>
		</div>
	</s:if>
	
	<s:if test="id">	
		<div class="item span-60">
		
		<div class="item-btn">
		<a href="/jsp/crm/editContact.action?accountID=<s:property value="account.id" />" target="_blank" class="item-link">
			添加联系人
		</a>
		</div>
		<div class="item-title">日常联系人</div>
	<s:if test="mainContactList">
	<s:iterator value="mainContactList" status="st" id="mainContact">
			<table class="table table-striped" cellspacing="0" cellpadding="0" >
				<tr>
					<td class="td-width">
						<label class="record-label">姓名：</label>
					</td>
					<td class="td-width">
						<label class="record-label">
							<s:property value="#mainContact.last_name" />
						</label>
               		</td>
					<td class="td-width">
						<label class="record-label">性别：</label>
               		</td>
               		<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.salutation.getLabel_zh_CN()" /></label>
					</td>
               		<td class="td-width">
						<label class="record-label">昵称：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.nick_name" /></label>
               		</td>
				</tr>
				<tr>
					<td class="td-width">
						<label class="record-label">职称：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.title" /></label>
               		</td>
					<td class="td-width">
						<label class="record-label">手机(主要)：</label>
               		</td>
               		<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.office_phone" /></label>
					</td>
               		<td class="td-width">
						<label class="record-label">手机(备份)：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.person_phone" /></label>
               		</td>
				</tr>
				<tr>
					<td class="td-width">
						<label class="record-label">办公邮箱：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.office_email" /></label>
               		</td>
					<td class="td-width">
						<label class="record-label">私人邮箱：</label>
               		</td>
               		<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.person_email" /></label>
					</td>
               		<td class="td-width">
						<label class="record-label">座机：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.mobile" /></label>
               		</td>
				</tr>
				<tr>
					<td class="td-width">
						<label class="record-label">QQ：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.qq" /></label>
               		</td>
               		<td class="td-width">
						<label class="record-label">微信账号：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.skype_id" /></label>
               		</td>
               		<td class="td-width">
               		</td>
               		<td class="td-width" >
               		<div style="width: 90px;">
						<a href="/jsp/crm/editContact.action?id=<s:property value="#mainContact.id" />" target="_blank" class="item-link">
							修改联系人
		 				</a>
					</div>
               		</td>
				</tr>
			</table>
		</s:iterator>
	</s:if>
		</div>
	</s:if>	
		
	<s:if test="id">
	<s:if test="otherContactList">
		<div class="item span-60">
		<div class="item-title">其他联系人</div>
	<s:iterator value="otherContactList" status="st" id="mainContact">
			<table class="table table-striped" cellspacing="0" cellpadding="0" >
				<tr>
					<td class="td-width">
						<label class="record-label">姓名：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.last_name" /></label>
               		</td>
					<td class="td-width">
						<label class="record-label">性别：</label>
               		</td>
               		<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.salutation.getLabel_zh_CN()" /></label>
					</td>
               		<td class="td-width">
						<label class="record-label">昵称：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.nick_name" /></label>
               		</td>
				</tr>
				<tr>
					<td class="td-width">
						<label class="record-label">职称：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.title" /></label>
               		</td>
					<td class="td-width">
						<label class="record-label">手机(主要)：</label>
               		</td>
               		<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.office_phone" /></label>
					</td>
               		<td class="td-width">
						<label class="record-label">手机(备份)：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.person_phone" /></label>
               		</td>
				</tr>
				<tr>
					<td class="td-width">
						<label class="record-label">办公邮箱：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.office_email" /></label>
               		</td>
					<td class="td-width">
						<label class="record-label">私人邮箱：</label>
               		</td>
               		<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.person_email" /></label>
					</td>
               		<td class="td-width">
						<label class="record-label">座机：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.mobile" /></label>
               		</td>
				</tr>
				<tr>
					<td class="td-width">
						<label class="record-label">QQ：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.qq" /></label>
               		</td>
               		<td class="td-width">
						<label class="record-label">微信账号：</label>
					</td>
					<td class="td-width">
						<label class="record-label"><s:property value="#mainContact.skype_id" /></label>
               		</td>
               		<td class="td-width"></td>
               		<td class="td-width" >
               		<div style="width: 90px;">
						<a href="/jsp/crm/editContact.action?id=<s:property value="#mainContact.id" />" target="_blank" class="item-link">
							修改联系人
		 				</a>
					</div>
               		</td>

				</tr>
			</table>
		</s:iterator>
	</s:if>
		</div>
	</s:if>	
	
	<s:if test="id">
		<div class="item span-38" style="position: absolute;right: 1px;top: 317px;">
			<div class="item-btn" style="right:152px">
			<a href="/jsp/crm/editRecord.action?outerId=<s:property value="id"/>&table=account" target="_blank" class="item-link">添加跟踪记录</a>
			</div>
			
		<div class="item-btn" style="right:82px">
			<a href="/jsp/crm/listContract.jsp?accountId=<s:property value="id"/>" class="item-link" target="_blank">合同列表</a>
		</div>
		<div class="item-btn">
		<a href="/jsp/crm/editContract.action?accountId=<s:property value="id"/>" target="_blank" class="item-link">上传合同</a>
		</div>
		<div class="item-title" >跟踪记录</div>
		<div class="item-box">
			<ul>
			<s:iterator value="recordList" status="st" id="r">
		    <li>
		        <p style="color:#000"><s:property value="#r.assignedDateName" /></p>
		        <p style="color:#c80000"><s:property value="#r.typeShowName" /></p>
		         <p style="left:105px;top:48px;position:absolute"><s:property value="#r.contactName"/></p>
		        <p><s:property value="#r.recordText" /></p>
		         <p><s:property value="#r.visitNote" /></p>
		        <p style="position: absolute; right:20px; top:20px"><s:property value="#r.createByName" /></p>
		    </li>
		    </s:iterator>
		  </ul>
		</div>
	</s:if>
		</div>
		
	<!-- tips msg -->
	<div id="id_tips_box" class="popu-background" style="display:none">
	<div id="id_tips" class="item span-38" style="width:25%; left:50%; top:30%; margin-left:-12.5%;">
		<div class="item-btn">
		<a href="javascript:void(0)" class="item-link" onclick="closeTips()">关闭</a>
		</div>
		<div class="item-btn" style="right:56px;display:none" id="id_continueSave">
		<a href="javascript:void(0)" class="item-link" onclick="continueSave()">继续添加</a>
		</div>
		<div class="item-title" >提示窗</div>
		<div class="item-box">
			<ul>
		    <li>
		        <p style="color:#000" id="id_tipsMsg"></p>
		    </li>
		  </ul>
		</div>
	</div>
	</div>
	
		<div style="display:block;clear:both;height:0;line-height:0;"></div>
    </div>
  </div>
   
</body>
<style>
.popu-background{
	position:fixed;
	top:0;
	left:0;
	width:100%;
	height:100%;
	background:rgba(0,0,0,0.7)
}
.item{
	float:left;
	border-radius: 10px;
	background: #fff;
	border:solid 1px #c8c8c8;
	margin:10px 0 0 10px;
	position:relative;
}
.item .item-title{
	padding:10px;
	border-radius: 10px 10px 0 0;
	background: #e6e6e6;
	padding-left: 15px;
}
.item .item-box{
	padding:10px;
}
.item .item-box li{
	background:#fafafa;
	border:solid 1px #c8c8c8; 
	padding: 20px 10px 0; 
	position:relative; 
	border-radius:10px;
	margin-bottom:10px;
}
.item .item-btn{
	position:absolute;
	top:5px;
	right:10px;
}
.item a.item-link,.item a:visited.item-link{
	display:block;
	padding: 5px;
	border-radius: 3px;
	background: #3276b1;
	text-decoration: none;
	color: #fff;
}
.span-38{
	width:38%
}
.span-60{
	width:60%
}

.td-width{
	width:15%
}

.table {
  width: 98%;
  background: #fafafa;
  margin:10px auto;
}

.table > thead > tr > th, 
.table > tbody > tr > td {
  padding: 4px 10px;
  line-height: 1.428571429;
  vertical-align: top;
  border-top: 1px solid #fff;
}
 
.table-striped > tbody > tr > td:nth-child(odd) {
  background-color: #f2f2f2;
}
.updownicon{ 
	width:90%; 
	height:20px; 
	line-height:20px; 
	border-bottom:solid 1px #3276b1; 
	text-align:center; 
	margin:10px auto; 
	cursor: pointer;  
	font-size:12px;
}
.updownicon b{
	display:inline-block;
	border-bottom:20px #3276b1 solid;
	width:0px;
	height:0px;
	overflow:hidden; 
}
.updownicon b.icon_l{
	border-left:20px transparent dotted;
	margin-left:-20px;
}
.updownicon b.icon_r{
	border-right:20px transparent dotted;
}
.updownicon span{
	display:inline-block; 
	background:#3276b1; 
	color:#fff; 
	padding:0 10px;
	vertical-align:top;
}

</style>

</html>



