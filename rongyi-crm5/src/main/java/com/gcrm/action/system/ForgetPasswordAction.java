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

import com.gcrm.service.IUserService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Forgets password Action
 * 
 */
public class ForgetPasswordAction extends ActionSupport {

    private static final long serialVersionUID = 1L;

    private IUserService baseService;
    private String username;
    private String email;
    private String notification = null;

    @Override
    public String execute() throws Exception {
        String subject = this.getText("login.forgetPassword.email.subject");
        String content = this.getText("login.forgetPassword.email.content");
        boolean flag = baseService.forgetPassword(username, email, subject,
                content);
        if (flag) {
            notification = this.getText("login.forgetPassword.success");
        } else {
            notification = this.getText("login.forgetPassword.fail");
        }
        return SUCCESS;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the baseService
     */
    public IUserService getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IUserService baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the notification
     */
    public String getNotification() {
        return notification;
    }

    /**
     * @param notification
     *            the notification to set
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

}