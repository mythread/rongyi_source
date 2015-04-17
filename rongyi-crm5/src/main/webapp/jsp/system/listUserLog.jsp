<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.gcrm.util.DateTimeUtil"%>
<%@ page language="java" import="com.gcrm.domain.User"%>
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
		  many_deleterow("/crm/deleteTask.action?seleteIDs=");
	  });	

	  $("#massUpdate").click(function() {	
		  many_massUpdaterow("/crm/editTask.action?seleteIDs=");
	  });
	  
	  $("#export").click(function() {	
		  many_exportrow("/crm/exportTask.action?seleteIDs=");
	  });

	  $("#copy").click(function() {	
		  many_copyrow("/crm/copyTask.action?seleteIDs=");
	  });
	  	  	  
	  var mygrid = jQuery("#grid").jqGrid({
			datatype: "json", 
			url:'listUserLogFull.action', 
			mtype: 'POST',
			height: "auto",
		   	colNames:['<s:text name="entity.id.label" />','<s:text name="姓名" />',
		  		   	'<s:text name="登录时间" />','<s:text name="登录次数" />',
		  		   	'<s:text name="IP" />',
		  		   	],
		   	colModel:[
		   		{name:'id',index:'id', width:120, key: true,sorttype:"int",resizable:true, hidden:true},
		   		{name:'name',index:'name', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'loginOn',index:'loginOn', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'count',index:'count', width:150, resizable:true, formatter:urlFmatter},
		   		{name:'ip',index:'ip', width:150, resizable:true, formatter:urlFmatter},
		   	],
		   	pager: 'pager', 
		   	imgpath: 'image/images', 
		   	rowNum:15, 
		   	viewrecords: true, 
		   	rowList:[15,50,100], 
		   	multiselect: true, 
		   	caption: "<s:text name='登录记录'/>"
		});
	  function urlFmatter (cellvalue, options, rowObject)
		{  
		   var par='<%=((User)session.getAttribute("loginUser")).getUpdate_system()%>';
		   if (par == 1){
		     new_format_value = "<a href='editUser.action?id=" + rowObject[0] + "'>" + cellvalue + "</a>";
		   }else {
			 new_format_value = cellvalue;
		   }			
		   return new_format_value
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
		
	}); 
    
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
		jQuery("#grid").jqGrid('setGridParam',{url:"listUserLogFull.action?" + param,page:1}).trigger("reloadGrid");
	}

  </script>
</head>
<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
      <div id="feature-content">
      <%-- <s:form>
      	<table>
      		<tr>
      				<th>用户名:</th>
				<td>
					<input  name="name" type="text"  placeholder="支持模糊查询" style="width:250px;height:100%" />
				</td>
					<th >登录时间:</th>
				<td >
					<input  name="LoginDate" type="text" class="easyui-datebox" />
      		</tr>
      		<tr>
				<td colspan="2">
				<input type="button" value="查询" id="id_search1" style="margin-left:40px;width:80px" onclick="doSearch()" />
				</td>
			</tr>
      	</table>
      </s:form> --%>
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
          <s:text name="登录记录" />
        </div>
      </div>
    </div>

    <s:include value="../footer.jsp" />

  </div>
</body>
</html>



