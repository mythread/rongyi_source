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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.action.system.ListUserAction;
import com.gcrm.domain.Account;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Target;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists TargetList
 * 
 */
public class ListTargetListAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<TargetList> baseService;
    private IBaseService<User> userService;
    private IBaseService<Campaign> campaignService;
    private TargetList targetList;

    private static final String CLAZZ = TargetList.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {
        SearchCondition searchCondition = getSearchCondition();
        SearchResult<TargetList> result = baseService.getPaginationObjects(
                CLAZZ, searchCondition);
        Iterator<TargetList> targetLists = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(targetLists, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_targetList");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_targetList(), loginUser);
        SearchResult<TargetList> result = baseService.getPaginationObjects(
                CLAZZ, searchCondition);
        Iterator<TargetList> targetLists = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(targetLists, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<TargetList> targetLists,
            long totalRecords, SearchCondition searchCondition, boolean isList)
            throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String assignedTo = null;
        while (targetLists.hasNext()) {
            TargetList instance = targetLists.next();
            int id = instance.getId();
            String name = CommonUtil.fromNullToEmpty(instance.getName());
            User user = instance.getAssigned_to();
            if (user != null) {
                assignedTo = user.getName();
            } else {
                assignedTo = "";
            }

            if (isList) {
                User createdBy = instance.getCreated_by();
                String createdByName = "";
                if (createdBy != null) {
                    createdByName = CommonUtil.fromNullToEmpty(createdBy
                            .getName());
                }
                User updatedBy = instance.getUpdated_by();
                String updatedByName = "";
                if (updatedBy != null) {
                    updatedByName = CommonUtil.fromNullToEmpty(updatedBy
                            .getName());
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        Constant.DATE_TIME_FORMAT);
                Date createdOn = instance.getCreated_on();
                String createdOnName = "";
                if (createdOn != null) {
                    createdOnName = dateFormat.format(createdOn);
                }
                Date updatedOn = instance.getUpdated_on();
                String updatedOnName = "";
                if (updatedOn != null) {
                    updatedOnName = dateFormat.format(updatedOn);
                }

                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
                        .append(name).append("\",\"").append(assignedTo)
                        .append("\",\"").append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnName).append("\",\"")
                        .append(updatedOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"name\":\"").append(name)
                        .append("\",\"assigned_to.name\":\"")
                        .append(assignedTo).append("\"}");
            }
            if (targetLists.hasNext()) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]}");

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }

    /**
     * Gets the related accounts.
     * 
     * @return null
     */
    public String relateTargetListAccount() throws Exception {
        targetList = baseService.getEntityById(TargetList.class, id);
        Set<Account> accounts = targetList.getAccounts();
        Iterator<Account> accountIterator = accounts.iterator();
        long totalRecords = accounts.size();
        ListAccountAction.getListJson(accountIterator, totalRecords, null,
                false);
        return null;
    }

    /**
     * Gets the related leads.
     * 
     * @return null
     */
    public String relateTargetListLead() throws Exception {
        targetList = baseService.getEntityById(TargetList.class, id);
        Set<Lead> leads = targetList.getLeads();
        Iterator<Lead> leadIterator = leads.iterator();
        long totalRecords = leads.size();
        ListLeadAction.getListJson(leadIterator, totalRecords, null, false);
        return null;
    }

    public String relateTargetListContact() throws Exception {
        targetList = baseService.getEntityById(TargetList.class, id);
        Set<Contact> contacts = targetList.getContacts();
        Iterator<Contact> contactIterator = contacts.iterator();
        long totalRecords = contacts.size();
        ListContactAction.getListJson(contactIterator, totalRecords, null,
                false);
        return null;
    }

    public String relateTargetListTarget() throws Exception {
        targetList = baseService.getEntityById(TargetList.class, id);
        Set<Target> targets = targetList.getTargets();
        Iterator<Target> targetIterator = targets.iterator();
        long totalRecords = targets.size();
        ListTargetAction.getListJson(targetIterator, totalRecords, null, false);
        return null;
    }

    public String relateTargetListUser() throws Exception {
        targetList = baseService.getEntityById(TargetList.class, id);
        Set<User> users = targetList.getUsers();
        Iterator<User> userIterator = users.iterator();
        int totalRecords = users.size();
        ListUserAction.getListJson(userIterator, totalRecords, null, false);
        return null;
    }

    /**
     * Selects the entities
     * 
     * @return the SUCCESS result
     */
    public String select() throws ServiceException {
        Campaign campaign = null;
        Set<TargetList> targetLists = null;

        if ("Campaign".equals(this.getRelationKey())) {
            campaign = campaignService.getEntityById(Campaign.class,
                    Integer.valueOf(this.getRelationValue()));
            targetLists = campaign.getTargetLists();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String selectId = ids[i];
                targetList = baseService.getEntityById(TargetList.class,
                        Integer.valueOf(selectId));
                targetLists.add(targetList);
            }
        }

        if ("Campaign".equals(this.getRelationKey())) {
            campaignService.makePersistent(campaign);
        }
        return SUCCESS;
    }

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */
    public String unselect() throws ServiceException {
        Campaign campaign = null;
        Set<TargetList> targetLists = null;

        if ("Campaign".equals(this.getRelationKey())) {
            campaign = campaignService.getEntityById(Campaign.class,
                    Integer.valueOf(this.getRelationValue()));
            targetLists = campaign.getTargetLists();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            Collection<TargetList> selectedTargetLists = new ArrayList<TargetList>();
            for (int i = 0; i < ids.length; i++) {
                Integer selectId = Integer.valueOf(ids[i]);
                A: for (TargetList targetList : targetLists) {
                    if (targetList.getId().intValue() == selectId.intValue()) {
                        selectedTargetLists.add(targetList);
                        break A;
                    }
                }
            }
            targetLists.removeAll(selectedTargetLists);
        }

        if ("Campaign".equals(this.getRelationKey())) {
            campaignService.makePersistent(campaign);
        }
        return SUCCESS;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_targetList");
        baseService.batchDeleteEntity(TargetList.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_targetList");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                TargetList oriRecord = baseService.getEntityById(
                        TargetList.class, Integer.valueOf(copyid));
                TargetList targetListRecord = oriRecord.clone();
                targetListRecord.setId(null);
                this.getbaseService().makePersistent(targetListRecord);
            }
        }
        return SUCCESS;
    }

    /**
     * Exports the entities
     * 
     * @return the exported entities inputStream
     */
    public InputStream getInputStream() throws Exception {
        return getDownloadContent(false);
    }

    /**
     * Exports the template
     * 
     * @return the exported template inputStream
     */
    public InputStream getTemplateStream() throws Exception {
        return getDownloadContent(true);
    }

    private InputStream getDownloadContent(boolean isTemplate) throws Exception {
        UserUtil.permissionCheck("view_targetList");
        String fileName = getText("entity.targetList.label") + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] { getText("entity.id.label"),
                    getText("entity.name.label"),
                    getText("entity.notes.label"),
                    getText("entity.assigned_to_id.label"),
                    getText("entity.assigned_to_name.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    TargetList targetList = baseService.getEntityById(
                            TargetList.class, Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], targetList.getId());
                    data1.put(header[1],
                            CommonUtil.fromNullToEmpty(targetList.getName()));
                    data1.put(header[2],
                            CommonUtil.fromNullToEmpty(targetList.getNotes()));
                    if (targetList.getAssigned_to() != null) {
                        data1.put(header[3], targetList.getAssigned_to()
                                .getId());
                        data1.put(header[4], targetList.getAssigned_to()
                                .getName());
                    } else {
                        data1.put(header[3], "");
                        data1.put(header[4], "");
                    }
                    writer.write(data1, header);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            writer.close();
        }

        InputStream in = new FileInputStream(file);
        this.setFileName(fileName);
        return in;
    }

    /**
     * Imports the entities
     * 
     * @return the SUCCESS result
     */
    public String importCSV() throws Exception {
        File file = this.getUpload();
        CsvListReader reader = new CsvListReader(new FileReader(file),
                CsvPreference.EXCEL_PREFERENCE);
        int failedNum = 0;
        int successfulNum = 0;
        try {
            final String[] header = reader.getCSVHeader(true);

            List<String> line = new ArrayList<String>();
            Map<String, String> failedMsg = new HashMap<String, String>();
            while ((line = reader.read()) != null) {

                Map<String, String> row = new HashMap<String, String>();
                for (int i = 0; i < line.size(); i++) {
                    row.put(header[i], line.get(i));
                }

                TargetList targetList = new TargetList();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        targetList.setId(Integer.parseInt(id));
                    }
                    targetList.setName(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.name.label"))));
                    targetList.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                        targetList.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        targetList.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(targetList);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    String Name = CommonUtil.fromNullToEmpty(targetList
                            .getName());
                    failedMsg.put(Name, e.getMessage());
                }

            }

            this.setFailedMsg(failedMsg);
            this.setFailedNum(failedNum);
            this.setSuccessfulNum(successfulNum);
            this.setTotalNum(successfulNum + failedNum);
        } finally {
            reader.close();
        }
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return TargetList.class.getSimpleName();
    }

    public IBaseService<TargetList> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<TargetList> baseService) {
        this.baseService = baseService;
    }

    public TargetList getTargetList() {
        return targetList;
    }

    public void setTargetList(TargetList targetList) {
        this.targetList = targetList;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
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

}
