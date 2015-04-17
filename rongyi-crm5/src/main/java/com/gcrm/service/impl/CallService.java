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

import com.gcrm.dao.ICallDao;
import com.gcrm.domain.Call;
import com.gcrm.service.ICallService;

/**
 * Call service
 */
public class CallService extends BaseService<Call> implements ICallService {

    private ICallDao callDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.ICallService#findScheduleCalls(java.util.Date)
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<Call> findScheduleCalls(Date startDate) throws Exception {
        return callDao.findScheduleCalls(startDate);
    }

    /**
     * @return the callDao
     */
    public ICallDao getCallDao() {
        return callDao;
    }

    /**
     * @param callDao
     *            the callDao to set
     */
    public void setCallDao(ICallDao callDao) {
        this.callDao = callDao;
    }
}
