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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists ChnageLog
 * 
 */
public class ListChangeLogAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<ChangeLog> baseService;
    private IBaseService<User> userService;
    private String entity;
    private Integer recordID;

    private static final String CLAZZ = ChangeLog.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {
        this.setSort("id");
        this.setOrder("desc");
        SearchCondition searchCondition = getSearchCondition();
        String condition = searchCondition.getCondition();
        if (condition.length() > 0) {
            condition += " and ";
        }
        condition += " entityName = '" + entity + "' and recordID = "
                + recordID;
        searchCondition.setCondition(condition);
        SearchResult<ChangeLog> result = baseService.getPaginationObjects(
                CLAZZ, searchCondition);
        Iterator<ChangeLog> changeLogs = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(changeLogs, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_system");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_account(), loginUser);
        SearchResult<ChangeLog> result = baseService.getPaginationObjects(
                CLAZZ, searchCondition);
        Iterator<ChangeLog> changeLogs = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(changeLogs, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public void getListJson(Iterator<ChangeLog> changeLogs, long totalRecords,
            SearchCondition searchCondition, boolean isList) throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_TIME_FORMAT);
        while (changeLogs.hasNext()) {
            ChangeLog instance = changeLogs.next();
            int id = instance.getId();
            String columnName = CommonUtil.fromNullToEmpty(instance
                    .getColumnName());
            columnName = this.getText(columnName);
            String oldValue = CommonUtil
                    .fromNullToEmpty(instance.getOldValue());
            String newValue = CommonUtil
                    .fromNullToEmpty(instance.getNewValue());
            User updatedBy = instance.getUpdated_by();
            String updatedByName = "";
            if (updatedBy != null) {
                updatedByName = CommonUtil.fromNullToEmpty(updatedBy.getName());
            }
            Date updatedOn = instance.getUpdated_on();
            String updatedOnName = "";
            if (updatedOn != null) {
                updatedOnName = dateFormat.format(updatedOn);
            }

            if (isList) {
                String entityName = CommonUtil.fromNullToEmpty(instance
                        .getEntityName());
                Integer recordID = instance.getRecordID();
                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
                        .append(entityName).append("\",\"").append(recordID)
                        .append("\",\"").append(columnName).append("\"")
                        .append(",\"").append(oldValue).append("\",\"")
                        .append(newValue).append("\",\"").append(updatedByName)
                        .append("\",\"").append(updatedOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"columnName\":\"").append(columnName)
                        .append("\",\"oldValue\":\"").append(oldValue)
                        .append("\",\"newValue\":\"").append(newValue)
                        .append("\",\"updatedBy\":\"").append(updatedByName)
                        .append("\",\"updatedOn\":\"").append(updatedOnName)
                        .append("\"}");
            }
            if (changeLogs.hasNext()) {
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
     * Goes to the list page
     * 
     * @return SUCCESS result
     */
    public String showPage() throws ServiceException {
        return SUCCESS;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_system");
        baseService.batchDeleteEntity(ChangeLog.class, this.getSeleteIDs());
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return ChangeLog.class.getSimpleName();
    }

    public IBaseService<ChangeLog> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<ChangeLog> baseService) {
        this.baseService = baseService;
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
     * @return the recordID
     */
    public Integer getRecordID() {
        return recordID;
    }

    /**
     * @param recordID
     *            the recordID to set
     */
    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    /**
     * @return the entity
     */
    public String getEntity() {
        return entity;
    }

    /**
     * @param entity
     *            the entity to set
     */
    public void setEntity(String entity) {
        this.entity = entity;
    }

}
