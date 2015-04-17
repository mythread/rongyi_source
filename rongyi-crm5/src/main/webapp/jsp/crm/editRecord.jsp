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

	
	$(document).ready(function() {
		if ($("#seleteIDs").val()!= ""){
			  $("input:checkbox[name=massUpdate]").css("display",'block');
			  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
		}	
		if ($("#id").val() == ""){
			  $('#tt').tabs('close', '<s:text name='tab.relations'/>');
			  if ($("#seleteIDs").val() == ""){
				     $("#addObjectForm").form('validate');
			  }			  
		}
		$('#id_outerId').combogrid('setValue',
		'<s:property value="outerId"/>');
		$('#id_outerId').combogrid('setText',
		'<s:property value="accountName"/>');

			if ($("#saveFlag").val() == "true") {
				$.messager.show({
					title : '<s:text name="message.title" />',
					msg : '<s:text name="message.save" />',
					timeout : 5000,
					showType : 'slide'
				});
				$("#saveFlag").val("");
			}
			
			var paramid=<%= request.getParameter("outerId")%>;
			
			if(paramid!=null){
				$('#assignedDateName').datebox({
					onSelect: function(date){
						var today = new Date();
						if(date>today){
							alert('时间不能大于今天');
							var curr_time = new Date();
							   var strDate = curr_time.getFullYear()+"-";
							   strDate += curr_time.getMonth()+1+"-";
							   strDate += curr_time.getDate();
							$('#assignedDateName').datebox('setValue',strDate);
						}
						
					}
				});	
			}
		
			
	})

</script>

</head>
<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <div id="shortcuts" class="headerList">
        <b style="white-space: nowrap; color: #444;"><s:text
            name="title.action" />:&nbsp;&nbsp;</b> <span> 
        <s:if test="id==null">  
        <span> 
        <span style="white-space: nowrap;"> <a
              id="save_go_btn" href="#" class="easyui-linkbutton"
              iconCls="icon-save-go" onclick="saveClose()" plain="true"><s:text
                  name="button.saveClose" /></a>
          </span> 
          </s:if>
          
          <span style="white-space: nowrap;"> <a id="cancel_btn"
              href="#" class="easyui-linkbutton" iconCls="icon-cancel"
              onclick="cancel()" plain="true"><s:text
                  name="button.cancel" /></a>
          </span> <s:if test="document!=null && document.id!=null">
              <span style="white-space: nowrap;"><a
                href="javascript:void(0)" id="mtmt"
                class="easyui-menubutton"
                data-options="menu:'#mtm1',iconCls:'icon-more'"><s:text
                    name='menu.toolbar.more.title' /></a>
                <div id="mtm1" style="width: 150px;">
                  <div data-options="iconCls:'icon-import'"
                    onClick="openwindow2('/crm/showChangeLogPage.action?entity=Document&recordID=' + '<s:property value="document.id" />',750,500)">
                    <s:text name='menu.item.changeLog.title' />
                  </div>
                </div> </span>
            </s:if>
        </span>
      </div>

      <div id="feature-title">
        <s:if test="document!=null && document.id!=null">
          <h2>
           修改合同
          </h2>
        </s:if>
        <s:else>
            <h2>
             	新建跟踪记录
            </h2>
        </s:else>
      </div>

      <div id="feature-content">
        <s:form id="addObjectForm" validate="true" namespace="/jsp/crm"
          method="post" >
          <s:hidden id="id" name="record.id" value="%{record.id}" />
          <s:hidden id="saveFlag" name="saveFlag" />
          <s:hidden name="relationKey" id="relationKey"
            value="%{relationKey}" />
          <s:hidden name="relationValue" id="relationValue"
            value="%{relationValue}" />
          <s:hidden id="seleteIDs" name="seleteIDs" value="%{seleteIDs}" />
          <s:hidden id="createdBy" name="createdBy" />
          <s:hidden id="createdOn" name="createdOn" />
          <s:hidden id="updatedBy" name="updatedBy" />
          <s:hidden id="updatedOn" name="updatedOn" />          

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
              <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="name" /></td>
              <td class="td-label"><label class="record-label">跟进时间：</label></td>
                <td class="td-value">
                <s:if test="id==null">
               	 <input name="assignedDateName"  id="assignedDateName"  type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser, required:true" />
               	 </s:if>
               	 <s:else>
               	 	<s:property value="record.assignDateName"/>
               	 
               	 </s:else>
                </td>
                  <td class="td-mass-update"><input id="massUpdate"
                name="massUpdate" type="checkbox" class="massUpdate"
                value="owner" /></td>
              <td class="td-label"><label class="record-label">对应的商场：</label></td>
              <td class="td-value">
                <s:if test="id==null&&outerId==null">
              <select 
                class="easyui-combogrid record-value" id="id_outerId" name="record.outerId"
                style="width: 180px;"
                data-options="  
					            panelWidth:520,  
					            idField:'id',  
					            textField:'name',  
					            url:'<s:url action="listAccount" namespace="/jsp/crm"/>',
		                        loadMsg: '<s:text name="datagrid.loading" />',
		                        pagination : true,
		                        pageSize: 10,
		                        pageList: [10,30,50],
				                fit: true,
					            mode:'remote',
					            columns:[[  
					                {field:'id',title:'<s:text name="entity.id.label" />',width:60},  
					                {field:'name',title:'<s:text name="entity.name.label" />',width:100},  
					                {field:'detailAddress',title:'地址',width:249},  
					                {field:'assignedToName',title:'指派者',width:100}
					            ]]  
					        ">
              </select>
              </s:if>
              <s:else>
              		<s:property value="accountName" />
              		<input type="hidden" name="record.outerId" value="<s:property value='outerId' />" />
              		
              </s:else>
              
              
              </td>
            </tr>
            <tr>
              <td class="td-mass-update">
              </td>
              <td class="td-label"> <label class="record-label">访问形式：</label></td>
              <td class="td-value">
                <s:if test="id==null">
              <select name="record.visitType" id="visitType">
              	 	<option value="">--选择访问形式--</option>
              	 	<option value="PHONE_VISIT">电话</option>
              	 	<option value="DROP_IN_VISIT">上门拜访</option>
              	 	<option value="EMAIL_VISIT">邮件往来</option>
              	 	<option value="IM_VISIT">IM沟通</option>
              	 	<option value="STRANGE_VISIT">陌生拜访</option>
               </select>
               </s:if>
               <s:else>
               		<s:property value="record.visitTypeText" />	
               
               </s:else>
              </td>
                 <td class="td-mass-update">
             	 </td>
              <td class="td-label"> <label class="record-label">受访人：</label></td>
              <td class="td-value">
               <s:if test="id==null">
              <select name="record.contactId" style="width:100px">
              	 	<option value=""></option>
              	 	<s:iterator value="contactList" status="st" id="contact">
              	 		<option value="<s:property value="#contact.id"/>"><s:property value="#contact.last_name"/></option>
              	 	</s:iterator>
               </select>
               </s:if>
                 <s:else>
               		<s:property value="contactName" />	
               
               </s:else>
              </td>
            </tr>
            <tr>
              <td class="td-mass-update">
              </td>
              <td class="td-label"> <label class="record-label">客户意向：</label></td>
              <td class="td-value">
                <s:if test="id==null">
              <select name="record.accountIntent">
              	 	<option value="0" <s:if test="intentStatus == 0" >selected </s:if>>暂无</option>
              	 	<option value="5" <s:if test="intentStatus == 5" >selected </s:if>>已有设备</option>
              	 	<option value="6" <s:if test="intentStatus == 6" >selected </s:if>>无投放价值</option>
              	 	<option value="1" <s:if test="intentStatus == 1" >selected </s:if>>初步意向</option>
              	 	<option value="2" <s:if test="intentStatus == 2" >selected </s:if>>口头承诺</option>
              	 	<option value="3" <s:if test="intentStatus == 3" >selected </s:if>>试用合同</option>
              	 	<option value="4" <s:if test="intentStatus == 4" >selected </s:if>>正式合同</option>
              	 	<option value="7" <s:if test="intentStatus == 7" >selected </s:if>>已进场</option>
               </select>
               </s:if>
               <s:else>
               		<s:property value="record.accountIntentText" />	
               </s:else>
              </td>
                 <td class="td-mass-update">
              </td>
              <td class="td-label"> <label class="record-label">拜访进度：</label></td>
              <td class="td-value">
                <s:if test="id==null">
              <select name="record.accountVisit">
              	 	<option value="0" <s:if test="visitStatus == 0" >selected </s:if>>暂无</option>
              	 	<option value="1" <s:if test="visitStatus == 1" >selected </s:if>>初次拜访</option>
              	 	<option value="2" <s:if test="visitStatus == 2" >selected </s:if>>二次拜访</option>
              	 	<option value="3" <s:if test="visitStatus == 3" >selected </s:if>>多次拜访</option>
              	 	<option value="4" <s:if test="visitStatus == 4" >selected </s:if>>谈判中</option>
              	 	<option value="5" <s:if test="visitStatus == 5" >selected </s:if>>售后维护</option>
               </select>
               </s:if>
               <s:else>
               	<s:property value="record.accountVisitText" />	
               
               </s:else>
              </td>
            </tr>
            <tr>
            <td class="td-mass-update">
              </td>
              <td class="td-label"> <label class="record-label">访问记录：</label></td>
              <td class="td-value" valign="top">
                <s:if test="id==null">
              <s:textarea
                      name="record.visitNote" rows="5" cssStyle="width:350px;" cssClass="record-value" id="memo"/>
                </s:if>    
               <s:else>
               <s:property value="record.visitNote" />	
               
               </s:else>       
                      
                      </td>
                  
                  <td class="td-mass-update"></td>
                  
            </tr>
            <!--  
            <tr>
            <td class="td-mass-update">
              </td>
              <td class="td-label"> <label class="record-label">备注：</label></td>
              <td class="td-value" valign="top"><s:textarea
                      name="contract.memo" rows="5" cssStyle="width:350px;" cssClass="record-value" /></td>
                  <td class="td-mass-update"></td>
            </tr>
            -->
          </table>
        </s:form>
      </div>
    </div>

    <s:include value="../footer.jsp" />

  </div>
</body>
</html>



