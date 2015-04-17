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

import com.gcrm.dao.IVisitDao;
import com.gcrm.domain.Visit;

/**
 * @author ShaoYanbin
 * Visit DAO
 */
public class VisitDao extends BaseDao<Visit> implements IVisitDao {

    /*
     * (non-Javadoc)
     * 
     */
    @Override
    public List<Visit> findScheduleVisits(Date startDate) throws Exception {
        List<Visit> visits = this.findByParam(
                "from Visit where reminder_email = true and start_date > ? ",
                startDate);
        for (Visit visit : visits) {
            Hibernate.initialize(visit.getContacts());
            Hibernate.initialize(visit.getLeads());
            Hibernate.initialize(visit.getUsers());
        }

        return visits;
    }

}
