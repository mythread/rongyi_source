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
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript"
  src="../../js/datagrid-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
    function save() {
        baseSave("User");
        $.messager.show({  
	          title:'<s:text name="message.title" />',  
	          msg:'<s:text name="message.save" />',  
	          timeout:5000,  
	          showType:'slide'  
	      }); 
	}

	function saveClose() {
		baseSaveClose("User");
	}
	
	function cancel() {
		baseCancel("User");
	}
	
	  $(document).ready(function(){
		$('#reportToID').combogrid('setValue', '<s:property value="reportToName"/>');
		if ($("#seleteIDs").val()!= ""){
			  $("input:checkbox[name=massUpdate]").css("display",'block');
			  $('#tab').tabs('close', '<s:text name='tab.role'/>');
		}
		
		$('#roleID').combogrid('setValue', '<s:property value="roleID"/>');
		if ($("#seleteIDs").val()!= ""){
			  $("input:checkbox[name=massUpdate]").css("display",'block');
			  $('#tab').tabs('close', '<s:text name='tab.role'/>');
		}	
			
		if ($("#id").val() == ""){
			  $('#tab').tabs('close', '<s:text name='tab.role'/>');
			  if ($("#seleteIDs").val() == ""){
				     $("#addObjectForm").form('validate');
			  }			  
		}
		
		if ($("#saveFlag").val() == "true"){
			alert($("#saveFlag").val());
			$.messager.show({  
	          title:'<s:text name="message.title" />',  
	          msg:'<s:text name="message.save" />',  
	          timeout:5000,  
	          showType:'slide'  
	      });  
			$("#saveFlag").val("");
	    }		

		$("#remove").click(function() {
   		  many_removerow('unselectRole.action?relationKey=User&relationValue=<s:property value="user.id" />&seleteIDs=');
   	    });

        $('#tt').datagrid({
		title:"<s:text name='title.grid.roles'/>",
		iconCls:'icon-save',
		width:700,
		height:350,
		idField:'id', 
		url:'filterUserRole.action?id=<s:property value="user.id" />',
		columns:[[
					{field:'ck',checkbox:true},
					{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
					{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
						   new_format_value = "<a href='editRole.action?id=" + row.id + "' >" + value + "</a>";
						   return new_format_value 
		             }  
		            },
					{field:'description',title:'<s:text name="entity.description.label" />',width:80,align:'center',sortable:'true'}
				]],
	  });
		
    }); 	  
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
        </span><span style="white-space: nowrap;"> <a
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
        <s:if test="user!=null && user.id!=null">
          <h2>
            <s:text name="title.updateUser" />
          </h2>
        </s:if>
        <s:else>
          <s:if test="seleteIDs!=null && seleteIDs!= ''">
            <h2>
              <s:text name="title.massUpdateUser" />
            </h2>
          </s:if>
          <s:else>
            <h2>
              <s:text name="title.createUser" />
            </h2>
          </s:else>
        </s:else>
      </div>

      <div id="feature-content">
      <s:actionmessage/>
        <s:form id="addObjectForm" validate="true"
          namespace="/jsp/system" method="post">
          <s:hidden id="id" name="user.id" value="%{user.id}" />
          <s:hidden id="saveFlag" name="saveFlag" />
          <s:hidden id="seleteIDs" name="seleteIDs" value="%{seleteIDs}" />

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
              <td class="td-label"><label class="record-label"><s:text
                    name="账号"></s:text>：</label></td>
              <td class="td-value"><input name="user.name"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="user.name" />" /></td>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="last_name" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.last_name.label"></s:text>：</label></td>
              <td class="td-value"><input name="user.last_name"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="user.last_name" />" /></td>
            </tr>
            <tr>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="status" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.status.label"></s:text>：</label></td>
              <td class="td-value"><s:select name="statusID"
                  list="statuses" listKey="id" listValue="label"
                  cssClass="record-value" /></td>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="first_name" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.first_name.label"></s:text>：</label></td>
              <td class="td-value"><s:textfield
                  name="user.first_name" cssClass="record-value" /></td>
            </tr>
            <tr>
            <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="role" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="角色"></s:text>：</label></td>
                  <td class="td-value"><select id="roleID"
                    class="easyui-combogrid record-value" name="roleID" 
                    style="width: 180px;"
                    data-options="  
							            panelWidth:520,  
							            idField:'id',  
							            textField:'name',  
							            url:'listRole.action',
							            pagination : true,
							            fit: true,
							            mode:'remote',
							            pageSize: 10,
							            pageList: [10],  
							            columns:[[  
							                {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
							                {field:'name',title:'<s:text name="entity.name.label" />',width:100},  
							            ]]  
							        ">
                  </select></td>
                  
              <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="report_to" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="主管"></s:text>：</label></td>
                  <td class="td-value"><select id="reportToID"
                    class="easyui-combogrid record-value" name="reportToID" 
                    style="width: 180px;"
                    data-options="  
							            panelWidth:520,  
							            idField:'id',  
							            textField:'name',  
							            url:'listUser.action',
							            pagination : true,
							            fit: true,
							            mode:'remote',
							            pageSize: 10,
							            pageList: [10],  
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

          <div id="tab" class="easyui-tabs">
            <div title="<s:text name='tab.overview'/>"
              style="padding: 10px;">
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="password" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="user.password.label"></s:text>：</label></td>
                  <td class="td-value"><input type="password"
                    name="user.password" class="record-value"
                    value="<s:property value="user.password" />" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="title" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.title.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="user.title" cssClass="record-value" /></td>
                </tr>
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="department" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.department.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="user.department" cssClass="record-value" /></td>
                  
                </tr>
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="email" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.email.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="user.email" cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="phone" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="user.phone.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="user.phone" cssClass="record-value" /></td>
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
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="description" /></td>
                  <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="entity.description.label"></s:text>：</label></td>
                  <td class="td-value" valign="top"><s:textarea
                      name="user.description" rows="20"
                      cssStyle="width:450px;" cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="notes" /></td>
                  <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="entity.notes.label"></s:text>：</label></td>
                  <td class="td-value" valign="top"><s:textarea
                      name="user.notes" rows="20"
                      cssStyle="width:450px;" cssClass="record-value" /></td>
                </tr>
                <tr>
                  <td class="td-mass-update"></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.createdBy.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="createdBy" /></label></td>
                  <td class="td-mass-update"></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.createdOn.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="createdOn" /></label></td>
                </tr>
                <tr>
                  <td class="td-mass-update"></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.updatedBy.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="updatedBy" /></label></td>
                  <td class="td-mass-update"></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.updatedOn.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="updatedOn" /></label></td>
                </tr>
                <tr>
                  <td class="td-mass-update"></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.id.label"></s:text>：</label></td>
                  <td class="td-value"><label class="record-value"><s:property
                        value="id" /></label></td>
                  <td class="td-mass-update"></td>
                  <td class="td-label"></td>
                  <td class="td-value"></td>
                </tr>
              </table>
            </div>
            <div title="<s:text name='tab.role'/>" id="roles"
              style="padding: 10px;">
              <div id="shortcuts" class="headerList">
                <span style="white-space: nowrap;"> <a
                  id="remove" href="#" class="easyui-linkbutton"
                  iconCls="icon-remove" plain="true"><s:text
                      name="action.remove" /></a>
                </span> <span style="white-space: nowrap;"> <a
                  id="select" href="#" class="easyui-linkbutton"
                  iconCls="icon-search" plain="true"
                  onClick="openwindow2('/system/selectRolePage.action?relationKey=User&relationValue=<s:property value="user.id" />',750,500)"><s:text
                      name="action.select" /></a>
                </span> 
              </div>
              <s:form id="selectRoleForm" namespace="/jsp/system"
                method="post">
                <table id="tt"></table>
              </s:form>
            </div>

          </div>

        </s:form>
      </div>
    </div>

    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



