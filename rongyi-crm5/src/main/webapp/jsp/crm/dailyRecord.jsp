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
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>

<script type="text/javascript">
    	
    function save() {
    	if(beforeSubmit()){
        baseSave("Record");
    	}
	}
    function beforeSubmit(){
    var assignedDateNames = $('#assignedDateName').datebox('getValue');
    var Account ="";
    if($("#id_outerId").length>0){
    	  Account = $("#id_outerId").combogrid('getValue');
    }else{
    	Account = $("#recordOuterId").val();
    } 
    
    
	var memo = $('#memo').val();
	var visitType = $('#visitType').val();
    	if(assignedDateNames==""){
    	alert('跟踪时间不能为空!');
    	return false;
    	}else{
    		var curr_time = new Date();
			var strDate = curr_time.getFullYear()+"-";
			strDate += curr_time.getMonth()+1+"-";
			strDate += curr_time.getDate();
    		var strselectday = assignedDateNames.replace("-","").replace("-","");
    		var strDateselect = strDate.replace("-","").replace("-","");
    		if(strselectday>strDateselect){
    			alert('时间不能大于今天');
    	    	return false;
    		}
    		$('#assignedDateName').datebox('setValue',strDate);
    	}
    		
    	if(Account==""){
    	alert('商场不能为空！');
    	return false;
    	}
    	if(visitType==""){
    	alert('拜访形式不能为空！');
    	return false;
    	}
    	if(memo=="") {
    	alert('记录不能为空！');
    	return false;
    	}
    	return true;
    	}
	function saveClose() {
		if(beforeSubmit()){
		baseSaveClose("Record");
		}
	}
	
	function cancel() {
		baseCancel("Record");
	}

	  function myformatter(date){
	        var y = date.getFullYear();
	        var m = date.getMonth()+1;
	        var d = date.getDate();
	        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	    }
	    function myparser(s){
	        if (!s) return new Date();
	        var ss = (s.split('-'));
	        var y = parseInt(ss[0],10);
	        var m = parseInt(ss[1],10);
	        var d = parseInt(ss[2],10);
	        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
	            return new Date(y,m-1,d);
	        } else {
	            return new Date();
	        }
	    }
$(function(){
	
	$('#dailyDateName').datebox('setValue','<s:property value="dailyDateName"/>');
	
})
</script>

</head>
<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />

        <s:form id="addObjectForm" validate="true" action="/jsp/crm/create.action"
          method="post" >
          <table style="padding: 10px;" cellspacing="10" cellpadding="0"
            width="100%">
            <tr>
              <td class="td-label"><label class="record-label">跟进时间：</label></td>
                <td class="td-value">
               	 <input name="dailyDateName"  id="dailyDateName"  type="text" value="<s:property value="dailyDateName"/>" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser, required:true" />
               	 <input type="submit" value="生成日报"/>（亲，选择日期可以生成对应的日报哦！）
                </td>
               </tr>
               <tr> 
              <td class="td-label"> <label class="record-label">日报记录：</label></td>
              <td  valign="top">
              <textarea name="content" rows="5" style="padding:10px;width:550px;height:350px;text-align:left;" id="memo"><s:property value="content"/></textarea>
              </td>
            </tr>
          </table>
        </s:form>
</body>
</html>



