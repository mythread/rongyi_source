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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Account;
import com.gcrm.domain.CaseInstance;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Contact;
import com.gcrm.domain.EmailTemplate;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.MeetingStatus;
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
 * Edits Meeting
 * 
 */
public class EditMeetingAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;
    private static final Logger log = LoggerFactory.getLogger(EditMeetingAction.class);
    
    private IBaseService<Meeting> baseService;
    private IOptionService<MeetingStatus> meetingStatusService;
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
    private Meeting meeting;
    private List<MeetingStatus> statuses;
    private List<ReminderOption> reminderOptions;
    private List<EmailTemplate> emailTemplates;
    private List<EmailTemplate> reminderTemplates;
    private Integer statusID = null;
    private Integer reminderOptionEmailID = null;
    private Integer emailTemplateID = null;
    private Integer reminderTemplateID = null;
    private String startDate = null;
    private String endDate = null;
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
        Meeting originalMeeting = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalMeeting,
                meeting);
        // Validate Reminder Email Template
        if (meeting.isReminder_email() && reminderTemplateID == null) {
            String errorMessage = getText("error.reminderEamilTemplate");
            super.addActionError(errorMessage);
            return INPUT;
        }
        meeting = getBaseService().makePersistent(meeting);
        this.setId(meeting.getId());
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
     * @param originalMeeting
     *            original meeting record
     * @param meeting
     *            current meeting record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Meeting originalMeeting,
            Meeting meeting) {
        Collection<ChangeLog> changeLogs = null;
        if (originalMeeting != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = Meeting.class.getSimpleName();
            Integer recordID = meeting.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldSubject = CommonUtil.fromNullToEmpty(originalMeeting
                    .getSubject());
            String newSubject = CommonUtil
                    .fromNullToEmpty(meeting.getSubject());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.subject.label", oldSubject, newSubject, loginUser);

            String oldStatus = getOptionValue(originalMeeting.getStatus());
            String newStatus = getOptionValue(meeting.getStatus());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.status.label", oldStatus, newStatus, loginUser);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_EDIT_FORMAT);
            String oldStartDateValue = "";
            Date oldStartDate = originalMeeting.getStart_date();
            if (oldStartDate != null) {
                oldStartDateValue = dateFormat.format(oldStartDate);
            }
            String newStartDateValue = "";
            Date newStartDate = meeting.getStart_date();
            if (newStartDate != null) {
                newStartDateValue = dateFormat.format(newStartDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.start_date.label", oldStartDateValue,
                    newStartDateValue, loginUser);
            String oldEndDateValue = "";
            Date oldEndDate = originalMeeting.getEnd_date();
            if (oldEndDate != null) {
                oldEndDateValue = dateFormat.format(oldEndDate);
            }
            String newEndDateValue = "";
            Date newEndDate = meeting.getEnd_date();
            if (newEndDate != null) {
                newEndDateValue = dateFormat.format(newEndDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.end_date.label", oldEndDateValue, newEndDateValue,
                    loginUser);

            String oldRelatedObject = CommonUtil
                    .fromNullToEmpty(originalMeeting.getRelated_object());
            String newRelatedObject = CommonUtil.fromNullToEmpty(meeting
                    .getRelated_object());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.related_object.label", oldRelatedObject,
                    newRelatedObject, loginUser);

            String oldRelatedRecord = String.valueOf(originalMeeting
                    .getRelated_record());
            String newRelatedRecord = String.valueOf(meeting
                    .getRelated_record());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.related_record.label", oldRelatedRecord,
                    newRelatedRecord, loginUser);

            String oldLocation = CommonUtil.fromNullToEmpty(originalMeeting
                    .getLocation());
            String newLocation = CommonUtil.fromNullToEmpty(meeting
                    .getLocation());
            createChangeLog(changeLogs, entityName, recordID,
                    "meeting.location.label", oldLocation, newLocation,
                    loginUser);

            boolean oldReminderEmail = originalMeeting.isReminder_email();

            boolean newReminderEmail = meeting.isReminder_email();
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.reminder.label", String.valueOf(oldReminderEmail),
                    String.valueOf(newReminderEmail), loginUser);

            String oldReminderOption = getOptionValue(originalMeeting
                    .getReminder_option_email());
            String newReminderOption = getOptionValue(meeting
                    .getReminder_option_email());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.reminder_option_email_name.label",
                    oldReminderOption, newReminderOption, loginUser);

            String oldReminderTemplateName = "";
            EmailTemplate oldReminderTemplate = originalMeeting
                    .getReminder_template();
            if (oldReminderTemplate != null) {
                oldReminderTemplateName = CommonUtil
                        .fromNullToEmpty(oldReminderTemplate.getName());
            }
            String newReminderTemplateName = "";
            EmailTemplate newReminderTemplate = meeting.getReminder_template();
            if (newReminderTemplate != null) {
                newReminderTemplateName = CommonUtil
                        .fromNullToEmpty(newReminderTemplate.getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.reminder_template.label", oldReminderTemplateName,
                    newReminderTemplateName, loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalMeeting
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(meeting.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalMeeting.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = meeting.getAssigned_to();
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

        UserUtil.permissionCheck("update_meeting");
        meeting = baseService.getEntityById(Meeting.class, meeting.getId());
        Date start_date = meeting.getStart_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        startDate = "";
        if (start_date != null) {
            startDate = dateFormat.format(start_date);
        }
        Date end_date = meeting.getEnd_date();
        endDate = "";
        if (end_date != null) {
            endDate = dateFormat.format(end_date);
        }
        this.setId(meeting.getId());
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        User loginUser = (User) session
                .get(AuthenticationSuccessListener.LOGIN_USER);

        StringBuilder targetEmails = new StringBuilder("");
        Set<Contact> contacts = meeting.getContacts();
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
        Set<Lead> leads = meeting.getLeads();
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
        Set<User> users = meeting.getUsers();
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
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'meetingInvite' order by created_on";
        emailTemplates = emailTemplateService.findByHQL(hql);
        return SUCCESS;
    }

    public String selectTemplate() throws Exception {
        UserUtil.permissionCheck("update_meeting");
        meeting = baseService.getEntityById(Meeting.class, this.getId());
        Date start_date = meeting.getStart_date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        startDate = "";
        if (start_date != null) {
            startDate = dateFormat.format(start_date);
        }
        Date end_date = meeting.getEnd_date();
        endDate = "";
        if (end_date != null) {
            endDate = dateFormat.format(end_date);
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
            content = content.replaceAll("\\$meeting.subject",
                    CommonUtil.fromNullToEmpty(meeting.getSubject()));
            content = content.replaceAll("\\$meeting.start_date", startDate);
            content = content.replaceAll("\\$meeting.end_date", endDate);
            content = content.replaceAll("\\$meeting.location",
                    CommonUtil.fromNullToEmpty(meeting.getLocation()));
        }
        if (this.isText_only()) {
            this.setText_body(content);
        } else {
            this.setHtml_body(content);
        }
        // Gets email template list
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'meetingInvite' order by created_on";
        emailTemplates = emailTemplateService.findByHQL(hql);
        return SUCCESS;
    }

    /**
     * Sends invitation mail to all participants.
     * 
     * @return the SUCCESS result
     */
    public String send() throws Exception {

        UserUtil.permissionCheck("update_meeting");
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
            meeting = baseService.getEntityById(Meeting.class, this.getId());
            MeetingStatus status = meeting.getStatus();
            if (status != null) {
                statusID = status.getId();
            }
            ReminderOption reminderOptionEmail = meeting
                    .getReminder_option_email();
            if (reminderOptionEmail != null) {
                reminderOptionEmailID = reminderOptionEmail.getId();
            }
            EmailTemplate reminderTemplate = meeting.getReminder_template();
            if (reminderTemplate != null) {
                reminderTemplateID = reminderTemplate.getId();
            }
            User assignedTo = meeting.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            Date start_date = meeting.getStart_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_TIME_FORMAT);
            if (start_date != null) {
                startDate = dateFormat.format(start_date);
            }
            Date end_date = meeting.getEnd_date();
            if (end_date != null) {
                endDate = dateFormat.format(end_date);
            }
            String relatedObject = meeting.getRelated_object();
            Integer relatedRecord = meeting.getRelated_record();
            if (relatedRecord != null) {
                setRelatedRecord(relatedObject, relatedRecord);
            }
            this.getBaseInfo(meeting, Meeting.class.getSimpleName(),
                    Constant.CRM_NAMESPACE);
        } else {
            this.initBaseInfo();
            if (!CommonUtil.isNullOrEmpty(this.getRelationKey())) {
                meeting.setRelated_object(this.getRelationKey());
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
            this.relatedAccountText = this.accountService.getEntityById(
                    Account.class, relatedRecord).getName();
        } else if ("CaseInstance".equals(relatedObject)) {
            this.relatedCaseID = relatedRecord;
            this.relatedCaseText = this.caseService.getEntityById(
                    CaseInstance.class, relatedRecord).getSubject();
        } else if ("Contact".equals(relatedObject)) {
            this.relatedContactID = relatedRecord;
            this.relatedContactText = this.contactService.getEntityById(
                    Contact.class, relatedRecord).getName();
        } else if ("Lead".equals(relatedObject)) {
            this.relatedLeadID = relatedRecord;
            this.relatedLeadText = this.leadService.getEntityById(Lead.class,
                    relatedRecord).getName();
        } else if ("Opportunity".equals(relatedObject)) {
            this.relatedOpportunityID = relatedRecord;
            this.relatedOpportunityText = this.opportunityService
                    .getEntityById(Opportunity.class, relatedRecord).getName();
        } else if ("Target".equals(relatedObject)) {
            this.relatedTargetID = relatedRecord;
            this.relatedTargetText = this.targetService.getEntityById(
                    Target.class, relatedRecord).getName();
        } else if ("Task".equals(relatedObject)) {
            this.relatedTaskID = relatedRecord;
            this.relatedTaskText = this.taskService.getEntityById(Task.class,
                    relatedRecord).getSubject();
        }
    }

    /**
     * Mass update entity record information
     */
    public String massUpdate() throws Exception {
        saveEntity();
        String[] fieldNames = this.massUpdate;
        if (fieldNames != null) {
            String[] selectIDArray = this.seleteIDs.split(",");
            Collection<Meeting> meetings = new ArrayList<Meeting>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Meeting meetingInstance = this.baseService.getEntityById(
                        Meeting.class, id);
                Meeting originalMeeting = meetingInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(meeting, fieldName);
                    BeanUtil.setFieldValue(meetingInstance, fieldName, value);
                }
                meetingInstance.setUpdated_by(user);
                meetingInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(originalMeeting,
                        meetingInstance);
                allChangeLogs.addAll(changeLogs);
                meetings.add(meetingInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (meetings.size() > 0) {
                this.baseService.batchUpdate(meetings);
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
     * @return original meeting record
     * @throws ParseException
     */
    private Meeting saveEntity() throws Exception {
        Meeting originalMeeting = null;
        if (meeting.getId() == null) {
            UserUtil.permissionCheck("create_meeting");
        } else {
            UserUtil.permissionCheck("update_meeting");
            originalMeeting = baseService.getEntityById(Meeting.class,
                    meeting.getId());
            meeting.setContacts(originalMeeting.getContacts());
            meeting.setLeads(originalMeeting.getLeads());
            meeting.setUsers(originalMeeting.getUsers());
            meeting.setCreated_on(originalMeeting.getCreated_on());
            meeting.setCreated_by(originalMeeting.getCreated_by());
        }

        MeetingStatus status = null;
        if (statusID != null) {
            status = meetingStatusService.getEntityById(MeetingStatus.class,
                    statusID);
        }
        meeting.setStatus(status);
        ReminderOption reminderOptionEmail = null;
        if (reminderOptionEmailID != null) {
            reminderOptionEmail = reminderOptionService.getEntityById(
                    ReminderOption.class, reminderOptionEmailID);
        }
        meeting.setReminder_option_email(reminderOptionEmail);
        EmailTemplate reminderTemplate = null;
        if (reminderTemplateID != null) {
            reminderTemplate = emailTemplateService.getEntityById(
                    EmailTemplate.class, reminderTemplateID);
        }
        meeting.setReminder_template(reminderTemplate);
        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        meeting.setAssigned_to(assignedTo);
        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        meeting.setOwner(owner);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        Date start_date = null;
        if (!CommonUtil.isNullOrEmpty(startDate)) {
            start_date = dateFormat.parse(startDate);
        }
        meeting.setStart_date(start_date);
        Date end_date = null;
        if (!CommonUtil.isNullOrEmpty(endDate)) {
            end_date = dateFormat.parse(endDate);
        }
        meeting.setEnd_date(end_date);

        String relatedObject = meeting.getRelated_object();
        if ("Account".equals(relatedObject)) {
            meeting.setRelated_record(relatedAccountID);
        } else if ("CaseInstance".equals(relatedObject)) {
            meeting.setRelated_record(relatedCaseID);
        } else if ("Contact".equals(relatedObject)) {
            meeting.setRelated_record(relatedContactID);
        } else if ("Lead".equals(relatedObject)) {
            meeting.setRelated_record(relatedLeadID);
        } else if ("Opportunity".equals(relatedObject)) {
            meeting.setRelated_record(relatedOpportunityID);
        } else if ("Target".equals(relatedObject)) {
            meeting.setRelated_record(relatedTargetID);
        } else if ("Task".equals(relatedObject)) {
            meeting.setRelated_record(relatedTaskID);
        }
        super.updateBaseInfo(meeting);
        return originalMeeting;
    }

    /**
     * Gets Meeting Relation Counts
     * 
     * @return null
     */
    public String getMeetingRelationCounts() throws Exception {
        long contactNumber = this.baseService
                .countsByParams(
                        "select count(*) from Meeting meeting join meeting.contacts where meeting.id = ?",
                        new Integer[] { this.getId() });
        long leadNumber = this.baseService
                .countsByParams(
                        "select count(*) from Meeting meeting join meeting.leads where meeting.id = ?",
                        new Integer[] { this.getId() });
        long userNumber = this.baseService
                .countsByParams(
                        "select count(*) from Meeting meeting join meeting.users where meeting.id = ?",
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
        this.statuses = meetingStatusService.getOptions(
                MeetingStatus.class.getSimpleName(), local);
        this.reminderOptions = reminderOptionService.getOptions(
                ReminderOption.class.getSimpleName(), local);
        // Gets reminder email template list
        String hql = "select new EmailTemplate(id,name) from EmailTemplate where type = 'meetingRemind' order by created_on";
        reminderTemplates = emailTemplateService.findByHQL(hql);
        log.error("execute select meetingStatus ...........");
    }

    /**
     * @return the baseService
     */
    public IBaseService<Meeting> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Meeting> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the userService
     */
    public IBaseService<User> getUserService() {
        return userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

    /**
     * @return the meeting
     */
    public Meeting getMeeting() {
        return meeting;
    }

    /**
     * @param meeting
     *            the meeting to set
     */
    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    /**
     * @return the statuses
     */
    public List<MeetingStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<MeetingStatus> statuses) {
        this.statuses = statuses;
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
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
     * @return the relatedAccountText
     */
    public String getRelatedAccountText() {
        return relatedAccountText;
    }

    /**
     * @param relatedAccountText
     *            the relatedAccountText to set
     */
    public void setRelatedAccountText(String relatedAccountText) {
        this.relatedAccountText = relatedAccountText;
    }

    /**
     * @return the relatedCaseText
     */
    public String getRelatedCaseText() {
        return relatedCaseText;
    }

    /**
     * @param relatedCaseText
     *            the relatedCaseText to set
     */
    public void setRelatedCaseText(String relatedCaseText) {
        this.relatedCaseText = relatedCaseText;
    }

    /**
     * @return the relatedContactText
     */
    public String getRelatedContactText() {
        return relatedContactText;
    }

    /**
     * @param relatedContactText
     *            the relatedContactText to set
     */
    public void setRelatedContactText(String relatedContactText) {
        this.relatedContactText = relatedContactText;
    }

    /**
     * @return the relatedLeadText
     */
    public String getRelatedLeadText() {
        return relatedLeadText;
    }

    /**
     * @param relatedLeadText
     *            the relatedLeadText to set
     */
    public void setRelatedLeadText(String relatedLeadText) {
        this.relatedLeadText = relatedLeadText;
    }

    /**
     * @return the relatedOpportunityText
     */
    public String getRelatedOpportunityText() {
        return relatedOpportunityText;
    }

    /**
     * @param relatedOpportunityText
     *            the relatedOpportunityText to set
     */
    public void setRelatedOpportunityText(String relatedOpportunityText) {
        this.relatedOpportunityText = relatedOpportunityText;
    }

    /**
     * @return the relatedTargetText
     */
    public String getRelatedTargetText() {
        return relatedTargetText;
    }

    /**
     * @param relatedTargetText
     *            the relatedTargetText to set
     */
    public void setRelatedTargetText(String relatedTargetText) {
        this.relatedTargetText = relatedTargetText;
    }

    /**
     * @return the relatedTaskText
     */
    public String getRelatedTaskText() {
        return relatedTaskText;
    }

    /**
     * @param relatedTaskText
     *            the relatedTaskText to set
     */
    public void setRelatedTaskText(String relatedTaskText) {
        this.relatedTaskText = relatedTaskText;
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
     * @return the meetingStatusService
     */
    public IOptionService<MeetingStatus> getMeetingStatusService() {
        return meetingStatusService;
    }

    /**
     * @param meetingStatusService
     *            the meetingStatusService to set
     */
    public void setMeetingStatusService(
            IOptionService<MeetingStatus> meetingStatusService) {
        this.meetingStatusService = meetingStatusService;
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
