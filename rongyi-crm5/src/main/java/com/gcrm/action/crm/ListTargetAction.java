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

import com.gcrm.domain.Account;
import com.gcrm.domain.Salutation;
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
 * Lists Target
 * 
 */
public class ListTargetAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Target> baseService;
    private IBaseService<Account> accountService;
    private IBaseService<User> userService;
    private IBaseService<Salutation> salutationService;
    private IBaseService<TargetList> targetListService;
    private Target target;

    private static final String CLAZZ = Target.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<Target> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Target> targets = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(targets, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_target");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_target(), loginUser);
        SearchResult<Target> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        Iterator<Target> targets = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(targets, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<Target> targets, long totalRecords,
            SearchCondition searchCondition, boolean isList) throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String assignedTo = null;
        String accountName = null;
        while (targets.hasNext()) {
            Target instance = targets.next();
            int id = instance.getId();
            String firstName = CommonUtil.fromNullToEmpty(instance
                    .getFirst_name());
            String lastName = CommonUtil.fromNullToEmpty(instance
                    .getLast_name());
            String name = instance.getName();
            String title = CommonUtil.fromNullToEmpty(instance.getTitle());

            Account account = instance.getAccount();
            if (account != null) {
                accountName = CommonUtil.fromNullToEmpty(account.getName());
            } else {
                accountName = "";
            }
            String email = CommonUtil.fromNullToEmpty(instance.getEmail());
            String officePhone = CommonUtil.fromNullToEmpty(instance
                    .getOffice_phone());
            User user = instance.getAssigned_to();
            if (user != null) {
                assignedTo = CommonUtil.fromNullToEmpty(user.getName());
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
                        .append(firstName).append("\",\"").append(lastName)
                        .append("\",\"").append(title).append("\",\"")
                        .append(accountName).append("\",\"").append(email)
                        .append("\",\"").append(officePhone).append("\",\"")
                        .append(assignedTo).append("\",\"")
                        .append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnName).append("\",\"")
                        .append(updatedOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"name\":\"").append(name)
                        .append("\",\"title\":\"").append(title)
                        .append("\",\"account.name\":\"")
                        .append(accountName + "\",\"email\":\"").append(email)
                        .append("\",\"office_phone\":\"").append(officePhone)
                        .append("\",\"assigned_to.name\":\"")
                        .append(assignedTo).append("\"}");
            }
            if (targets.hasNext()) {
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
     * Selects the entities
     * 
     * @return the SUCCESS result
     */
    public String select() throws ServiceException {
        TargetList targetList = null;
        Set<Target> targets = null;

        if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class,
                    Integer.valueOf(this.getRelationValue()));
            targets = targetList.getTargets();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String selectId = ids[i];
                target = baseService.getEntityById(Target.class,
                        Integer.valueOf(selectId));
                targets.add(target);
            }
        }

        if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        }
        return SUCCESS;
    }

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */
    public String unselect() throws ServiceException {
        TargetList targetList = null;
        Set<Target> targets = null;

        if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class,
                    Integer.valueOf(this.getRelationValue()));
            targets = targetList.getTargets();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            Collection<Target> selectedTargets = new ArrayList<Target>();
            for (int i = 0; i < ids.length; i++) {
                Integer selectId = Integer.valueOf(ids[i]);
                A: for (Target target : targets) {
                    if (target.getId().intValue() == selectId.intValue()) {
                        selectedTargets.add(target);
                        break A;
                    }
                }
            }
            targets.removeAll(selectedTargets);
        }

        if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        }
        return SUCCESS;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_target");
        baseService.batchDeleteEntity(Target.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_target");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                Target oriRecord = baseService.getEntityById(Target.class,
                        Integer.valueOf(copyid));
                Target targetRecord = oriRecord.clone();
                targetRecord.setId(null);
                this.getbaseService().makePersistent(targetRecord);
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
        UserUtil.permissionCheck("view_target");
        String fileName = getText("entity.target.label") + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] { getText("entity.id.label"),
                    getText("entity.salutation_id.label"),
                    getText("entity.salutation_name.label"),
                    getText("entity.first_name.label"),
                    getText("entity.last_name.label"),
                    getText("entity.office_phone.label"),
                    getText("entity.company.label"),
                    getText("entity.title.label"),
                    getText("entity.mobile.label"),
                    getText("entity.department.label"),
                    getText("entity.fax.label"),
                    getText("entity.account_id.label"),
                    getText("entity.account_name.label"),
                    getText("entity.primary_street.label"),
                    getText("entity.primary_city.label"),
                    getText("entity.primary_state.label"),
                    getText("entity.primary_postal_code.label"),
                    getText("entity.primary_country.label"),
                    getText("entity.other_street.label"),
                    getText("entity.other_city.label"),
                    getText("entity.other_state.label"),
                    getText("entity.other_postal_code.label"),
                    getText("entity.other_country.label"),
                    getText("entity.email.label"),
                    getText("entity.notes.label"),
                    getText("entity.not_call.label"),
                    getText("entity.assigned_to_id.label"),
                    getText("entity.assigned_to_name.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    Target target = baseService.getEntityById(Target.class,
                            Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], target.getId());
                    Salutation salutation = target.getSalutation();
                    if (salutation != null) {
                        data1.put(header[1], salutation.getId());
                    } else {
                        data1.put(header[1], "");
                    }
                    data1.put(header[2], CommonUtil.getOptionLabel(salutation));
                    data1.put(header[3],
                            CommonUtil.fromNullToEmpty(target.getFirst_name()));
                    data1.put(header[4],
                            CommonUtil.fromNullToEmpty(target.getLast_name()));
                    data1.put(header[5], CommonUtil.fromNullToEmpty(target
                            .getOffice_phone()));
                    data1.put(header[6],
                            CommonUtil.fromNullToEmpty(target.getCompany()));
                    data1.put(header[7],
                            CommonUtil.fromNullToEmpty(target.getTitle()));
                    data1.put(header[8],
                            CommonUtil.fromNullToEmpty(target.getMobile()));
                    data1.put(header[9],
                            CommonUtil.fromNullToEmpty(target.getDepartment()));
                    data1.put(header[10],
                            CommonUtil.fromNullToEmpty(target.getFax()));
                    if (target.getAccount() != null) {
                        data1.put(header[11], target.getAccount().getId());
                        data1.put(header[12], target.getAccount().getName());
                    } else {
                        data1.put(header[11], "");
                        data1.put(header[12], "");
                    }
                    data1.put(header[13], CommonUtil.fromNullToEmpty(target
                            .getPrimary_street()));
                    data1.put(header[14], CommonUtil.fromNullToEmpty(target
                            .getPrimary_city()));
                    data1.put(header[15], CommonUtil.fromNullToEmpty(target
                            .getPrimary_state()));
                    data1.put(header[16], CommonUtil.fromNullToEmpty(target
                            .getPrimary_postal_code()));
                    data1.put(header[17], CommonUtil.fromNullToEmpty(target
                            .getPrimary_country()));
                    data1.put(header[18], CommonUtil.fromNullToEmpty(target
                            .getOther_street()));
                    data1.put(header[19],
                            CommonUtil.fromNullToEmpty(target.getOther_city()));
                    data1.put(header[20],
                            CommonUtil.fromNullToEmpty(target.getOther_state()));
                    data1.put(header[21], CommonUtil.fromNullToEmpty(target
                            .getOther_postal_code()));
                    data1.put(header[22], CommonUtil.fromNullToEmpty(target
                            .getOther_country()));
                    data1.put(header[23],
                            CommonUtil.fromNullToEmpty(target.getEmail()));
                    data1.put(header[24],
                            CommonUtil.fromNullToEmpty(target.getNotes()));
                    data1.put(header[25], target.isNot_call());
                    if (target.getAssigned_to() != null) {
                        data1.put(header[26], target.getAssigned_to().getId());
                        data1.put(header[27], target.getAssigned_to().getName());
                    } else {
                        data1.put(header[26], "");
                        data1.put(header[27], "");
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

                Target target = new Target();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        target.setId(Integer.parseInt(id));
                    }
                    String salutationID = row
                            .get(getText("entity.salutation_id.label"));
                    if (CommonUtil.isNullOrEmpty(salutationID)) {
                        target.setSalutation(null);
                    } else {
                        Salutation salutation = salutationService
                                .getEntityById(Salutation.class,
                                        Integer.parseInt(salutationID));
                        target.setSalutation(salutation);
                    }
                    target.setFirst_name(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.first_name.label"))));
                    target.setLast_name(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.last_name.label"))));
                    target.setOffice_phone(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.office_phone.label"))));
                    target.setCompany(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.company.label"))));
                    target.setTitle(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.title.label"))));
                    target.setMobile(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mobile.label"))));
                    target.setDepartment(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.department.label"))));
                    target.setFax(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.fax.label"))));
                    String accountID = row
                            .get(getText("entity.account_id.label"));
                    if (CommonUtil.isNullOrEmpty(accountID)) {
                        target.setAccount(null);
                    } else {
                        Account account = accountService.getEntityById(
                                Account.class, Integer.parseInt(accountID));
                        target.setAccount(account);
                    }
                    target.setPrimary_street(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_street.label"))));
                    target.setPrimary_city(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_city.label"))));
                    target.setPrimary_state(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_state.label"))));
                    target.setPrimary_postal_code(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_postal_code.label"))));
                    target.setPrimary_country(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_country.label"))));
                    target.setOther_street(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_street.label"))));
                    target.setOther_city(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_city.label"))));
                    target.setOther_state(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_state.label"))));
                    target.setOther_postal_code(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_postal_code.label"))));
                    target.setOther_country(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_country.label"))));
                    target.setEmail(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.email.label"))));
                    target.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    String doNotCall = row
                            .get(getText("entity.not_call.label"));
                    if (CommonUtil.isNullOrEmpty(doNotCall)) {
                        target.setNot_call(false);
                    } else {
                        target.setNot_call(Boolean.parseBoolean(doNotCall));
                    }
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                        target.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        target.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(target);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    String firstName = CommonUtil.fromNullToEmpty(target
                            .getFirst_name());
                    String lastName = CommonUtil.fromNullToEmpty(target
                            .getLast_name());
                    failedMsg.put(firstName + " " + lastName, e.getMessage());
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
        return Target.class.getSimpleName();
    }

    public IBaseService<Target> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Target> baseService) {
        this.baseService = baseService;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
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
     * @return the salutationService
     */
    public IBaseService<Salutation> getSalutationService() {
        return salutationService;
    }

    /**
     * @param salutationService
     *            the salutationService to set
     */
    public void setSalutationService(IBaseService<Salutation> salutationService) {
        this.salutationService = salutationService;
    }

}
