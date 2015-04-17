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
        <h1>
          <a href=""> <!--؅׃Logo <img src="logo.png"   height="50"  width="100"  alt="logo">--></a>
        </h1>
        <c:if test="${not empty param.login_error}">
          <br />
          <font color="red"><s:text name='error.login.info' /><br />
            <s:text name='error.login.reason' />: <c:out
              value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </font>
        </c:if>
      </div>
      <div id="darkbanner" class="banner320">
        <h2>
          <s:text name='login.system.label' />
        </h2>
      </div>
      <div id="darkbannerwrap"></div>
      <form name="f" action="<c:url value='/j_spring_security_check'/>"
        method="POST">
        <fieldset class="form">
          <p>
            <label class="loginlabel" for="j_username"><s:text
                name='login.username.label' />:</label> <input
              class="logininput ui-keyboard-input ui-widget-content ui-corner-all"
              id="j_username" name="j_username"
              onkeypress="keypressTab();" type="text"
              value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
          </p>
          <p>
            <label class="loginlabel" for="j_password"> <s:text
                name='login.password.label' />:
            </label> <span> <input
              class="logininput ui-keyboard-input ui-widget-content ui-corner-all"
              name="j_password" id="j_password" type="password"
              onkeypress="keypress();" />
            </span>
          </p>
          <%-- <p>
            <label class="loginlabel" for="j_language"> <s:text
                name='login.language.label' />:
            </label> <span> <select
              class="loginselect ui-widget-content ui-corner-all"
              id="j_language" name="j_language"
              onchange="switchLanguage(this.value)">
                <option selected="true" value="zh_CN">简体中文</option>
            </select>
            </span>
          </p> --%>
          <button id="loginbtn" type="button" class="positive"
            name="Submit" type="submit" onclick="f.submit();">
            <img src="<s:url value="/images/key.png"/>"
              alt="Announcement" />
            <s:text name='login.button.label' />
          </button>
        </fieldset>
      </form>
      <%-- <div style="cursor: hand; text-align: right;">
        <a href="<s:url value="/jsp/forgetPassword.jsp"/>"><s:text
            name='login.forgetPassword.label' /></a>&nbsp;
      </div> --%>
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
			document.f.j_username.focus();
			var URLParams = new Array();
			var aParams = document.location.search.substr(1).split('&');
			for (i=0; i < aParams.length ; i++){
			   var aParam = aParams[i].split('=');
			   URLParams[aParam[0]] = aParam[1];
			}
			locale=URLParams["request_locale"];
			if (locale != null && locale == "zh_CN"){
				$("#j_language").val("zh_CN");
			} else {
				$("#j_language").val("en_US");
			}
		});

		function keypressTab(){
			var ev=window.event.keyCode;
			if(ev == 13){
				document.f.j_password.focus();
			}
		}

		function keypress(){
			var ev=window.event.keyCode;
			if(ev == 13){
				f.submit();
			}
		}
		
		function switchLanguage(language){
			f.action= getWebPath() + "/jsp/login.jsp?request_locale="+language;
			f.submit();
		}		
	</script>
</body>
</html>
