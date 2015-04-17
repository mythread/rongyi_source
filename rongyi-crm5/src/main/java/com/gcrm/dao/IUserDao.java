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
package com.gcrm.dao;

import com.gcrm.domain.User;
import com.gcrm.exception.DaoException;

/**
 * User DAO
 */
public interface IUserDao extends IBaseDao<User> {
    /**
     * Finds user by user name
     * 
     * @param userName
     *            user name
     * @return user entity
     * @throws serviceException
     */
    public User findByName(String userName) throws DaoException;
}
