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
package com.gcrm.action.crm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.core.task.TaskExecutor;

import com.gcrm.domain.Account;
import com.gcrm.domain.Call;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.LeadSource;
import com.gcrm.domain.LeadStatus;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.Salutation;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.domain.Visit;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.service.ILeadService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Lead
 * 
 */
public class EditLeadAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private ILeadService baseService;
    private IBaseService<Account> accountService;
    private IOptionService<LeadStatus> leadStatusService;
    private IOptionService<LeadSource> leadSourceService;
    private IOptionService<Salutation> salutationService;
    private IBaseService<User> userService;
    private IBaseService<Campaign> campaignService;
    private IBaseService<Contact> contactService;
    private IBaseService<Opportunity> opportunityService;
    private IBaseService<TargetList> targetListService;
    private IBaseService<Call> callService;
    private IBaseService<Meeting> meetingService;
    private IBaseService<Visit> visitService;
    private Lead lead;
    private List<LeadStatus> leadStatuses;
    private List<LeadSource> leadSources;
    private List<Salutation> salutations;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private Integer accountID = null;
    private String accountText = null;
    private Integer leadStatusID = null;
    private Integer leadSourceID = null;
    private Integer salutationID = null;
    private Integer campaignID = null;
    private String campaignText = null;
    private boolean accountCheck;
    private boolean contactCheck;
    private boolean opportunityCheck;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        Lead originalLead = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalLead, lead);
        lead = this.getBaseService().makePersistent(lead);
        this.setId(lead.getId());
        this.setSaveFlag("true");
        if (changeLogs != null) {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    batchInserChangeLogs(changeLogs);
                }
            });
        }
        return SUCCESS;
    }

    /**
     * Batch update change log
     * 
     * @param changeLogs
     *            change log collection
     */
    private void batchInserChangeLogs(Collection<ChangeLog> changeLogs) {
        this.getChangeLogService().batchUpdate(changeLogs);
    }

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            lead = baseService.getEntityById(Lead.class, this.getId());
            Account account = lead.getAccount();
            if (account != null) {
                accountID = account.getId();
                accountText = account.getName();
            }

            LeadStatus leadStatus = lead.getStatus();
            if (leadStatus != null) {
                leadStatusID = leadStatus.getId();
            }

            LeadSource leadSource = lead.getLead_source();
            if (leadSource != null) {
                leadSourceID = leadSource.getId();
            }

            Salutation salutation = lead.getSalutation();
            if (salutation != null) {
                salutationID = salutation.getId();
            }

            Campaign campaign = lead.getCampaign();
            if (campaign != null) {
                campaignID = campaign.getId();
                campaignText = campaign.getName();
            }
            User assignedTo = lead.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            this.getBaseInfo(lead, Lead.class.getSimpleName(),
                    Constant.CRM_NAMESPACE);
        } else {
            this.initBaseInfo();
        }
        return SUCCESS;
    }

    /**
     * Mass update entity record information
     */
    public String massUpdate() throws Exception {
        saveEntity();
        String[] fieldNames = this.massUpdate;
        if (fieldNames != null) {
            String[] selectIDArray = this.seleteIDs.split(",");
            Collection<Lead> leads = new ArrayList<Lead>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Lead leadInstance = this.baseService.getEntityById(Lead.class,
                        id);
                Lead originalLead = leadInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(lead, fieldName);
                    BeanUtil.setFieldValue(leadInstance, fieldName, value);
                }
                leadInstance.setUpdated_by(user);
                leadInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(originalLead,
                        leadInstance);
                allChangeLogs.addAll(changeLogs);
                leads.add(leadInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (leads.size() > 0) {
                this.baseService.batchUpdate(leads);
                taskExecutor.execute(new Runnable() {
                    public void run() {
                        batchInserChangeLogs(changeLogsForSave);
                    }
                });
            }
        }
        return SUCCESS;
    }

    /**
     * Saves entity field
     * 
     * @return original lead record
     * @throws Exception
     */
    private Lead saveEntity() throws Exception {
        Lead originalLead = null;
        if (lead.getId() == null) {
            UserUtil.permissionCheck("create_lead");
        } else {
            UserUtil.permissionCheck("update_lead");
            originalLead = baseService.getEntityById(Lead.class, lead.getId());
            lead.setContacts(originalLead.getContacts());
            lead.setOpportunities(originalLead.getOpportunities());
            lead.setTargetLists(originalLead.getTargetLists());
            lead.setCalls(originalLead.getCalls());
            lead.setMeetings(originalLead.getMeetings());
            lead.setCreated_on(originalLead.getCreated_on());
            lead.setCreated_by(originalLead.getCreated_by());
        }

        Account account = null;
        if (accountID != null) {
            account = accountService.getEntityById(Account.class, accountID);
        }
        lead.setAccount(account);

        LeadStatus leadStatus = null;
        if (leadStatusID != null) {
            leadStatus = leadStatusService.getEntityById(LeadStatus.class,
                    leadStatusID);
        }
        lead.setStatus(leadStatus);

        LeadSource leadSource = null;
        if (leadSourceID != null) {
            leadSource = leadSourceService.getEntityById(LeadSource.class,
                    leadSourceID);
        }
        lead.setLead_source(leadSource);

        Salutation salutation = null;
        if (salutationID != null) {
            salutation = salutationService.getEntityById(Salutation.class,
                    salutationID);
        }
        lead.setSalutation(salutation);

        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        lead.setAssigned_to(assignedTo);

        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        lead.setOwner(owner);

        Campaign campaign = null;
        if (campaignID != null) {
            campaign = campaignService
                    .getEntityById(Campaign.class, campaignID);
        }
        lead.setCampaign(campaign);

        if ("Opportunity".equals(this.getRelationKey())) {
            Opportunity opportunity = opportunityService
                    .getEntityById(Opportunity.class,
                            Integer.valueOf(this.getRelationValue()));
            Set<Opportunity> opportunities = lead.getOpportunities();
            if (opportunities == null) {
                opportunities = new HashSet<Opportunity>();
            }
            opportunities.add(opportunity);
        } else if ("TargetList".equals(this.getRelationKey())) {
            TargetList targetList = targetListService.getEntityById(
                    TargetList.class, Integer.valueOf(this.getRelationValue()));
            Set<TargetList> targetLists = lead.getTargetLists();
            if (targetLists == null) {
                targetLists = new HashSet<TargetList>();
            }
            targetLists.add(targetList);
        } else if ("Call".equals(this.getRelationKey())) {
            Call call = callService.getEntityById(Call.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Call> calls = lead.getCalls();
            if (calls == null) {
                calls = new HashSet<Call>();
            }
            calls.add(call);
        } else if ("Meeting".equals(this.getRelationKey())) {
            Meeting meeting = meetingService.getEntityById(Meeting.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Meeting> meetings = lead.getMeetings();
            if (meetings == null) {
                meetings = new HashSet<Meeting>();
            }
            meetings.add(meeting);
        }else if ("Visit".equals(this.getRelationKey())) {
        	Visit visit = visitService.getEntityById(Visit.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Visit> visits = lead.getVisits();
            if (visits == null) {
            	visits = new HashSet<Visit>();
            }
            visits.add(visit);
        }
        else if ("Contact".equals(this.getRelationKey())) {
            Contact contact = contactService.getEntityById(Contact.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Contact> contacts = lead.getContacts();
            if (contacts == null) {
                contacts = new HashSet<Contact>();
            }
            contacts.add(contact);
        }
        super.updateBaseInfo(lead);
        return originalLead;
    }

    /**
     * Creates change log
     * 
     * @param originalLead
     *            original lead record
     * @param lead
     *            current lead record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Lead originalLead, Lead lead) {
        Collection<ChangeLog> changeLogs = null;
        if (originalLead != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = Lead.class.getSimpleName();
            Integer recordID = lead.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldSalutation = getOptionValue(originalLead.getSalutation());
            String newSalutation = getOptionValue(lead.getSalutation());
            createChangeLog(changeLogs, entityName, recordID,
                    "menu.salutation.title", oldSalutation, newSalutation,
                    loginUser);

            String oldFirstName = CommonUtil.fromNullToEmpty(originalLead
                    .getFirst_name());
            String newFirstName = CommonUtil.fromNullToEmpty(lead
                    .getFirst_name());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.first_name.label", oldFirstName, newFirstName,
                    loginUser);

            String oldLastName = CommonUtil.fromNullToEmpty(originalLead
                    .getLast_name());
            String newLastName = CommonUtil
                    .fromNullToEmpty(lead.getLast_name());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.last_name.label", oldLastName, newLastName,
                    loginUser);

            String oldOfficePhone = CommonUtil.fromNullToEmpty(originalLead
                    .getOffice_phone());
            String newOfficePhone = CommonUtil.fromNullToEmpty(lead
                    .getOffice_phone());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.office_phone.label", oldOfficePhone,
                    newOfficePhone, loginUser);

            String oldTitle = CommonUtil.fromNullToEmpty(originalLead
                    .getTitle());
            String newTitle = CommonUtil.fromNullToEmpty(lead.getTitle());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.title.label", oldTitle, newTitle, loginUser);

            String oldMobile = CommonUtil.fromNullToEmpty(originalLead
                    .getMobile());
            String newMobile = CommonUtil.fromNullToEmpty(lead.getMobile());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.mobile.label", oldMobile, newMobile, loginUser);

            String oldDepartment = CommonUtil.fromNullToEmpty(originalLead
                    .getDepartment());
            String newDepartment = CommonUtil.fromNullToEmpty(lead
                    .getDepartment());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.department.label", oldDepartment, newDepartment,
                    loginUser);

            String oldFax = CommonUtil.fromNullToEmpty(originalLead.getFax());
            String newWFax = CommonUtil.fromNullToEmpty(lead.getFax());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.fax.label", oldFax, newWFax, loginUser);

            String oldPrimaryStreet = CommonUtil.fromNullToEmpty(originalLead
                    .getPrimary_street());
            String newPrimaryStreet = CommonUtil.fromNullToEmpty(lead
                    .getPrimary_street());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_street.label", oldPrimaryStreet,
                    newPrimaryStreet, loginUser);

            String oldPrimaryState = CommonUtil.fromNullToEmpty(originalLead
                    .getPrimary_state());
            String newPrimaryState = CommonUtil.fromNullToEmpty(lead
                    .getPrimary_state());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_state.label", oldPrimaryState,
                    newPrimaryState, loginUser);

            String oldPrimaryPostalCode = CommonUtil
                    .fromNullToEmpty(originalLead.getPrimary_postal_code());
            String newPrimaryPostalCode = CommonUtil.fromNullToEmpty(lead
                    .getPrimary_postal_code());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_postal_code.label", oldPrimaryPostalCode,
                    newPrimaryPostalCode, loginUser);

            String oldPrimaryCountry = CommonUtil.fromNullToEmpty(originalLead
                    .getPrimary_country());
            String newPrimaryCountry = CommonUtil.fromNullToEmpty(lead
                    .getPrimary_country());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.primary_country.label", oldPrimaryCountry,
                    newPrimaryCountry, loginUser);

            String oldOtherStreet = CommonUtil.fromNullToEmpty(originalLead
                    .getOther_street());
            String newOtherStreet = CommonUtil.fromNullToEmpty(lead
                    .getOther_street());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_street.label", oldOtherStreet,
                    newOtherStreet, loginUser);

            String oldOtherState = CommonUtil.fromNullToEmpty(originalLead
                    .getOther_state());
            String newOtherState = CommonUtil.fromNullToEmpty(lead
                    .getOther_state());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_state.label", oldOtherState, newOtherState,
                    loginUser);

            String oldOtherPostalCode = CommonUtil.fromNullToEmpty(originalLead
                    .getOther_postal_code());
            String newOtherPostalCode = CommonUtil.fromNullToEmpty(lead
                    .getOther_postal_code());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_postal_code.label", oldOtherPostalCode,
                    newOtherPostalCode, loginUser);

            String oldOtherCountry = CommonUtil.fromNullToEmpty(originalLead
                    .getOther_country());
            String newOtherCountry = CommonUtil.fromNullToEmpty(lead
                    .getOther_country());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.other_country.label", oldOtherCountry,
                    newOtherCountry, loginUser);

            String oldEmail = CommonUtil.fromNullToEmpty(originalLead
                    .getEmail());
            String newEmail = CommonUtil.fromNullToEmpty(lead.getEmail());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.email.label", oldEmail, newEmail, loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalLead
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(lead.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldLeadSource = getOptionValue(originalLead.getLead_source());
            String newLeadSource = getOptionValue(lead.getLead_source());
            createChangeLog(changeLogs, entityName, recordID,
                    "menu.leadSource.title", oldLeadSource, newLeadSource,
                    loginUser);

            String oldLeadSourceDescription = CommonUtil
                    .fromNullToEmpty(originalLead.getLead_source_description());
            String newLeadSourceDescription = CommonUtil.fromNullToEmpty(lead
                    .getLead_source_description());
            createChangeLog(changeLogs, entityName, recordID,
                    "lead.lead_source_description.label",
                    oldLeadSourceDescription, newLeadSourceDescription,
                    loginUser);

            String oldReferredBy = CommonUtil.fromNullToEmpty(originalLead
                    .getReferred_by());
            String newReferredBy = CommonUtil.fromNullToEmpty(lead
                    .getLead_source_description());
            createChangeLog(changeLogs, entityName, recordID,
                    "lead.referred_by.label", oldReferredBy, newReferredBy,
                    loginUser);

            String oldAccountName = "";
            Account oldAccount = originalLead.getAccount();
            if (oldAccount != null) {
                oldAccountName = CommonUtil.fromNullToEmpty(oldAccount
                        .getName());
            }
            String newAccountName = "";
            Account newAccount = lead.getAccount();
            if (newAccount != null) {
                newAccountName = CommonUtil.fromNullToEmpty(newAccount
                        .getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.account.label", oldAccountName, newAccountName,
                    loginUser);

            boolean oldNotCall = originalLead.isNot_call();

            boolean newNotCall = lead.isNot_call();
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.not_call.label", String.valueOf(oldNotCall),
                    String.valueOf(newNotCall), loginUser);

            String oldCampaignName = "";
            Campaign oldCampaign = originalLead.getCampaign();
            if (oldCampaign != null) {
                oldCampaignName = oldCampaign.getName();
            }
            String newCampaignName = "";
            Campaign newCampaign = lead.getCampaign();
            if (newCampaign != null) {
                newCampaignName = newCampaign.getName();
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.campaign.label",
                    CommonUtil.fromNullToEmpty(oldCampaignName),
                    CommonUtil.fromNullToEmpty(newCampaignName), loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalLead.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = lead.getAssigned_to();
            if (newAssignedTo != null) {
                newAssignedToName = newAssignedTo.getName();
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.assigned_to.label",
                    CommonUtil.fromNullToEmpty(oldAssignedToName),
                    CommonUtil.fromNullToEmpty(newAssignedToName), loginUser);
        }
        return changeLogs;
    }

    /**
     * Gets Lead Relation Counts
     * 
     * @return null
     */
    public String getLeadRelationCounts() throws Exception {
        long taskNumber = this.baseService
                .countsByParams(
                        "select count(task.id) from Task task where related_object='Lead' and related_record = ?",
                        new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"taskNumber\":\"").append(taskNumber)
                .append("\"}");
        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
        return null;
    }

    /**
     * Prepares the list
     * 
     */
    public void prepare() throws Exception {
        ActionContext context = ActionContext.getContext();
        Map<String, Object> session = context.getSession();
        String local = (String) session.get("locale");
        this.leadStatuses = leadStatusService.getOptions(
                LeadStatus.class.getSimpleName(), local);
        this.leadSources = leadSourceService.getOptions(
                LeadSource.class.getSimpleName(), local);
        this.salutations = salutationService.getOptions(
                Salutation.class.getSimpleName(), local);
    }

    /**
     * Converts the lead
     * 
     * @return the SUCCESS result
     */
    public String convert() throws Exception {
        this.getBaseService().convert(this.getId(), accountCheck, contactCheck,
                opportunityCheck);
        return SUCCESS;
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
     * @return the userService
     */
    public IBaseService<User> getUserService() {
        return userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(IBaseService<User> userService) {
        this.userService = userService;
    }

    /**
     * @return the campaignService
     */
    public IBaseService<Campaign> getCampaignService() {
        return campaignService;
    }

    /**
     * @param campaignService
     *            the campaignService to set
     */
    public void setCampaignService(IBaseService<Campaign> campaignService) {
        this.campaignService = campaignService;
    }

    /**
     * @return the lead
     */
    public Lead getLead() {
        return lead;
    }

    /**
     * @param lead
     *            the lead to set
     */
    public void setLead(Lead lead) {
        this.lead = lead;
    }

    /**
     * @return the accountID
     */
    public Integer getAccountID() {
        return accountID;
    }

    /**
     * @param accountID
     *            the accountID to set
     */
    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    /**
     * @return the leadSourceID
     */
    public Integer getLeadSourceID() {
        return leadSourceID;
    }

    /**
     * @param leadSourceID
     *            the leadSourceID to set
     */
    public void setLeadSourceID(Integer leadSourceID) {
        this.leadSourceID = leadSourceID;
    }

    /**
     * @return the campaignID
     */
    public Integer getCampaignID() {
        return campaignID;
    }

    /**
     * @param campaignID
     *            the campaignID to set
     */
    public void setCampaignID(Integer campaignID) {
        this.campaignID = campaignID;
    }

    /**
     * @return the leadSources
     */
    public List<LeadSource> getLeadSources() {
        return leadSources;
    }

    /**
     * @param leadSources
     *            the leadSources to set
     */
    public void setLeadSources(List<LeadSource> leadSources) {
        this.leadSources = leadSources;
    }

    /**
     * @return the leadStatuses
     */
    public List<LeadStatus> getLeadStatuses() {
        return leadStatuses;
    }

    /**
     * @param leadStatuses
     *            the leadStatuses to set
     */
    public void setLeadStatuses(List<LeadStatus> leadStatuses) {
        this.leadStatuses = leadStatuses;
    }

    /**
     * @return the leadStatusID
     */
    public Integer getLeadStatusID() {
        return leadStatusID;
    }

    /**
     * @param leadStatusID
     *            the leadStatusID to set
     */
    public void setLeadStatusID(Integer leadStatusID) {
        this.leadStatusID = leadStatusID;
    }

    /**
     * @return the accountCheck
     */
    public boolean isAccountCheck() {
        return accountCheck;
    }

    /**
     * @param accountCheck
     *            the accountCheck to set
     */
    public void setAccountCheck(boolean accountCheck) {
        this.accountCheck = accountCheck;
    }

    /**
     * @return the contactCheck
     */
    public boolean isContactCheck() {
        return contactCheck;
    }

    /**
     * @param contactCheck
     *            the contactCheck to set
     */
    public void setContactCheck(boolean contactCheck) {
        this.contactCheck = contactCheck;
    }

    /**
     * @return the opportunityCheck
     */
    public boolean isOpportunityCheck() {
        return opportunityCheck;
    }

    /**
     * @param opportunityCheck
     *            the opportunityCheck to set
     */
    public void setOpportunityCheck(boolean opportunityCheck) {
        this.opportunityCheck = opportunityCheck;
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
     * @return the targetListService
     */
    public IBaseService<TargetList> getTargetListService() {
        return targetListService;
    }

    /**
     * @param targetListService
     *            the targetListService to set
     */
    public void setTargetListService(IBaseService<TargetList> targetListService) {
        this.targetListService = targetListService;
    }

    /**
     * @return the callService
     */
    public IBaseService<Call> getCallService() {
        return callService;
    }

    /**
     * @param callService
     *            the callService to set
     */
    public void setCallService(IBaseService<Call> callService) {
        this.callService = callService;
    }

    /**
     * @return the meetingService
     */
    public IBaseService<Meeting> getMeetingService() {
        return meetingService;
    }

    /**
     * @param meetingService
     *            the meetingService to set
     */
    public void setMeetingService(IBaseService<Meeting> meetingService) {
        this.meetingService = meetingService;
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
     * @return the accountText
     */
    public String getAccountText() {
        return accountText;
    }

    /**
     * @param accountText
     *            the accountText to set
     */
    public void setAccountText(String accountText) {
        this.accountText = accountText;
    }

    /**
     * @return the campaignText
     */
    public String getCampaignText() {
        return campaignText;
    }

    /**
     * @param campaignText
     *            the campaignText to set
     */
    public void setCampaignText(String campaignText) {
        this.campaignText = campaignText;
    }

    /**
     * @return the baseService
     */
    public ILeadService getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(ILeadService baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the salutations
     */
    public List<Salutation> getSalutations() {
        return salutations;
    }

    /**
     * @param salutations
     *            the salutations to set
     */
    public void setSalutations(List<Salutation> salutations) {
        this.salutations = salutations;
    }

    /**
     * @return the salutationID
     */
    public Integer getSalutationID() {
        return salutationID;
    }

    /**
     * @param salutationID
     *            the salutationID to set
     */
    public void setSalutationID(Integer salutationID) {
        this.salutationID = salutationID;
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

    /**
     * @return the leadSourceService
     */
    public IOptionService<LeadSource> getLeadSourceService() {
        return leadSourceService;
    }

    /**
     * @param leadSourceService
     *            the leadSourceService to set
     */
    public void setLeadSourceService(
            IOptionService<LeadSource> leadSourceService) {
        this.leadSourceService = leadSourceService;
    }

    /**
     * @return the salutationService
     */
    public IOptionService<Salutation> getSalutationService() {
        return salutationService;
    }

    /**
     * @param salutationService
     *            the salutationService to set
     */
    public void setSalutationService(
            IOptionService<Salutation> salutationService) {
        this.salutationService = salutationService;
    }

    /**
     * @return the changeLogService
     */
    public IBaseService<ChangeLog> getChangeLogService() {
        return changeLogService;
    }

    /**
     * @param changeLogService
     *            the changeLogService to set
     */
    public void setChangeLogService(IBaseService<ChangeLog> changeLogService) {
        this.changeLogService = changeLogService;
    }

    /**
     * @return the taskExecutor
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    /**
     * @param taskExecutor
     *            the taskExecutor to set
     */
    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

	public IBaseService<Visit> getVisitService() {
		return visitService;
	}

	public void setVisitService(IBaseService<Visit> visitService) {
		this.visitService = visitService;
	}

}
