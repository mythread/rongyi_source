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
package com.gcrm.action.system;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.action.crm.BaseListAction;
import com.gcrm.domain.EmailTemplate;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists EmailTemplate
 * 
 */
public class ListEmailTemplateAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<EmailTemplate> baseService;
    private EmailTemplate emailTemplate;

    private static final String CLAZZ = EmailTemplate.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_system");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_system(), loginUser);
        SearchResult<EmailTemplate> result = baseService.getPaginationObjects(
                CLAZZ, searchCondition);

        Iterator<EmailTemplate> emailTemplates = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(emailTemplates, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public void getListJson(Iterator<EmailTemplate> emailTemplates,
            long totalRecords, SearchCondition searchCondition, boolean isList)
            throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        while (emailTemplates.hasNext()) {
            EmailTemplate instance = emailTemplates.next();
            int id = instance.getId();
            String name = CommonUtil.fromNullToEmpty(instance.getName());
            String type = CommonUtil.fromNullToEmpty(instance.getType());
            // Get type label
            String typeLabel = "";
            if ("meetingInvite".equals(type)) {
                typeLabel = getText("emailTemplate.meetingInvite.label");
            } else if ("meetingRemind".equals(type)) {
                typeLabel = getText("emailTemplate.meetingRemind.label");
            } else if ("callInvite".equals(type)) {
                typeLabel = getText("emailTemplate.callInvite.label");
            } else if ("callRemind".equals(type)) {
                typeLabel = getText("emailTemplate.callRemind.label");
            } else if ("campaignInvite".equals(type)) {
                typeLabel = getText("emailTemplate.campaignInvite.label");
            }
            String description = CommonUtil.fromNullToEmpty(instance
                    .getDescription());
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
                        .append(name).append("\",\"").append(typeLabel)
                        .append("\",\"").append(description).append("\",\"")
                        .append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnName).append("\",\"")
                        .append(updatedOnName).append("\"]}");
            }
            if (emailTemplates.hasNext()) {
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
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_system");
        baseService.batchDeleteEntity(EmailTemplate.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_system");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                EmailTemplate oriRecord = baseService.getEntityById(
                        EmailTemplate.class, Integer.valueOf(copyid));
                EmailTemplate targetRecord = oriRecord.clone();
                targetRecord.setId(null);
                this.baseService.makePersistent(targetRecord);
            }
        }
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return EmailTemplate.class.getSimpleName();
    }

    /**
     * @return the emailTemplate
     */
    public EmailTemplate getEmailTemplate() {
        return emailTemplate;
    }

    /**
     * @param emailTemplate
     *            the emailTemplate to set
     */
    public void setEmailTemplate(EmailTemplate emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<EmailTemplate> baseService) {
        this.baseService = baseService;
    }

    @Override
    public String list() throws Exception {
        return null;
    }

}
