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

import com.gcrm.domain.Lead;
import com.gcrm.domain.LeadStatus;
import com.gcrm.domain.Target;
import com.gcrm.service.IBaseService;
import com.gcrm.service.ITargetService;

/**
 * Target service
 */
public class TargetService extends BaseService<Target> implements
        ITargetService {

    private IBaseService<Lead> leadService;
    private IBaseService<LeadStatus> leadStatusService;

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.ITargetService#convert(java.lang.Integer)
     */
    public void convert(Integer id) throws Exception {
        Target target = this.getEntityById(Target.class, id);
        Lead lead = new Lead();
        lead.setSalutation(target.getSalutation());
        lead.setFirst_name(target.getFirst_name());
        lead.setLast_name(target.getLast_name());
        lead.setOffice_phone(target.getOffice_phone());
        lead.setCompany(target.getCompany());
        lead.setTitle(target.getTitle());
        lead.setMobile(target.getMobile());
        lead.setDepartment(target.getDepartment());
        lead.setFax(target.getFax());
        lead.setAccount(target.getAccount());
        lead.setPrimary_street(target.getPrimary_street());
        lead.setPrimary_city(target.getPrimary_city());
        lead.setPrimary_country(target.getPrimary_country());
        lead.setPrimary_postal_code(target.getPrimary_postal_code());
        lead.setPrimary_state(target.getPrimary_state());
        lead.setOther_street(target.getOther_street());
        lead.setOther_city(target.getOther_city());
        lead.setOther_country(target.getOther_country());
        lead.setOther_postal_code(target.getOther_postal_code());
        lead.setOther_state(target.getOther_state());
        lead.setEmail(target.getEmail());
        lead.setNotes(target.getNotes());
        lead.setNot_call(target.isNot_call());
        lead.setAssigned_to(target.getAssigned_to());
        lead.setOwner(target.getOwner());
        LeadStatus status = this.getLeadStatusService().findByName(
                LeadStatus.class.getSimpleName(), "New");
        lead.setStatus(status);
        lead = this.getLeadService().makePersistent(lead);
        target.setLead_id(lead.getId());
        this.makePersistent(target);

    }

    /**
     * @return the leadService
     */
    public IBaseService<Lead> getLeadService() {
        return leadService;
    }

    /**
     * @param leadService
     *            the leadService to set
     */
    public void setLeadService(IBaseService<Lead> leadService) {
        this.leadService = leadService;
    }

    /**
     * @return the leadStatusService
     */
    public IBaseService<LeadStatus> getLeadStatusService() {
        return leadStatusService;
    }

    /**
     * @param leadStatusService
     *            the leadStatusService to set
     */
    public void setLeadStatusService(IBaseService<LeadStatus> leadStatusService) {
        this.leadStatusService = leadStatusService;
    }

}
