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
    function save() {
        baseSave("Contract");
	}

	function saveClose() {
		baseSaveClose("Contract");
	}
	
	function cancel() {
		baseCancel("Contract");
	}

	$(document).ready(function() {		
		if ($("#seleteIDs").val()!= ""){
			  $("input:checkbox[name=massUpdate]").css("display",'block');
			  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
		}	
		if ($("#id").val() == ""){
			  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
			  if ($("#seleteIDs").val() == ""){
				     $("#addObjectForm").form('validate');
			  }			  
		}
		$('#id_accountID').combogrid('setValue',
		'<s:property value="accountId"/>');
		$('#id_accountID').combogrid('setText',
		'<s:property value="accountName"/>');
		
		$('#id_ownerID').combogrid('setValue',
		'<s:property value="signedId"/>');
		$('#id_ownerID').combogrid('setText',
		'<s:property value="signedName"/>');

		if ($("#saveFlag").val() == "true"){
			$.messager.show({  
	          title:'<s:text name="message.title" />',  
	          msg:'<s:text name="message.save" />',  
	          timeout:5000,  
	          showType:'slide'  
	      });  
			$("#saveFlag").val("");
	    }	
		
	})
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
            iconCls="icon-save-accept" onclick="save()" plain="true"><s:text
                name="button.save" /></a>
        </span> <span> <span style="white-space: nowrap;"> <a
              id="save_go_btn" href="#" class="easyui-linkbutton"
              iconCls="icon-save-go" onclick="saveClose()" plain="true"><s:text
                  name="button.saveClose" /></a>
          </span> <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="cancel()" plain="true"><s:text
                  name="button.cancel" /></a>
          </span> 
        </span>
      </div>

      <div id="feature-title">
        <s:if test="document!=null && document.id!=null">
          <h2>
           修改合同
          </h2>
        </s:if>
        <s:else>
            <h2>
             	新建合同
            </h2>
        </s:else>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
          method="post" enctype="multipart/form-data">
          <s:hidden id="id" name="contract.id" value="%{contract.id}" />
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

          <table style="" cellspacing="10" cellpadding="0" width="100%">
            <s:actionerror />
            <s:if test="hasFieldErrors()">
              <tr>
                <td align="left" colspan="4"><s:actionerror /> <s:iterator
                    value="fieldErrors" status="st">
                    <s:if test="#st.index  == 0">
                      <s:iterator value="value">
                        <font color="red"> <s:property
                          escape="false" /></font>
                      </s:iterator>
                    </s:if>
                  </s:iterator></td>
              </tr>
            </s:if>
          </table>

          <table style="padding: 10px;" cellspacing="10" cellpadding="0"
            width="100%">
            <tr>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="name" /></td>
              <td class="td-label"><label class="record-label">合同名称：</label></td>
              <td class="td-value"><input name="contract.contractName"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="contract.contractName" />" /></td>
              <td class="td-mass-update"></td>
              <td class="td-label"><label class="record-label">上传合同附件：</label></td>
              <td class="td-value"><s:url id="url"
                  action="downloadContract.action?id=%{contract.id}" />
                <s:a href="%{url}">
                  <s:property value="contract.fileName" />
                </s:a> <s:file name="upload" label="File" /></td>
            </tr>
            <tr>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="owner" /></td>
              <td class="td-label"><label class="record-label">对应的商场：</label></td>
              <td class="td-value">
              
              <s:if test="accountId==null&&id==null">
              <select id="id_accountID"
                class="easyui-combogrid record-value" name="contract.accountId"
                style="width: 180px;"
                data-options="  
					            panelWidth:520,  
					            idField:'id',  
					            textField:'name',  
					            url:'<s:url action="listAccount" namespace="/jsp/crm"/>',
		                        loadMsg: '<s:text name="datagrid.loading" />',
		                        pagination : true,
		                        pageSize: 10,
		                        pageList: [10,30,50],
				                fit: true,
					            mode:'remote',
					            columns:[[  
					                {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
					                {field:'name',title:'<s:text name="entity.name.label" />',width:100},  
					                {field:'detailAddress',title:'地址',width:249},  
					                {field:'assignedToName',title:'指派者',width:100}
					            ]]  
					        ">
              </select>
              </s:if>
              <s:else>
              	<s:property value="accountName" />
              	<input type="hidden" name="contract.accountId" value="<s:property value="accountId" />" />
              </s:else>
              </td>
              <td class="td-mass-update">
              </td>
              <td class="td-label"> <label class="record-label">签订者：</label></td>
              <td class="td-value">
               <s:if test="contract.accountId==null">
              <select id="id_ownerID"
                class="easyui-combogrid record-value" name="contract.signedId"
                style="width: 180px;"
                data-options="  
					            panelWidth:520,  
					            idField:'id',  
					            textField:'name',  
					            url:'<s:url action="listUser" namespace="/jsp/system"/>',
		                        loadMsg: '<s:text name="datagrid.loading" />',
		                        pagination : true,
		                        pageSize: 10,
		                        pageList: [10,30,50],
				                fit: true,
					            mode:'remote',
					            columns:[[  
					                {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
					                {field:'name',title:'<s:text name="entity.name.label" />',width:100},  
					                {field:'title',title:'<s:text name="entity.title.label" />',width:120},  
					                {field:'department',title:'<s:text name="entity.department.label" />',width:100},
					                {field:'status.name',title:'<s:text name="entity.status.label" />',width:100}   
					            ]]  
					        ">
              </select>
              </s:if>
              <s:else>
              		<s:property value="signedName" />
              </s:else>
              </td>
            </tr>
            <tr>
            <td class="td-mass-update">
              </td>
              <td class="td-label"> <label class="record-label">备注：</label></td>
              <td class="td-value" valign="top"><s:textarea
                      name="contract.memo" rows="5" cssStyle="width:350px;" cssClass="record-value" /></td>
                  <td class="td-mass-update"></td>
            </tr>
          </table>
        </s:form>
      </div>
    </div>

    <s:include value="../footer.jsp" />

  </div>
</body>
</html>



