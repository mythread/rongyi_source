<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page language="java" import="com.gcrm.domain.EmailSetting"%>

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
        baseSave("Meeting");
	}

	function saveClose() {
		baseSaveClose("Meeting");
	}
	
	function cancel() {
		baseCancel("Meeting");
	}

	function sendInvites() {
		disableBtn();
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = 'sendInvitesMeeting.action';
		addObjectForm.submit();		
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
		$('#assignedToID').combogrid('setValue', '<s:property value="assignedToID"/>');
		$('#assignedToID').combogrid('setText', '<s:property value="assignedToText"/>');		
		$('#startDate').datebox('setValue', '<s:property value="startDate"/>');
		$('#endDate').datebox('setValue', '<s:property value="endDate"/>');
		$("#relatedObject ").val('<s:property value="meeting.related_object"/>');		
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
		
		$('#relatedObject').change(function() {
			checkRelatedObject();
		});
		checkRelatedObject();	
		if ($("#seleteIDs").val()!= ""){
			  $("input:checkbox[name=massUpdate]").css("display",'block');
			  $('#send_span').css("display",'none');
			  $('#tt').tabs('close', '<s:text name='tab.invitees'/>');
		}
		if ($("#id").val() == ""){
			  $('#tt').tabs('close', '<s:text name='tab.invitees'/>');
			  $('#send_span').css("display",'none');
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
	    } else if ($("#saveFlag").val() == "<%=EmailSetting.STATUS_SENT%>"){
	     	 $.messager.show({  
	             title:'<s:text name="message.title" />',  
	             msg:'<s:text name="message.invitation.sent" />',  
	             timeout:5000,  
	             showType:'slide'  
	        })
	     };  
	     $("#saveFlag").val("");	
	     
		$('#tt').tabs({
			onSelect:function(title){
		        if (title == "<s:text name='tab.invitees'/>"){
					 var params = {
					   id : $("#id").val()
					 };
					  $.ajax({
					    type: "POST",
					    url: "getMeetingRelationCounts.action",
					    data: params,
					    dataType:"text", 
					    success: function(json){  
					      var obj = $.parseJSON(json);  
					      $("#contactNumber").html(obj.contactNumber); 
					      $("#leadNumber").html(obj.leadNumber); 
					      $("#userNumber").html(obj.userNumber); 
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
          </span> 
          <!--<span style="white-space: nowrap;" id="send_span"> <a
              id="send_btn" href="#" class="easyui-linkbutton"
              iconCls="icon-mail" onclick="sendInvites()" plain="true"><s:text
                  name="button.sendInvites" /></a>
          </span>-->
          
           <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="cancel()" plain="true"><s:text
                  name="button.cancel" /></a>
          </span> 
          <!-- 
          <s:if test="meeting!=null && meeting.id!=null">
              <span style="white-space: nowrap;"><a
                href="javascript:void(0)" id="mtmt"
                class="easyui-menubutton"
                data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
                    name='menu.toolbar.more.title' /></a>
                <div id="mtm1" style="width: 150px;">
                  <div data-options="iconCls:'icon-import'"
                    onClick="openwindow2('/crm/showChangeLogPage.action?entity=Meeting&recordID=' + '<s:property value="meeting.id" />',750,500)">
                    <s:text name='menu.item.changeLog.title' />
                  </div>
                </div> </span>
            </s:if>
            -->
        </span>
      </div>

      <div id="feature-title">
        <s:if test="meeting!=null && meeting.id!=null">
          <h2>
            <s:text name="title.updateMeeting" />
          </h2>
        </s:if>
        <s:else>
          <s:if test="seleteIDs!=null && seleteIDs!= ''">
            <h2>
              <s:text name="title.massUpdateMeeting" />
            </h2>
          </s:if>
          <s:else>
            <h2>
              <s:text name="title.createMeeting" />
            </h2>
          </s:else>
        </s:else>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
          method="post">
          <s:hidden id="id" name="meeting.id" value="%{meeting.id}" />
          <s:hidden id="saveFlag" name="saveFlag" />
          <s:hidden id="seleteIDs" name="seleteIDs" value="%{seleteIDs}" />
          <s:hidden id="createdBy" name="createdBy" />
          <s:hidden id="createdOn" name="createdOn" />
          <s:hidden id="updatedBy" name="updatedBy" />
          <s:hidden id="updatedOn" name="updatedOn" />          

          <table style="" cellspacing="10" cellpadding="0" width="100%">
            <font color="red"> <s:actionerror /></font>
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
              <td class="td-value"><input name="meeting.subject"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="meeting.subject" />" /></td>
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
                    class="easyui-datetimebox record-value" /></input></td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="end_date" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.end_date.label"></s:text>：</label></td>
                  <td class="td-value"><input id=endDate
                    name="endDate" type="text"
                    class="easyui-datetimebox record-value" /></input></td>
                </tr>

                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="related_object" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.related_object.label"></s:text>：</label></td>
                  <td class="td-value"><select id="relatedObject"
                    name="meeting.related_object" style="width: 150px;">
                      <option value="Account">
                        <s:text name="entity.account.label" />
                      </option>
                      <option value="Case">
                        <s:text name="entity.caseInstance.label" />
                      </option>
                      <option value="Contact">
                        <s:text name="entity.contact.label" />
                      </option>
                      <option value="Lead">
                        <s:text name="entity.lead.label" />
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
                    value="reminder_email" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.reminder.label"></s:text>：</label></td>
                  <td class="td-value">
                    <table>
                      <tr>
                        <td style="text-align: left; width: 35%;">
                          <s:checkbox id="meeting.reminder_email"
                            name="meeting.reminder_email"
                            cssClass="record-value" />&nbsp;&nbsp;<label
                          class="record-label"><s:text
                              name="entity.email.label"></s:text></label>
                        </td>
                        <td style="text-align: left; width: 65%;">
                          <s:select name="reminderOptionEmailID"
                            list="reminderOptions" listKey="id"
                            listValue="label" cssClass="record-value" />
                        </td>
                      </tr>
                    </table>
                  </td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="reminder_template" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.reminder_template.label"></s:text>：</label></td>
                  <td class="td-value"><s:select
                      name="reminderTemplateID" list="reminderTemplates"
                      listKey="id" listValue="name"
                      cssClass="record-value" /></td>
                </tr>
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="location" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="meeting.location.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="meeting.location" cssClass="record-value" />
                  </td>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="assigned_to" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="entity.assigned_to.label"></s:text>：</label></td>
                  <td style="text-align: left" width="37.5%"><select
                    id="assignedToID"
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
                </tr>
                <tr>
                  <td class="td-mass-update"><input id="massUpdate"
                    name="massUpdate" type="checkbox" class="massUpdate"
                    value="notes" /></td>
                  <td class="td-label" valign="top"><label
                    class="record-label"><s:text
                        name="entity.notes.label"></s:text>：</label></td>
                  <td class="td-value" valign="top"><s:textarea
                      name="meeting.notes" rows="5" cssStyle="width:350px;" cssClass="record-value" /></td>
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

            <div title="<s:text name='tab.invitees'/>"
              style="padding: 10px;">
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td width="20%" valign="top">
                    <div class="easyui-accordion" style="width: 200px;">
                      <div title="<s:text name="menu.sales.title"/>"
                        style="overflow: auto; padding: 10px;"
                        selected="true">
                        <a
                          href="relateMeetingContactPage.action?id=<s:property value="meeting.id" />"
                          target="contentFrame"><label
                          class="record-value menuLink"><s:text
                              name="menu.contacts.title" />(<span
                            id="contactNumber"></span>)</label></a><br /> <a
                          href="relateMeetingLeadPage.action?id=<s:property value="meeting.id" />"
                          target="contentFrame"><label
                          class="record-value menuLink"><s:text
                              name="menu.leads.title" />(<span
                            id="leadNumber"></span>)</label></a>
                      </div>
                      <div title="<s:text name="menu.system.title"/>"
                        style="overflow: auto; padding: 10px;">
                        <a
                          href="relateMeetingUserPage.action?id=<s:property value="meeting.id" />"
                          target="contentFrame"><label
                          class="record-value menuLink"><s:text
                              name="menu.user.title" />(<span
                            id="userNumber"></span>)</label></a>
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



