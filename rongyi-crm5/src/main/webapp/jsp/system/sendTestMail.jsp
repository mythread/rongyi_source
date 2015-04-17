<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
	function submit(){
		var sentTestMailForm = document.getElementById('sentTestMailForm');
		sentTestMailForm.action = "sendEmail.action";
		sentTestMailForm.submit();
	}
	
	function cancel(){
		window.close();
	}

  </script>
</head>
<body>
  <div id="feature">
    <div id="feature-title">
      <h2>
        <s:text name="title.sendTestMail" />
      </h2>
      <s:actionerror />
      <s:fielderror />
    </div>

    <div id="feature-content">
      <s:form id="sentTestMailForm" action="sendEmail" method="POST"
        namespace="/jsp/system">
        <table style="width: 100%;" border="0">
          <tr>
            <td><br></td>
            <td />
          </tr>
          <tr>
            <td style="width: 50%;"><s:text
                name="emailSetting.test_email_address.label" />:</td>
            <td style="text-align: left;"><s:textfield
                name="emailAddress" cssClass="record-value" /></td>
          </tr>
          <tr>
            <td><br></td>
            <td />
          </tr>
          <tr>
            <td><br></td>
            <td />
          </tr>
          <tr>
            <td style="text-align: left;"><span
              style="white-space: nowrap;"> <a href="#"
                class="easyui-linkbutton" iconCls="icon-mail"
                onclick="submit()" plain="true"><s:text
                    name="button.send" /></a>
            </span> <span style="white-space: nowrap;"> <a href="#"
                class="easyui-linkbutton" iconCls="icon-cancel"
                onclick="cancel()" plain="true"><s:text
                    name="button.cancel" /></a>
            </span></td>
            <td></td>
          </tr>
        </table>
      </s:form>
    </div>
  </div>

</body>
</html>

