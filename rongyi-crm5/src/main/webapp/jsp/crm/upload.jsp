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

	$(document).ready(function(){	  
	  $("#exportTemplate").click(function() {	
		var entityName = '<%=(String)request.getParameter("entityName")%>';
		var importObjectForm = document.getElementById('importObjectForm');
		var namespace = getWebPath() + '/' + '<%=(String)request.getParameter("namespace")%>' + '/';
		importObjectForm.action = namespace + 'exportTemplate' + entityName + '.action';
		importObjectForm.submit();
	  });	
	  
      $("#upload").click(function() {   
          var entityName = '<%=(String)request.getParameter("entityName")%>';
          var importObjectForm = document.getElementById('importObjectForm');
          var namespace = getWebPath() + '/' + '<%=(String)request.getParameter("namespace")%>' + '/';
          importObjectForm.action = namespace + 'import' + entityName + '.action';
          importObjectForm.submit();
        });	  
	  
	})

	function cancel(){
		window.close();
	}

  </script>
</head>
<body>
  <div id="feature">
    <div id="feature-title">
      <h2>
        <s:property value="#parameters.title" />
      </h2>
      <s:actionerror />
      <s:fielderror />
    </div>

    <div id="feature-content">
      <table style="width: 100%;" border="0">
        <tr>
          <td><s:text name="info.import.title" /></td>
        </tr>
        <tr>
          <td><a id="exportTemplate" href="#"><s:text
                name="info.download.template" /></a></td>
        </tr>
      </table>
      <s:form id="importObjectForm" method="POST" namespace="/jsp/crm"
        enctype="multipart/form-data">
        <table style="width: 100%;" border="0">
          <tr>
            <td><br></td>
            <td />
          </tr>
          <tr>
            <td style="width: 35%; text-align: right;"><s:text
                name="info.selectFile.label" />:</td>
            <td style="text-align: left;"><s:file name="upload"
                label="File" /></td>
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
            <td></td>
            <td style="text-align: left;"><span
              style="white-space: nowrap;"> <a id="upload" href="#"
                class="easyui-linkbutton" iconCls="icon-ok" plain="true"><s:text
                    name="button.submit" /></a>
            </span> <span style="white-space: nowrap;"> <a href="#"
                class="easyui-linkbutton" iconCls="icon-cancel"
                onclick="cancel()" plain="true"><s:text
                    name="button.cancel" /></a>
            </span></td>
          </tr>
        </table>
      </s:form>
    </div>
  </div>

</body>
</html>

