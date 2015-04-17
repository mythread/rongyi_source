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
<script type="text/javascript" src="../../ckeditor/ckeditor.js"></script>

<script type="text/javascript">
    function save() {
        baseSave("EmailTemplate");
	}

	function saveClose() {
		baseSaveClose("EmailTemplate");
	}
	
	function cancel() {
		baseCancel("EmailTemplate");
	}
	
	function insertCampaign() {
		var value = $('#variableCampain').children('option:selected').val();
		if ($("#text_only").attr("checked")){
			$('#text_body').val($('#text_body').val() + value);
		}else{
		   CKEDITOR.instances.html_body.insertHtml(value);
		}
	}

	function insertMeeting() {
		var value = $('#variableMeeting').children('option:selected').val();
		if ($("#text_only").attr("checked")){
			$('#text_body').val($('#text_body').val() + value);
		}else{
		   CKEDITOR.instances.html_body.insertHtml(value);
		}
	}
	
	function insertCall() {
		var value = $('#variableCall').children('option:selected').val();
		if ($("#text_only").attr("checked")){
			$('#text_body').val($('#text_body').val() + value);
		}else{
		   CKEDITOR.instances.html_body.insertHtml(value);
		}
	}
	
	function checkType() {
		var type = $('#type').children('option:selected')
				.val();
		if (type == 'meetingInvite' || type == 'meetingRemind') {
			$("span[id^='span']").css("display", "none");
			$('#spanMeeting').css("display", "block");
		} else if (type == 'callInvite' || type == 'callRemind') {
			$("span[id^='span']").css("display", "none");
			$('#spanCall').css("display", "block");
		} else if (type == 'campaignInvite') {
			$("span[id^='span']").css("display", "none");
			$('#spanCampaign').css("display", "block");
		} 
	}	

	function checkText() {
		if ($("#text_only").attr("checked")){
			$('#ckeditor').css("display", "none");
			$('#texteditor').css("display", "block");
		} else{
			$('#ckeditor').css("display", "block");
			$('#texteditor').css("display", "none");
		}
	}	
	
	  $(document).ready(function(){
		if ($("#saveFlag").val() == "true"){
			$.messager.show({  
	          title:'<s:text name="message.title" />',  
	          msg:'<s:text name="message.save" />',  
	          timeout:5000,  
	          showType:'slide'  
	      });  
			$("#saveFlag").val("");
	    }	
		$("#type ").val('<s:property value="emailTemplate.type"/>');
		if ("<%=(String)session.getAttribute("locale")%>" == "zh_CN"){
		    CKEDITOR.config.language = "zh-cn";
	    } else{
	    	 CKEDITOR.config.language = "en-us";
	    }
		$('#type').change(function() {
			checkType();
		});
		checkType();
		if ($.browser.msie) { 
			$('input:checkbox').click(function () { 
			  this.blur(); 
			  this.focus(); 
			}); 
		}; 
		$("#text_only").change(function() { 
			checkText();
		}); 
		checkText();
    }); 	  
    </script>
</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
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
        <s:if test="user!=null && user.id!=null">
          <h2>
            <s:text name="title.updateEmailTemplate" />
          </h2>
        </s:if>
        <s:else>
          <s:if test="seleteIDs!=null && seleteIDs!= ''">
            <h2>
              <s:text name="title.massUpdateEmailTemplate" />
            </h2>
          </s:if>
          <s:else>
            <h2>
              <s:text name="title.createEmailTemplate" />
            </h2>
          </s:else>
        </s:else>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true"
          namespace="/jsp/system" method="post">
          <s:hidden id="id" name="emailTemplate.id"
            value="%{emailTemplate.id}" />
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
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.name.label"></s:text>：</label></td>
              <td class="td-value"><input name="emailTemplate.name"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="emailTemplate.name"/>"
                size="60" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.type.label"></s:text>：</label></td>
              <td class="td-value"><select id="type"
                name="emailTemplate.type" style="width: 150px;">
                  <option value="meetingInvite">
                    <s:text name="emailTemplate.meetingInvite.label" />
                  </option>
                  <option value="meetingRemind">
                    <s:text name="emailTemplate.meetingRemind.label" />
                  </option>
                  <option value="callInvite">
                    <s:text name="emailTemplate.callInvite.label" />
                  </option>
                  <option value="callRemind">
                    <s:text name="emailTemplate.callRemind.label" />
                  </option>
                  <option value="campaignInvite">
                    <s:text name="emailTemplate.campaignInvite.label" />
                  </option>
              </select></td>
            </tr>
          </table>
          <table style="padding: 10px;" cellspacing="10" cellpadding="0"
            width="100%">
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.description.label"></s:text>：</label></td>
              <td><s:textfield name="emailTemplate.description"
                  cssClass="record-value" size="80" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.variable.label"></s:text>：</label></td>
              <td><span id="spanCampaign"> <select
                  id="variableCampain" name="variableCampain"
                  style="width: 150px;">
                    <option value="$campain.start_date">
                      <s:text name="entity.campaign.label" />
                      .
                      <s:text name="entity.start_date.label" />
                    </option>
                    <option value="$campaign.end_date">
                      <s:text name="entity.campaign.label" />
                      .
                      <s:text name="entity.end_date.label" />
                    </option>
                </select> <a id="insert_campaign_btn" href="#"
                  class="easyui-linkbutton" iconCls="icon-import"
                  onclick="insertCampaign()" plain="true"><s:text
                      name="title.action.insert" /></a>
              </span> <span id="spanMeeting"> <select
                  id="variableMeeting" name="variableMeeting"
                  style="width: 150px;">
                    <option value="$meeting.subject">
                      <s:text name="entity.meeting.label" />
                      .
                      <s:text name="entity.subject.label" />
                    </option>
                    <option value="$meeting.start_date">
                      <s:text name="entity.meeting.label" />
                      .
                      <s:text name="entity.start_date.label" />
                    </option>
                    <option value="$meeting.end_date">
                      <s:text name="entity.meeting.label" />
                      .
                      <s:text name="entity.end_date.label" />
                    </option>
                    <option value="$meeting.location">
                      <s:text name="entity.meeting.label" />
                      .
                      <s:text name="meeting.location.label" />
                    </option>
                </select> <a id="insert_meeting_btn" href="#"
                  class="easyui-linkbutton" iconCls="icon-import"
                  onclick="insertMeeting()" plain="true"><s:text
                      name="title.action.insert" /></a>
              </span> <span id="spanCall"> <select id="variableCall"
                  name="variableCall" style="width: 150px;">
                    <option value="$call.subject">
                      <s:text name="entity.call.label" />
                      .
                      <s:text name="entity.subject.label" />
                    </option>
                    <option value="$call.start_date">
                      <s:text name="entity.call.label" />
                      .
                      <s:text name="entity.start_date.label" />
                    </option>
                </select> <a id="insert_call_btn" href="#"
                  class="easyui-linkbutton" iconCls="icon-import"
                  onclick="insertCall()" plain="true"><s:text
                      name="title.action.insert" /></a>
              </span></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.subject.label"></s:text>：</label></td>
              <td><s:textfield name="emailTemplate.subject"
                  cssClass="record-value" size="80" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.text_only.label"></s:text>：</label></td>
              <td><s:checkbox id="text_only"
                  name="emailTemplate.text_only" cssClass="record-value" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.body.label"></s:text>：</label></td>
              <td><span id="ckeditor"> <s:textarea
                    id="html_body" rows="30" cols="100"
                    name="emailTemplate.html_body" /> <script
                    type="text/javascript">CKEDITOR.replace('emailTemplate.html_body');</script>
              </span> <span id="texteditor"> <s:textarea id="text_body"
                    rows="30" cols="100" name="emailTemplate.text_body" />
              </span></td>
            </tr>
          </table>
          <table style="padding: 10px;" cellspacing="10" cellpadding="0"
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
        </s:form>
      </div>
    </div>

    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



