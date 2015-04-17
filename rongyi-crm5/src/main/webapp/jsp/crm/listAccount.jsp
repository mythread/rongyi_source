<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="s" uri="/struts-tags"%> <%@
page import="com.gcrm.util.DateTimeUtil"%> <%@ page language="java"
import="com.gcrm.domain.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<link rel="stylesheet" type="text/css" media="screen"
 href="../../css/redmond/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" media="screen"
 href="../../css/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" media="screen"
 href="../../css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
 href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />

 <script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
 src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
 src="../../js/jquery-ui-1.9.2.custom.min.js"></script>
<script type="text/javascript" src="../../js/ui.multiselect.js"></script>
<script type="text/javascript" src="../../js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>


<script type="text/javascript"
 src="../../js/i18n/grid.locale-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
 src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
	  $("#delete").click(function() {	
		  many_deleterow("/crm/deleteAccount.action?seleteIDs=");
	  });	

	  $("#massUpdate").click(function() {	
		  many_massUpdaterow("/crm/editAccount.action?seleteIDs=");
	  });
	  
	  $("#export").click(function() {	
		  many_exportrow("/crm/exportAccount.action?seleteIDs=");
	  });		  
	  
	  $("#copy").click(function() {	
		  many_copyrow("/crm/copyAccount.action?seleteIDs=");
	  });

		  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listAccountFull.action', 
			mtype: 'POST',
			height: "auto",
		   	colNames:['序号','商场名','地址','客户意向','拜访进度','指派者','指派时间','跟进时间','广告等级','商场BD等级',
		   	          '类型','营业面积','商户数量','日均人流量','峰值人流量','销售额','备注','创建者'],
		   	colModel:[
		   		{name:'id',index:'id', width:120, key: true,sorttype:"int",resizable:true, hidden:true},
		   		{name:'name',index:'name', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'detailAddress',index:'detailAddress', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'intentName',index:'intentName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'visitName',index:'visitName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'assignedToName',index:'assignedToName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'assignedDateName',index:'assignedDateName', width:150, resizable:true, formatter:urlFmatter},		   		
		   		{name:'updateOnName',index:'updateOnName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'advert_level',index:'advert_level', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'mallbd_level',index:'mallbd_level', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'accountTypeName',index:'accountTypeName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'mallAcreage',index:'mallAcreage', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'merchant_num',index:'merchant_num', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'day_people_flow',index:'day_people_flow', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'peak_people_flow',index:'peak_people_flow', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'year_sales',index:'year_sales', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'memo',index:'memo', width:150, resizable:true, hidden:true, formatter:urlFmatter},
		   		{name:'createdByName',index:'createdByName', width:150, resizable:true, hidden:true, formatter:urlFmatter}
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "<s:text name='title.grid.accounts'/>"
		});
		function urlFmatter (cellvalue, options, rowObject)
		{  
		   var par='<%=((User)session.getAttribute("loginUser")).getUpdate_account()%>';
		   if (par == 1){
		     new_format_value = "<a href='editAccount.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
		   }else {
			 new_format_value = cellvalue;
		   }
		   return new_format_value;
		};	
		
		jQuery("#grid").jqGrid('navGrid','#pager',{del:false,add:false,edit:false,refresh:false,search:false});
		jQuery("#grid").jqGrid('navButtonAdd',"#pager",{caption:"",title:"<s:text name='grid.button.toggle.title'/>", buttonicon :'ui-icon-pin-s',
			onClickButton:function(){
				mygrid[0].toggleToolbar();
			} 
		});		
		jQuery("#grid").jqGrid('navButtonAdd',"#pager",{caption:"",title:"<s:text name='grid.button.advancedSearch.title'/>",buttonicon :'ui-icon-search',
			onClickButton:function(){
				jQuery("#grid").jqGrid('searchGrid', {multipleSearch:true} );
			} 
		});	
		jQuery("#grid").jqGrid('navButtonAdd',"#pager",{caption:"",title:"<s:text name='grid.button.clear.title'/>",buttonicon :'ui-icon-refresh',
			onClickButton:function(){
				var postdata = jQuery("#grid").jqGrid('getGridParam','postData');
				postdata.filters = "";
				mygrid[0].clearToolbar()
			} 
		});
		jQuery("#grid").jqGrid('navButtonAdd','#pager',{caption: "",title: "<s:text name='grid.button.reorderColumn.title'/>",
		    onClickButton : function (){
		    	jQuery("#grid").jqGrid('columnChooser');
		    }
		});		
		jQuery("#grid").jqGrid('filterToolbar');	
		
		  //获取联系人
		  jQuery.post('/jsp/crm/listUnderlingUsers.action',function(res){
			  var jsonArray = jQuery.parseJSON(res);
				if(jsonArray.length > 0){
					var sel = $('#id_assignUser');
					for(var i = 0 ; i < jsonArray.length; i++){
						var obj = jsonArray[i];
						var op = "<option value=" + obj.id + ">"+ obj.name + "</option>";
						sel.append(op);
					}
				}			  			  
		  });
		  initAreaSelect();	
	});	
    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
    }
    function myparser(s){
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d);
        } else {
            return new Date();
        }
    }
	
    //查询
    function doSearch(){
    	var kw =$('input[name="kwName"]').val();
    	var kv = $('#id_kwValue').val();
    	var param = "";
    	if(kv != ''){
    		param = kw + "=" +  encodeURIComponent(kv) + "&";
    	}
    	var F = $('#search_form');
    	var ser = F.serialize();
    	var index = ser.indexOf('startAssignDate');
    	if(index != -1){
    		param += ser.substring(index);
    	}
		jQuery("#grid").jqGrid('setGridParam',{url:"listAccountFull.action?" + param,page:1}).trigger("reloadGrid");
	}
    //回车事件
    function keypress(){
            if(event.keyCode == "13")    
            {
            	doSearch();
            }
        };
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
      name="title.action" />:&nbsp;&nbsp;</b> <span> <s:if
      test="#session.loginUser.create_account == 1">
      <span style="white-space: nowrap;"> <a
       href="editAccount.action" class="easyui-linkbutton"
       iconCls="icon-add" plain="true"><s:text
         name="action.createAccount" /></a>
      </span>
     </s:if> <s:if test="#session.loginUser.delete_account == 1">
      <span style="white-space: nowrap;"> <a id="delete" href="#"
       class="easyui-linkbutton" iconCls="icon-remove" plain="true"><s:text
         name="action.deleteAccount" /></a>
      </span>
     </s:if> 
     <span style="white-space: nowrap;"><a
      href="javascript:void(0)" id="mtmt" class="easyui-menubutton"
      data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
        name='menu.toolbar.more.title' /></a>
      <div id="mtm1" style="width: 150px;">
       <s:if
        test="#session.loginUser.create_account == 1 || #session.loginUser.update_account == 1">
        <div data-options="iconCls:'icon-import'"
         onClick="openwindow('/crm/upload.jsp?entityName=Account&namespace=crm&title=' + '<s:text name="title.import.account" />')">
                  <s:text name='menu.item.import.title' />
       </div> 
       </s:if>
     </div>
   </span> </span>
  </div>
  <div style="margin:10px 5px">
   <h2>
    <s:text name="title.listAccount" />
   </h2>
  </div>
<style type="text/css">
	#id_search_table{width:100%;border:1px solid #D9E5EF;border-spacing:0px;height:150px}
	#id_search_table td{border: 1px solid #D9E5EF}
	#id_search_table th{
		width:150px;
		background-color:#F2F6FA;
		border:1px solid #D9E5EF;
	}
	.checkboxcls{margin-right:10px}
</style>

  <div style="margin-left: 20px;">
  	<s:form id="search_form">
  		<table id="id_search_table">
  			<tr>
				<td style="width:100px">
					<select id="id_keyword" class="easyui-combobox" name="kwName" style="width:100px;"> 
	   					<option value="name">商场名</option> 
	   					<option value="id">商场ID</option> 
	   					<option value="floors">楼层/区域</option> 
	   					<option value="merchant_num">商户数量</option> 
	   					<option value="mall_acreage">营业面积</option>
	   					<option value="day_people_flow">日均人流量</option> 
	   					<option value="peak_people_flow">峰值人流量</option>
	   					<option value="year_sales">年销售额</option>  
					</select>
				</td>
				<td>
					<input id="id_kwValue" name="kvName" type="text" placeholder="输入查询值" style="width:250px;height:100%" onkeydown="keypress()"/>
				</td>
				<th >跟进时间:</th>
				<td >
					<input  name="startAssignDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
					~
					<input  name="endAssignDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" />
				</td>
			</tr>
			<tr>
				<th>商场地址</th>
				<td>
					<input  name="detailAddress" type="text"  placeholder="支持模糊查询" style="width:250px;height:100%" onkeydown="keypress()"/>
				</td>
				<th>当前跟进人:</th>
				<td>
					<select  id="id_assignUser"  name="assignTo" style="width:100px;"> 
					<s:if test="#request.admin">
						<option value="all">全部</option>
					</s:if>
	   					<option value="self">自己</option> 
	   					<option value="selfAndunderling">自己及下属</option> 
	   					<option value="underling">所有下属</option> 
					</select>
				</td>		
			</tr>
			<tr>
				<th>商场BD等级:</th>
				<td>
					<input type="checkbox" name="mallbdLevels" value="S" style="margin-left: 10px;"/>
					<span class="checkboxcls">S</span>
					<input type="checkbox" name="mallbdLevels" value="A" />
					<span class="checkboxcls">A</span>
					<input type="checkbox" name="mallbdLevels" value="B" />
					<span class="checkboxcls">B</span>
					<input type="checkbox" name="mallbdLevels" value="C" />
					<span class="checkboxcls">C</span>
				</td>
				<th>客户意向:</th>
				<td>
					<input type="checkbox" name="intentNames" value="0" style="margin-left: 10px;"/>
					<span class="checkboxcls">暂无</span>
					<input type="checkbox" name="intentNames" value="5"/>
					<span class="checkboxcls">已有设备</span>
					<input type="checkbox" name="intentNames" value="6"/>
					<span class="checkboxcls">无投放价值</span>
					<input type="checkbox" name="intentNames" value="1"/>
					<span class="checkboxcls">初步</span>
					<input type="checkbox" name="intentNames" value="2"/>
					<span class="checkboxcls">口头</span>
					<input type="checkbox" name="intentNames" value="3"/>
					<span class="checkboxcls">试用</span>
					<input type="checkbox" name="intentNames" value="4"/>
					<span class="checkboxcls">正式</span>
					<input type="checkbox" name="intentNames" value="7"/>
					<span class="checkboxcls">已进场</span>
				</td>
			</tr>
			<tr>
				<th>广告等级:</th>
				<td>
					<input type="checkbox" name="advertLevels" value="SS" style="margin-left: 10px;"/>
					<span class="checkboxcls">SS</span>
					<input type="checkbox" name="advertLevels" value="S" style="margin-left: 10px;"/>
					<span class="checkboxcls">S</span>
					<input type="checkbox" name="advertLevels" value="A"/>
					<span class="checkboxcls">A</span>
					<input type="checkbox" name="advertLevels" value="B"/>
					<span class="checkboxcls">B</span>
					<input type="checkbox" name="advertLevels" value="C"/>
					<span class="checkboxcls">C</span>
				</td>
				<th>拜访进度:</th>
				<td>
					<input type="checkbox" name="visitNames" value="0" style="margin-left: 10px;" />
					<span class="checkboxcls">暂无</span>
					<input type="checkbox" name="visitNames" value="1"/>
					<span class="checkboxcls">初次</span>
					<input type="checkbox" name="visitNames" value="2"/>
					<span class="checkboxcls">二次</span>
					<input type="checkbox" name="visitNames" value="3"/>
					<span class="checkboxcls">多次</span>
					<input type="checkbox" name="visitNames" value="4" />
					<span class="checkboxcls">谈判</span>
					<input type="checkbox" name="visitNames" value="5" />
					<span class="checkboxcls">售后</span>
				</td>
				
				
			</tr>
			<tr>
				<th>商场类型:</th>
				<td>
					<input type="checkbox" name="accountTypes" value="1" style="margin-left: 10px;" />
					<span class="checkboxcls">商圈购物中心</span>
					<input type="checkbox" name="accountTypes" value="2" />
					<span class="checkboxcls">社区购物中心</span>
					<input type="checkbox" name="accountTypes" value="3" />
					<span class="checkboxcls">综合性百货</span>
					<input type="checkbox" name="accountTypes" value="4" />
					<span class="checkboxcls">纯百货</span>
				</td>
				<th>区域:</th>  
				  <td>
				  	<input type="hidden" id="accountProvice" name="account.province"  value="<s:property value="account.province" />" />
                    <input type="hidden" id="accountCity" name="account.city" value="<s:property value="account.city"/>" />
                    <input  type="hidden" id="accountDistrict" name="account.district"  value="<s:property value="account.district"/>" />
                    <div style="float:left;" id="provineceDiv"></div><div style="float:left;" id="cityDiv"></div><div style="float:left;" id="areaDiv"></div>
                  </td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="reset" id="id_clear" style="margin-left:40px;width:80px"/>
				</td>
				<td colspan="2">
				<input type="button" value="查询" id="id_search1" style="margin-left:40px;width:80px" onclick="doSearch()" />
				</td>
			</tr>
		</table>  	
  	</s:form>
  </div>
  
  <div id="feature-content" style="margin-left: 20px;width:1000px ">
   <table style="" cellspacing="10" cellpadding="0" width="100%">
    <s:if test="hasActionErrors()">
     <tr>
      <td align="left" colspan="4"><font color="red"><s:actionerror /></font></td>
     </tr>
    </s:if>
   </table>
   <table id="grid" class="scroll" cellpadding="0" cellspacing="0"></table>
   <div id="pager" class="scroll"></div>
   <div id="filter" style="margin-left: 30%; display: none">
    <s:text name="title.listAccount" />
   </div>
  </div>
 </div>
 <s:include value="../footer.jsp" />
 </div>
</body>
</html>