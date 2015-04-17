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

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gcrm.dao.IMeetingDao;
import com.gcrm.domain.Meeting;
import com.gcrm.service.IMeetingService;

/**
 * Meeting service
 */
public class MeetingService extends BaseService<Meeting> implements
        IMeetingService {

    private IMeetingDao meetingDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.gcrm.service.IMeetingService#findScheduleMeetings(java.util.Date)
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Meeting> findScheduleMeetings(Date startDate) throws Exception {
        return meetingDao.findScheduleMeetings(startDate);
    }

    /**
     * @return the meetingDao
     */
    public IMeetingDao getMeetingDao() {
        return meetingDao;
    }

    /**
     * @param meetingDao
     *            the meetingDao to set
     */
    public void setMeetingDao(IMeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

}
