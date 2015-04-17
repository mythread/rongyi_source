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
import com.gcrm.domain.EmailSetting;
import com.gcrm.service.IBaseService;
import com.gcrm.util.Constant;
import com.gcrm.util.mail.MailService;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Account
 * 
 */
public class EmailSettingAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<EmailSetting> baseService;
    private MailService mailService;
    private EmailSetting emailSetting;
    private String emailAddress;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        UserUtil.permissionCheck("update_system");
        super.updateBaseInfo(emailSetting);
        getBaseService().makePersistent(emailSetting);
        return SUCCESS;
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        UserUtil.permissionCheck("view_system");
        List<EmailSetting> emailSettings = baseService
                .getAllObjects(EmailSetting.class.getSimpleName());
        if (emailSettings != null && emailSettings.size() > 0) {
            emailSetting = emailSettings.get(0);
            this.getBaseInfo(emailSetting, EmailSetting.class.getSimpleName(),
                    Constant.SYSTEM_NAMESPACE);
        }
        return SUCCESS;
    }

    /**
     * Sends test email
     * 
     * @return the SUCCESS result
     */
    public String sendEmail() throws Exception {
        mailService.sendSimpleMail(this.getEmailAddress());
        return SUCCESS;
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
    public IBaseService<EmailSetting> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<EmailSetting> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the emailSetting
     */
    public EmailSetting getEmailSetting() {
        return emailSetting;
    }

    /**
     * @param emailSetting
     *            the emailSetting to set
     */
    public void setEmailSetting(EmailSetting emailSetting) {
        this.emailSetting = emailSetting;
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
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress
     *            the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
