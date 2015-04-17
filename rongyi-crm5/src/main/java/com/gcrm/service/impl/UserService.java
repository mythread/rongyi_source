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
package com.gcrm.service.impl;

import java.util.List;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gcrm.dao.IUserDao;
import com.gcrm.domain.User;
import com.gcrm.exception.DaoException;
import com.gcrm.exception.ServiceException;
import com.gcrm.security.AuthenticationFilter;
import com.gcrm.service.IUserService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.mail.MailService;

/**
 * User service
 */
public class UserService extends BaseService<User> implements IUserService {

    private IUserDao userDao;
    private MailService mailService;

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IUserService#findByName(java.lang.String)
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public User findByName(String username) throws ServiceException {
        User user;
        try {
            user = this.userDao.findByName(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.IUserService#forgetPassword(java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean forgetPassword(String username, String email,
            String subject, String content) throws Exception {
        List<User> users = this.userDao.findByParams(
                "from User where name =  ? and email = ?", new String[] {
                        username, email });

        boolean flag = false;
        if (users != null & users.size() == 1) {
            // Generates a random user password
            String newPassword = CommonUtil.randomString(6);

            // Saves the new password
            User user = users.get(0);
            Md5PasswordEncoder encoder = new Md5PasswordEncoder();
            user.setPassword(encoder.encodePassword(newPassword,
                    AuthenticationFilter.SALT));
            this.makePersistent(user);

            // Sends the new password to user
            mailService.sendSystemSimpleMail(email, subject, content
                    + newPassword);
            flag = true;
        }
        return flag;
    }

    /**
     * @return the userDao
     */
    public IUserDao getUserDao() {
        return userDao;
    }

    /**
     * @param userDao
     *            the userDao to set
     */
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
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

}
