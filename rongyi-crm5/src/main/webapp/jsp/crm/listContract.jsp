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
		  many_deleterow("/crm/deleteContract.action?seleteIDs=");
	  });	

	  $("#massUpdate").click(function() {	
		  many_massUpdaterow("/crm/editContract.action?seleteIDs=");
	  });
	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listContractFull.action?accountId=<%= request.getParameter("accountId")%>', 
			mtype: 'POST',
			height: "auto",
		   	colNames:['序号','合同名称','商场名称','签订者','上传者','备注','创建时间','附件名'],
		   	colModel:[
		   		{name:'id',index:'id', width:120, key: true,sorttype:"int",resizable:true, hidden:true},
		   		{name:'contractName',index:'contractName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'accountName',index:'accountName', width:180, resizable:true, formatter:urlFmatter},
		   		{name:'signName',index:'signName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'createdByName',index:'createdByName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'memo',index:'memo', width:180, resizable:true, formatter:urlFmatter},
		   		{name:'createdOnName',index:'createdOnName', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'fileName',index:'fileName', width:180, resizable:true, formatter:downloadAttach}
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "合同列表"
		});
		function urlFmatter (cellvalue, options, rowObject)
		{  
		     new_format_value = "<a href='editContract.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
		   return new_format_value;
		};	
		function downloadAttach(cellvalue, options, rowObject){
			  new_format_value = "<a href='/jsp/crm/downloadContract.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
			   return new_format_value;
		}
		
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
	});	    
  </script>
</head>
<body>
 <div id="page-wrap">
  <s:include value="../header.jsp" />
  <s:include value="../menu.jsp" />
  <div id="feature">
   <div id="shortcuts" class="headerList">
    <b style="white-space: nowrap; color: #444;"><s:text
      name="title.action" />:&nbsp;&nbsp;</b> 
   <span> 
    <s:if test="#session.loginUser.create_contract == 1">
      <span style="white-space: nowrap;"> 
      <a href="editContract.action" class="easyui-linkbutton" iconCls="icon-add" plain="true">
      <s:text name="action.createContract" /></a>
      </span>
    </s:if>
     <s:if test="#session.loginUser.delete_contract == 1">
      <span style="white-space: nowrap;"> <a id="delete" href="#"
       class="easyui-linkbutton" iconCls="icon-remove" plain="true"><s:text
         name="action.deleteContract" /></a>
      </span>
     </s:if>
   </span>
  </div>
  <div id="feature-title">
   <h2>
    <s:text name="title.listContract" />
   </h2>
  </div>
  <div id="feature-content">
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
    <s:text name="title.listContract" />
   </div>
  </div>
 </div>
 <s:include value="../footer.jsp" />
 </div>
</body>
</html>