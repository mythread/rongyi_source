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

<style type="text/css">
    
    .for-clear {
    	clear: both;
    }
    
    .info-block {
    	width: 800px;
    	border: 1px solid #95B8E7;
    	margin-bottom: 10px; 
    	font-size: 12px;
    }
   	
   	.block-header {
   		height: 120px;
   	}
   	
   	.block-header-left {
   		width: 120px;
   		float: left;
   	}
   	
   	.block-header-left img {
   		width: 82px;
   		height: 82px;
   		margin: 16px;
   	}
   	
   	.block-header-right {
   		width: 660px;
   		height: 120px;
   		float: left;
   	}
   	
   	.block-header-right-top {
   		height: 60px;
   		border-bottom: 1px dashed #95B8E7;
   		line-height: 60px;
   	}
   	
   	.block-header-right-bottom {
   		line-height: 60px;
   	}
   	
   	.block-header-right-bottom span {
   		margin-right: 20px;
   	}
   	
   	.btn-div {
   		float: right;
   	}
   	
   	.block-body {
   		margin-left: 20px;
   		margin-right: 20px;
   	}
   	
   	.block-body-block {
   		width: 354px;
   		float: left;
   		margin: 10px;
   		border: 1px solid #95B8E7;
   	}
   	
   	.block-body-title {
   		height: 26px;
   		line-height: 26px;
   		background: url("/css/redmond/images/panel_title.png");
   		padding-left: 15px;
   		color: #15428B;
   		font-weight: bold;
   		border-bottom: 1px solid #95B8E7;
   	}
   	
   	.block-body-body {
   		padding: 15px;
   	}
   	
   	.btns-div {
   		text-align: right;
   	}
   	
   	.body-line {
   		border: 1px solid #95B8E7;
   		margin: 10px;
   	}
   	
   	.body-line-title {
   		background: url("/css/redmond/images/panel_title.png");
   		color: #15428B;
   		font-weight: bold;
   		height: 26px;
   		line-height: 26px;
   		width: 100px;
   		float: left;
   		text-align: center;
   		border-right: 1px solid #95B8E7;
   	}
   	
   	.body-line-content {
   		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
   		height: 26px;
   		line-height: 26px;
   		margin-left: 110px;
   	}
   	
   	.body-img {
   		margin: 10px;
   	}
   	
   	.body-img img {
   		width: 147px;
   		height: 96px;
   	}
   	
   	
   	
   	#id_search_table{border:1px solid #D9E5EF;border-spacing:0px;height:80px}
	#id_search_table td{border: 1px solid #D9E5EF}
	#id_search_table th{
		width:150px;
		background-color:#F2F6FA;
		border:1px solid #D9E5EF;
	}
    </style>
<script type="text/javascript"
 src="../../js/i18n/grid.locale-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
 src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
	  jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listCmsTaskFull.action', 
			mtype: 'POST',
			height: "auto",
		   	colNames:['序号','商场名称','审核信息','审核类型','审核状态','时间','操作'],
		   	colModel:[
		   		{name:'id',index:'id', width:120, key: true,sorttype:"int",resizable:true, hidden:true},
		   		{name:'mallName',index:'mallName', width:150, resizable:true},
		   		{name:'reviewInfo',index:'reviewInfo', width:150, resizable:true},
		   		{name:'type',index:'type', width:150, resizable:true},
		   		{name:'reviewStatus',index:'reviewStatus', width:150, resizable:true},
		   		{name:'gmtJobCreateName',index:'gmtJobCreateName', width:150, resizable:true},
		   		{name:'button',index:'button', width:150, resizable:true}
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "<s:text name='任务审核列表'/>"
		});
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
    	var kv = $('#keys_Value').val();
    	var param = "";
    	if(kv != ''){
    		param = "nameOrId=" +  encodeURIComponent(kv) + "&";
    	}
    	var F = $('#search_form');
    	var ser = F.serialize();
    	var index = ser.indexOf('startAssignDate');
    	if(index != -1){
    		param += ser.substring(index);
    	}
		jQuery("#grid").jqGrid('setGridParam',{url:"listCmsTaskFull.action?" + param,page:1}).trigger("reloadGrid");
	}
    //回车事件
    function keypress(){
            if(event.keyCode == "13")    
            {
            	doSearch();
            }
        };
       
    //弹出新页面   
    function OpenFrame(taskid,type,reviewStatus,obj) {
    	
//     	console.log('mallId>>>'+$(obj).attr('mallId'));
		var mallId = $(obj).attr('mallId');
   		$('#dd').window({
   			href:'editCmsTask.action?taskid='+taskid+'&type='+type+'&reviewStatus='+reviewStatus+'&mallId='+mallId+''
   		});
   		$('#dd').window('refresh');
   		$('#dd').window('open');
    }
    
    
    </script>
</head>
<body>
 <div id="page-wrap">
  <s:include value="../header.jsp" />
  <s:include value="../menu.jsp" />
  <div id="feature">
   <s:include value="../navigation.jsp" />
  <div style="margin:10px 5px">
   <h2>
    <s:text name="审核任务" />
   </h2>
  </div>
  <div style="margin-left: 20px;">
  	<s:form id="search_form">
				<table id="id_search_table">
				<tr><th>关键字：</th><td>
					<input id="keys_Value" name="keys" type="text" style="width:150px;" onkeydown="keypress()"/></td>
				<th>提交时间:</th>
				<td>
					<input  name="startAssignDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"/>
					~
					<input  name="endAssignDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" />
				</td></tr>
			<tr><th>
				审核类型:</th>
				<td>
					<input type="checkbox" name="taskType" value="0" style="margin-left: 10px;"/>
					<span class="checkboxcls">商铺信息</span>
					<input type="checkbox" name="taskType" value="1" />
					<span class="checkboxcls">广告信息</span>
				</td><th>
				审核状态:</th>
				<td>
					<input type="checkbox" name="taskreviewStatus" value="1" style="margin-left: 10px;"/>
					<span class="checkboxcls">待审核</span>
					<input type="checkbox" name="taskreviewStatus" value="2"/>
					<span class="checkboxcls">已审核</span>
					<input type="checkbox" name="taskreviewStatus" value="3"/>
					<span class="checkboxcls">驳回</span>
				</td>
			</tr><tr>
			<td colspan="2">
			
				<input type="reset" id="id_clear" style="margin-left:40px;width:80px"/></td>
				<td colspan="2">
				<input type="button" value="查询" id="id_search1" style="margin-left:40px;width:80px" onclick="doSearch()" /></td>
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
   <div id="dd" title="审核"  class="easyui-window"  closed="true" style="width:900px;height:480px;padding:5px;">
   </div>
   <table id="grid" class="scroll" cellpadding="0" cellspacing="0"></table>
   <div id="pager" class="scroll"></div>
   <div id="filter" style="margin-left: 30%; display: none">
    <s:text name="审核任务列表" />
   </div>
  </div>
 </div>
 <s:include value="../footer.jsp" />
 </div>
</body>
</html>