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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Account;
import com.gcrm.domain.CaseInstance;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.Target;
import com.gcrm.domain.Task;
import com.gcrm.domain.TaskPriority;
import com.gcrm.domain.TaskStatus;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Task
 * 
 */
public class EditTaskAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Task> baseService;
    private IOptionService<TaskStatus> taskStatusService;
    private IBaseService<Contact> contactService;
    private IOptionService<TaskPriority> taskPriorityService;
    private IBaseService<User> userService;
    private IBaseService<Account> accountService;
    private IBaseService<CaseInstance> caseService;
    private IBaseService<Lead> leadService;
    private IBaseService<Opportunity> opportunityService;
    private IBaseService<Target> targetService;
    private IBaseService<Task> taskService;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private Task task = new Task();
    private List<TaskStatus> statuses;
    private List<TaskPriority> priorities;
    private Integer statusID = null;
    private Integer priorityID = null;
    private Integer contactID = null;
    private String contactText = null;
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
    private String dueDate = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        Task originalTask = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalTask, task);
        task = getBaseService().makePersistent(task);
        this.setId(task.getId());
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
     * @param originalTask
     *            original task record
     * @param task
     *            current task record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Task originalTask, Task task) {
        Collection<ChangeLog> changeLogs = null;
        if (originalTask != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = Task.class.getSimpleName();
            Integer recordID = task.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldSubject = CommonUtil.fromNullToEmpty(originalTask
                    .getSubject());
            String newSubject = CommonUtil.fromNullToEmpty(task.getSubject());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.subject.label", oldSubject, newSubject, loginUser);

            String oldStatus = getOptionValue(originalTask.getStatus());
            String newStatus = getOptionValue(task.getStatus());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.status.label", oldStatus, newStatus, loginUser);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_EDIT_FORMAT);
            String oldStartDateValue = "";
            Date oldStartDate = originalTask.getStart_date();
            if (oldStartDate != null) {
                oldStartDateValue = dateFormat.format(oldStartDate);
            }
            String newStartDateValue = "";
            Date newStartDate = task.getStart_date();
            if (newStartDate != null) {
                newStartDateValue = dateFormat.format(newStartDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.start_date.label", oldStartDateValue,
                    newStartDateValue, loginUser);

            String oldDueDateValue = "";
            Date oldDueDate = originalTask.getDue_date();
            if (oldDueDate != null) {
                oldDueDateValue = dateFormat.format(oldDueDate);
            }
            String newDueDateValue = "";
            Date newDueDate = task.getDue_date();
            if (newDueDate != null) {
                newDueDateValue = dateFormat.format(newDueDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "task.due_date.label", oldDueDateValue, newDueDateValue,
                    loginUser);

            String oldRelatedObject = CommonUtil.fromNullToEmpty(originalTask
                    .getRelated_object());
            String newRelatedObject = CommonUtil.fromNullToEmpty(task
                    .getRelated_object());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.related_object.label", oldRelatedObject,
                    newRelatedObject, loginUser);

            String oldRelatedRecord = String.valueOf(originalTask
                    .getRelated_record());
            String newRelatedRecord = String.valueOf(task.getRelated_record());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.related_record.label", oldRelatedRecord,
                    newRelatedRecord, loginUser);

            String oldContactName = "";
            Contact oldContact = originalTask.getContact();
            if (oldContact != null) {
                oldContactName = CommonUtil.fromNullToEmpty(oldContact
                        .getName());
            }
            String newContactName = "";
            Contact newContact = task.getContact();
            if (newContact != null) {
                newContactName = CommonUtil.fromNullToEmpty(newContact
                        .getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.contact.label", oldContactName, newContactName,
                    loginUser);

            String oldTaskPriority = getOptionValue(originalTask.getPriority());
            String newTaskPriority = getOptionValue(task.getPriority());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.priority.label", oldTaskPriority, newTaskPriority,
                    loginUser);

            String oldDescription = CommonUtil.fromNullToEmpty(originalTask
                    .getDescription());
            String newDescription = CommonUtil.fromNullToEmpty(task
                    .getDescription());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.description.label", oldDescription, newDescription,
                    loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalTask
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(task.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalTask.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = task.getAssigned_to();
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
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            task = baseService.getEntityById(Task.class, this.getId());
            TaskStatus status = task.getStatus();
            if (status != null) {
                statusID = status.getId();
            }
            TaskPriority priority = task.getPriority();
            if (priority != null) {
                priorityID = priority.getId();
            }
            Contact contact = task.getContact();
            if (contact != null) {
                contactID = contact.getId();
                contactText = contact.getName();
            }
            User assignedTo = task.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            Date start_date = task.getStart_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_TIME_FORMAT);
            if (start_date != null) {
                startDate = dateFormat.format(start_date);
            }
            Date due_date = task.getDue_date();
            if (due_date != null) {
                dueDate = dateFormat.format(due_date);
            }
            String relatedObject = task.getRelated_object();
            Integer relatedRecord = task.getRelated_record();
            if (relatedRecord != null) {
                setRelatedRecord(relatedObject, relatedRecord);
            }
            this.getBaseInfo(task, Task.class.getSimpleName(),
                    Constant.CRM_NAMESPACE);
        } else {
            this.initBaseInfo();
            if (!CommonUtil.isNullOrEmpty(this.getRelationKey())) {
                task.setRelated_object(this.getRelationKey());
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
        } else if ("Case".equals(relatedObject)) {
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
            Collection<Task> tasks = new ArrayList<Task>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Task taskInstance = this.baseService.getEntityById(Task.class,
                        id);
                Task originalTask = taskInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(task, fieldName);
                    BeanUtil.setFieldValue(taskInstance, fieldName, value);
                }
                taskInstance.setUpdated_by(user);
                taskInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(originalTask,
                        taskInstance);
                allChangeLogs.addAll(changeLogs);
                tasks.add(taskInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (tasks.size() > 0) {
                this.baseService.batchUpdate(tasks);
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
     * @return original task record
     * @throws ParseException
     */
    private Task saveEntity() throws Exception {
        Task originalTask = null;
        if (task.getId() == null) {
            UserUtil.permissionCheck("create_task");
        } else {
            UserUtil.permissionCheck("update_task");
            originalTask = baseService.getEntityById(Task.class, task.getId());
        }

        TaskStatus status = null;
        if (statusID != null) {
            status = taskStatusService
                    .getEntityById(TaskStatus.class, statusID);
        }
        task.setStatus(status);
        TaskPriority priority = null;
        if (priorityID != null) {
            priority = taskPriorityService.getEntityById(TaskPriority.class,
                    priorityID);
        }
        task.setPriority(priority);
        Contact contact = null;
        if (contactID != null) {
            contact = contactService.getEntityById(Contact.class, contactID);
        }
        task.setContact(contact);
        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        task.setAssigned_to(assignedTo);
        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        task.setOwner(owner);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        Date start_date = null;
        if (!CommonUtil.isNullOrEmpty(startDate)) {
            start_date = dateFormat.parse(startDate);
        }
        task.setStart_date(start_date);
        Date due_date = null;
        if (!CommonUtil.isNullOrEmpty(dueDate)) {
            due_date = dateFormat.parse(dueDate);
        }
        task.setDue_date(due_date);
        String relatedObject = task.getRelated_object();
        if ("Account".equals(relatedObject)) {
            task.setRelated_record(relatedAccountID);
        } else if ("Case".equals(relatedObject)) {
            task.setRelated_record(relatedCaseID);
        } else if ("Contact".equals(relatedObject)) {
            task.setRelated_record(relatedContactID);
        } else if ("Lead".equals(relatedObject)) {
            task.setRelated_record(relatedLeadID);
        } else if ("Opportunity".equals(relatedObject)) {
            task.setRelated_record(relatedOpportunityID);
        } else if ("Target".equals(relatedObject)) {
            task.setRelated_record(relatedTargetID);
        } else if ("Task".equals(relatedObject)) {
            task.setRelated_record(relatedTaskID);
        }
        super.updateBaseInfo(task);
        return originalTask;
    }

    /**
     * Prepares the list
     * 
     */
    public void prepare() throws Exception {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        this.statuses = taskStatusService.getOptions(
                TaskStatus.class.getSimpleName(), local);
        this.priorities = taskPriorityService.getOptions(
                TaskPriority.class.getSimpleName(), local);
    }

    /**
     * @return the baseService
     */
    public IBaseService<Task> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Task> baseService) {
        this.baseService = baseService;
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
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    /**
     * @param task
     *            the task to set
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * @return the statuses
     */
    public List<TaskStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<TaskStatus> statuses) {
        this.statuses = statuses;
    }

    /**
     * @return the priorities
     */
    public List<TaskPriority> getPriorities() {
        return priorities;
    }

    /**
     * @param priorities
     *            the priorities to set
     */
    public void setPriorities(List<TaskPriority> priorities) {
        this.priorities = priorities;
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
     * @return the priorityID
     */
    public Integer getPriorityID() {
        return priorityID;
    }

    /**
     * @param priorityID
     *            the priorityID to set
     */
    public void setPriorityID(Integer priorityID) {
        this.priorityID = priorityID;
    }

    /**
     * @return the contactID
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * @param contactID
     *            the contactID to set
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
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
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate
     *            the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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
     * @return the contactText
     */
    public String getContactText() {
        return contactText;
    }

    /**
     * @param contactText
     *            the contactText to set
     */
    public void setContactText(String contactText) {
        this.contactText = contactText;
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
     * @return the taskStatusService
     */
    public IOptionService<TaskStatus> getTaskStatusService() {
        return taskStatusService;
    }

    /**
     * @param taskStatusService
     *            the taskStatusService to set
     */
    public void setTaskStatusService(
            IOptionService<TaskStatus> taskStatusService) {
        this.taskStatusService = taskStatusService;
    }

    /**
     * @return the taskPriorityService
     */
    public IOptionService<TaskPriority> getTaskPriorityService() {
        return taskPriorityService;
    }

    /**
     * @param taskPriorityService
     *            the taskPriorityService to set
     */
    public void setTaskPriorityService(
            IOptionService<TaskPriority> taskPriorityService) {
        this.taskPriorityService = taskPriorityService;
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
