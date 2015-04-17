/**
 * Copyright (C) 2012 - 2013, Grass CRM Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gcrm.action.crm;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Account;
import com.gcrm.domain.Call;
import com.gcrm.domain.CallDirection;
import com.gcrm.domain.CallStatus;
import com.gcrm.domain.CaseInstance;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Contact;
import com.gcrm.domain.EmailTemplate;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.ReminderOption;
import com.gcrm.domain.Target;
import com.gcrm.domain.Task;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.mail.MailService;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Call
 * 
 */
public class EditCallAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Call> baseService;
    private IOptionService<CallStatus> callStatusService;
    private IOptionService<CallDirection> callDirectionService;
    private IOptionService<ReminderOption> reminderOptionService;
    private IBaseService<User> userService;
    private IBaseService<Account> accountService;
    private IBaseService<CaseInstance> caseService;
    private IBaseService<Contact> contactService;
    private IBaseService<Lead> leadService;
    private IBaseService<Opportunity> opportunityService;
    private IBaseService<Target> targetService;
    private IBaseService<Task> taskService;
    private MailService mailService;
    private IBaseService<EmailTemplate> emailTemplateService;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private Call call;
    private List<CallStatus> statuses;
    private List<CallDirection> directions;
    private List<ReminderOption> reminderOptions;
    private List<EmailTemplate> emailTemplates;
    private List<EmailTemplate> reminderTemplates;
    private Integer emailTemplateID = null;
    private Integer statusID = null;
    private Integer directionID = null;
    private Integer reminderOptionEmailID = null;
    private Integer reminderTemplateID = null;
    private Integer relatedAccountID = null;
    private String relatedAccountText = null;
    private Integer relatedCaseID = null;
    private String relatedCaseText = null;
    private Integer relatedContactID = null;
    private String relatedContactText = null;
    private Integer relatedLeadID = null;
    private String relatedLeadText = null;
    private Integer relatedOpportunityID = null;
    private String relatedOpportunityText = null;
    private Integer relatedTargetID = null;
    private String relatedTargetText = null;
    private Integer relatedTaskID = null;
    private String relatedTaskText = null;
    private String startDate = null;
    private String subject;
    boolean text_only;
    private String html_body;
    private String text_body;
    private String from;
    private String to;
    private File[] uploads;
    private String[] uploadFileNames;
    private String[] uploadContentTypes;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        Call originalCall = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalCall, call);
        // Validate Reminder Email Template
        if (call.isReminder_email() && reminderTemplateID == null) {
            String errorMessage = getText("error.reminderEamilTemplate");
            super.addActionError(errorMessage);
            return INPUT;
        }
        call = getbaseService().makePersistent(call);
        this.setId(call.getId());
        this.setSaveFlag("true");
        if (changeLogs != null) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    batchInserChangeLogs(changeLogs);
                }
            });
        }
        return SUCCESS;
    }

    /**
     * Batch update change log
     * 
     * @param changeLogs
     *            change log collection
     */
    private void batchInserChangeLogs(Collection<ChangeLog> changeLogs) {
        this.getChangeLogService().batchUpdate(changeLogs);
    }

    /**
     * Creates change log
     * 
     * @param originalCall
     *            original call record
     * @param call
     *            current call record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Call originalCall, Call call) {
        Collection<ChangeLog> changeLogs = null;
        if (originalCall != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = Call.class.getSimpleName();
            Integer recordID = call.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldSubject = CommonUtil.fromNullToEmpty(originalCall
                    .getSubject());
            String newSubject = CommonUtil.fromNullToEmpty(call.getSubject());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.subject.label", oldSubject, newSubject, loginUser);

            String oldStatus = getOptionValue(originalCall.getStatus());
            String newStatus = getOptionValue(call.getStatus());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.status.label", oldStatus, newStatus, loginUser);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_EDIT_FORMAT);
            String oldStartDateValue = "";
            Date oldStartDate = originalCall.getStart_date();
            if (oldStartDate != null) {
                oldStartDateValue = dateFormat.format(oldStartDate);
            }
            String newStartDateValue = "";
            Date newStartDate = call.getStart_date();
            if (newStartDate != null) {
                newStartDateValue = dateFormat.format(newStartDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.start_date.label", oldStartDateValue,
                    newStartDateValue, loginUser);

            String oldRelatedObject = CommonUtil.fromNullToEmpty(originalCall
                    .getRelated_object());
            String newRelatedObject = CommonUtil.fromNullToEmpty(call
                    .getRelated_object());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.related_object.label", oldRelatedObject,
                    newRelatedObject, loginUser);

            String oldRelatedRecord = String.valueOf(originalCall
                    .getRelated_record());
            String newRelatedRecord = String.valueOf(call.getRelated_record());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.related_record.label", oldRelatedRecord,
                    newRelatedRecord, loginUser);

            boolean oldReminderEmail = originalCall.isReminder_email();
            boolean newReminderEmail = call.isReminder_email();
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.reminder.label", String.valueOf(oldReminderEmail),
                    String.valueOf(newReminderEmail), loginUser);

            String oldReminderOption = getOptionValue(originalCall
                    .getReminder_option_email());
            String newReminderOption = getOptionValue(call
                    .getReminder_option_email());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.reminder_option_email_name.label",
                    oldReminderOption, newReminderOption, loginUser);

            String oldReminderTemplateName = "";
            EmailTemplate oldReminderTemplate = originalCall
                    .getReminder_template();
            if (oldReminderTemplate != null) {
                oldReminderTemplateName = CommonUtil
                        .fromNullToEmpty(oldReminderTemplate.getName());
            }
            String newReminderTemplateName = "";
            EmailTemplate newReminderTemplate = call.getReminder_template();
            if (newReminderTemplate != null) {
                newReminderTemplateName = CommonUtil
                        .fromNullToEmpty(newReminderTemplate.getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.reminder_template.label", oldReminderTemplateName,
                    newReminderTemplateName, loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalCall
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(call.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalCall.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = call.getAssigned_to();
            if (newAssignedTo != null) {
                newAssignedToName = newAssignedTo.getName();
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.assigned_to.label",
                    CommonUtil.fromNullToEmpty(oldAssignedToName),
                    CommonUtil.fromNullToEmpty(newAssignedToName), loginUser);
        }
        return changeLogs;
    }

    /**
     * Sends invitation mail to all participants.
     * 
     * @return the SUCCESS result
     */
    public String sendInvites() throws Exception {

        UserUtil.permissionCheck("update_call");
        call = baseService.getEntityById(Call.class, call.getId());
        Date start_date = call.getStart_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        startDate = "";
        if (start_date != null) {
            startDate = dateFormat.format(start_date);
        }
        this.setId(call.getId());
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        User loginUser = (User) session
                .get(AuthenticationSuccessListener.LOGIN_USER);

        StringBuilder targetEmails = new StringBuilder("");
        Set<Contact> contacts = call.getContacts();
        if (contacts != null) {
            for (Contact contact : contacts) {
                String email = contact.getOffice_email();
                if (CommonUtil.isNullOrEmpty(email)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        Set<Lead> leads = call.getLeads();
        if (leads != null) {
            for (Lead lead : leads) {
                String email = lead.getEmail();
                if (CommonUtil.isNullOrEmpty(email)) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        from = loginUser.getEmail();
        if (from == null) {
            from = "";
        }
        Set<User> users = call.getUsers();
        if (users != null) {
            for (User user : users) {
                String email = user.getEmail();
                if (CommonUtil.isNullOrEmpty(email)
                        || (from != null && email.endsWith(from))) {
                    continue;
                }
                if (targetEmails.length() > 0) {
                    targetEmails.append(",");
                }
                targetEmails.append(email);
            }
        }
        if (targetEmails.length() > 0) {
            to = targetEmails.toString();
        }

        // Gets email template list
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'callInvite' order by created_on";
        emailTemplates = emailTemplateService.findByHQL(hql);
        return SUCCESS;
    }

    public String selectTemplate() throws Exception {
        UserUtil.permissionCheck("update_call");
        call = baseService.getEntityById(Call.class, this.getId());
        Date start_date = call.getStart_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        startDate = "";
        if (start_date != null) {
            startDate = dateFormat.format(start_date);
        }

        EmailTemplate emailTemplte = emailTemplateService.getEntityById(
                EmailTemplate.class, emailTemplateID);
        this.setText_only(emailTemplte.isText_only());
        this.setSubject(CommonUtil.fromNullToEmpty(emailTemplte.getSubject()));
        String content = "";
        if (this.isText_only()) {
            content = emailTemplte.getText_body();
        } else {
            content = emailTemplte.getHtml_body();
        }
        // Replaces the variable in the body
        if (content != null) {
            content = content.replaceAll("\\$call.subject",
                    CommonUtil.fromNullToEmpty(call.getSubject()));
            content = content.replaceAll("\\$call.start_date", startDate);
        }
        if (this.isText_only()) {
            this.setText_body(content);
        } else {
            this.setHtml_body(content);
        }
        // Gets email template list
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'callInvite' order by created_on";
        emailTemplates = emailTemplateService.findByHQL(hql);
        return SUCCESS;
    }

    /**
     * Sends invitation mail to all participants.
     * 
     * @return the SUCCESS result
     */
    public String send() throws Exception {

        UserUtil.permissionCheck("update_call");
        String content = "";
        if (to != null && to.trim().length() > 0) {
            String[] tos = to.split(",");
            if (this.isText_only()) {
                content = this.getText_body();
            } else {
                content = this.getHtml_body();
            }
            // Gets attachments
            String realPath = ServletActionContext.getRequest().getSession()
                    .getServletContext().getRealPath("/upload");
            String targetDirectory = realPath;
            File[] tFiles = null;
            if (uploads != null) {
                String[] tNames = new String[uploads.length];
                tFiles = new File[uploads.length];
                for (int i = 0; i < uploads.length; i++) {
                    tNames[i] = generateFileName(uploadFileNames[i]);
                    File target = new File(targetDirectory, tNames[i]);
                    FileUtils.copyFile(uploads[i], target);
                    tFiles[i] = target;
                }
            }
            mailService.asynSendHtmlMail(from, tos, subject, content,
                    this.getUploadFileName(), tFiles);
        }
        return SUCCESS;
    }

    /**
     * Generates file name for upload file automatically to invoid duplicate
     * file names
     * 
     * @param fileName
     *            original file names
     * @return generated file name
     */
    private String generateFileName(String fileName) {
        DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            UserUtil.permissionCheck("view_call");
            call = baseService.getEntityById(Call.class, this.getId());
            UserUtil.scopeCheck(call, "scope_call");
            CallStatus status = call.getStatus();
            if (status != null) {
                statusID = status.getId();
            }
            CallDirection direction = call.getDirection();
            if (direction != null) {
                directionID = direction.getId();
            }
            ReminderOption reminderOptionEmail = call
                    .getReminder_option_email();
            if (reminderOptionEmail != null) {
                reminderOptionEmailID = reminderOptionEmail.getId();
            }
            EmailTemplate reminderTemplate = call.getReminder_template();
            if (reminderTemplate != null) {
                reminderTemplateID = reminderTemplate.getId();
            }
            User assignedTo = call.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            Date start_date = call.getStart_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_TIME_FORMAT);
            if (start_date != null) {
                startDate = dateFormat.format(start_date);
            }
            String relatedObject = call.getRelated_object();
            Integer relatedRecord = call.getRelated_record();
            if (relatedRecord != null) {
                setRelatedRecord(relatedObject, relatedRecord);
            }
            this.getBaseInfo(call, Call.class.getSimpleName(),
                    Constant.CRM_NAMESPACE);
        } else {
            this.initBaseInfo();
            if (!CommonUtil.isNullOrEmpty(this.getRelationKey())) {
                call.setRelated_object(this.getRelationKey());
                setRelatedRecord(this.getRelationKey(),
                        Integer.parseInt(this.getRelationValue()));
            }
        }
        return SUCCESS;
    }

    /**
     * Sets the related record ID
     * 
     * @param relatedObject
     *            Related Object name
     * @param relatedRecord
     *            Related Record ID
     */
    private void setRelatedRecord(String relatedObject, Integer relatedRecord) {
        if ("Account".equals(relatedObject)) {
            this.relatedAccountID = relatedRecord;
            if (relatedRecord != null) {
                this.relatedAccountText = this.accountService.getEntityById(
                        Account.class, relatedRecord).getName();
            }
        } else if ("CaseInstance".equals(relatedObject)) {
            this.relatedCaseID = relatedRecord;
            if (relatedRecord != null) {
                this.relatedCaseText = this.caseService.getEntityById(
                        CaseInstance.class, relatedRecord).getSubject();
            }
        } else if ("Contact".equals(relatedObject)) {
            this.relatedContactID = relatedRecord;
            if (relatedRecord != null) {
                this.relatedContactText = this.contactService.getEntityById(
                        Contact.class, relatedRecord).getName();
            }
        } else if ("Lead".equals(relatedObject)) {
            this.relatedLeadID = relatedRecord;
            if (relatedRecord != null) {
                this.relatedLeadText = this.leadService.getEntityById(
                        Lead.class, relatedRecord).getName();
            }
        } else if ("Opportunity".equals(relatedObject)) {
            this.relatedOpportunityID = relatedRecord;
            if (relatedRecord != null) {
                this.relatedOpportunityText = this.opportunityService
                        .getEntityById(Opportunity.class, relatedRecord)
                        .getName();
            }
        } else if ("Target".equals(relatedObject)) {
            this.relatedTargetID = relatedRecord;
            if (relatedRecord != null) {
                this.relatedTargetText = this.targetService.getEntityById(
                        Target.class, relatedRecord).getName();
            }
        } else if ("Task".equals(relatedObject)) {
            this.relatedTaskID = relatedRecord;
            if (relatedRecord != null) {
                this.relatedTaskText = this.taskService.getEntityById(
                        Task.class, relatedRecord).getSubject();
            }
        }
    }

    /**
     * Mass update entity record information
     */
    public String massUpdate() throws Exception {
        saveEntity();
        String[] fieldNames = this.massUpdate;
        if (fieldNames != null) {
            Collection<String> feildNameCollection = new ArrayList<String>();
            for (int i = 0; i < fieldNames.length; i++) {
                feildNameCollection.add(fieldNames[i]);
                if ("reminder_pop".equals(fieldNames[i])) {
                    feildNameCollection.add("reminder_email");
                    feildNameCollection.add("reminder_option_pop");
                    feildNameCollection.add("reminder_option_email");
                }
            }

            String[] selectIDArray = this.seleteIDs.split(",");
            Collection<Call> calls = new ArrayList<Call>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Call callInstance = this.baseService.getEntityById(Call.class,
                        id);
                Call originalCall = callInstance.clone();
                for (String fieldName : feildNameCollection) {
                    Object value = BeanUtil.getFieldValue(call, fieldName);
                    BeanUtil.setFieldValue(callInstance, fieldName, value);
                }
                callInstance.setUpdated_by(user);
                callInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(originalCall,
                        callInstance);
                allChangeLogs.addAll(changeLogs);
                calls.add(callInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (calls.size() > 0) {
                this.baseService.batchUpdate(calls);
                taskExecutor.execute(new Runnable() {
                    public void run() {
                        batchInserChangeLogs(changeLogsForSave);
                    }
                });
            }
        }
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @return original call record
     * @throws ParseException
     */
    private Call saveEntity() throws Exception {
        Call originalCall = null;
        if (call.getId() == null) {
            UserUtil.permissionCheck("create_call");
        } else {
            UserUtil.permissionCheck("update_call");
            originalCall = baseService.getEntityById(Call.class, call.getId());
            call.setContacts(originalCall.getContacts());
            call.setLeads(originalCall.getLeads());
            call.setUsers(originalCall.getUsers());
            call.setCreated_on(originalCall.getCreated_on());
            call.setCreated_by(originalCall.getCreated_by());
        }

        CallDirection direction = null;
        if (directionID != null) {
            direction = callDirectionService.getEntityById(CallDirection.class,
                    directionID);
        }
        call.setDirection(direction);
        CallStatus status = null;
        if (statusID != null) {
            status = callStatusService
                    .getEntityById(CallStatus.class, statusID);
        }
        call.setStatus(status);
        ReminderOption reminderOptionEmail = null;
        if (reminderOptionEmailID != null) {
            reminderOptionEmail = reminderOptionService.getEntityById(
                    ReminderOption.class, reminderOptionEmailID);
        }
        call.setReminder_option_email(reminderOptionEmail);
        EmailTemplate reminderTemplate = null;
        if (reminderTemplateID != null) {
            reminderTemplate = emailTemplateService.getEntityById(
                    EmailTemplate.class, reminderTemplateID);
        }
        call.setReminder_template(reminderTemplate);
        User user = null;
        if (this.getAssignedToID() != null) {
            user = userService
                    .getEntityById(User.class, this.getAssignedToID());
        }
        call.setAssigned_to(user);
        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        call.setOwner(owner);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        Date start_date = null;
        if (!CommonUtil.isNullOrEmpty(startDate)) {
            start_date = dateFormat.parse(startDate);
        }
        call.setStart_date(start_date);

        String relatedObject = call.getRelated_object();
        if ("Account".equals(relatedObject)) {
            call.setRelated_record(relatedAccountID);
        } else if ("CaseInstance".equals(relatedObject)) {
            call.setRelated_record(relatedCaseID);
        } else if ("Contact".equals(relatedObject)) {
            call.setRelated_record(relatedContactID);
        } else if ("Lead".equals(relatedObject)) {
            call.setRelated_record(relatedLeadID);
        } else if ("Opportunity".equals(relatedObject)) {
            call.setRelated_record(relatedOpportunityID);
        } else if ("Target".equals(relatedObject)) {
            call.setRelated_record(relatedTargetID);
        } else if ("Task".equals(relatedObject)) {
            call.setRelated_record(relatedTaskID);
        }
        super.updateBaseInfo(call);
        return originalCall;
    }

    /**
     * Gets Contact Relation Counts
     * 
     * @return null
     */
    public String getCallRelationCounts() throws Exception {
        long contactNumber = this.baseService
                .countsByParams(
                        "select count(*) from Call call join call.contacts where call.id = ?",
                        new Integer[] { this.getId() });
        long leadNumber = this.baseService
                .countsByParams(
                        "select count(*) from Call call join call.leads where call.id = ?",
                        new Integer[] { this.getId() });
        long userNumber = this.baseService
                .countsByParams(
                        "select count(*) from Call call join call.users where call.id = ?",
                        new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"contactNumber\":\"").append(contactNumber)
                .append("\",\"leadNumber\":\"").append(leadNumber)
                .append("\",\"userNumber\":\"").append(userNumber)
                .append("\"}");
        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
        return null;
    }

    /**
     * Prepares the list
     * 
     */
    public void prepare() throws Exception {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        this.statuses = callStatusService.getOptions(
                CallStatus.class.getSimpleName(), local);
        this.directions = callDirectionService.getOptions(
                CallDirection.class.getSimpleName(), local);
        this.reminderOptions = reminderOptionService.getOptions(
                ReminderOption.class.getSimpleName(), local);
        // Gets reminder email template list
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'callRemind' order by created_on";
        reminderTemplates = emailTemplateService.findByHQL(hql);
    }

    public IBaseService<Call> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Call> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the statusID
     */
    public Integer getStatusID() {
        return statusID;
    }

    /**
     * @param statusID
     *            the statusID to set
     */
    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     *            the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the reminderOptions
     */
    public List<ReminderOption> getReminderOptions() {
        return reminderOptions;
    }

    /**
     * @param reminderOptions
     *            the reminderOptions to set
     */
    public void setReminderOptions(List<ReminderOption> reminderOptions) {
        this.reminderOptions = reminderOptions;
    }

    /**
     * @return the call
     */
    public Call getCall() {
        return call;
    }

    /**
     * @param call
     *            the call to set
     */
    public void setCall(Call call) {
        this.call = call;
    }

    /**
     * @return the statuses
     */
    public List<CallStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<CallStatus> statuses) {
        this.statuses = statuses;
    }

    /**
     * @return the directionID
     */
    public Integer getDirectionID() {
        return directionID;
    }

    /**
     * @param directionID
     *            the directionID to set
     */
    public void setDirectionID(Integer directionID) {
        this.directionID = directionID;
    }

    /**
     * @return the directions
     */
    public List<CallDirection> getDirections() {
        return directions;
    }

    /**
     * @param directions
     *            the directions to set
     */
    public void setDirections(List<CallDirection> directions) {
        this.directions = directions;
    }

    /**
     * @return the baseService
     */
    public IBaseService<Call> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Call> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the relatedAccountID
     */
    public Integer getRelatedAccountID() {
        return relatedAccountID;
    }

    /**
     * @param relatedAccountID
     *            the relatedAccountID to set
     */
    public void setRelatedAccountID(Integer relatedAccountID) {
        this.relatedAccountID = relatedAccountID;
    }

    /**
     * @return the relatedCaseID
     */
    public Integer getRelatedCaseID() {
        return relatedCaseID;
    }

    /**
     * @param relatedCaseID
     *            the relatedCaseID to set
     */
    public void setRelatedCaseID(Integer relatedCaseID) {
        this.relatedCaseID = relatedCaseID;
    }

    /**
     * @return the relatedContactID
     */
    public Integer getRelatedContactID() {
        return relatedContactID;
    }

    /**
     * @param relatedContactID
     *            the relatedContactID to set
     */
    public void setRelatedContactID(Integer relatedContactID) {
        this.relatedContactID = relatedContactID;
    }

    /**
     * @return the relatedLeadID
     */
    public Integer getRelatedLeadID() {
        return relatedLeadID;
    }

    /**
     * @param relatedLeadID
     *            the relatedLeadID to set
     */
    public void setRelatedLeadID(Integer relatedLeadID) {
        this.relatedLeadID = relatedLeadID;
    }

    /**
     * @return the relatedOpportunityID
     */
    public Integer getRelatedOpportunityID() {
        return relatedOpportunityID;
    }

    /**
     * @param relatedOpportunityID
     *            the relatedOpportunityID to set
     */
    public void setRelatedOpportunityID(Integer relatedOpportunityID) {
        this.relatedOpportunityID = relatedOpportunityID;
    }

    /**
     * @return the relatedTargetID
     */
    public Integer getRelatedTargetID() {
        return relatedTargetID;
    }

    /**
     * @param relatedTargetID
     *            the relatedTargetID to set
     */
    public void setRelatedTargetID(Integer relatedTargetID) {
        this.relatedTargetID = relatedTargetID;
    }

    /**
     * @return the relatedTaskID
     */
    public Integer getRelatedTaskID() {
        return relatedTaskID;
    }

    /**
     * @param relatedTaskID
     *            the relatedTaskID to set
     */
    public void setRelatedTaskID(Integer relatedTaskID) {
        this.relatedTaskID = relatedTaskID;
    }

    /**
     * @return the userService
     */
    public IBaseService<User> getUserService() {
        return userService;
    }

    /**
     * @return the reminderOptionEmailID
     */
    public Integer getReminderOptionEmailID() {
        return reminderOptionEmailID;
    }

    /**
     * @param reminderOptionEmailID
     *            the reminderOptionEmailID to set
     */
    public void setReminderOptionEmailID(Integer reminderOptionEmailID) {
        this.reminderOptionEmailID = reminderOptionEmailID;
    }

    /**
     * @return the relatedAccountText
     */
    public String getRelatedAccountText() {
        return relatedAccountText;
    }

    /**
     * @return the relatedCaseText
     */
    public String getRelatedCaseText() {
        return relatedCaseText;
    }

    /**
     * @return the relatedContactText
     */
    public String getRelatedContactText() {
        return relatedContactText;
    }

    /**
     * @return the relatedLeadText
     */
    public String getRelatedLeadText() {
        return relatedLeadText;
    }

    /**
     * @return the relatedOpportunityText
     */
    public String getRelatedOpportunityText() {
        return relatedOpportunityText;
    }

    /**
     * @return the relatedTargetText
     */
    public String getRelatedTargetText() {
        return relatedTargetText;
    }

    /**
     * @return the relatedTaskText
     */
    public String getRelatedTaskText() {
        return relatedTaskText;
    }

    /**
     * @return the accountService
     */
    public IBaseService<Account> getAccountService() {
        return accountService;
    }

    /**
     * @param accountService
     *            the accountService to set
     */
    public void setAccountService(IBaseService<Account> accountService) {
        this.accountService = accountService;
    }

    /**
     * @return the caseService
     */
    public IBaseService<CaseInstance> getCaseService() {
        return caseService;
    }

    /**
     * @param caseService
     *            the caseService to set
     */
    public void setCaseService(IBaseService<CaseInstance> caseService) {
        this.caseService = caseService;
    }

    /**
     * @return the contactService
     */
    public IBaseService<Contact> getContactService() {
        return contactService;
    }

    /**
     * @param contactService
     *            the contactService to set
     */
    public void setContactService(IBaseService<Contact> contactService) {
        this.contactService = contactService;
    }

    /**
     * @return the leadService
     */
    public IBaseService<Lead> getLeadService() {
        return leadService;
    }

    /**
     * @param leadService
     *            the leadService to set
     */
    public void setLeadService(IBaseService<Lead> leadService) {
        this.leadService = leadService;
    }

    /**
     * @return the opportunityService
     */
    public IBaseService<Opportunity> getOpportunityService() {
        return opportunityService;
    }

    /**
     * @param opportunityService
     *            the opportunityService to set
     */
    public void setOpportunityService(
            IBaseService<Opportunity> opportunityService) {
        this.opportunityService = opportunityService;
    }

    /**
     * @return the targetService
     */
    public IBaseService<Target> getTargetService() {
        return targetService;
    }

    /**
     * @param targetService
     *            the targetService to set
     */
    public void setTargetService(IBaseService<Target> targetService) {
        this.targetService = targetService;
    }

    /**
     * @return the taskService
     */
    public IBaseService<Task> getTaskService() {
        return taskService;
    }

    /**
     * @param taskService
     *            the taskService to set
     */
    public void setTaskService(IBaseService<Task> taskService) {
        this.taskService = taskService;
    }

    /**
     * @return the callStatusService
     */
    public IOptionService<CallStatus> getCallStatusService() {
        return callStatusService;
    }

    /**
     * @param callStatusService
     *            the callStatusService to set
     */
    public void setCallStatusService(
            IOptionService<CallStatus> callStatusService) {
        this.callStatusService = callStatusService;
    }

    /**
     * @return the callDirectionService
     */
    public IOptionService<CallDirection> getCallDirectionService() {
        return callDirectionService;
    }

    /**
     * @param callDirectionService
     *            the callDirectionService to set
     */
    public void setCallDirectionService(
            IOptionService<CallDirection> callDirectionService) {
        this.callDirectionService = callDirectionService;
    }

    /**
     * @return the reminderOptionService
     */
    public IOptionService<ReminderOption> getReminderOptionService() {
        return reminderOptionService;
    }

    /**
     * @param reminderOptionService
     *            the reminderOptionService to set
     */
    public void setReminderOptionService(
            IOptionService<ReminderOption> reminderOptionService) {
        this.reminderOptionService = reminderOptionService;
    }

    /**
     * @return the mailService
     */
    public MailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService
     *            the mailService to set
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * @return the emailTemplateService
     */
    public IBaseService<EmailTemplate> getEmailTemplateService() {
        return emailTemplateService;
    }

    /**
     * @param emailTemplateService
     *            the emailTemplateService to set
     */
    public void setEmailTemplateService(
            IBaseService<EmailTemplate> emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the text_only
     */
    public boolean isText_only() {
        return text_only;
    }

    /**
     * @param text_only
     *            the text_only to set
     */
    public void setText_only(boolean text_only) {
        this.text_only = text_only;
    }

    /**
     * @return the html_body
     */
    public String getHtml_body() {
        return html_body;
    }

    /**
     * @param html_body
     *            the html_body to set
     */
    public void setHtml_body(String html_body) {
        this.html_body = html_body;
    }

    /**
     * @return the text_body
     */
    public String getText_body() {
        return text_body;
    }

    /**
     * @param text_body
     *            the text_body to set
     */
    public void setText_body(String text_body) {
        this.text_body = text_body;
    }

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     *            the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     *            the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @param relatedAccountText
     *            the relatedAccountText to set
     */
    public void setRelatedAccountText(String relatedAccountText) {
        this.relatedAccountText = relatedAccountText;
    }

    /**
     * @param relatedCaseText
     *            the relatedCaseText to set
     */
    public void setRelatedCaseText(String relatedCaseText) {
        this.relatedCaseText = relatedCaseText;
    }

    /**
     * @param relatedContactText
     *            the relatedContactText to set
     */
    public void setRelatedContactText(String relatedContactText) {
        this.relatedContactText = relatedContactText;
    }

    /**
     * @param relatedLeadText
     *            the relatedLeadText to set
     */
    public void setRelatedLeadText(String relatedLeadText) {
        this.relatedLeadText = relatedLeadText;
    }

    /**
     * @param relatedOpportunityText
     *            the relatedOpportunityText to set
     */
    public void setRelatedOpportunityText(String relatedOpportunityText) {
        this.relatedOpportunityText = relatedOpportunityText;
    }

    /**
     * @param relatedTargetText
     *            the relatedTargetText to set
     */
    public void setRelatedTargetText(String relatedTargetText) {
        this.relatedTargetText = relatedTargetText;
    }

    /**
     * @param relatedTaskText
     *            the relatedTaskText to set
     */
    public void setRelatedTaskText(String relatedTaskText) {
        this.relatedTaskText = relatedTaskText;
    }

    /**
     * @return the emailTemplates
     */
    public List<EmailTemplate> getEmailTemplates() {
        return emailTemplates;
    }

    /**
     * @param emailTemplates
     *            the emailTemplates to set
     */
    public void setEmailTemplates(List<EmailTemplate> emailTemplates) {
        this.emailTemplates = emailTemplates;
    }

    /**
     * @return the emailTemplateID
     */
    public Integer getEmailTemplateID() {
        return emailTemplateID;
    }

    /**
     * @param emailTemplateID
     *            the emailTemplateID to set
     */
    public void setEmailTemplateID(Integer emailTemplateID) {
        this.emailTemplateID = emailTemplateID;
    }

    public File[] getUpload() {
        return this.uploads;
    }

    public void setUpload(File[] upload) {
        this.uploads = upload;
    }

    public String[] getUploadFileName() {
        return this.uploadFileNames;
    }

    public void setUploadFileName(String[] uploadFileName) {
        this.uploadFileNames = uploadFileName;
    }

    public String[] getUploadContentType() {
        return this.uploadContentTypes;
    }

    public void setUploadContentType(String[] uploadContentType) {
        this.uploadContentTypes = uploadContentType;
    }

    /**
     * @return the reminderTemplates
     */
    public List<EmailTemplate> getReminderTemplates() {
        return reminderTemplates;
    }

    /**
     * @param reminderTemplates
     *            the reminderTemplates to set
     */
    public void setReminderTemplates(List<EmailTemplate> reminderTemplates) {
        this.reminderTemplates = reminderTemplates;
    }

    /**
     * @return the reminderTemplateID
     */
    public Integer getReminderTemplateID() {
        return reminderTemplateID;
    }

    /**
     * @param reminderTemplateID
     *            the reminderTemplateID to set
     */
    public void setReminderTemplateID(Integer reminderTemplateID) {
        this.reminderTemplateID = reminderTemplateID;
    }

    public IBaseService<ChangeLog> getChangeLogService() {
        return changeLogService;
    }

    public void setChangeLogService(IBaseService<ChangeLog> changeLogService) {
        this.changeLogService = changeLogService;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

}
