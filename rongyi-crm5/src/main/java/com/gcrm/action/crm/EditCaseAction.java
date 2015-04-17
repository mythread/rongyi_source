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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Account;
import com.gcrm.domain.CaseInstance;
import com.gcrm.domain.CaseOrigin;
import com.gcrm.domain.CasePriority;
import com.gcrm.domain.CaseReason;
import com.gcrm.domain.CaseStatus;
import com.gcrm.domain.CaseType;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Document;
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
 * Edits Case
 * 
 */
public class EditCaseAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<CaseInstance> baseService;
    private IOptionService<CaseStatus> caseStatusService;
    private IOptionService<CasePriority> casePriorityService;
    private IOptionService<CaseType> caseTypeService;
    private IOptionService<CaseOrigin> caseOriginService;
    private IOptionService<CaseReason> caseReasonService;
    private IBaseService<Account> accountService;
    private IBaseService<Document> documentService;
    private IBaseService<Contact> contactService;
    private IBaseService<User> userService;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private CaseInstance caseInstance;
    private List<CaseStatus> statuses;
    private List<CasePriority> casePriorities;
    private List<CaseType> caseTypes;
    private List<CaseOrigin> caseOrigins;
    private List<CaseReason> caseReasons;
    private Integer statusID = null;
    private Integer priorityID = null;
    private Integer typeID = null;
    private Integer originID = null;
    private Integer reasonID = null;
    private Integer accountID = null;
    private String accountText = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        CaseInstance originalCase = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalCase,
                caseInstance);
        caseInstance = getBaseService().makePersistent(caseInstance);
        this.setId(caseInstance.getId());
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
     * @param originalCaseInstance
     *            original caseInstance record
     * @param caseInstance
     *            current caseInstance record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(CaseInstance originalCaseInstance,
            CaseInstance caseInstance) {
        Collection<ChangeLog> changeLogs = null;
        if (originalCaseInstance != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = CaseInstance.class.getSimpleName();
            Integer recordID = caseInstance.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldSubject = CommonUtil.fromNullToEmpty(originalCaseInstance
                    .getSubject());
            String newSubject = CommonUtil.fromNullToEmpty(caseInstance
                    .getSubject());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.subject.label", oldSubject, newSubject, loginUser);

            String oldPriority = getOptionValue(originalCaseInstance
                    .getPriority());
            String newPriority = getOptionValue(caseInstance.getPriority());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.priority.label", oldPriority, newPriority,
                    loginUser);

            String oldStatus = getOptionValue(originalCaseInstance.getStatus());
            String newStatus = getOptionValue(caseInstance.getStatus());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.status.label", oldStatus, newStatus, loginUser);

            String oldAccountName = "";
            Account oldAccount = originalCaseInstance.getAccount();
            if (oldAccount != null) {
                oldAccountName = CommonUtil.fromNullToEmpty(oldAccount
                        .getName());
            }
            String newAccountName = "";
            Account newAccount = caseInstance.getAccount();
            if (newAccount != null) {
                newAccountName = CommonUtil.fromNullToEmpty(newAccount
                        .getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.account.label", oldAccountName, newAccountName,
                    loginUser);

            String oldType = getOptionValue(originalCaseInstance.getType());
            String newType = getOptionValue(caseInstance.getType());
            createChangeLog(changeLogs, entityName, recordID,
                    "case.type.label", oldType, newType, loginUser);

            String oldOrigin = getOptionValue(originalCaseInstance.getOrigin());
            String newOrigin = getOptionValue(caseInstance.getOrigin());
            createChangeLog(changeLogs, entityName, recordID,
                    "case.origin.label", oldOrigin, newOrigin, loginUser);

            String oldReason = getOptionValue(originalCaseInstance.getReason());
            String newReason = getOptionValue(caseInstance.getReason());
            createChangeLog(changeLogs, entityName, recordID,
                    "case.reason.label", oldReason, newReason, loginUser);

            String oldResolution = CommonUtil
                    .fromNullToEmpty(originalCaseInstance.getResolution());
            String newResolution = CommonUtil.fromNullToEmpty(caseInstance
                    .getResolution());
            createChangeLog(changeLogs, entityName, recordID,
                    "case.resolution.label", oldResolution, newResolution,
                    loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalCaseInstance
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(caseInstance
                    .getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalCaseInstance.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = caseInstance.getAssigned_to();
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
            caseInstance = baseService.getEntityById(CaseInstance.class,
                    this.getId());
            CaseStatus status = caseInstance.getStatus();
            if (status != null) {
                statusID = status.getId();
            }
            CasePriority priority = caseInstance.getPriority();
            if (priority != null) {
                priorityID = priority.getId();
            }
            CaseType type = caseInstance.getType();
            if (type != null) {
                typeID = type.getId();
            }
            CaseOrigin origin = caseInstance.getOrigin();
            if (origin != null) {
                originID = origin.getId();
            }
            CaseReason reason = caseInstance.getReason();
            if (reason != null) {
                reasonID = reason.getId();
            }
            Account account = caseInstance.getAccount();
            if (account != null) {
                accountID = account.getId();
                accountText = account.getName();
            }
            User assignedTo = caseInstance.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            this.getBaseInfo(caseInstance, CaseInstance.class.getSimpleName(),
                    Constant.CRM_NAMESPACE);
        } else {
            this.initBaseInfo();
        }
        return SUCCESS;
    }

    /**
     * Mass update entity record information
     */
    public String massUpdate() throws Exception {
        saveEntity();
        String[] fieldNames = this.massUpdate;
        if (fieldNames != null) {
            String[] selectIDArray = this.seleteIDs.split(",");
            Collection<CaseInstance> cases = new ArrayList<CaseInstance>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                CaseInstance newCaseInstance = this.baseService.getEntityById(
                        CaseInstance.class, id);
                CaseInstance originalCaseInstance = newCaseInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(caseInstance,
                            fieldName);
                    BeanUtil.setFieldValue(newCaseInstance, fieldName, value);
                }
                newCaseInstance.setUpdated_by(user);
                newCaseInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(
                        originalCaseInstance, newCaseInstance);
                allChangeLogs.addAll(changeLogs);
                cases.add(newCaseInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (cases.size() > 0) {
                this.baseService.batchUpdate(cases);
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
     * @return original caseInstance record
     * @throws Exception
     */
    private CaseInstance saveEntity() throws Exception {
        CaseInstance originalCase = null;
        if (caseInstance.getId() == null) {
            UserUtil.permissionCheck("create_case");
        } else {
            UserUtil.permissionCheck("update_case");
            originalCase = baseService.getEntityById(CaseInstance.class,
                    caseInstance.getId());
            caseInstance.setContacts(originalCase.getContacts());
            caseInstance.setDocuments(originalCase.getDocuments());
            caseInstance.setCreated_on(originalCase.getCreated_on());
            caseInstance.setCreated_by(originalCase.getCreated_by());
        }

        CaseStatus status = null;
        if (statusID != null) {
            status = caseStatusService
                    .getEntityById(CaseStatus.class, statusID);
        }
        caseInstance.setStatus(status);
        CasePriority priority = null;
        if (priorityID != null) {
            priority = casePriorityService.getEntityById(CasePriority.class,
                    priorityID);
        }
        caseInstance.setPriority(priority);
        CaseType type = null;
        if (typeID != null) {
            type = caseTypeService.getEntityById(CaseType.class, typeID);
        }
        caseInstance.setType(type);
        CaseOrigin origin = null;
        if (originID != null) {
            origin = caseOriginService
                    .getEntityById(CaseOrigin.class, originID);
        }
        caseInstance.setOrigin(origin);
        CaseReason reason = null;
        if (reasonID != null) {
            reason = caseReasonService
                    .getEntityById(CaseReason.class, reasonID);
        }
        caseInstance.setReason(reason);
        Account account = null;
        if (accountID != null) {
            account = accountService.getEntityById(Account.class, accountID);
        }
        caseInstance.setAccount(account);

        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        caseInstance.setAssigned_to(assignedTo);

        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        caseInstance.setOwner(owner);

        if ("Document".equals(this.getRelationKey())) {
            Document document = documentService.getEntityById(Document.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Document> documents = caseInstance.getDocuments();
            if (documents == null) {
                documents = new HashSet<Document>();
            }
            documents.add(document);
        } else if ("Contact".equals(this.getRelationKey())) {
            Contact contact = contactService.getEntityById(Contact.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Contact> contacts = caseInstance.getContacts();
            if (contacts == null) {
                contacts = new HashSet<Contact>();
            }
            contacts.add(contact);
        }
        super.updateBaseInfo(caseInstance);
        return originalCase;
    }

    /**
     * Gets Case Relation Counts
     * 
     * @return null
     */
    public String getCaseRelationCounts() throws Exception {
        long contactNumber = this.baseService
                .countsByParams(
                        "select count(*) from CaseInstance caseInstance join caseInstance.contacts where caseInstance.id = ?",
                        new Integer[] { this.getId() });
        long documentNumber = this.baseService
                .countsByParams(
                        "select count(*) from CaseInstance caseInstance join caseInstance.documents where caseInstance.id = ?",
                        new Integer[] { this.getId() });
        long taskNumber = this.baseService
                .countsByParams(
                        "select count(task.id) from Task task where related_object='Case' and related_record = ?",
                        new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"contactNumber\":\"").append(contactNumber)
                .append("\",\"documentNumber\":\"").append(documentNumber)
                .append("\",\"taskNumber\":\"").append(taskNumber)
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
        this.statuses = caseStatusService.getOptions(
                CaseStatus.class.getSimpleName(), local);
        this.casePriorities = casePriorityService.getOptions(
                CasePriority.class.getSimpleName(), local);
        this.caseTypes = caseTypeService.getOptions(
                CaseType.class.getSimpleName(), local);
        this.caseOrigins = caseOriginService.getOptions(
                CaseOrigin.class.getSimpleName(), local);
        this.caseReasons = caseReasonService.getOptions(
                CaseReason.class.getSimpleName(), local);
    }

    /**
     * @return the baseService
     */
    public IBaseService<CaseInstance> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<CaseInstance> baseService) {
        this.baseService = baseService;
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
     * @return the caseInstance
     */
    public CaseInstance getCaseInstance() {
        return caseInstance;
    }

    /**
     * @param caseInstance
     *            the caseInstance to set
     */
    public void setCaseInstance(CaseInstance caseInstance) {
        this.caseInstance = caseInstance;
    }

    /**
     * @return the statuses
     */
    public List<CaseStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<CaseStatus> statuses) {
        this.statuses = statuses;
    }

    /**
     * @return the casePriorities
     */
    public List<CasePriority> getCasePriorities() {
        return casePriorities;
    }

    /**
     * @param casePriorities
     *            the casePriorities to set
     */
    public void setCasePriorities(List<CasePriority> casePriorities) {
        this.casePriorities = casePriorities;
    }

    /**
     * @return the caseTypes
     */
    public List<CaseType> getCaseTypes() {
        return caseTypes;
    }

    /**
     * @param caseTypes
     *            the caseTypes to set
     */
    public void setCaseTypes(List<CaseType> caseTypes) {
        this.caseTypes = caseTypes;
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
     * @return the typeID
     */
    public Integer getTypeID() {
        return typeID;
    }

    /**
     * @param typeID
     *            the typeID to set
     */
    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    /**
     * @return the accountID
     */
    public Integer getAccountID() {
        return accountID;
    }

    /**
     * @param accountID
     *            the accountID to set
     */
    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    /**
     * @return the documentService
     */
    public IBaseService<Document> getDocumentService() {
        return documentService;
    }

    /**
     * @param documentService
     *            the documentService to set
     */
    public void setDocumentService(IBaseService<Document> documentService) {
        this.documentService = documentService;
    }

    /**
     * @return the caseOrigins
     */
    public List<CaseOrigin> getCaseOrigins() {
        return caseOrigins;
    }

    /**
     * @param caseOrigins
     *            the caseOrigins to set
     */
    public void setCaseOrigins(List<CaseOrigin> caseOrigins) {
        this.caseOrigins = caseOrigins;
    }

    /**
     * @return the caseReasons
     */
    public List<CaseReason> getCaseReasons() {
        return caseReasons;
    }

    /**
     * @param caseReasons
     *            the caseReasons to set
     */
    public void setCaseReasons(List<CaseReason> caseReasons) {
        this.caseReasons = caseReasons;
    }

    /**
     * @return the originID
     */
    public Integer getOriginID() {
        return originID;
    }

    /**
     * @param originID
     *            the originID to set
     */
    public void setOriginID(Integer originID) {
        this.originID = originID;
    }

    /**
     * @return the reasonID
     */
    public Integer getReasonID() {
        return reasonID;
    }

    /**
     * @param reasonID
     *            the reasonID to set
     */
    public void setReasonID(Integer reasonID) {
        this.reasonID = reasonID;
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
     * @return the accountText
     */
    public String getAccountText() {
        return accountText;
    }

    /**
     * @param accountText
     *            the accountText to set
     */
    public void setAccountText(String accountText) {
        this.accountText = accountText;
    }

    /**
     * @return the caseStatusService
     */
    public IOptionService<CaseStatus> getCaseStatusService() {
        return caseStatusService;
    }

    /**
     * @param caseStatusService
     *            the caseStatusService to set
     */
    public void setCaseStatusService(
            IOptionService<CaseStatus> caseStatusService) {
        this.caseStatusService = caseStatusService;
    }

    /**
     * @return the casePriorityService
     */
    public IOptionService<CasePriority> getCasePriorityService() {
        return casePriorityService;
    }

    /**
     * @param casePriorityService
     *            the casePriorityService to set
     */
    public void setCasePriorityService(
            IOptionService<CasePriority> casePriorityService) {
        this.casePriorityService = casePriorityService;
    }

    /**
     * @return the caseTypeService
     */
    public IOptionService<CaseType> getCaseTypeService() {
        return caseTypeService;
    }

    /**
     * @param caseTypeService
     *            the caseTypeService to set
     */
    public void setCaseTypeService(IOptionService<CaseType> caseTypeService) {
        this.caseTypeService = caseTypeService;
    }

    /**
     * @return the caseOriginService
     */
    public IOptionService<CaseOrigin> getCaseOriginService() {
        return caseOriginService;
    }

    /**
     * @param caseOriginService
     *            the caseOriginService to set
     */
    public void setCaseOriginService(
            IOptionService<CaseOrigin> caseOriginService) {
        this.caseOriginService = caseOriginService;
    }

    /**
     * @return the caseReasonService
     */
    public IOptionService<CaseReason> getCaseReasonService() {
        return caseReasonService;
    }

    /**
     * @param caseReasonService
     *            the caseReasonService to set
     */
    public void setCaseReasonService(
            IOptionService<CaseReason> caseReasonService) {
        this.caseReasonService = caseReasonService;
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
