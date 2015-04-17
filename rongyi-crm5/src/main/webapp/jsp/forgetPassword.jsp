<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" type="image/ico" href="/images/favicon.ico" />
<title>Login</title>
<link href="<s:url value="/css/styles.css"/>" type="text/css"
  media="screen" rel="stylesheet" />
<link href="<s:url value="/css/redmond/jquery-ui-1.9.2.custom.css"/>"
  type="text/css" rel="stylesheet" />
<script src="<s:url value="/js/jquery-1.8.3.min.js"/>"></script>
<script src="<s:url value="/js/jquery-ui-1.9.2.custom.min.js"/>"></script>
<script src="<s:url value="/js/global.js"/>"></script>
</head>
<body id="login" onload="document.f.j_username.focus();">
  <div id="wrappertop"></div>
  <div id="wrapper">
    <div id="content">
      <div id="header">
        <s:actionerror />
        <s:if test="hasFieldErrors()">
          <s:iterator value="fieldErrors" status="st">
            <s:if test="#st.index  == 0">
              <s:iterator value="value">
                <font color="red"> <s:property escape="false" /></font>
              </s:iterator>
            </s:if>
          </s:iterator>
        </s:if>
        <s:if test="notification!=null">
          <s:property value="notification" />
        </s:if>
      </div>
      <div id="darkbanner" class="banner320">
        <h2>
          <s:text name='login.forgetPassword.label' />
        </h2>
      </div>
      <div id="darkbannerwrap"></div>
      <s:form id="forgetPasswordForm" validate="true"
        namespace="/jsp/system" action="forgetPassword" method="post">
        <fieldset class="form">
          <p>
            <label class="loginlabel" for="username"><s:text
                name='login.username.label' />:</label>
            <s:textfield id="username" name="username"
              cssClass="logininput ui-keyboard-input ui-widget-content ui-corner-all"
              onkeypress="keypressTab();" />
          </p>
          <p>
            <label class="loginlabel" for="email"> <s:text
                name='entity.email.label' />:
            </label> <span> <s:textfield id="email" name="email"
                cssClass="logininput ui-keyboard-input ui-widget-content ui-corner-all"
                onkeypress="keypressTab();" />
            </span>
          </p>
          <button id="loginbtn" type="button" class="positive"
            name="Submit" type="submit"
            onclick="forgetPasswordForm.submit();">
            <img src="<s:url value="/images/key.png"/>"
              alt="Announcement" />
            <s:text name='submit.button.label' />
          </button>
        </fieldset>
      </s:form>
      <div style="cursor: hand; text-align: right;">
        <a href="<s:url value="/jsp/login.jsp"/>"><s:text
            name='login.loginpage.label' /></a>&nbsp;
      </div>
    </div>
  </div>
  <div id="wrapperbottom_branding">
    <div id="wrapperbottom_branding_text"></div>
  </div>
  <script type="text/javascript">
		$(document).ready(function() {
			$(".logininput").blur(function() {
				if ($(this).val() == "") {
					$(this).css("border-color", "red");
				} else
					$(this).css("border-color", "#D9D6C4");
			})
			document.forgetPasswordForm.username.focus();
		});

		function keypressTab(){
			var ev=window.event.keyCode;
			if(ev == 13){
				document.forgetPasswordForm.email.focus();
			}
		}

		function keypress(){
			var ev=window.event.keyCode;
			if(ev == 13){
				f.submit();
			}
		}
		
	</script>
</body>
</html>
