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

import com.gcrm.dao.IMeetingDao;
import com.gcrm.domain.Meeting;

/**
 * Meeting DAO
 */
public class MeetingDao extends BaseDao<Meeting> implements IMeetingDao {

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.dao.IMeetingDao#findScheduleMeetings(java.util.Date)
     */
    @Override
    public List<Meeting> findScheduleMeetings(Date startDate) throws Exception {
        List<Meeting> meetings = this.findByParam(
                "from Meeting where reminder_email = true and start_date > ? ",
                startDate);
        for (Meeting meeting : meetings) {
            Hibernate.initialize(meeting.getContacts());
            Hibernate.initialize(meeting.getLeads());
            Hibernate.initialize(meeting.getUsers());
        }

        return meetings;
    }

}
