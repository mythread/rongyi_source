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

import java.util.List;

import com.gcrm.action.crm.BaseEditAction;
import com.gcrm.domain.EmailTemplate;
import com.gcrm.domain.UserStatus;
import com.gcrm.service.IBaseService;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Email Template
 * 
 */
public class EditEmailTemplateAction extends BaseEditAction implements
        Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<EmailTemplate> baseService;
    private EmailTemplate emailTemplate;
    private List<UserStatus> statuses;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        saveEntity();
        emailTemplate = getBaseService().makePersistent(emailTemplate);
        this.setId(emailTemplate.getId());
        this.setSaveFlag("true");
        return SUCCESS;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            emailTemplate = baseService.getEntityById(EmailTemplate.class,
                    this.getId());
            this.getBaseInfo(emailTemplate,
                    EmailTemplate.class.getSimpleName(),
                    Constant.SYSTEM_NAMESPACE);
        }
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @throws Exception
     */
    private void saveEntity() throws Exception {
        if (emailTemplate.getId() != null) {
            UserUtil.permissionCheck("update_system");
        } else {
            UserUtil.permissionCheck("create_system");
        }
        super.updateBaseInfo(emailTemplate);
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
    public IBaseService<EmailTemplate> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<EmailTemplate> baseService) {
        this.baseService = baseService;
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
     * @return the statuses
     */
    public List<UserStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<UserStatus> statuses) {
        this.statuses = statuses;
    }

}
