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

<script type="text/javascript">
	function add() {
		var addObjectForm = document.getElementById('addObjectForm');
		addObjectForm.action = "saveEmailSetting.action";
		addObjectForm.submit();
	}
		
	$(document).ready(function() {
		$('#email_provider').combobox('setValue', '<s:property value="emailSetting.email_provider"/>');		
		$('#smtp_protocol').combobox('setValue', '<s:property value="emailSetting.smtp_protocol"/>');
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
        <span> <s:if test="#session.loginUser.update_system == 1">
            <span style="white-space: nowrap;"> <a href="#"
              class="easyui-linkbutton" iconCls="icon-ok"
              onclick="add()" plain="true"><s:text
                  name="button.save" /></a>
            </span>
          </s:if> <span style="white-space: nowrap;"> <a href="#"
            class="easyui-linkbutton" iconCls="icon-mail"
            onclick="openwindow2('/system/sendTestMail.jsp',450,200)"
            plain="true"><s:text name="button.sendTestMail" /></a>
        </span>
        </span>
      </div>

      <div id="feature-title">
        <h2>
          <s:text name="title.emailSetting" />
        </h2>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
          method="post">
          <s:hidden name="emailSetting.id" value="%{emailSetting.id}" />

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
                    name="emailSetting.from_name.label"></s:text>：</label></td>
              <td class="td-value"><input
                name="emailSetting.from_name"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="emailSetting.from_name" />" /></td>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailSetting.from_address.label"></s:text>：</label></td>
              <td class="td-value"><input
                name="emailSetting.from_address"
                class="easyui-validatebox record-value"
                data-options="required:true"
                value="<s:property value="emailSetting.from_address" />" /></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="emailSetting.email_provider.label"></s:text>：</label></td>
              <td class="td-value"><input id="email_provider"
                class="easyui-combobox"
                name="emailSetting.email_provider" style="width: 80px;"
                data-options="valueField:'value',textField:'label',
								data: [{
									label: '<s:text name="tab.gmail" />',
									value: '1',
									selected: true 
								},{label: '<s:text name="tab.yahoo" />',
									value: '2'						
								},{label: '<s:text name="tab.other" />',
									value: '3'															
								}]" />
              </td>
              <td class="td-label"></td>
              <td class="td-value"></td>
            </tr>
          </table>

          <div id="tt" class="easyui-tabs">
            <div id="gmail" title="<s:text name='tab.gmail'/>"
              style="padding: 10px;">
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.gmail_address.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="emailSetting.gmail_address"
                      cssClass="record-value" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.gmail_password.label"></s:text>：</label></td>
                  <td class="td-value"><input type="password"
                    name="emailSetting.gmail_password"
                    class="record-value"
                    value="<s:property value="emailSetting.gmail_password" />" />
                  </td>
                </tr>
              </table>
            </div>

            <div id="yahoo" title="<s:text name='tab.yahoo'/>"
              style="padding: 10px;">
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.yahoo_mail_ID.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="emailSetting.yahoo_mail_ID"
                      cssClass="record-value" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.yahoo_mail_password.label"></s:text>：</label></td>
                  <td class="td-value"><input type="password"
                    name="emailSetting.yahoo_mail_password"
                    class="record-value"
                    value="<s:property value="emailSetting.yahoo_mail_password" />" />
                  </td>
                </tr>
              </table>
            </div>

            <div id="other" title="<s:text name='tab.other'/>"
              style="padding: 10px;">
              <table style="" cellspacing="10" cellpadding="0"
                width="100%">
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.smtp_server.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="emailSetting.smtp_server"
                      cssClass="record-value" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.smtp_port.label"></s:text>：</label></td>
                  <td class="td-value"><input
                    name="emailSetting.smtp_port" type="text"
                    class="easyui-numberbox record-value"
                    value="<s:property value="emailSetting.smtp_port" />"
                    data-options="min:0,precision:0"></input></td>
                </tr>
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.smtp_authentication.label"></s:text>：</label></td>
                  <td class="td-value"><s:checkbox
                      id="emailSetting.smtp_authentication"
                      name="emailSetting.smtp_authentication"
                      cssClass="record-value" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.smtp_protocol.label"></s:text>：</label></td>
                  <td class="td-value"><input id="smtp_protocol"
                    class="easyui-combobox"
                    name="emailSetting.smtp_protocol"
                    style="width: 80px;"
                    data-options="valueField:'value',textField:'label',
										data: [{
											label: '<s:text name="smtp.protocol.none" />',
											value: '1',
											selected: true 
										},{label: '<s:text name="smtp.protocol.ssl" />',
											value: '2'						
										},{label: '<s:text name="smtp.protocol.tls" />',
											value: '3'															
										}]" />
                  </td>
                </tr>
                <tr>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.smtp_username.label"></s:text>：</label></td>
                  <td class="td-value"><s:textfield
                      name="emailSetting.smtp_username"
                      cssClass="record-value" /></td>
                  <td class="td-label"><label class="record-label"><s:text
                        name="emailSetting.smtp_password.label"></s:text>：</label></td>
                  <td class="td-value"><input type="password"
                    name="emailSetting.smtp_password"
                    class="record-value"
                    value="<s:property value="emailSetting.smtp_password" />" />
                  </td>
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



