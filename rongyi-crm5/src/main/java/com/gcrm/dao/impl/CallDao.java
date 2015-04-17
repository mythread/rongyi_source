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
package com.gcrm.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.gcrm.dao.ICallDao;
import com.gcrm.domain.Call;

/**
 * Call DAO
 */
public class CallDao extends BaseDao<Call> implements ICallDao {

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.dao.ICallDao#findScheduleCalls(java.util.Date)
     */
    @Override
    public List<Call> findScheduleCalls(Date startDate) throws Exception {
        List<Call> calls = this.findByParam(
                "from Call where reminder_email = 1 and start_date > ? ",
                startDate);
        for (Call call : calls) {
            Hibernate.initialize(call.getContacts());
            Hibernate.initialize(call.getLeads());
            Hibernate.initialize(call.getUsers());
        }

        return calls;
    }

}
