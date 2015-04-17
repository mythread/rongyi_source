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
        baseSave("Task");
	}

	function saveClose() {
		baseSaveClose("Task");
	}
	
	function cancel() {
		baseCancel("Task");
	}

	function checkRelatedObject() {
		var relatedObject = $('#relatedObject').children('option:selected')
				.val();
		if (relatedObject == 'Account') {
			$("span[id^='span']").css("display", "none");
			$('#spanAccount').css("display", "block");
		} else if (relatedObject == 'Case') {
			$("span[id^='span']").css("display", "none");
			$('#spanCase').css("display", "block");
		} else if (relatedObject == 'Contact') {
			$("span[id^='span']").css("display", "none");
			$('#spanContact').css("display", "block");
		} else if (relatedObject == 'Lead') {
			$("span[id^='span']").css("display", "none");
			$('#spanLead').css("display", "block");
		} else if (relatedObject == 'Opportunity') {
			$("span[id^='span']").css("display", "none");
			$('#spanOpportunity').css("display", "block");
		} else if (relatedObject == 'Target') {
			$("span[id^='span']").css("display", "none");
			$('#spanTarget').css("display", "block");
		} else if (relatedObject == 'Task') {
			$("span[id^='span']").css("display", "none");
			$('#spanTask').css("display", "block");
		}
	}

	$(document).ready(function() {
		$('#ownerID').combogrid('setValue', '<s:property value="ownerID"/>');
		$('#ownerID').combogrid('setText', '<s:property value="ownerText"/>');			
		$('#contactID').combogrid('setValue', '<s:property value="contactID"/>');
		$('#contactID').combogrid('setText', '<s:property value="contactText"/>');		
		$("#relatedObject ").val('<s:property value="task.related_object"/>');
		$('#relatedAccountID').combogrid('setValue', '<s:property value="relatedAccountID"/>');
		$('#relatedAccountID').combogrid('setText', '<s:property value="relatedAccountText"/>');
		$('#relatedCaseID').combogrid('setValue', '<s:property value="relatedCaseID"/>');
		$('#relatedCaseID').combogrid('setText', '<s:property value="relatedCaseText"/>');
		$('#relatedContactID').combogrid('setValue', '<s:property value="relatedContactID"/>');
		$('#relatedContactID').combogrid('setText', '<s:property value="relatedContactText"/>');
		$('#relatedLeadID').combogrid('setValue', '<s:property value="relatedLeadID"/>');
		$('#relatedLeadID').combogrid('setText', '<s:property value="relatedLeadText"/>');
		$('#relatedOpportunityID').combogrid('setValue', '<s:property value="relatedOpportunityID"/>');
		$('#relatedOpportunityID').combogrid('setText', '<s:property value="relatedOpportunityText"/>');
		$('#relatedTargetID').combogrid('setValue', '<s:property value="relatedTargetID"/>');
		$('#relatedTargetID').combogrid('setText', '<s:property value="relatedTargetText"/>');
		$('#relatedTaskID').combogrid('setValue', '<s:property value="relatedTaskID"/>');	
		$('#relatedTaskID').combogrid('setText', '<s:property value="relatedTaskText"/>');		
		$('#assignedToID').combogrid('setValue', '<s:property value="assignedToID"/>');
		$('#assignedToID').combogrid('setText', '<s:property value="assignedToText"/>');		
		$('#startDate').datebox('setValue', '<s:property value="startDate"/>');
		$('#dueDate').datebox('setValue', '<s:property value="dueDate"/>');
		$('#relatedObject').change(function() {
			checkRelatedObject();
		});
		checkRelatedObject();
		if ($("#seleteIDs").val()!= ""){
			  $("input:checkbox[name=massUpdate]").css("display",'block');
		}
		if ($("#id").val() == ""){
			  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
			  if ($("#seleteIDs").val() == ""){
				     $("#addObjectForm").form('validate');
			  }			  
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
          <!-- <s:if test="task!=null && task.id!=null">
              <span style="white-space: nowrap;"><a
                href="javascript:void(0)" id="mtmt"
                class="easyui-menubutton"
                data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
                    name='menu.toolbar.more.title' /></a>
                <div id="mtm1" style="width: 150px;">
                  <div data-options="iconCls:'icon-import'"
                    onClick="openwindow2('/crm/showChangeLogPage.action?entity=Task&recordID=' + '<s:property value="task.id" />',750,500)">
                    <s:text name='menu.item.changeLog.title' />
                  </div>
                </div> </span>
            </s:if> --> 
        </span>
      </div>

      <div id="feature-title">
        <s:if test="task!=null && task.id!=null">
          <h2>
            <s:text name="title.updateTask" />
          </h2>
        </s:if>
        <s:else>
          <s:if test="seleteIDs!=null && seleteIDs!= ''">
            <h2>
              <s:text name="title.massUpdateTask" />
            </h2>
          </s:if>
          <s:else>
            <h2>
              <s:text name="title.createTask" />
            </h2>
          </s:else>
        </s:else>
      </div>


      <div id="feature-content">
        <s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
          method="post">
          <s:hidden id="id" name="task.id" value="%{task.id}" />
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
                value="subject" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.subject.label"></s:text>：</label></td>
              <td class="td-value"><input name="task.subject"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="task.subject" />" /></td>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="status" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.status.label"></s:text>：</label></td>
              <td class="td-value"><s:select name="statusID"
                  list="statuses" listKey="id" listValue="label"
                  cssClass="record-value" /></td>
            </tr>
            <tr>
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="owner" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.owner.label"></s:text>：</label></td>
              <td class="td-value"><select id="ownerID"
                class="easyui-combogrid record-value" name="ownerID"
                style="width: 180;"
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
              <td class="td-mass-update"></td>
              <td class="td-label"></td>
              <td class="td-value"></td>
            </tr>
          </table>

          <div id="tt" class="easyui-tabs">
            <div title="<s:text name='tab.overview'/>"
              style="padding: 10px;">
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="start_date" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.start_date.label"></s:text>：</label></td>
                  <td class="td-value"><input id="startDate"
                    name="startDate" type="text"
                    class="easyui-datetimebox record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="due_date" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="task.due_date.label"></s:text>：</label></td>
                  <td class="td-value"><input id="dueDate"
                    name="dueDate" type="text"
                    class="easyui-datetimebox record-value" /></td>
                </tr>

                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="related_object" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.related_object.label"></s:text>：</label></td>
                  <td class="td-value"><select id="relatedObject"
                    name="task.related_object" style="width: 150px;">
                      <option value="Account">
                        <s:text name="entity.account.label" />
                      </option>
                      <option value="Case">
                        <s:text name="entity.caseInstance.label" />
                      </option>
                      <option value="Contact">
                        <s:text name="entity.contact.label" />
                      </option>
                      <option value="Opportunity">
                        <s:text name="entity.opportunity.label" />
                      </option>
                      <option value="Target">
                        <s:text name="entity.target.label" />
                      </option>
                      <option value="Task">
                        <s:text name="entity.task.label" />
                      </option>
                  </select></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="related_record" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.related_record.label"></s:text>：</label></td>
                  <td class="td-value"><span id="spanAccount">
                      <select id="relatedAccountID"
                      class="easyui-combogrid record-value"
                      name="relatedAccountID" style="width: 180px;"
                      data-options="  
							            panelWidth:520,  
							            idField:'id',  
							            textField:'name',  
							            url:'listAccount.action',
							            loadMsg: '<s:text name="datagrid.loading" />',
							            pagination : true,
							            pageSize: 10,
							            pageList: [10,30,50],
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
                    </select>
                  </span> <span id="spanCase"> <select
                      id="relatedCaseID"
                      class="easyui-combogrid record-value"
                      name="relatedCaseID" style="width: 180px;"
                      data-options="  
						            panelWidth:520,  
						            idField:'id',  
						            textField:'subject',  
						            url:'listCase.action',
						            loadMsg: '<s:text name="datagrid.loading" />',
						            pagination : true,
						            pageSize: 10,
						            pageList: [10,30,50],
								    fit: true,
						            mode:'remote',
						            columns:[[  
						                {field:'id',title:'<s:text name="entity.id.label" />',width:60,align:'center',sortable:'true'},  
						                {field:'subject',title:'<s:text name="entity.subject.label" />',width:100,align:'center',sortable:'true'},  
										{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'center',sortable:'true'},
										{field:'priority.name',title:'<s:text name="entity.priority.label" />',width:80,align:'right',sortable:'true'},
										{field:'status.name',title:'<s:text name="entity.status.label" />',width:80,align:'center',sortable:'true'},
										{field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'}
						            ]]  
						        ">
                    </select>
                  </span> <span id="spanContact"> <select
                      id="relatedContactID"
                      class="easyui-combogrid record-value"
                      name="relatedContactID" style="width: 180px;"
                      data-options="  
						            panelWidth:520,  
						            idField:'id',  
						            textField:'name',  
						            url:'listContact.action',
						            loadMsg: '<s:text name="datagrid.loading" />',
						            pagination : true,
						            pageSize: 10,
						            pageList: [10,30,50],
								    fit: true,
						            mode:'remote',
						            columns:[[  
										{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
										{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true'},
										{field:'title',title:'<s:text name="entity.title.label" />',width:80,align:'center',sortable:'true'},
										{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'right',sortable:'true'},
										{field:'email',title:'<s:text name="entity.email.label" />',width:80,align:'center',sortable:'true'},
										{field:'office_phone',title:'<s:text name="entity.office_phone.label" />',width:80,align:'center',sortable:'true'},
										{field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'}
						            ]]  
						        ">
                    </select>
                  </span> <span id="spanLead"> <select
                      id="relatedLeadID"
                      class="easyui-combogrid record-value"
                      name="relatedLeadID" style="width: 180px;"
                      data-options="  
						            panelWidth:520,  
						            idField:'id',  
						            textField:'name',  
						            url:'listLead.action',
						            loadMsg: '<s:text name="datagrid.loading" />',
						            pagination : true,
						            pageSize: 10,
						            pageList: [10,30,50],
								    fit: true,
						            mode:'remote',
						            columns:[[  
										{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
										{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true'},
										{field:'title',title:'<s:text name="entity.title.label" />',width:80,align:'center',sortable:'true'},
										{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'right',sortable:'true'},
										{field:'office_phone',title:'<s:text name="entity.office_phone.label" />',width:80,align:'center',sortable:'true'},
										{field:'email',title:'<s:text name="entity.email.label" />',width:80,align:'center',sortable:'true'},
										{field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'}
						            ]]  
						        ">
                    </select>
                  </span> </span> <span id="spanOpportunity"> <select
                      id="relatedOpportunityID"
                      class="easyui-combogrid record-value"
                      name="relatedOpportunityID" style="width: 180px;"
                      data-options="  
						            panelWidth:520,  
						            idField:'id',  
						            textField:'name',  
						            url:'listOpportunity.action',
						            loadMsg: '<s:text name="datagrid.loading" />',
						            pagination : true,
						            pageSize: 10,
						            pageList: [10,30,50],
								    fit: true,
						            mode:'remote',
						            columns:[[  
										{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
										{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true'},
										{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'center',sortable:'true'},
										{field:'sales_stage.name',title:'<s:text name="menu.salesStage.title" />',width:80,align:'right',sortable:'true'},
										{field:'opportunity_amount',title:'<s:text name="opportunity.opportunity_amount.label" />',width:80,align:'center',sortable:'true'},
										{field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'}
						            ]]  
						        ">
                    </select>
                  </span> <span id="spanTarget"> <select
                      id="relatedTargetID"
                      class="easyui-combogrid record-value"
                      name="relatedTargetID" style="width: 180px;"
                      data-options="  
						            panelWidth:520,  
						            idField:'id',  
						            textField:'name',  
						            url:'listTarget.action',
						            loadMsg: '<s:text name="datagrid.loading" />',
						            pagination : true,
						            pageSize: 10,
						            pageList: [10,30,50],
								    fit: true,
						            mode:'remote',
						            columns:[[  
										{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
										{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true'},
										{field:'title',title:'<s:text name="entity.title.label" />',width:80,align:'center',sortable:'true'},
										{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'right',sortable:'true'},
										{field:'office_phone',title:'<s:text name="entity.office_phone.label" />',width:80,align:'center',sortable:'true'},
										{field:'email',title:'<s:text name="entity.email.label" />',width:80,align:'center',sortable:'true'},
										{field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'}
						            ]]  
						        ">
                    </select>
                  </span> <span id="spanTask"> <select
                      id="relatedTaskID"
                      class="easyui-combogrid record-value"
                      name="relatedTaskID" style="width: 180px;"
                      data-options="  
						            panelWidth:520,  
						            idField:'id',  
						            textField:'subject',  
						            url:'listTask.action',
						            loadMsg: '<s:text name="datagrid.loading" />',
						            pagination : true,
						            pageSize: 10,
						            pageList: [10,30,50],
								    fit: true,
						            mode:'remote',
						            columns:[[  
										{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
										{field:'subject',title:'<s:text name="entity.subject.label" />',width:80,align:'center',sortable:'true'},
										{field:'contact.name',title:'<s:text name="entity.contact.label" />',width:80,align:'center',sortable:'true'},
										{field:'related_object',title:'<s:text name="entity.related_object.label" />',width:80,align:'center',sortable:'true'},
										{field:'due_date',title:'<s:text name="task.due_date.label" />',width:80,align:'center',sortable:'true'},			
										{field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'}
						            ]]  
						        ">
                    </select>
                  </span></td>
                </tr>

                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="contact" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.contact.label"></s:text>：</label></td>
                  <td class="td-value"><select id="contactID"
                    class="easyui-combogrid record-value"
                    name="contactID" style="width: 180px;"
                    data-options="  
						            panelWidth:520,  
						            idField:'id',  
						            textField:'name',  
						            url:'listContact.action',
						            loadMsg: '<s:text name="datagrid.loading" />',
						            pagination : true,
						            pageSize: 10,
						            pageList: [10,30,50],
								    fit: true,
						            mode:'remote',
						            columns:[[  
										{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
										{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true'},
										{field:'title',title:'<s:text name="entity.title.label" />',width:80,align:'center',sortable:'true'},
										{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'right',sortable:'true'},
										{field:'email',title:'<s:text name="entity.email.label" />',width:80,align:'center',sortable:'true'},
										{field:'office_phone',title:'<s:text name="entity.office_phone.label" />',width:80,align:'center',sortable:'true'},
										{field:'assigned_to.name',title:'<s:text name="entity.assigned_to.label" />',width:80,align:'center',sortable:'true'}
						            ]]  
						        ">
                  </select></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="priority" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.priority.label"></s:text>：</label></td>
                  <td class="td-value"><s:select name="priorityID"
                      list="priorities" listKey="id" listValue="label"
                      cssClass="record-value" /></td>
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
                  <td class="td-mass-update"></td>
                  <td class="td-label"></td>
                  <td class="td-value"></td>
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
                      name="task.description" rows="20"
                      cssStyle="width:450px;" cssClass="record-value" /></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="notes" /></td>
                  <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="entity.notes.label"></s:text>：</label></td>
                  <td class="td-value" valign="top"><s:textarea
                      name="task.notes" rows="20"
                      cssStyle="width:450px;" cssClass="record-value" /></td>
                </tr>
              </table>

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

          </div>
        </s:form>
      </div>
    </div>

    <s:include value="../footer.jsp" />

  </div>
</body>
</html>



