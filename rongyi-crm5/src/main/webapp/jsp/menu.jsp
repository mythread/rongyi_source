
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.Map"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.gcrm.domain.User"%>
<%
	User user = (User) session.getAttribute("loginUser");
%>
<%
	request.setAttribute("user", user);
%>
<script>
$(function(){
	$('#mb5').click(function(){
		openPage('/system/listUserPage.action');
	});
	$('#mb4').click(function(){
		openPage('/crm/create.action')
	});
	$('#mb3').click(function(){
		openPage('/crm/listRecordPage.action')
	});
	$('#mb2').click(function(){
		openPage('/crm/listAccountPage.action');
	});
	$('#mb1').click(function(){
		openPage('/crm/homePage.action');
	});
})
</script>
<div
	style="background: #fafafa; padding: 3px; width: 99.4%; border: 1px solid #ccc">
	<a href="javascript:void(0)" id="mb1" class="easyui-menubutton">首页</a> 
	<a href="javascript:void(0)" id="mb2" class="easyui-menubutton">商场列表</a> 
	<a href="javascript:void(0)" id="mb3" class="easyui-menubutton">跟踪记录</a> 
	<a href="javascript:void(0)" id="mb4" class="easyui-menubutton">日报</a> 
	<!-- <a href="javascript:void(0)" id="mb4" class="easyui-menubutton">权限</a> 
	<a href="javascript:void(0)" id="mb5" class="easyui-menubutton">账号</a> -->
	 
    <a href="javascript:void(0)" id="mb7" class="easyui-menubutton" 
        data-options="menu:'#mm7',iconCls:'icon-system'"><s:text name='menu.system.title' /></a> 
     <a href="javascript:void(0)" id="mb8" class="easyui-menubutton"
		data-options="menu:'#mm8',iconCls:'icon-system'"><s:text name='CMS系统审核' /></a>  
</div>
<div id="mm1" style="width: 150px; display: none">
	<div onClick="openPage('/crm/homePage.action')">
		<s:text name='menu.home.title' />
	</div>
	<!-- test -->
	<div class="menu-sep"></div>
	<s:if test="#session.loginUser.view_account == 1">
		<div onClick="openPage('/crm/listAccountPage.action')">
			<s:text name='menu.accounts.title' />
		</div>
	</s:if>
	<s:if test="#session.loginUser.view_opportunity == 1">
		<div onClick="openPage('/crm/listOpportunityPage.action')">
			<s:text name='menu.opportunities.title' />
		</div>
	</s:if>
</div>
<div id="mm2" style="width: 100px; display: none">
	<s:if test="#session.loginUser.view_account == 1">
		<div onClick="openPage('/crm/listAccountPage.action')">
			<s:text name='menu.accounts.title' />
		</div>
	</s:if>
</div>
<div id="mm3" style="width: 100px; display: none">
	<s:if test="#session.loginUser.view_case == 1">
		<div onClick="openPage('/crm/listCasePage.action')">
			<s:text name='menu.cases.title' />
		</div>
	</s:if>
</div>
<div id="mm4" style="width: 100px; display: none">
	<div onClick="openPage('/crm/calendarPage.action')">
		<s:text name='menu.calendar.title' />
	</div>
	<s:if test="#session.loginUser.view_call == 1">
		<div onClick="openPage('/crm/listCallPage.action')">
			<s:text name='menu.calls.title' />
		</div>
	</s:if>
	<s:if test="#session.loginUser.view_meeting == 1">
		<div onClick="openPage('/crm/listMeetingPage.action')">
			<s:text name='menu.meetings.title' />
		</div>
	</s:if>
	<s:if test="#session.loginUser.view_visit == 1">
		<div onClick="openPage('/crm/listVisitPage.action')">
			<s:text name='menu.visits.title' />
		</div>
	</s:if>
	<s:if test="#session.loginUser.view_task == 1">
		<div onClick="openPage('/crm/listTaskPage.action')">
			<s:text name='menu.tasks.title' />
		</div>
	</s:if>
</div>
<div id="mm5" style="width: 100px; display: none">
	<s:if test="#session.loginUser.view_targetList == 1">
		<div onClick="openPage('/crm/listTargetListPage.action')">
			<s:text name='menu.targetLists.title' />
		</div>
	</s:if>
	<s:if test="#session.loginUser.view_contract == 1">
		<div onClick="openPage('/crm/listContractPage.action')">
			<s:text name='menu.contractLists.title' />
		</div>
	</s:if>
	<div onClick="openPage('/crm/listRecordPage.action')">跟踪记录</div>
</div>
<div id="mm7" style="width: 150px; display: none">
	<s:if test="#session.loginUser.view_system == 1">
		<div>
			<span><s:text name='menu.dropdown.title' /></span>
			<div style="width: 170px;">
				<div onClick="openPage('/system/listAccountLevelPage.action')">
					<s:text name='menu.accountLevel.title' />
				</div>
				<div onClick="openPage('/system/listAccountNaturePage.action')">
					<s:text name='menu.accountNature.title' />
				</div>
				<div onClick="openPage('/system/listAccountTypePage.action')">
					<s:text name='menu.accountType.title' />
				</div>
				<div onClick="openPage('/system/listAnnualRevenuePage.action')">
					<s:text name='menu.annualRevenue.title' />
				</div>
				<div onClick="openPage('/system/listCallStatusPage.action')">
					<s:text name='menu.callStatus.title' />
				</div>
				<div onClick="openPage('/system/listCallDirectionPage.action')">
					<s:text name='menu.callDirection.title' />
				</div>
				<div onClick="openPage('/system/listCapitalPage.action')">
					<s:text name='menu.capital.title' />
				</div>
				<div onClick="openPage('/system/listCompanySizePage.action')">
					<s:text name='menu.companySize.title' />
				</div>
				<div onClick="openPage('/system/listReminderOptionPage.action')">
					<s:text name='menu.reminderOption.title' />
				</div>
				<div onClick="openPage('/system/listCampaignTypePage.action')">
					<s:text name='menu.campaignType.title' />
				</div>
				<div onClick="openPage('/system/listCampaignStatusPage.action')">
					<s:text name='menu.campaignStatus.title' />
				</div>
				<div onClick="openPage('/system/listCaseOriginPage.action')">
					<s:text name='menu.caseOrigin.title' />
				</div>
				<div onClick="openPage('/system/listCasePriorityPage.action')">
					<s:text name='menu.casePriority.title' />
				</div>
				<div onClick="openPage('/system/listCaseReasonPage.action')">
					<s:text name='menu.caseReason.title' />
				</div>
				<div onClick="openPage('/system/listCaseStatusPage.action')">
					<s:text name='menu.caseStatus.title' />
				</div>
				<div onClick="openPage('/system/listCaseTypePage.action')">
					<s:text name='menu.caseType.title' />
				</div>
				<div onClick="openPage('/system/listDocumentCategoryPage.action')">
					<s:text name='menu.documentCategory.title' />
				</div>
				<div onClick="openPage('/system/listDocumentStatusPage.action')">
					<s:text name='menu.documentStatus.title' />
				</div>
				<div
					onClick="openPage('/system/listDocumentSubCategoryPage.action')">
					<s:text name='menu.documentSubCategory.title' />
				</div>
				<div onClick="openPage('/system/listDocumentTypePage.action')">
					<s:text name='menu.documentType.title' />
				</div>
				<div onClick="openPage('/system/listIndustryPage.action')">
					<s:text name='menu.industry.title' />
				</div>
				<div onClick="openPage('/system/listLeadSourcePage.action')">
					<s:text name='menu.leadSource.title' />
				</div>
				<div onClick="openPage('/system/listLeadStatusPage.action')">
					<s:text name='menu.leadStatus.title' />
				</div>
				<div onClick="openPage('/system/listUserStatusPage.action')">
					<s:text name='menu.userStatus.title' />
				</div>
				<div onClick="openPage('/system/listReligiousPage.action')">
					<s:text name='menu.religious.title' />
				</div>
				<div onClick="openPage('/system/listSalesStagePage.action')">
					<s:text name='menu.salesStage.title' />
				</div>
				<div onClick="openPage('/system/listSalutationPage.action')">
					<s:text name='menu.salutation.title' />
				</div>
				<div onClick="openPage('/system/listTaskStatusPage.action')">
					<s:text name='menu.taskStatus.title' />
				</div>
				<div onClick="openPage('/system/listTaskPriorityPage.action')">
					<s:text name='menu.taskPriority.title' />
				</div>
			</div>
		</div>
		<div class="menu-sep"></div>
		<div onClick="openPage('/system/listCurrencyPage.action')">
			<s:text name='menu.currency.title' />
		</div>
		<div onClick="openPage('/system/listUserPage.action')">
			<s:text name='menu.user.title' />
		</div>
		<div onClick="openPage('/system/listUserLogPage.action')">
			<s:text name='登录记录' />
		</div>
		<div onClick="openPage('/system/listRolePage.action')">
			<s:text name='menu.role.title' />
		</div>
		<div class="menu-sep"></div>
		<div onClick="openPage('/system/editEmailSetting.action')">
			<s:text name='menu.emailSetting.title' />
		</div>
		<div onClick="openPage('/system/listEmailTemplatePage.action')">
			<s:text name='menu.emailTemplate.title' />
		</div>
		<div onClick="openPage('/system/listChangeLogPage.action')">
			<s:text name='menu.changeLog.title' />
		</div>
	</s:if>
	<div onClick="openPage('/system/changePasswordPage.action')">
		<s:text name='menu.changePassword.title' />
	</div>
</div>
<div id="mm8" style="width: 150px; display: none">
	<s:if test="#session.loginUser.view_check == 1">
	<div onClick="openPage('/mallcms/taskListPage.action')">
		<s:text name='审核任务列表' />
	</div>
	</s:if>
	<s:if test="#session.loginUser.view_system == 1">
	<div onClick="openPage('/mallcms/showInitPage.action')">
		<s:text name='CMS信息审核' />
	</div>
	<div onClick="openPage('/mallcms/listMallIp.action')">
		<s:text name='商场IP配置' />
	</div>
	</s:if>
</div>
