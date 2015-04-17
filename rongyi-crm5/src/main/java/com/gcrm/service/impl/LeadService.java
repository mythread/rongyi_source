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

import com.gcrm.domain.Account;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.LeadStatus;
import com.gcrm.domain.Opportunity;
import com.gcrm.service.IBaseService;
import com.gcrm.service.ILeadService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.CommonUtil;

/**
 * Lead service
 */
public class LeadService extends BaseService<Lead> implements ILeadService {

    private IBaseService<Account> accountService;
    private IBaseService<Contact> contactService;
    private IBaseService<Opportunity> opportunityService;
    private IOptionService<LeadStatus> leadStatusService;

    /*
     * (non-Javadoc)
     * 
     * @see com.gcrm.service.ILeadService#convert(java.lang.Integer, boolean,
     * boolean, boolean)
     */
    public void convert(Integer id, boolean accountCheck, boolean contactCheck,
            boolean opportunityCheck) throws Exception {
        Lead lead = this.getEntityById(Lead.class, id);
        if (accountCheck) {
            Account account = new Account();
            String firstName = CommonUtil.fromNullToEmpty(lead.getFirst_name());
            String lastName = CommonUtil.fromNullToEmpty(lead.getLast_name());
            account.setName(firstName + " " + lastName);
            account.setOffice_phone(lead.getOffice_phone());
            account.setFax(lead.getFax());
            account.setBill_street(lead.getPrimary_street());
            account.setBill_city(lead.getPrimary_city());
            account.setBill_country(lead.getPrimary_country());
            account.setBill_postal_code(lead.getPrimary_postal_code());
            account.setBill_state(lead.getPrimary_state());
            account.setShip_street(lead.getOther_street());
            account.setShip_city(lead.getOther_city());
            account.setShip_country(lead.getOther_country());
            account.setShip_postal_code(lead.getOther_postal_code());
            account.setShip_state(lead.getOther_state());
            account.setEmail(lead.getEmail());
            account.setNotes(lead.getNotes());
            account.setAssigned_to(lead.getAssigned_to());
            account.setOwner(lead.getOwner());
            this.getAccountService().makePersistent(account);
        }
        if (contactCheck) {
            Contact contact = new Contact();
            contact.setSalutation(lead.getSalutation());
            contact.setNick_name(lead.getFirst_name());
            contact.setLast_name(lead.getLast_name());
            contact.setOffice_phone(lead.getOffice_phone());
            contact.setTitle(lead.getTitle());
            contact.setMobile(lead.getMobile());
            contact.setDepartment(lead.getDepartment());
            contact.setFax(lead.getFax());
            contact.setAccount(lead.getAccount());
            contact.setPrimary_street(lead.getPrimary_street());
            contact.setPrimary_city(lead.getPrimary_city());
            contact.setPrimary_country(lead.getPrimary_country());
            contact.setPrimary_postal_code(lead.getPrimary_postal_code());
            contact.setPrimary_state(lead.getPrimary_state());
            contact.setOther_street(lead.getOther_street());
            contact.setOther_city(lead.getOther_city());
            contact.setOther_country(lead.getOther_country());
            contact.setOther_postal_code(lead.getOther_postal_code());
            contact.setOther_state(lead.getOther_state());
            contact.setOffice_email(lead.getEmail());
            contact.setNotes(lead.getNotes());
            contact.setCampaign(lead.getCampaign());
            contact.setNot_call(lead.isNot_call());
            contact.setAssigned_to(lead.getAssigned_to());
            contact.setOwner(lead.getOwner());
            this.getContactService().makePersistent(contact);
        }
        if (opportunityCheck) {
            Opportunity opportunity = new Opportunity();
            String firstName = CommonUtil.fromNullToEmpty(lead.getFirst_name());
            String lastName = CommonUtil.fromNullToEmpty(lead.getLast_name());
            opportunity.setName(firstName + " " + lastName);
            opportunity.setCampaign(lead.getCampaign());
            opportunity.setAssigned_to(lead.getAssigned_to());
            opportunity.setOwner(lead.getOwner());
            this.getOpportunityService().makePersistent(opportunity);
        }
        LeadStatus status = this.getLeadStatusService().findByValue(
                LeadStatus.class.getSimpleName(), "Converted");
        lead.setStatus(status);
        this.makePersistent(lead);
    }

    /**
     * @return the contactService
     */
    public IBaseService<Contact> getContactService() {
        return contactService;
    }

    /**
     * @param contactService
     *            the contactService to set
     */
    public void setContactService(IBaseService<Contact> contactService) {
        this.contactService = contactService;
    }

    /**
     * @return the opportunityService
     */
    public IBaseService<Opportunity> getOpportunityService() {
        return opportunityService;
    }

    /**
     * @param opportunityService
     *            the opportunityService to set
     */
    public void setOpportunityService(
            IBaseService<Opportunity> opportunityService) {
        this.opportunityService = opportunityService;
    }

    /**
     * @return the accountService
     */
    public IBaseService<Account> getAccountService() {
        return accountService;
    }

    /**
     * @param accountService
     *            the accountService to set
     */
    public void setAccountService(IBaseService<Account> accountService) {
        this.accountService = accountService;
    }

    /**
     * @return the leadStatusService
     */
    public IOptionService<LeadStatus> getLeadStatusService() {
        return leadStatusService;
    }

    /**
     * @param leadStatusService
     *            the leadStatusService to set
     */
    public void setLeadStatusService(
            IOptionService<LeadStatus> leadStatusService) {
        this.leadStatusService = leadStatusService;
    }

}
