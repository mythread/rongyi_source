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
<script type="text/javascript" src="../../js/jquery.MultiFile.js"></script>
<script type="text/javascript" src="../../js/jquery.MetaData.js"></script>
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
	function send() {
		disableBtn();
		$("#saveFlag").val("<%=EmailSetting.STATUS_SENT%>");
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = 'sendEmailMeeting.action';
		addObjectForm.submit();		
	}	

	function selectTemplate() {
		disableBtn();
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = 'selectMeetingTemplate.action';
		addObjectForm.submit();		
	}
	
	function cancel() {
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = 'editMeeting.action';
		addObjectForm.submit();	
	}
	
	function insertMeeting() {
		var value = $('#variableMeeting').children('option:selected').val();
		if (value == '$meeting.subject'){
			value = '<s:property value="meeting.subject" />';
		} else if (value == '$meeting.start_date'){
			value = '<s:property value="startDate" />';
		} else if (value == '$meeting.end_date'){
			value = '<s:property value="endDate" />';
		} else if (value == '$meeting.location'){
			value = '<s:property value="meeting.location" />';
		}
		if ($("#text_only").attr("checked")){
			$('#text_body').val($('#text_body').val() + value);
		}else{
		   CKEDITOR.instances.html_body.insertHtml(value);
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
		$("#saveFlag").val("");
		if ("<%=(String)session.getAttribute("locale")%>" == "zh_CN"){
		    CKEDITOR.config.language = "zh-cn";
	    } else{
	    	 CKEDITOR.config.language = "en-us";
	    }
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
        <span style="white-space: nowrap;" id="send_span"> <a
          id="send_btn" href="#" class="easyui-linkbutton"
          iconCls="icon-mail" onclick="send()" plain="true"><s:text
              name="button.send" /></a>
        </span> <span style="white-space: nowrap;"> <a id="cancel_btn"
          href="#" class="easyui-linkbutton" iconCls="icon-cancel"
          onclick="cancel()" plain="true"><s:text
              name="button.cancel" /></a>
        </span>
      </div>

      <div id="feature-title">
        <h2>
          <s:text name="button.sendInvites" />
        </h2>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
          method="post" enctype="multipart/form-data">
          <s:hidden id="id" name="id" value="%{id}" />
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
                    name="emailTemplate.from.label"></s:text>：</label></td>
              <td><s:textfield name="from" cssClass="record-value"
                  size="80" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.to.label"></s:text>：</label></td>
              <td><s:textarea name="to" rows="1"
                  cssClass="record-value"
                  cssStyle="width:900px;overflow:scroll;overflow-y:hidden;overflow-x:hidden"
                  onfocus="window.activeobj=this;this.clock=setInterval(function(){activeobj.style.height=activeobj.scrollHeight+'px';},200);"
                  onblur="clearInterval(this.clock);" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.template.label"></s:text>：</label></td>
              <td><s:select name="emailTemplateID"
                  list="emailTemplates" listKey="id" listValue="name"
                  cssClass="record-value" /> <a
                id="select_template_btn" href="#"
                class="easyui-linkbutton" iconCls="icon-search"
                onclick="selectTemplate()" plain="true"><s:text
                    name="action.select" /></a> </span></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.variable.label"></s:text>：</label></td>
              <td><span id="spanMeeting"> <select
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
              </span></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="entity.subject.label"></s:text>：</label></td>
              <td><s:textfield name="subject"
                  cssClass="record-value" size="80" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.text_only.label"></s:text>：</label></td>
              <td><s:checkbox id="text_only" name="text_only"
                  cssClass="record-value" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.body.label"></s:text>：</label></td>
              <td><span id="ckeditor"> <s:textarea
                    id="html_body" rows="30" cols="100"
                    cssClass="record-value" name="html_body" /> <script
                    type="text/javascript">CKEDITOR.replace('html_body');</script>
              </span> <span id="texteditor"> <s:textarea id="text_body"
                    rows="30" cols="100" cssClass="record-value"
                    name="text_body" />
              </span></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailTemplate.attachment.label"></s:text>：</label></td>
              <td><input type="file" class="multi" name="upload"
                id="file-upload" class="record-value" /></td>
            </tr>
          </table>
        </s:form>
      </div>
    </div>

    <s:include value="../footer.jsp" />
  </div>
</body>
</html>



