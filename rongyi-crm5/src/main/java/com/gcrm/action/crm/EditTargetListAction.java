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
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Campaign;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits TargetList
 * 
 */
public class EditTargetListAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<TargetList> baseService;
    private IBaseService<User> userService;
    private IBaseService<Campaign> campaignService;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private TargetList targetList;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        TargetList originalTargetList = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalTargetList,
                targetList);
        if ("Campaign".equals(this.getRelationKey())) {
            Campaign campaign = campaignService.getEntityById(Campaign.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Campaign> campaigns = targetList.getCampaigns();
            if (campaigns == null) {
                campaigns = new HashSet<Campaign>();
            }
            campaigns.add(campaign);
        }
        targetList = getBaseService().makePersistent(targetList);
        this.setId(targetList.getId());
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
     * @param originalTargetList
     *            original targetList record
     * @param targetList
     *            current targetList record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(TargetList originalTargetList,
            TargetList targetList) {
        Collection<ChangeLog> changeLogs = null;
        if (originalTargetList != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = TargetList.class.getSimpleName();
            Integer recordID = targetList.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldName = CommonUtil.fromNullToEmpty(originalTargetList
                    .getName());
            String newName = CommonUtil.fromNullToEmpty(targetList.getName());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.name.label", oldName, newName, loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalTargetList
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(targetList.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalTargetList.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = targetList.getAssigned_to();
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
            targetList = baseService.getEntityById(TargetList.class,
                    this.getId());
            User assignedTo = targetList.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            this.getBaseInfo(targetList, TargetList.class.getSimpleName(),
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
            Collection<TargetList> targetLists = new ArrayList<TargetList>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                TargetList targetListInstance = this.baseService.getEntityById(
                        TargetList.class, id);
                TargetList originalTargetList = targetListInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil
                            .getFieldValue(targetList, fieldName);
                    BeanUtil.setFieldValue(targetListInstance, fieldName, value);
                }
                targetListInstance.setUpdated_by(user);
                targetListInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(
                        originalTargetList, targetListInstance);
                allChangeLogs.addAll(changeLogs);
                targetLists.add(targetListInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (targetLists.size() > 0) {
                this.baseService.batchUpdate(targetLists);
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
     * @return original TargetList record
     * @throws Exception
     */
    private TargetList saveEntity() throws Exception {
        TargetList originalTargetList = null;
        if (targetList.getId() == null) {
            UserUtil.permissionCheck("create_targetList");
        } else {
            UserUtil.permissionCheck("update_targetList");
            originalTargetList = baseService.getEntityById(TargetList.class,
                    targetList.getId());
            targetList.setTargets(originalTargetList.getTargets());
            targetList.setContacts(originalTargetList.getContacts());
            targetList.setLeads(originalTargetList.getLeads());
            targetList.setUsers(originalTargetList.getUsers());
            targetList.setAccounts(originalTargetList.getAccounts());
            targetList.setCreated_on(originalTargetList.getCreated_on());
            targetList.setCreated_by(originalTargetList.getCreated_by());
        }

        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        targetList.setAssigned_to(assignedTo);

        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        targetList.setOwner(owner);

        super.updateBaseInfo(targetList);
        return originalTargetList;
    }

    /**
     * Gets TargetList Relation Counts
     * 
     * @return null
     */
    public String getTargetListRelationCounts() throws Exception {
        long accountNumber = this.baseService
                .countsByParams(
                        "select count(*) from TargetList targetList join targetList.accounts where targetList.id = ?",
                        new Integer[] { this.getId() });
        long contactNumber = this.baseService
                .countsByParams(
                        "select count(*) from TargetList targetList join targetList.contacts where targetList.id = ?",
                        new Integer[] { this.getId() });
        long leadNumber = this.baseService
                .countsByParams(
                        "select count(*) from TargetList targetList join targetList.leads where targetList.id = ?",
                        new Integer[] { this.getId() });
        long targetNumber = this.baseService
                .countsByParams(
                        "select count(*) from TargetList targetList join targetList.targets where targetList.id = ?",
                        new Integer[] { this.getId() });
        long userNumber = this.baseService
                .countsByParams(
                        "select count(*) from TargetList targetList join targetList.users where targetList.id = ?",
                        new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"accountNumber\":\"").append(accountNumber)
                .append("\",\"contactNumber\":\"").append(contactNumber)
                .append("\",\"leadNumber\":\"").append(leadNumber)
                .append("\",\"targetNumber\":\"").append(targetNumber)
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
    }

    /**
     * @return the baseService
     */
    public IBaseService<TargetList> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<TargetList> baseService) {
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
     * @return the targetList
     */
    public TargetList getTargetList() {
        return targetList;
    }

    /**
     * @param targetList
     *            the targetList to set
     */
    public void setTargetList(TargetList targetList) {
        this.targetList = targetList;
    }

    /**
     * @return the campaignService
     */
    public IBaseService<Campaign> getCampaignService() {
        return campaignService;
    }

    /**
     * @param campaignService
     *            the campaignService to set
     */
    public void setCampaignService(IBaseService<Campaign> campaignService) {
        this.campaignService = campaignService;
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
