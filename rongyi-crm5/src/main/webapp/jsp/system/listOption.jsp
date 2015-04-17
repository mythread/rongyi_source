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
<script type="text/javascript" src="../../js/jquery.edatagrid.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>


<script type="text/javascript">
	$(function() {
		var entityName = '<%=(String)request.getAttribute("entityName")%>';
		$('#tt').edatagrid({
			url : 'list' + entityName + '.action',
			saveUrl : 'save' + entityName + '.action',
			updateUrl : 'save' + entityName + '.action',
			destroyUrl : 'delete' + entityName + '.action'
		});
	    $("#delete").click(function() {	
	    	many_deleterow_easyui('delete' + entityName + '.action?seleteIDs=');
		});		
	});
</script>
</head>
<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
      <div id="feature-title">
        <h2>
          <s:property value="#request.title" />
        </h2>
      </div>
      <div id="feature-content">
        <table style="" cellspacing="10" cellpadding="0" width="100%">
          <s:if test="hasActionErrors()">
            <tr>
              <td align="left" colspan="4"><font color="red">
                <s:actionerror /></font></td>
            </tr>
          </s:if>
        </table>

        <table id="tt" title='<s:property value="#request.title"/>'
          style="width: 700px; height: 380px" toolbar="#toolbar"
          pagination="true" rownumbers="true" fitColumns="true"
          singleSelect="false">
          <thead>
            <tr>
              <th data-options="field:'ck',checkbox:true"></th>
              <th field="id" width="1" hidden="true"><s:text
                  name='entity.id.label' /></th>
              <th field="entity.id" width="10"><s:text
                  name='entity.id.label' /></th>
              <th field="entity.value" width="50"
                editor="{type:'validatebox',options:{required:true}}">
                <s:text name='entity.value.label' /> <s:if
                  test="#request.entityName == 'ReminderOption'">
                  <s:text name='entity.unit.label' />
                </s:if>
              </th>
              <th field="entity.label_en_US" width="50" editor="text"><s:text
                  name='entity.label_en_US.label' /></th>
              <th field="entity.label_zh_CN" width="50" editor="text"><s:text
                  name='entity.label_zh_CN.label' /></th>
              <th field="entity.sequence" width="50"
                editor="{type:'numberbox',options:{precision:0}}"><s:text
                  name='entity.sequence.label' /></th>
            </tr>
          </thead>
        </table>
        <div id="toolbar">
          <s:if test="#session.loginUser.create_system == 1">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add"
              plain="true"
              onclick="javascript:$('#tt').edatagrid('addRow')"><s:text
                name='button.create' /></a>
          </s:if>
          <s:if test="#session.loginUser.delete_system == 1">
            <a id="delete" href="#" class="easyui-linkbutton"
              iconCls="icon-remove" plain="true"><s:text
                name='button.delete' /></a>
          </s:if>
          <s:if
            test="#session.loginUser.create_system == 1 || #session.loginUser.update_system == 1">
            <a href="#" class="easyui-linkbutton" iconCls="icon-save"
              plain="true"
              onclick="javascript:$('#tt').edatagrid('saveRow')"><s:text
                name='button.save' /></a>
          </s:if>
          <a href="#" class="easyui-linkbutton" iconCls="icon-undo"
            plain="true"
            onclick="javascript:$('#tt').edatagrid('cancelRow')"><s:text
              name='button.cancel' /></a>
        </div>
      </div>
    </div>
    <s:include value="../footer.jsp" />

  </div>
</body>
</html>



