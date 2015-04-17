<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page language="java" import="com.gcrm.domain.User"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<link rel="stylesheet" type="text/css"
  href="../../themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="../../themes/icon.css" />
<link rel="stylesheet" type="text/css" href="../../css/global.css" />
<link rel="stylesheet" type="text/css" href="../../css/portal.css" />
<style type="text/css">
.title {
	font-size: 16px;
	font-weight: bold;
	padding: 20px 10px;
	background: #eee;
	overflow: hidden;
	border-bottom: 1px solid #ccc;
}

.t-list {
	padding: 5px;
}
</style>
<script type="text/javascript" src="../../js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="../../js/jquery.easyui.min.js"></script>
<script type="text/javascript"
  src="../../js/locale/easyui-lang-<%=(String)session.getAttribute("locale")%>.js"></script>
<script type="text/javascript" src="../../js/global.js"></script>
<script type="text/javascript" src="../../js/jquery.portal.js"></script>
<script>
		$(function(){
			$('#pp').portal({
				border:false,
				fit:true
			});
			var viewAccount='<%=((User)session.getAttribute("loginUser")).getView_account()%>';
			if (viewAccount == 1){		
			  $('#myAccountGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listAccount.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:60,align:'center',sortable:'true'},
						{field:'name',title:'<s:text name="entity.name.label" />',width:130,align:'center',sortable:'true',formatter:function(value,row,index){ 
							var updateAccount='<%=((User)session.getAttribute("loginUser")).getUpdate_account()%>';
							if (updateAccount == 1){
							   new_format_value = "<a href='editAccount.action?id=" + row.id + "'>" + value + "</a>";
							} else {
								new_format_value = value;
							}   
							return new_format_value 
			             }  
			            },
						{field:'intentName',title:'客户意向',width:80,align:'center',sortable:'true'},
						{field:'visitName',title:'拜访进度',width:80,align:'center',sortable:'true'},
						{field:'assignedDateName',title:'跟进时间',width:180,align:'center',sortable:'true'},
						{field:'guanli',title:'管理',width:80,align:'center',sortable:'false'}
					]]
				  });			
			}
			//首页跟踪日报
			$('#myListRecord').datagrid({
				  border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listHomeRecord.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'mallName',title:'商场',width:180,align:'center',sortable:'false',formatter:function(val,row,index){ 
							   new_format_value = "<a href='editAccount.action?id=" + row.mallId + "'>" + val+ "</a>";
							   return new_format_value;	
							}   
			            },
						{field:'intentChange',title:'客户意向变更',width:120,align:'center',sortable:'false'}, 
						{field:'visitChange',title:'拜访进度',width:120,align:'center',sortable:'false'},
						{field:'createDateName',title:'时间',width:120,align:'center',sortable:'false'},
						{field:'assignedToName',title:'跟进人',width:60,align:'center',sortable:'false'}
					]]
			});
			
			
			var viewTask='<%=((User)session.getAttribute("loginUser")).getView_task()%>';
			if (viewTask == 1){				
			  $('#myTaskGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listTask.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
						{field:'subject',title:'<s:text name="entity.subject.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
							var updateTask='<%=((User)session.getAttribute("loginUser")).getUpdate_task()%>';
							if (updateTask == 1){   
							  new_format_value = "<a href='editTask.action?id=" + row.id + "'>" + value + "</a>";
							} else{
							  new_format_value = value;
							}  
							   return new_format_value 
			             }  
			            },
						{field:'contact',title:'<s:text name="entity.contact.label" />',width:80,align:'center',sortable:'true'},
						{field:'related_object',title:'<s:text name="entity.related_object.label" />',width:80,align:'center',sortable:'true'},
						{field:'statuName',title:'<s:text name="状态" />',width:80,align:'center',sortable:'true'},
						{field:'due_date',title:'<s:text name="task.due_date.label" />',width:120,align:'center',sortable:'true'}			
					]]
				  });
			}
			
			var viewLead='<%=((User)session.getAttribute("loginUser")).getView_lead()%>';
			if (viewLead == 1){				
			  $('#myLeadGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listLead.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
						{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
							var updateLead='<%=((User)session.getAttribute("loginUser")).getUpdate_lead()%>';
							if (updateLead == 1){   
							  new_format_value = "<a href='editLead.action?id=" + row.id + "'>" + value + "</a>";
							} else{
						      new_format_value = value;
							} 							  
							return new_format_value 
			             }  
			            },
						{field:'title',title:'<s:text name="entity.title.label" />',width:80,align:'center',sortable:'true'},
						{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'right',sortable:'true'},
						{field:'office_phone',title:'<s:text name="entity.office_phone.label" />',width:80,align:'center',sortable:'true'},
						{field:'email',title:'<s:text name="entity.email.label" />',width:80,align:'center',sortable:'true'}			
					]]
				  });	
			}

			var viewOpportunity='<%=((User)session.getAttribute("loginUser")).getView_opportunity()%>';
			if (viewOpportunity == 1){				
			  $('#myOpportunityGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listOpportunity.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
						{field:'name',title:'<s:text name="entity.name.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
								var updateOpportunity='<%=((User)session.getAttribute("loginUser")).getUpdate_opportunity()%>';
								if (updateOpportunity == 1){   
									new_format_value = "<a href='editOpportunity.action?id=" + row.id + "'>" + value + "</a>";
								} else{
							      new_format_value = value;
								} 							  
							   return new_format_value 
			             }  
			            },
						{field:'account.name',title:'<s:text name="entity.account.label" />',width:80,align:'center',sortable:'true'},
						{field:'sales_stage.name',title:'<s:text name="menu.salesStage.title" />',width:80,align:'right',sortable:'true'},
						{field:'opportunity_amount',title:'<s:text name="opportunity.opportunity_amount.label" />',width:80,align:'center',sortable:'true'}		
					]]
				  });			  
			}
			
			var viewMeeting='<%=((User)session.getAttribute("loginUser")).getView_meeting()%>';
			if (viewMeeting == 1){			
			  $('#myMeetingGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listMeeting.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
						{field:'subject',title:'<s:text name="entity.subject.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
								var updateMeeting='<%=((User)session.getAttribute("loginUser")).getUpdate_meeting()%>';
								if (updateMeeting == 1){   
									new_format_value = "<a href='editMeeting.action?id=" + row.id + "'>" + value + "</a>";
								} else{
							      new_format_value = value;
								} 							  
							   return new_format_value 
			             }  
			            },
						{field:'status.name',title:'<s:text name="entity.status.label" />',width:80,align:'center',sortable:'true'},
						{field:'start_date',title:'<s:text name="entity.start_date.label" />',width:80,align:'right',sortable:'true'},
						{field:'end_date',title:'<s:text name="entity.end_date.label" />',width:80,align:'center',sortable:'true'},
						{field:'location',title:'<s:text name="meeting.location.label" />',width:80,align:'center',sortable:'true'}			
					]]
				  });
			}
			
			var viewCall='<%=((User)session.getAttribute("loginUser")).getView_call()%>';
			if (viewCall == 1){				
			  $('#myCallGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listCall.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
						{field:'direction',title:'<s:text name="call.direction.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
								var updateCall='<%=((User)session.getAttribute("loginUser")).getUpdate_call()%>';
								if (updateCall == 1){   
									new_format_value = "<a href='editCall.action?id=" + row.id + "'>" + value + "</a>";
								} else{
							      new_format_value = value;
								} 							   
							   return new_format_value 
			             }  
			            },
						{field:'subject',title:'<s:text name="entity.subject.label" />',width:80,align:'center',sortable:'true'},
						{field:'statusName',title:'<s:text name="entity.status.label" />',width:80,align:'right',sortable:'true'},
						{field:'start_date',title:'<s:text name="entity.start_date.label" />',width:80,align:'center',sortable:'true'}		
					]]
				  });				  
			}
			
			var viewVisit='<%=((User)session.getAttribute("loginUser")).getView_visit()%>';
			if (viewVisit == 1){			
			  $('#myVisitGrid').datagrid({
				    border:false,
					iconCls:'icon-save',
					height:350,
					pagination:true,
					idField:'id', 
					url:'listVisit.action?_search=true&filter_key=assigned_to.id&filter_op==&filter_value=<s:property value="userID" />',
					columns:[[
						{field:'id',title:'<s:text name="entity.id.label" />',width:80,align:'center',sortable:'true'},
						{field:'subject',title:'<s:text name="entity.subject.label" />',width:80,align:'center',sortable:'true',formatter:function(value,row,index){  
								var updateVisit='<%=((User)session.getAttribute("loginUser")).getUpdate_visit()%>';
								if (updateVisit == 1){   
									new_format_value = "<a href='editVisit.action?id=" + row.id + "'>" + value + "</a>";
								} else{
							      new_format_value = value;
								} 							  
							   return new_format_value 
			             }  
			            },
						{field:'status.name',title:'<s:text name="entity.status.label" />',width:80,align:'center',sortable:'true'},
						{field:'start_date',title:'<s:text name="entity.start_date.label" />',width:80,align:'right',sortable:'true'},
						{field:'end_date',title:'<s:text name="entity.end_date.label" />',width:80,align:'center',sortable:'true'},
						{field:'location',title:'<s:text name="visit.location.label" />',width:80,align:'center',sortable:'true'}			
					]]
				  });
			}
		});
	</script>
</head>

<body>
  <div id="page-wrap">
    <s:include value="../header.jsp" />
    <s:include value="../menu.jsp" />
    <div id="feature">
      <!--<s:include value="../navigation.jsp" />-->
      <div id="feature-content">
        <br></br>
        <div region="center" border="false">
          <div id="pp" style="position: relative">
            <div style="width: 50%;">
              <s:if test="#session.loginUser.view_account == 1">
                <div title="<s:text name='title.grid.myAccounts'/>"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myAccountGrid"></table>
                </div>
              </s:if>
            <!-- 
              <s:if test="#session.loginUser.view_meeting == 1">
                <div title="<s:text name='title.grid.myMeetings'/>"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myMeetingGrid"></table>
                </div>
              </s:if>
              <s:if test="#session.loginUser.view_visit == 1">
                <div title="<s:text name='title.grid.myVisits'/>"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myVisitGrid"></table>
                </div>
              </s:if>
               -->
            </div>
            <div style="width: 50%;">
                <div title="跟踪日报"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myListRecord"></table>
                </div>
              <!-- 
              <s:if test="#session.loginUser.view_opportunity == 1">
                <div title="<s:text name='title.grid.myOpportunities'/>"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myOpportunityGrid"></table>
                </div>
              </s:if>
              <s:if test="#session.loginUser.view_call == 1">
                <div title="<s:text name='title.grid.myCalls'/>"
                  collapsible="true" closable="true"
                  style="height: 385px; padding: 5px;">
                  <table id="myCallGrid"></table>
                </div>
              </s:if>
               -->
            </div>
          </div>
        </div>
      </div>
    </div>
    <s:include value="../footer.jsp" />

  </div>
</body>
</html>
