<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page language="java" import="com.gcrm.domain.Target"%>

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
    	$('#convert_btn').linkbutton('disable');
        $("input[name='target.other_street']").removeAttr("disabled"); 
        $("input[name='target.other_city']").removeAttr("disabled"); 
        $("input[name='target.other_state']").removeAttr("disabled"); 
        $("input[name='target.other_postal_code']").removeAttr("disabled"); 
        $("input[name='target.other_country']").removeAttr("disabled");     	
        baseSave("Target");
	}

	function saveClose() {
    	$('#convert_btn').linkbutton('disable');
        $("input[name='target.other_street']").removeAttr("disabled"); 
        $("input[name='target.other_city']").removeAttr("disabled"); 
        $("input[name='target.other_state']").removeAttr("disabled"); 
        $("input[name='target.other_postal_code']").removeAttr("disabled"); 
        $("input[name='target.other_country']").removeAttr("disabled");     	
		baseSaveClose("Target");
	}
	
	function cancel() {
		baseCancel("Target");
	}

	function convert(){
		$('#convert_btn').linkbutton('disable');
		disableBtn();
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = 'convertTarget.action';
		addObjectForm.submit();
	}
	  
function copyAddress(){
	if ($('#copy_checkbox').attr('checked')) { 
		$("input[name='target.other_street']").attr("value",$("input[name='target.primary_street']").val());	
		$("input[name='target.other_street']").attr("disabled","disabled"); 
		$("input[name='target.other_city']").attr("value",$("input[name='target.primary_city']").val());
		$("input[name='target.other_city']").attr("disabled","disabled");
		$("input[name='target.other_state']").attr("value",$("input[name='target.primary_state']").val());
		$("input[name='target.other_state']").attr("disabled","disabled");
		$("input[name='target.other_postal_code']").attr("value",$("input[name='target.primary_postal_code']").val());
		$("input[name='target.other_postal_code']").attr("disabled","disabled");
		$("input[name='target.other_country']").attr("value",$("input[name='target.primary_country']").val());
		$("input[name='target.other_country']").attr("disabled","disabled");
	} else {
		$("input[name='target.other_street']").removeAttr("disabled"); 
		$("input[name='target.other_city']").removeAttr("disabled"); 
		$("input[name='target.other_state']").removeAttr("disabled"); 
		$("input[name='target.other_postal_code']").removeAttr("disabled"); 
		$("input[name='target.other_country']").removeAttr("disabled"); 	
	}	
  }
  
$(document).ready(function(){

	$('#accountID').combogrid('setValue', '<s:property value="accountID"/>');
	$('#ownerID').combogrid('setValue', '<s:property value="ownerID"/>');
	$('#ownerID').combogrid('setText', '<s:property value="ownerText"/>');		
	$('#assignedToID').combogrid('setValue', '<s:property value="assignedToID"/>');
	$('#assignedToID').combogrid('setText', '<s:property value="assignedToText"/>');
	if ($("#seleteIDs").val()!= ""){
		  $("input:checkbox[name=massUpdate]").css("display",'block');
		  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
	}	
	if ($("#id").val() == ""){
		  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
		  if ($("#seleteIDs").val() == ""){
			    $("#last_name").focus();
		  }			  
	}
	
	if ($("#saveFlag").val() == "true"){
		$.messager.show({  
          title:'<s:text name="message.title" />',  
          msg:'<s:text name="message.save" />',  
          timeout:5000,  
          showType:'slide'  
        })
     } else if ($("#saveFlag").val() == "<%=Target.STATUS_CONVERTED%>"){
    	 $.messager.show({  
             title:'<s:text name="message.title" />',  
             msg:'<s:text name="message.convert" />',  
             timeout:5000,  
             showType:'slide'  
        })
     };  
	$("#saveFlag").val("");		
	
	$('#tt').tabs({
		onSelect:function(title){
	        if (title == "<s:text name='tab.relations'/>"){
				 var params = {
				   id : $("#id").val()
				 };
				  $.ajax({
				    type: "POST",
				    url: "getTargetRelationCounts.action",
				    data: params,
				    dataType:"text", 
				    success: function(json){  
				      var obj = $.parseJSON(json);  
				      $("#taskNumber").html(obj.taskNumber); 
				    },
				    error: function(json){
				      alert("json=" + json);
				      return false;
				    }
				  });				        	
	        }
	    }
	});	
	})
</script>

</head>
<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
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
          </span><span style="white-space: nowrap;"> <a id="convert_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-edit"
              onclick="convert()" plain="true"><s:text
                  name="button.convertToLead" /></a>
          </span> <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="cancel()" plain="true"><s:text
                  name="button.cancel" /></a>
          </span> <s:if test="target!=null && target.id!=null">
              <span style="white-space: nowrap;"><a
                href="javascript:void(0)" id="mtmt"
                class="easyui-menubutton"
                data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
                    name='menu.toolbar.more.title' /></a>
                <div id="mtm1" style="width: 150px;">
                  <div data-options="iconCls:'icon-import'"
                    onClick="openwindow2('/crm/showChangeLogPage.action?entity=Target&recordID=' + '<s:property value="target.id" />',750,500)">
                    <s:text name='menu.item.changeLog.title' />
                  </div>
                </div> </span>
            </s:if>
        </span>
      </div>

      <div id="feature-title">
        <s:if test="target!=null && target.id!=null">
          <h2>
            <s:text name="title.updateTarget" />
          </h2>
        </s:if>
        <s:else>
          <s:if test="seleteIDs!=null && seleteIDs!= ''">
            <h2>
              <s:text name="title.massUpdateTarget" />
            </h2>
          </s:if>
          <s:else>
            <h2>
              <s:text name="title.createTarget" />
            </h2>
          </s:else>
        </s:else>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
          method="post">
          <s:hidden id="id" name="target.id" value="%{target.id}" />
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
                value="salutation" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="menu.salutation.title"></s:text>：</label></td>
              <td class="td-value"><s:select name="salutationID"
                  list="salutations" listKey="id" listValue="label"
                  cssClass="record-value" /></td>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="first_name" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.first_name.label"></s:text>：</label></td>
              <td class="td-value"><s:textfield
                  name="target.first_name" cssClass="record-value" /></td>
            </tr>

            <tr>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="last_name" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.last_name.label"></s:text>：</label></td>
              <td class="td-value"><input id="last_name"
                name="target.last_name"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="target.last_name" />" /></td>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="company" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.company.label"></s:text>：</label></td>
              <td class="td-value"><s:textfield
                  name="target.company" cssClass="record-value" /></td>
            </tr>

            <tr>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="title" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.title.label"></s:text>：</label></td>
              <td class="td-value"><s:textfield name="target.title"
                  cssClass="record-value" /></td>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="owner" /></td>
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
            <s:if test="lead!=null">
              <tr>
                <td class="td-mass-update"></td>
                <td class="td-label"><label class="record-label"><s:text
                      name="target.converted_lead.label"></s:text>：</label></td>
                <td class="td-value"><a
                  href="<s:url value='editLead.action?id=%{lead.id}'/>"><s:property
                      value="lead.name" /></a></td>
                <td class="td-mass-update"></td>
                <td class="td-label"></label></td>
                <td class="td-value"></td>
              </tr>
            </s:if>
          </table>

          <div id="tt" class="easyui-tabs">
            <div title="<s:text name='tab.overview'/>"
              style="padding: 10px;">
              <div class="section-header">
                <span><s:text name="span.contact" /></span>
              </div>
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="email" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.email.label"></s:text>：</label></td>
                  <td class="td-value"><input name="target.email"
                    class="easyui-validatebox record-value"
                    data-options="validType:'email'"
                    value="<s:property value="target.email" />" /></td>

                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="office_phone" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.office_phone.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.office_phone" cssClass="record-value" /></td>
                </tr>
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="fax" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.fax.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.fax" cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="mobile" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.mobile.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.mobile" cssClass="record-value" /></td>
                </tr>
              </table>

              <div class="section-header">
                <span><s:text name="span.primary_address" /></span>
              </div>
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="primary_street" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.street.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.primary_street"
                      cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="primary_city" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.city.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.primary_city" cssClass="record-value" /></td>
                </tr>

                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="primary_state" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.state.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.primary_state"
                      cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="primary_postal_code" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.postal_code.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.primary_postal_code"
                      cssClass="record-value" /></td>
                </tr>

                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="primary_country" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.country.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.primary_country"
                      cssClass="record-value" /></td>
                  <td class="td-mass-update"></td>
                  <td class="td-label"></td>
                  <td class="td-value"></td>
                </tr>
              </table>

              <div class="section-header">
                <span> <s:text name="span.shipping_address" /></span>
              </div>
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="other_street" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.street.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.other_street" cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="other_city" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.city.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.other_city" cssClass="record-value" /></td>
                </tr>

                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="other_state" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.state.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.other_state" cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="other_postal_code" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.postal_code.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.other_postal_code"
                      cssClass="record-value" /></td>
                </tr>

                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="other_country" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.country.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.other_country"
                      cssClass="record-value" /></td>
                  <td class="td-mass-update"></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="target.copyAddress.label" />：</label></td>
                  <td class="td-value"><input id="copy_checkbox"
                    name="copy_checkbox" type="checkbox"
                    class="record-value" onclick="copyAddress();" /></td>
                </tr>
              </table>

              <div class="section-header">
                <span><s:text name="span.other_info" /></span>
              </div>
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="department" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.department.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="target.department" cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="account" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.account.label"></s:text>：</label></td>
                  <td class="td-value"><select id="accountID"
                    class="easyui-combogrid record-value"
                    name="accountID" style="width: 180px;"
                    data-options="  
						            panelWidth:500,  
						            idField:'id',  
						            textField:'name',  
						            url:'listAccount.action',
						            fit: true,
						            mode:'remote',
						            columns:[[  
                                      {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
                                      {field:'name',title:'<s:text name="entity.name.label" />',width:100},  
                                      {field:'office_phone',title:'<s:text name="entity.office_phone.label" />',width:120},  
                                      {field:'email',title:'<s:text name="entity.email.label" />',width:100},
                                      {field:'bill_street',title:'<s:text name="entity.billing_street.label" />',width:100},
                                      {field:'bill_city',title:'<s:text name="entity.billing_city.label" />',width:100},
                                      {field:'bill_state',title:'<s:text name="entity.billing_state.label" />',width:100},
                                      {field:'bill_country',title:'<s:text name="entity.billing_country.label" />',width:100},
                                      {field:'bill_postal_code',title:'<s:text name="entity.billing_postal_code.label" />',width:100},
                                      {field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:100} 
						            ]]  
						        ">
                  </select></td>
                </tr>
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="assigned_to" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.assigned_to.label"></s:text>：</label></td>
                  <td class="td-value"><select id="assignedToID"
                    class="easyui-combogrid record-value"
                    name="assignedToID" style="width: 180px;"
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
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="email" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.not_call.label"></s:text>：</label></td>
                  <td class="td-value"><s:checkbox
                      id="target.not_call" name="target.not_call"
                      cssClass="record-value" /></td>
                </tr>
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="notes" /></td>
                  <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="entity.notes.label"></s:text>：</label></td>
                  <td class="td-value" valign="top"><s:textarea
                      name="target.notes" rows="5" cssStyle="width:350px;" cssClass="record-value" /></td>
                  <td class="td-mass-update"></td>
                  <td class="td-label"></td>
                  <td class="td-value"></td>                         
                </tr>                  
              </table>
            </div>

            <div title="<s:text name='tab.details'/>"
              style="padding: 10px;">
              <div class="section-header">
                <span><s:text name="span.system_info" /></span>
              </div>
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
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

            <div title="<s:text name='tab.relations'/>"
              style="padding: 10px;">
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td width="20%" valign="top">
                    <div class="easyui-accordion" style="width: 200px;">
                      <div
                        title="<s:text name="menu.activities.title"/>"
                        style="overflow: auto; padding: 10px;">
                        <a
                          href="filterTaskPage.action?filter_key=related_record&id=<s:property value="target.id" />&moreFilterKey=relationKey&moreFilterValue=Target&createKey=relationValue&removeKey=Target"
                          target="contentFrame"><label
                          class="record-value menuLink"><s:text
                              name="menu.tasks.title" />(<span
                            id="taskNumber"></span>)</label></a>
                      </div>
                    </div>
                  </td>
                  <td width="80%" valign="top"><Iframe
                      name="contentFrame" id="contentFrame"
                      scrolling="no" frameborder="0" width="100%"
                      height="390"></iframe></td>
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



