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
		$('#tt').edatagrid({
			url : 'listCurrency.action',
			saveUrl : 'saveCurrency.action',
			updateUrl : 'saveCurrency.action',
			destroyUrl : 'deleteCurrency.action'
		});
	    $("#delete").click(function() {	
	    	many_deleterow_easyui("deleteCurrency.action?seleteIDs=");
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
          <s:text name="title.grid.currency" />
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

        <table id="tt" title="<s:text name='title.grid.currency'/>"
          style="width: 700px; height: 380px" toolbar="#toolbar"
          pagination="true" rownumbers="true" fitColumns="true"
          singleSelect="false">
          <thead>
            <tr>
              <th data-options="field:'ck',checkbox:true"></th>
              <th field="id" width="1" hidden="true"><s:text
                  name='entity.id.label' /></th>
              <th field="currency.id" width="20"><s:text
                  name='entity.id.label' /></th>
              <th field="currency.name" width="50"
                editor="{type:'validatebox',options:{required:true}}"><s:text
                  name='entity.name.label' /></th>
              <th field="currency.code" width="50"
                editor="{type:'text'}"><s:text
                  name='currency.code.label' /></th>
              <th field="currency.rate" width="50"
                editor="{type:'numberbox',options:{required:true}}"><s:text
                  name='currency.rate.label' /></th>
              <th field="currency.symbol" width="50"
                editor="{type:'text',options:{required: true,missingMessage: 'please input'}}"><s:text
                  name='currency.symbol.label' /></th>
              <th field="currency.status" width="50"
                editor="{type:'text'}"><s:text
                  name='entity.status.label' /></th>
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

      <s:include value="../footer.jsp" />
    </div>
  </div>
</body>
</html>



