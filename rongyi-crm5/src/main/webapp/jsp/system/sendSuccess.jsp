<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
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
	function ok(){
		window.close();
	}
  </script>
</head>

<body>
  <div id="feature">
    <div id="feature-title">
      <h2>
        <s:text name="title.send.success" />
      </h2>
      <s:actionerror />
      <s:fielderror />
    </div>

    <div id="feature-content">
      <s:form id="importObjectForm">
        <table style="width: 100%;" border="0">
          <tr>
            <td style="text-align: center;"><s:text
                name="info.send.success" /></td>
          </tr>
        </table>
        <table style="width: 100%;" border="0">
          <tr>
            <td><br></td>
          </tr>
          <tr>
            <td style="text-align: center;"><span
              style="white-space: nowrap;"> <a href="#"
                class="easyui-linkbutton" iconCls="icon-ok"
                onclick="ok()" plain="true"><s:text
                    name="button.close" /></a>
            </span></td>
          </tr>
        </table>
      </s:form>
    </div>
  </div>
</body>
</html>

