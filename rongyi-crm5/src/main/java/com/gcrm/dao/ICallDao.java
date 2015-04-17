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

import java.util.Date;
import java.util.List;

import com.gcrm.domain.Call;

/**
 * Call DAO
 */
public interface ICallDao extends IBaseDao<Call> {
    /**
     * Finds scheduled calls
     * 
     * @param startDate
     *            Schedule start date
     * @return scheduled calls
     */
    public List<Call> findScheduleCalls(Date startDate) throws Exception;
}
