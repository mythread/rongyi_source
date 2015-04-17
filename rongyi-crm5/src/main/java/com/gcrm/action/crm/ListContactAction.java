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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Account;
import com.gcrm.domain.Call;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.CaseInstance;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Contact_default;
import com.gcrm.domain.Document;
import com.gcrm.domain.Lead;
import com.gcrm.domain.LeadSource;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.Religious;
import com.gcrm.domain.Salutation;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.domain.Visit;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Contact
 * 
 */
public class ListContactAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Contact> baseService;
    private IBaseService<Account> accountService;
    private IOptionService<Religious> religiousService;
    private IOptionService<LeadSource> leadSourceService;
    private IOptionService<Salutation> salutationService;
    private IOptionService<Contact_default> contact_defaultService;
    private IBaseService<User> userService;
    private IBaseService<Campaign> campaignService;
    private IBaseService<Opportunity> opportunityService;
    private IBaseService<TargetList> targetListService;
    private IBaseService<Call> callService;
    private IBaseService<Meeting> meetingService;
    private IBaseService<Visit> visitService;
    private IBaseService<Document> documentService;
    private IBaseService<CaseInstance> caseService;
    private Contact contact;

    private static final String CLAZZ = Contact.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<Contact> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);

        Iterator<Contact> contacts = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();

        getListJson(contacts, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_contact");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);

        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_contact(), loginUser);

        SearchResult<Contact> result = baseService.getPaginationObjects(CLAZZ,
                searchCondition);
        //aaa

        Iterator<Contact> contacts = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();
        getListJson(contacts, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<Contact> contacts,
            long totalRecords, SearchCondition searchCondition, boolean isList)
            throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String assignedTo = null;
        String accountName = null;
        while (contacts.hasNext()) {
            Contact instance = contacts.next();
            int id = instance.getId();
            Contact_default contact_default = instance.getContact_default();
            String lastName = instance.getLast_name();
            String nickName = instance.getNick_name();
            String name = instance.getName();
            String title = CommonUtil.fromNullToEmpty(instance.getTitle());    

            Account account = instance.getAccount();
            if (account != null) {
                accountName = CommonUtil.fromNullToEmpty(account.getName());
            } else {
                accountName = "";
            }
            String office_email = CommonUtil.fromNullToEmpty(instance.getOffice_email());
            String person_email = CommonUtil.fromNullToEmpty(instance.getOffice_email());
            String officePhone = CommonUtil.fromNullToEmpty(instance
                    .getOffice_phone());
            String personPhone = CommonUtil.fromNullToEmpty(instance
            		.getPerson_phone());
            User user = instance.getAssigned_to();
            if (user != null) {
                assignedTo = CommonUtil.fromNullToEmpty(user.getName());
            } else {
                assignedTo = "";
            }
            if (isList) {
                User createdBy = instance.getCreated_by();
                String createdByName = "";
                if (createdBy != null) {
                    createdByName = CommonUtil.fromNullToEmpty(createdBy
                            .getName());
                }
                User updatedBy = instance.getUpdated_by();
                String updatedByName = "";
                if (updatedBy != null) {
                    updatedByName = CommonUtil.fromNullToEmpty(updatedBy
                            .getName());
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        Constant.DATE_TIME_FORMAT);
                Date createdOn = instance.getCreated_on();
                String createdOnName = "";
                if (createdOn != null) {
                    createdOnName = dateFormat.format(createdOn);
                }
                Date updatedOn = instance.getUpdated_on();
                String updatedOnName = "";
                if (updatedOn != null) {
                    updatedOnName = dateFormat.format(updatedOn);
                }

                jsonBuilder.append("{\"cell\":[\"").append(id).append("\",\"")
                        .append(lastName).append("\",\"").append(nickName)
                        .append("\",\"").append(title).append("\",\"")
                        .append(accountName).append("\",\"").append(office_email).append("\",\"")
                        .append(assignedTo).append("\",\"")
                        .append(contact_default.getLabel_zh_CN()).append("\",\"")
                        .append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnName).append("\",\"")
                        .append(updatedOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"name\":\"").append(name)
                        .append("\",\"title\":\"").append(title)
                        .append("\",\"account.name\":\"").append(accountName)
                        .append("\",\"office_email\":\"").append(office_email)
                        .append("\",\"assigned_to.name\":\"")
                        .append(assignedTo).append("\"}");
            }
            if (contacts.hasNext()) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]}");

        // Returns JSON data back to page
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(jsonBuilder.toString());
    }

    /**
     * Selects the entities
     * 
     * @return the SUCCESS result
     */
    public String select() throws ServiceException {
        Opportunity opportunity = null;
        TargetList targetList = null;
        Call call = null;
        Meeting meeting = null;
        Visit visit=null;
        Document document = null;
        CaseInstance caseInstance = null;
        Set<Contact> contacts = null;

        if ("Opportunity".equals(this.getRelationKey())) {
            opportunity = opportunityService.getEntityById(Opportunity.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = opportunity.getContacts();
        } else if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = targetList.getContacts();
        } else if ("Call".equals(this.getRelationKey())) {
            call = callService.getEntityById(Call.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = call.getContacts();
        } else if ("Meeting".equals(this.getRelationKey())) {
            meeting = meetingService.getEntityById(Meeting.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = meeting.getContacts();
        }else if ("Visit".equals(this.getRelationKey())) {
            visit = visitService.getEntityById(Visit.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = visit.getContacts();
        } else if ("Document".equals(this.getRelationKey())) {
            document = documentService.getEntityById(Document.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = document.getContacts();
        } else if ("CaseInstance".equals(this.getRelationKey())) {
            caseInstance = caseService.getEntityById(CaseInstance.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = caseInstance.getContacts();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String selectId = ids[i];
                contact = baseService.getEntityById(Contact.class,
                        Integer.valueOf(selectId));
                contacts.add(contact);
            }
        }
        if ("Opportunity".equals(super.getRelationKey())) {
            opportunityService.makePersistent(opportunity);
        } else if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        } else if ("Call".equals(this.getRelationKey())) {
            callService.makePersistent(call);
        	visitService.makePersistent(visit);
        } else if ("Document".equals(this.getRelationKey())) {
            documentService.makePersistent(document);
        } else if ("CaseInstance".equals(this.getRelationKey())) {
            caseService.makePersistent(caseInstance);
        }
        return SUCCESS;
    }

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */
    public String unselect() throws ServiceException {
        Opportunity opportunity = null;
        TargetList targetList = null;
        Call call = null;
        Meeting meeting = null;
        Visit visit = null;
        Document document = null;
        CaseInstance caseInstance = null;
        Set<Contact> contacts = null;
        if ("Opportunity".equals(this.getRelationKey())) {
            opportunity = opportunityService.getEntityById(Opportunity.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = opportunity.getContacts();
        } else if ("TargetList".equals(this.getRelationKey())) {
            targetList = targetListService.getEntityById(TargetList.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = targetList.getContacts();
        } else if ("Call".equals(this.getRelationKey())) {
            call = callService.getEntityById(Call.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = call.getContacts();
        } else if ("Meeting".equals(this.getRelationKey())) {
            meeting = meetingService.getEntityById(Meeting.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = meeting.getContacts();
        } else if ("Visit".equals(this.getRelationKey())) {
        	visit = visitService.getEntityById(Visit.class,
        			Integer.valueOf(this.getRelationValue()));
        	contacts = visit.getContacts();
        } else if ("Document".equals(this.getRelationKey())) {
            document = documentService.getEntityById(Document.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = document.getContacts();
        } else if ("CaseInstance".equals(this.getRelationKey())) {
            caseInstance = caseService.getEntityById(CaseInstance.class,
                    Integer.valueOf(this.getRelationValue()));
            contacts = caseInstance.getContacts();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            Collection<Contact> selectedContacts = new ArrayList<Contact>();
            for (int i = 0; i < ids.length; i++) {
                Integer removeId = Integer.valueOf(ids[i]);
                A: for (Contact contact : contacts) {
                    if (contact.getId().intValue() == removeId.intValue()) {
                        selectedContacts.add(contact);
                        break A;
                    }
                }
            }
            contacts.removeAll(selectedContacts);
        }
        if ("Opportunity".equals(super.getRelationKey())) {
            opportunityService.makePersistent(opportunity);
        } else if ("TargetList".equals(this.getRelationKey())) {
            targetListService.makePersistent(targetList);
        } else if ("Call".equals(this.getRelationKey())) {
            callService.makePersistent(call);
        } else if ("Meeting".equals(this.getRelationKey())) {
            meetingService.makePersistent(meeting);
        } else if ("Visit".equals(this.getRelationKey())) {
        	visitService.makePersistent(visit);
        } else if ("Document".equals(this.getRelationKey())) {
            documentService.makePersistent(document);
        } else if ("CaseInstance".equals(this.getRelationKey())) {
            caseService.makePersistent(caseInstance);
        }
        return SUCCESS;
    }

    /**
     * Gets the related documents
     * 
     * @return null
     */
    public String relateContactDocument() throws Exception {
        contact = baseService.getEntityById(Contact.class, id);
        Set<Document> documents = contact.getDocuments();
        Iterator<Document> documentIterator = documents.iterator();
        long totalRecords = documents.size();
        ListDocumentAction.getListJson(documentIterator, totalRecords, null,
                false);
        return null;
    }

    /**
     * Gets the related cases
     * 
     * @return null
     */
    public String relateContactCase() throws Exception {
        contact = baseService.getEntityById(Contact.class, id);
        Set<CaseInstance> cases = contact.getCases();
        Iterator<CaseInstance> caseIterator = cases.iterator();
        long totalRecords = cases.size();
        ListCaseAction.getListJson(caseIterator, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the related opportunities
     * 
     * @return null
     */
    public String relateContactOpportunity() throws Exception {
        contact = baseService.getEntityById(Contact.class, id);
        Set<Opportunity> opportunities = contact.getOpportunities();
        Iterator<Opportunity> opportunityIterator = opportunities.iterator();
        long totalRecords = opportunities.size();
        ListOpportunityAction.getListJson(opportunityIterator, totalRecords,
                null, false);
        return null;
    }

    /**
     * Gets the related leads
     * 
     * @return null
     */
    public String relateContactLead() throws Exception {
        contact = baseService.getEntityById(Contact.class, id);
        Set<Lead> leads = contact.getLeads();
        Iterator<Lead> leadIterator = leads.iterator();
        long totalRecords = leads.size();
        ListLeadAction.getListJson(leadIterator, totalRecords, null, false);
        return null;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_contact");
        baseService.batchDeleteEntity(Contact.class, this.getSeleteIDs());
        return SUCCESS;
    }

    /**
     * Removes the related entities
     * 
     * @return the SUCCESS result
     */
    public String remove() throws ServiceException {
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String removeId = ids[i];
                contact = baseService.getEntityById(Contact.class,
                        Integer.valueOf(removeId));
                if ("Account".endsWith(super.getRemoveKey())) {
                    contact.setAccount(null);
                }
                this.baseService.makePersistent(contact);
            }
        }
        return SUCCESS;
    }

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */
    public String copy() throws Exception {
        UserUtil.permissionCheck("create_contact");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                Contact oriRecord = baseService.getEntityById(Contact.class,
                        Integer.valueOf(copyid));
                Contact targetRecord = oriRecord.clone();
                targetRecord.setId(null);
                this.getbaseService().makePersistent(targetRecord);
            }
        }
        return SUCCESS;
    }

    /**
     * Exports the entities
     * 
     * @return the exported entities inputStream
     */
    public InputStream getInputStream() throws Exception {
        return getDownloadContent(false);
    }

    /**
     * Exports the template
     * 
     * @return the exported template inputStream
     */
    public InputStream getTemplateStream() throws Exception {
        return getDownloadContent(true);
    }

    private InputStream getDownloadContent(boolean isTemplate) throws Exception {
        UserUtil.permissionCheck("view_contact");
        String fileName = getText("entity.contact.label") + ".xls";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] { getText("entity.id.label"),
                    getText("entity.salutation_id.label"),
                    getText("entity.salutation_name.label"),
                    getText("entity.contact_default_id.label"),
                    getText("entity.contact_default_name.label"),
                    getText("entity.first_name.label"),
                    getText("entity.last_name.label"),
                    getText("entity.email.label"),
                    getText("entity.office_phone.label"),
                    getText("entity.title.label"),
                    getText("entity.mobile.label"),
                    getText("contact.skype_id.label"),
                    getText("entity.department.label"),
                    getText("entity.fax.label"),
                    getText("entity.account_id.label"),
                    getText("entity.account_name.label"),
                    getText("entity.website.label"),
                    getText("entity.primary_street.label"),
                    getText("entity.primary_city.label"),
                    getText("entity.primary_state.label"),
                    getText("entity.primary_postal_code.label"),
                    getText("entity.primary_country.label"),
                    getText("entity.other_street.label"),
                    getText("entity.other_city.label"),
                    getText("entity.other_state.label"),
                    getText("entity.other_postal_code.label"),
                    getText("entity.other_country.label"),
                    getText("entity.origo.label"),
                    getText("entity.height.label"),
                    getText("entity.weight.label"),
                    getText("entity.relationship.label"),
                    getText("entity.character.label"),
                    getText("entity.interest.label"),
                    getText("entity.taboo.label"),
                    getText("entity.religious_id.label"),
                    getText("entity.religious_name.label"),
                    getText("entity.habit.label"),
                    getText("entity.diet.label"),
                    getText("entity.notes.label"),
                    getText("contact.report_to_id.label"),
                    getText("contact.report_to_name.label"),
                    getText("entity.not_call.label"),
                    getText("entity.leadSource_id.label"),
                    getText("entity.leadSource_name.label"),
                    getText("entity.campaign_id.label"),
                    getText("entity.campaign_name.label"),
                    getText("entity.assigned_to_id.label"),
                    getText("entity.assigned_to_name.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    Contact contact = baseService.getEntityById(Contact.class,
                            Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], contact.getId());
                    Salutation salutation = contact.getSalutation();
                    if (salutation != null) {
                        data1.put(header[1], salutation.getId());
                    } else {
                        data1.put(header[1], "");
                    }
                    Contact_default contact_default = contact.getContact_default();
                    if (contact_default != null) {
                    	data1.put(header[1], contact_default.getId());
                    } else {
                    	data1.put(header[1], "");
                    }
                    data1.put(header[2], CommonUtil.getOptionLabel(contact_default));
                    data1.put(header[2], CommonUtil.getOptionLabel(salutation));
                    data1.put(header[3],
                            CommonUtil.fromNullToEmpty(contact.getNick_name()));
                    data1.put(header[4],
                            CommonUtil.fromNullToEmpty(contact.getLast_name()));
                    data1.put(header[5],
                            CommonUtil.fromNullToEmpty(contact.getOffice_email()));
                    data1.put(header[6], CommonUtil.fromNullToEmpty(contact
                            .getOffice_phone()));
                    data1.put(header[7],
                            CommonUtil.fromNullToEmpty(contact.getTitle()));
                    data1.put(header[8],
                            CommonUtil.fromNullToEmpty(contact.getMobile()));
                    data1.put(header[9],
                            CommonUtil.fromNullToEmpty(contact.getSkype_id()));
                    data1.put(header[10],
                            CommonUtil.fromNullToEmpty(contact.getDepartment()));
                    data1.put(header[11],
                            CommonUtil.fromNullToEmpty(contact.getFax()));
                    if (contact.getAccount() != null) {
                        data1.put(header[12], contact.getAccount().getId());
                        data1.put(header[13], contact.getAccount().getName());
                    } else {
                        data1.put(header[12], "");
                        data1.put(header[13], "");
                    }
                    data1.put(header[14],
                            CommonUtil.fromNullToEmpty(contact.getWebsite()));
                    data1.put(header[15], CommonUtil.fromNullToEmpty(contact
                            .getPrimary_street()));
                    data1.put(header[16], CommonUtil.fromNullToEmpty(contact
                            .getPrimary_city()));
                    data1.put(header[17], CommonUtil.fromNullToEmpty(contact
                            .getPrimary_state()));
                    data1.put(header[18], CommonUtil.fromNullToEmpty(contact
                            .getPrimary_postal_code()));
                    data1.put(header[19], CommonUtil.fromNullToEmpty(contact
                            .getPrimary_country()));
                    data1.put(header[20], CommonUtil.fromNullToEmpty(contact
                            .getOther_street()));
                    data1.put(header[21],
                            CommonUtil.fromNullToEmpty(contact.getOther_city()));
                    data1.put(header[22], CommonUtil.fromNullToEmpty(contact
                            .getOther_state()));
                    data1.put(header[23], CommonUtil.fromNullToEmpty(contact
                            .getOther_postal_code()));
                    data1.put(header[24], CommonUtil.fromNullToEmpty(contact
                            .getOther_country()));
                    data1.put(header[25],
                            CommonUtil.fromNullToEmpty(contact.getOrigo()));
                    data1.put(header[26], contact.getHeight());
                    data1.put(header[27], contact.getWeight());
                    data1.put(header[28], CommonUtil.fromNullToEmpty(contact
                            .getRelationship()));
                    data1.put(header[29],
                            CommonUtil.fromNullToEmpty(contact.getCharacter()));
                    data1.put(header[30],
                            CommonUtil.fromNullToEmpty(contact.getInterest()));
                    data1.put(header[31],
                            CommonUtil.fromNullToEmpty(contact.getTaboo()));
                    Religious religious = contact.getReligious();
                    if (religious != null) {
                        data1.put(header[32], religious.getId());
                    } else {
                        data1.put(header[32], "");
                    }
                    data1.put(header[33], CommonUtil.getOptionLabel(religious));
                    data1.put(header[34],
                            CommonUtil.fromNullToEmpty(contact.getHabit()));
                    data1.put(header[35],
                            CommonUtil.fromNullToEmpty(contact.getDiet()));
                    data1.put(header[36],
                            CommonUtil.fromNullToEmpty(contact.getNotes()));
                    if (contact.getReport_to() != null) {
                        data1.put(header[37], contact.getReport_to().getId());
                        data1.put(header[38], contact.getReport_to().getName());
                    } else {
                        data1.put(header[37], "");
                        data1.put(header[38], "");
                    }
                    data1.put(header[39], contact.isNot_call());
                    LeadSource leadSource = contact.getLeadSource();
                    if (leadSource != null) {
                        data1.put(header[40], leadSource.getId());
                    } else {
                        data1.put(header[40], "");
                    }
                    data1.put(header[41], CommonUtil.getOptionLabel(leadSource));
                    if (contact.getCampaign() != null) {
                        data1.put(header[42], contact.getCampaign().getId());
                        data1.put(header[43], contact.getCampaign().getName());
                    } else {
                        data1.put(header[42], "");
                        data1.put(header[43], "");
                    }
                    if (contact.getAssigned_to() != null) {
                        data1.put(header[44], contact.getAssigned_to().getId());
                        data1.put(header[45], contact.getAssigned_to()
                                .getName());
                    } else {
                        data1.put(header[44], "");
                        data1.put(header[45], "");
                    }
                    writer.write(data1, header);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            writer.close();
        }

        InputStream in = new FileInputStream(file);
        this.setFileName(fileName);
        return in;
    }

    /**
     * Imports the entities
     * 
     * @return the SUCCESS result
     */
    public String importCSV() throws Exception {
        File file = this.getUpload();
        CsvListReader reader = new CsvListReader(new FileReader(file),
                CsvPreference.EXCEL_PREFERENCE);
        int failedNum = 0;
        int successfulNum = 0;
        try {
            final String[] header = reader.getCSVHeader(true);

            List<String> line = new ArrayList<String>();
            Map<String, String> failedMsg = new HashMap<String, String>();
            while ((line = reader.read()) != null) {

                Map<String, String> row = new HashMap<String, String>();
                for (int i = 0; i < line.size(); i++) {
                    row.put(header[i], line.get(i));
                }

                Contact contact = new Contact();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        contact.setId(Integer.parseInt(id));
                        UserUtil.permissionCheck("update_contact");
                    } else {
                        UserUtil.permissionCheck("create_contact");
                    }
                    String salutationID = row
                            .get(getText("entity.salutation_id.label"));
                    if (CommonUtil.isNullOrEmpty(salutationID)) {
                        contact.setSalutation(null);
                    } else {
                        Salutation salutation = salutationService
                                .getEntityById(Salutation.class,
                                        Integer.parseInt(salutationID));
                        contact.setSalutation(salutation);
                    }
                    
                    String contact_defaultID = row
                    		.get(getText("entity.contact_default_id.label"));
                    if (CommonUtil.isNullOrEmpty(contact_defaultID)) {
                    	contact.setContact_default(null);
                    } else {
                    	Contact_default contact_default = contact_defaultService
                    			.getEntityById(Contact_default.class,
                    					Integer.parseInt(contact_defaultID));
                    	contact.setContact_default(contact_default);
                    }
                    contact.setNick_name(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.first_name.label"))));
                    contact.setLast_name(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.last_name.label"))));
                    contact.setOffice_email(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.email.label"))));
                    contact.setOffice_phone(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.office_phone.label"))));
                    contact.setTitle(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.title.label"))));
                    contact.setMobile(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.mobile.label"))));
                    contact.setSkype_id(CommonUtil.fromNullToEmpty(row
                            .get(getText("contact.skype_id.label"))));
                    contact.setDepartment(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.department.label"))));
                    contact.setFax(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.fax.label"))));
                    String accountID = row
                            .get(getText("entity.account_id.label"));
                    if (CommonUtil.isNullOrEmpty(accountID)) {
                        contact.setAccount(null);
                    } else {
                        Account account = accountService.getEntityById(
                                Account.class, Integer.parseInt(accountID));
                        contact.setAccount(account);
                    }
                    contact.setWebsite(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.website.label"))));
                    contact.setPrimary_street(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_street.label"))));
                    contact.setPrimary_city(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_city.label"))));
                    contact.setPrimary_state(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_state.label"))));
                    contact.setPrimary_postal_code(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_postal_code.label"))));
                    contact.setPrimary_country(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.primary_country.label"))));
                    contact.setOther_street(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_street.label"))));
                    contact.setOther_city(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_city.label"))));
                    contact.setOther_state(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_state.label"))));
                    contact.setOther_postal_code(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_postal_code.label"))));
                    contact.setOther_country(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.other_country.label"))));
                    contact.setOrigo(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.origo.label"))));
                    String height = row.get(getText("entity.height.label"));
                    if (CommonUtil.isNullOrEmpty(height)) {
                        contact.setHeight(0);
                    } else {
                        contact.setHeight(Double.parseDouble(height));
                    }
                    String weight = row.get(getText("entity.weight.label"));
                    if (CommonUtil.isNullOrEmpty(weight)) {
                        contact.setWeight(0);
                    } else {
                        contact.setWeight(Double.parseDouble(weight));
                    }
                    contact.setRelationship(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.relationship.label"))));
                    contact.setCharacter(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.character.label"))));
                    contact.setInterest(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.interest.label"))));
                    contact.setTaboo(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.taboo.label"))));
                    String religiousID = row
                            .get(getText("entity.religious_id.label"));
                    if (CommonUtil.isNullOrEmpty(religiousID)) {
                        contact.setReligious(null);
                    } else {
                        Religious religious = religiousService.getEntityById(
                                Religious.class, Integer.parseInt(religiousID));
                        contact.setReligious(religious);
                    }
                    contact.setHabit(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.habit.label"))));
                    contact.setDiet(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.diet.label"))));
                    contact.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    String reportToID = row
                            .get(getText("contact.report_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(reportToID)) {
                        contact.setReport_to(null);
                    } else {
                        Contact reportTo = baseService.getEntityById(
                                Contact.class, Integer.parseInt(reportToID));
                        contact.setReport_to(reportTo);
                    }
                    String doNotCall = row
                            .get(getText("entity.not_call.label"));
                    if (CommonUtil.isNullOrEmpty(doNotCall)) {
                        contact.setNot_call(false);
                    } else {
                        contact.setNot_call(Boolean.parseBoolean(doNotCall));
                    }

                    String leadSourceID = row
                            .get(getText("entity.leadSource_id.label"));
                    if (CommonUtil.isNullOrEmpty(leadSourceID)) {
                        contact.setLeadSource(null);
                    } else {
                        LeadSource leadSource = leadSourceService
                                .getEntityById(LeadSource.class,
                                        Integer.parseInt(leadSourceID));
                        contact.setLeadSource(leadSource);
                    }
                    String campaignID = row
                            .get(getText("entity.campaign_id.label"));
                    if (CommonUtil.isNullOrEmpty(campaignID)) {
                        contact.setCampaign(null);
                    } else {
                        Campaign campaign = campaignService.getEntityById(
                                Campaign.class, Integer.parseInt(campaignID));
                        contact.setCampaign(campaign);
                    }
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                        contact.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        contact.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(contact);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    String firstName = CommonUtil.fromNullToEmpty(contact
                            .getNick_name());
                    String lastName = CommonUtil.fromNullToEmpty(contact
                            .getLast_name());
                    failedMsg.put(firstName + " " + lastName, e.getMessage());
                }

            }

            this.setFailedMsg(failedMsg);
            this.setFailedNum(failedNum);
            this.setSuccessfulNum(successfulNum);
            this.setTotalNum(successfulNum + failedNum);
        } finally {
            reader.close();
        }
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return Contact.class.getSimpleName();
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public IBaseService<Contact> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<Contact> baseService) {
        this.baseService = baseService;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * @return the id
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    @Override
    public void setId(Integer id) {
        this.id = id;
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
     * @return the documentService
     */
    public IBaseService<Document> getDocumentService() {
        return documentService;
    }

    /**
     * @param documentService
     *            the documentService to set
     */
    public void setDocumentService(IBaseService<Document> documentService) {
        this.documentService = documentService;
    }

    /**
     * @return the caseService
     */
    public IBaseService<CaseInstance> getCaseService() {
        return caseService;
    }

    /**
     * @param caseService
     *            the caseService to set
     */
    public void setCaseService(IBaseService<CaseInstance> caseService) {
        this.caseService = caseService;
    }

    /**
     * @return the baseService
     */
    public IBaseService<Contact> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Contact> baseService) {
        this.baseService = baseService;
    }

    /**
     * @return the religiousService
     */
    public IOptionService<Religious> getReligiousService() {
        return religiousService;
    }

    /**
     * @param religiousService
     *            the religiousService to set
     */
    public void setReligiousService(IOptionService<Religious> religiousService) {
        this.religiousService = religiousService;
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

	public IBaseService<Visit> getVisitService() {
		return visitService;
	}

	public void setVisitService(IBaseService<Visit> visitService) {
		this.visitService = visitService;
	}

	public IOptionService<Contact_default> getContact_defaultService() {
		return contact_defaultService;
	}

	public void setContact_defaultService(IOptionService<Contact_default> contact_defaultService) {
		this.contact_defaultService = contact_defaultService;
	}


}
