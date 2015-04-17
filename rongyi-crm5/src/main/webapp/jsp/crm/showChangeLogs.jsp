<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
  href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />

<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
    $(document).ready(function(){	 
	  $('#tt').datagrid({
		title:"<s:text name='title.grid.changeLogs'/>",
		iconCls:'icon-save',
		width:700,
		height:350,
		pagination:true,
		idField:'id', 
		url:'listChangeLog.action?entity=<s:property value="entity"/>&recordID=<s:property value="recordID"/>',
		columns:[[
				{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},            
				{field:'columnName',title:'<s:text name="changeLog.columnName.label" />',width:80,align:'center',sortable:'true'},
				{field:'oldValue',title:'<s:text name="changeLog.oldValue.label" />',width:80,align:'center',sortable:'true'},
				{field:'newValue',title:'<s:text name="changeLog.newValue.label" />',width:80,align:'right',sortable:'true'},
				{field:'updatedBy',title:'<s:text name="entity.updatedBy.label" />',width:80,align:'center',sortable:'true'},
				{field:'updatedOn',title:'<s:text name="entity.updatedOn.label" />',width:120,align:'center',sortable:'true'}
		]],
	  });
		
    }); 
  </script>
</head>
<body>
  <div id="page-wrap">
    <div id="feature">
      <div id="feature-title">
        <h2>
          <s:text name="entity.changeLog.label" />
        </h2>
      </div>
      <div id="feature-content">
        <div id="tb" style="padding: 5px; height: auto">
          <div>
            <input id="filter_key" class="easyui-combobox"
              name="filter_key" style="width: 90px;"
              data-options="
				        required:true,valueField:'value',textField:'label',
						data: [{
							label: '<s:text name="entity.id.label" />',
							value: 'id',
							selected: true 
						},{label: '<s:text name="changeLog.columnName.label" />',
							value: 'columnName'
						},{label: '<s:text name="changeLog.oldValue.label" />',
							value: 'oldValue'
						},{label: '<s:text name="changeLog.newValue.label" />',
							value: 'newValue'
						},{label: '<s:text name="entity.updatedBy.label" />',
							value: 'updatedBy'
						},{label: '<s:text name="entity.updatedOn.label" />',
							value: 'updatedOn'																					
						}]" />

            <input id="filter_op" class="easyui-combobox"
              name="filter_op" style="width: 40px;"
              data-options="valueField:'value',textField:'label',
						data: [{
							label: '<s:text name="filter.oper.equal" />',
							value: '=',
							selected: true 
						},{label: '<s:text name="filter.oper.notequal" />',
							value: '<>'
						},{label: '<s:text name="filter.oper.less" />',
							value: '<'
						},{label: '<s:text name="filter.oper.lessequal" />',
							value: '<='
						},{label: '<s:text name="filter.oper.greater" />',
							value: '>'		
						},{label: '<s:text name="filter.oper.greaterequal" />',
							value: '>='									
						},{label: '<s:text name="filter.oper.like" />',
							value: 'like'															
						}]" />
            <input id="filter_value"
              style="line-height: 20px; border: 1px solid #ccc" /> <a
              href="#" class="easyui-linkbutton" iconCls="icon-search"
              onclick="doSearch()" plain="true"><s:text
                name="button.search" /></a> <a href="#"
              class="easyui-linkbutton" iconCls="icon-reload"
              onclick="reset()" plain="true"><s:text
                name="button.reset" /></a>
          </div>
        </div>
        <s:form id="addObjectForm" namespace="/jsp/crm" method="post">
          <s:hidden name="entity" id="entity" value="%{entity}" />
          <s:hidden name="recordID" id="recordID" value="%{recordID}" />
          <table id="tt"></table>
        </s:form>
      </div>
    </div>
  </div>
</body>
</html>



