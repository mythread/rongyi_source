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
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Salutation;
import com.gcrm.domain.Target;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.service.ITargetService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Target
 * 
 */
public class EditTargetAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private ITargetService baseService;
    private IBaseService<Account> accountService;
    private IBaseService<Lead> leadService;
    private IBaseService<User> userService;
    private IBaseService<TargetList> targetListService;
    private IOptionService<Salutation> salutationService;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private List<Salutation> salutations;
    private Target target;
    private Lead lead;
    private Integer accountID = null;
    private Integer salutationID = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        Target originalTarget = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalTarget,
                target);
        target = getBaseService().makePersistent(target);
        this.setId(target.getId());
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
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            target = baseService.getEntityById(Target.class, this.getId());
            Account account = target.getAccount();
            if (account != null) {
                accountID = account.getId();
            }

            Salutation salutation = target.getSalutation();
            if (salutation != null) {
                salutationID = salutation.getId();
            }

            Integer leadID = target.getLead_id();
            if (leadID != null) {
                try {
                    lead = this.getLeadService().getEntityById(Lead.class,
                            leadID);
                } catch (Exception e) {
                    // in case the converted lead is deleted
                    lead = null;
                }
            }

            User assignedTo = target.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            this.getBaseInfo(target, Target.class.getSimpleName(),
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
            Collection<Target> targets = new ArrayList<Target>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Target targetInstance = this.baseService.getEntityById(
                        Target.class, id);
                Target originalTarget = targetInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(target, fieldName);
                    BeanUtil.setFieldValue(targetInstance, fieldName, value);
                }
                targetInstance.setUpdated_by(user);
                targetInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(originalTarget,
                        targetInstance);
                allChangeLogs.addAll(changeLogs);
                targets.add(targetInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (targets.size() > 0) {
                this.baseService.batchUpdate(targets);
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
     * @throws Exception
     */
    private Target saveEntity() throws Exception {
        Target originalTarget = null;
        if (target.getId() == null) {
            UserUtil.permissionCheck("create_target");
        } else {
            UserUtil.permissionCheck("update_target");
            originalTarget = baseService.getEntityById(Target.class,
                    target.getId());
            target.setTargetLists(originalTarget.getTargetLists());
            target.setCreated_on(originalTarget.getCreated_on());
            target.setCreated_by(originalTarget.getCreated_by());
        }

        Account account = null;
        if (accountID != null) {
            account = accountService.getEntityById(Account.class, accountID);
        }
        target.setAccount(account);

        Salutation salutation = null;
        if (salutationID != null) {
            salutation = salutationService.getEntityById(Salutation.class,
                    salutationID);
        }
        target.setSalutation(salutation);

        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        target.setAssigned_to(assignedTo);
        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        target.setOwner(owner);

        if ("TargetList".equals(this.getRelationKey())) {
            TargetList targetList = targetListService.getEntityById(
                    TargetList.class, Integer.valueOf(this.getRelationValue()));
            Set<TargetList> targetLists = target.getTargetLists();
            if (targetLists == null) {
                targetLists = new HashSet<TargetList>();
            }
            targetLists.add(targetList);
        }
        super.updateBaseInfo(target);
        return originalTarget;
    }

    /**
     * Converts the lead
     * 
     * @return the SUCCESS result
     */
    public String convert() throws Exception {

        this.getBaseService().convert(this.getTarget().getId());
        this.setSaveFlag(Target.STATUS_CONVERTED);
        return SUCCESS;
    }

    /**
     * Creates change log
     * 
     * @param originalTarget
     *            original target record
     * @param target
     *            current target record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Target originalTarget, Target target) {
        Collection<ChangeLog> changeLogs = null;
        if (originalTarget != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = Target.class.getSimpleName();
            Integer recordID = target.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldSalutation = getOptionValue(originalTarget
                    .getSalutation());
            String newSalutation = getOptionValue(target.getSalutation());
            createChangeLog(changeLogs, entityName, recordID,
                    "menu.salutation.title", oldSalutation, newSalutation,
                    loginUser);

            String oldFirstName = CommonUtil.fromNullToEmpty(originalTarget
                    .getFirst_name());
            String newFirstName = CommonUtil.fromNullToEmpty(target
                    .getFirst_name());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.first_name.label", oldFirstName, newFirstName,
                    loginUser);

            String oldLastName = CommonUtil.fromNullToEmpty(originalTarget
                    .getLast_name());
            String newLastName = CommonUtil.fromNullToEmpty(target
                    .getLast_name());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.last_name.label", oldLastName, newLastName,
                    loginUser);

            String oldOfficePhone = CommonUtil.fromNullToEmpty(originalTarget
                    .getOffice_phone());
            String newOfficePhone = CommonUtil.fromNullToEmpty(target
                    .getOffice_phone());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.office_phone.label", oldOfficePhone,
                    newOfficePhone, loginUser);

            String oldTitle = CommonUtil.fromNullToEmpty(originalTarget
                    .getTitle());
            String newTitle = CommonUtil.fromNullToEmpty(target.getTitle());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.title.label", oldTitle, newTitle, loginUser);

            String oldMobile = CommonUtil.fromNullToEmpty(originalTarget
                    .getMobile());
            String newMobile = CommonUtil.fromNullToEmpty(target.getMobile());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.mobile.label", oldMobile, newMobile, loginUser);

            String oldDepartment = CommonUtil.fromNullToEmpty(originalTarget
                    .getDepartment());
            String newDepartment = CommonUtil.fromNullToEmpty(target
                    .getDepartment());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.department.label", oldDepartment, newDepartment,
                    loginUser);

            String oldFax = CommonUtil.fromNullToEmpty(originalTarget.getFax());
            String newWFax = CommonUtil.fromNullToEmpty(target.getFax());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.fax.label", oldFax, newWFax, loginUser);

            String oldPrimaryStreet = CommonUtil.fromNullToEmpty(originalTarget
                    .getPrimary_street());
            String newPrimaryStreet = CommonUtil.fromNullToEmpty(target
                    .getPrimary_street());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_street.label", oldPrimaryStreet,
                    newPrimaryStreet, loginUser);

            String oldPrimaryState = CommonUtil.fromNullToEmpty(originalTarget
                    .getPrimary_state());
            String newPrimaryState = CommonUtil.fromNullToEmpty(target
                    .getPrimary_state());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_state.label", oldPrimaryState,
                    newPrimaryState, loginUser);

            String oldPrimaryPostalCode = CommonUtil
                    .fromNullToEmpty(originalTarget.getPrimary_postal_code());
            String newPrimaryPostalCode = CommonUtil.fromNullToEmpty(target
                    .getPrimary_postal_code());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_postal_code.label", oldPrimaryPostalCode,
                    newPrimaryPostalCode, loginUser);

            String oldPrimaryCountry = CommonUtil
                    .fromNullToEmpty(originalTarget.getPrimary_country());
            String newPrimaryCountry = CommonUtil.fromNullToEmpty(target
                    .getPrimary_country());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_country.label", oldPrimaryCountry,
                    newPrimaryCountry, loginUser);

            String oldOtherStreet = CommonUtil.fromNullToEmpty(originalTarget
                    .getOther_street());
            String newOtherStreet = CommonUtil.fromNullToEmpty(target
                    .getOther_street());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_street.label", oldOtherStreet,
                    newOtherStreet, loginUser);

            String oldOtherState = CommonUtil.fromNullToEmpty(originalTarget
                    .getOther_state());
            String newOtherState = CommonUtil.fromNullToEmpty(target
                    .getOther_state());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_state.label", oldOtherState, newOtherState,
                    loginUser);

            String oldOtherPostalCode = CommonUtil
                    .fromNullToEmpty(originalTarget.getOther_postal_code());
            String newOtherPostalCode = CommonUtil.fromNullToEmpty(target
                    .getOther_postal_code());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_postal_code.label", oldOtherPostalCode,
                    newOtherPostalCode, loginUser);

            String oldOtherCountry = CommonUtil.fromNullToEmpty(originalTarget
                    .getOther_country());
            String newOtherCountry = CommonUtil.fromNullToEmpty(target
                    .getOther_country());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_country.label", oldOtherCountry,
                    newOtherCountry, loginUser);

            String oldEmail = CommonUtil.fromNullToEmpty(originalTarget
                    .getEmail());
            String newEmail = CommonUtil.fromNullToEmpty(target.getEmail());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.email.label", oldEmail, newEmail, loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalTarget
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(target.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAccountName = "";
            Account oldAccount = originalTarget.getAccount();
            if (oldAccount != null) {
                oldAccountName = CommonUtil.fromNullToEmpty(oldAccount
                        .getName());
            }
            String newAccountName = "";
            Account newAccount = target.getAccount();
            if (newAccount != null) {
                newAccountName = CommonUtil.fromNullToEmpty(newAccount
                        .getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.account.label", oldAccountName, newAccountName,
                    loginUser);

            boolean oldNotCall = originalTarget.isNot_call();

            boolean newNotCall = target.isNot_call();
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.not_call.label", String.valueOf(oldNotCall),
                    String.valueOf(newNotCall), loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalTarget.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = target.getAssigned_to();
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
     * Gets Target Relation Counts
     * 
     * @return null
     */
    public String getTargetRelationCounts() throws Exception {
        long taskNumber = this.baseService
                .countsByParams(
                        "select count(task.id) from Task task where related_object='Target' and related_record = ?",
                        new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"taskNumber\":\"").append(taskNumber)
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
        this.salutations = salutationService.getOptions(
                Salutation.class.getSimpleName(), local);
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
     * @return the target
     */
    public Target getTarget() {
        return target;
    }

    /**
     * @param target
     *            the target to set
     */
    public void setTarget(Target target) {
        this.target = target;
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
     * @return the targetListService
     */
    public IBaseService<TargetList> getTargetListService() {
        return targetListService;
    }

    /**
     * @param targetListService
     *            the targetListService to set
     */
    public void setTargetListService(IBaseService<TargetList> targetListService) {
        this.targetListService = targetListService;
    }

    /**
     * @return the salutations
     */
    public List<Salutation> getSalutations() {
        return salutations;
    }

    /**
     * @param salutations
     *            the salutations to set
     */
    public void setSalutations(List<Salutation> salutations) {
        this.salutations = salutations;
    }

    /**
     * @return the salutationID
     */
    public Integer getSalutationID() {
        return salutationID;
    }

    /**
     * @param salutationID
     *            the salutationID to set
     */
    public void setSalutationID(Integer salutationID) {
        this.salutationID = salutationID;
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
     * @return the baseService
     */
    public ITargetService getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(ITargetService baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the lead
     */
    public Lead getLead() {
        return lead;
    }

    /**
     * @param lead
     *            the lead to set
     */
    public void setLead(Lead lead) {
        this.lead = lead;
    }

    /**
     * @return the salutationService
     */
    public IOptionService<Salutation> getSalutationService() {
        return salutationService;
    }

    /**
     * @param salutationService
     *            the salutationService to set
     */
    public void setSalutationService(
            IOptionService<Salutation> salutationService) {
        this.salutationService = salutationService;
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
