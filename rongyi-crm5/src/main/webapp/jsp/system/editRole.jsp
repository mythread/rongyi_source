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
		disableBtn();
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "saveRole.action";
		addObjectForm.submit();
	}

	function saveClose() {
		disableBtn();
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "saveCloseRole.action";
		addObjectForm.submit();
	}
	
	function cancel() {
		disableBtn();
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "listRolePage.action";
		addObjectForm.submit();
	}

	$(document).ready(function() {
		$('#ownerID').combogrid('setValue', '<s:property value="ownerID"/>');
		$('#ownerID').combogrid('setText', '<s:property value="ownerText"/>');			
		if ($("#id").val() == ""){
		  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
		  $("#addObjectForm").form('validate');
		}
		if ($("#saveFlag").val() == "true"){
			$.messager.show({  
	          title:'<s:text name="message.title" />',  
	          msg:'<s:text name="message.save" />',  
	          timeout:5000,  
	          showType:'slide'  
	      });  
			$("#saveFlag").val("");
	    }	
		
		$("#enable").click(function() {
			$("[id^=view_]").val(1);
			$("[id^=create_]").val(1);
			$("[id^=update_]").val(1);
			$("[id^=delete_]").val(1);
	   	});
		$("#disable").click(function() {
			$("[id^=view_]").val(2);
			$("[id^=create_]").val(2);
			$("[id^=update_]").val(2);
			$("[id^=delete_]").val(2);
	   	});		
		
	})	
</script>
</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <div id="shortcuts" class="headerList">
        <span> <span style="white-space: nowrap;"> <a
            id="save_accept_btn" href="#" class="easyui-linkbutton"
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
        <s:if test="role!=null && role.id!=null">
          <h2>
            <s:text name="title.updateRole" />
          </h2>
        </s:if>
        <s:else>
          <h2>
            <s:text name="title.createRole" />
          </h2>
        </s:else>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true"
          namespace="/jsp/system" method="post">
          <s:hidden id="id" name="role.id" value="%{role.id}" />
          <s:hidden id="saveFlag" name="saveFlag" />
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
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.name.label"></s:text>：</label></td>
              <td class="td-value"><input name="role.name"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="role.name" />" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.owner.label"></s:text>：</label></td>
              <td class="td-value"><select id="ownerID"
                class="easyui-combogrid record-value" name="ownerID"
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
              </select></td>
            </tr>
          </table>

          <div class="easyui-tabs">
            <div title="<s:text name='tab.overview'/>"
              style="padding: 10px;">
              <div id="shortcuts" class="headerList">
                <span style="white-space: nowrap;"> <a
                  id="enable" href="#" class="easyui-linkbutton"
                  iconCls="icon-enable" plain="true"><s:text
                      name="action.enable" /></a>
                </span> <span style="white-space: nowrap;"> <a
                  id="disable" href="#" class="easyui-linkbutton"
                  iconCls="icon-disable" plain="true"><s:text
                      name="action.disable" /></a>
                </span>
              </div>
              <table class="view-table" cellspacing="0" cellpadding="0"
                width="100%" border="0">
                <tr>
                  <th class="view-column"></th>
                  <th class="view-column"><s:text
                      name="access.scope.head" /></th>
                  <th class="view-column"><s:text
                      name="access.view.head" /></th>
                  <th class="view-column"><s:text
                      name="access.create.head" /></th>
                  <th class="view-column"><s:text
                      name="access.update.head" /></th>
                  <th class="view-column"><s:text
                      name="access.delete.head" /></th>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.account.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_account" name="role.scope_account"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_account" name="role.view_account"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_account" name="role.create_account"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_account" name="role.update_account"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_account" name="role.delete_account"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="合同" /></td>
                  <td class="view-column"><s:select
                      id="scope_contract" name="role.scope_contract"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_contract" name="role.view_contract"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_contract" name="role.create_contract"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_contract" name="role.update_contract"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_contract" name="role.delete_contract"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="商家审核" /></td>
                  <td class="view-column"><s:select
                      id="scope_check" name="role.scope_check"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_check" name="role.view_check"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_check" name="role.create_check"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_check" name="role.update_check"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_contract" name="role.delete_check"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.call.label" /></td>
                  <td class="view-column"><s:select id="scope_call"
                      name="role.scope_call" list="scopeMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select id="view_call"
                      name="role.view_call" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_call" name="role.create_call"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_call" name="role.update_call"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_call" name="role.delete_call"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.campaign.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_campaign" name="role.scope_campaign"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_campaign" name="role.view_campaign"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_campaign" name="role.create_campaign"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_campaign" name="role.update_campaign"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_campaign" name="role.delete_campaign"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.caseInstance.label" /></td>
                  <td class="view-column"><s:select id="scope_case"
                      name="role.scope_case" list="scopeMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select id="view_case"
                      name="role.view_case" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_case" name="role.create_case"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_case" name="role.update_case"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_case" name="role.delete_case"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.contact.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_contact" name="role.scope_contact"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_contact" name="role.view_contact"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_contact" name="role.create_contact"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_contact" name="role.update_contact"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_contact" name="role.delete_contact"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.document.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_document" name="role.scope_document"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_document" name="role.view_document"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_document" name="role.create_document"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_document" name="role.update_document"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_document" name="role.delete_document"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.visit.label" /></td>
                  <td class="view-column"><s:select id="scope_visit"
                      name="role.scope_visit" list="scopeMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select id="view_visit"
                      name="role.view_visit" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_visit" name="role.create_visit"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_visit" name="role.update_visit"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_visit" name="role.delete_visit"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.meeting.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_meeting" name="role.scope_meeting"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_meeting" name="role.view_meeting"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_meeting" name="role.create_meeting"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_meeting" name="role.update_meeting"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_meeting" name="role.delete_meeting"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.opportunity.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_opportunity"
                      name="role.scope_opportunity" list="scopeMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_opportunity" name="role.view_opportunity"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_opportunity"
                      name="role.create_opportunity" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_opportunity"
                      name="role.update_opportunity" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_opportunity"
                      name="role.delete_opportunity" list="accessMap"
                      style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.target.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_target" name="role.scope_target"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_target" name="role.view_target"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_target" name="role.create_target"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_target" name="role.update_target"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_target" name="role.delete_target"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.targetList.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_targetList" name="role.scope_targetList"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_targetList" name="role.view_targetList"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_targetList"
                      name="role.create_targetList" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_targetList"
                      name="role.update_targetList" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_targetList"
                      name="role.delete_targetList" list="accessMap"
                      style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.task.label" /></td>
                  <td class="view-column"><s:select id="scope_task"
                      name="role.scope_task" list="scopeMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select id="view_task"
                      name="role.view_task" list="accessMap"
                      style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_task" name="role.create_task"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_task" name="role.update_task"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_task" name="role.delete_task"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
                <tr>
                  <td class="view-column"><s:text
                      name="entity.system.label" /></td>
                  <td class="view-column"><s:select
                      id="scope_system" name="role.scope_system"
                      list="scopeMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="view_system" name="role.view_system"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="create_system" name="role.create_system"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="update_system" name="role.update_system"
                      list="accessMap" style="width:100px;" /></td>
                  <td class="view-column"><s:select
                      id="delete_system" name="role.delete_system"
                      list="accessMap" style="width:100px;" /></td>
                </tr>
              </table>
            </div>
            <div title="<s:text name='tab.details'/>"
              style="padding: 10px;">
              <div class="section-header">
                <span><s:text name="span.description" /></span>
              </div>
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="entity.description.label"></s:text>：</label></td>
                  <td class="td-value" valign="top"><s:textarea
                      name="role.description" rows="20"
                      cssStyle="width:450px;" cssClass="record-value" /></td>
                  <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="entity.notes.label"></s:text>：</label></td>
                  <td class="td-value" valign="top"><s:textarea
                      name="role.notes" rows="20"
                      cssStyle="width:450px;" cssClass="record-value" /></td>
                </tr>
              </table>

              <div class="section-header">
                <span><s:text name="span.system_info" /></span>
              </div>
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.createdBy.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="createdBy" /></label></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.createdOn.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="createdOn" /></label></td>
                </tr>
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.updatedBy.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="updatedBy" /></label></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.updatedOn.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="updatedOn" /></label></td>
                </tr>
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.id.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="id" /></label></td>
                  <td class="td-label"></td>
                  <td class="td-value"></td>
                </tr>
              </table>
            </div>
          </div>

        </s:form>
      </div>
    </div>

    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



