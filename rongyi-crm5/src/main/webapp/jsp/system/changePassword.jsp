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
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <s:include value="../navigation.jsp" />
      <div id="shortcuts" class="headerList">
        <span> <span style="white-space: nowrap;"> <a
            id="change_btn" href="#" class="easyui-linkbutton"
            iconCls="icon-update" onclick="changePasswordForm.submit();"
            plain="true"><s:text name="button.change" /></a>
        </span>
        </span>
      </div>

      <div id="feature-title">
        <h2>
          <s:text name="menu.changePassword.title" />
        </h2>
      </div>

      <div id="feature-content">
        <s:form id="changePasswordForm" validate="true"
          action="changePassword" namespace="/jsp/system" method="post">

          <table style="" cellspacing="10" cellpadding="0" width="100%">
            <font color="red">
            <s:actionerror /></font>
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
                    name="changePassword.oldPassword.label"></s:text>：</label></td>
              <td class="td-value"><input name="oldPassword"
                class="easyui-validatebox record-value"
                value="<s:property value="oldPassword"/>" size="60" /></td>
              <td class="td-label"></td>
              <td class="td-value"></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="changePassword.newPassword.label"></s:text>：</label></td>
              <td class="td-value"><input name="newPassword"
                class="easyui-validatebox record-value"
                value="<s:property value="newPassword"/>" size="60" /></td>
              <td class="td-label"></td>
              <td class="td-value"></td>
            </tr>
            <tr>
              <td class="td-label"><label class="record-label"><s:text
                    name="changePassword.confirmPassword.label"></s:text>：</label></td>
              <td class="td-value"><input name="confirmPassword"
                class="easyui-validatebox record-value"
                value="<s:property value="confirmPassword"/>" size="60" /></td>
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



