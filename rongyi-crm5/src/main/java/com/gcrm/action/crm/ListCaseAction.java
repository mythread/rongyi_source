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
import com.gcrm.domain.CaseInstance;
import com.gcrm.domain.CaseOrigin;
import com.gcrm.domain.CasePriority;
import com.gcrm.domain.CaseReason;
import com.gcrm.domain.CaseStatus;
import com.gcrm.domain.CaseType;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Document;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Case
 * 
 */
public class ListCaseAction extends BaseListAction {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<CaseInstance> baseService;
    private IBaseService<CaseStatus> caseStatusService;
    private IBaseService<CasePriority> casePriorityService;
    private IBaseService<CaseType> caseTypeService;
    private IBaseService<CaseOrigin> caseOriginService;
    private IBaseService<CaseReason> caseReasonService;
    private IBaseService<Account> accountService;
    private IBaseService<User> userService;
    private IBaseService<Document> documentService;
    private IBaseService<Contact> contactService;
    private CaseInstance caseInstance;

    private static final String CLAZZ = CaseInstance.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */
    @Override
    public String list() throws Exception {

        SearchCondition searchCondition = getSearchCondition();
        SearchResult<CaseInstance> result = baseService.getPaginationObjects(
                CLAZZ, searchCondition);
        Iterator<CaseInstance> cases = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();

        getListJson(cases, totalRecords, null, false);
        return null;
    }

    /**
     * Gets the list data.
     * 
     * @return null
     */
    public String listFull() throws Exception {
        UserUtil.permissionCheck("view_case");

        Map<String, String> fieldTypeMap = new HashMap<String, String>();
        fieldTypeMap.put("created_on", Constant.DATA_TYPE_DATETIME);
        fieldTypeMap.put("updated_on", Constant.DATA_TYPE_DATETIME);
        User loginUser = UserUtil.getLoginUser();
        SearchCondition searchCondition = getSearchCondition(fieldTypeMap,
                loginUser.getScope_case(), loginUser);
        SearchResult<CaseInstance> result = baseService.getPaginationObjects(
                CLAZZ, searchCondition);
        Iterator<CaseInstance> cases = result.getResult().iterator();
        long totalRecords = result.getTotalRecords();

        getListJson(cases, totalRecords, searchCondition, true);
        return null;
    }

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
    public static void getListJson(Iterator<CaseInstance> cases,
            long totalRecords, SearchCondition searchCondition, boolean isList)
            throws Exception {

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder
                .append(getJsonHeader(totalRecords, searchCondition, isList));

        String assignedTo = null;
        String accountName = null;
        String priorityName = null;
        String statusName = null;
        while (cases.hasNext()) {
            CaseInstance instance = cases.next();
            int id = instance.getId();
            String subject = instance.getSubject();
            Account account = instance.getAccount();
            if (account != null) {
                accountName = account.getName();
            } else {
                accountName = "";
            }
            CasePriority casePriority = instance.getPriority();
            priorityName = CommonUtil.getOptionLabel(casePriority);
            CaseStatus caseStatus = instance.getStatus();
            statusName = CommonUtil.getOptionLabel(caseStatus);
            User user = instance.getAssigned_to();
            if (user != null) {
                assignedTo = user.getName();
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
                        .append(subject).append("\",\"").append(accountName)
                        .append("\",\"").append(priorityName).append("\",\"")
                        .append(statusName).append("\",\"").append(assignedTo)
                        .append("\",\"").append(createdByName).append("\",\"")
                        .append(updatedByName).append("\",\"")
                        .append(createdOnName).append("\",\"")
                        .append(updatedOnName).append("\"]}");
            } else {
                jsonBuilder.append("{\"id\":\"").append(id)
                        .append("\",\"subject\":\"").append(subject)
                        .append("\",\"account.name\":\"").append(accountName)
                        .append("\",\"priority.name\":\"").append(priorityName)
                        .append("\",\"status.name\":\"").append(statusName)
                        .append("\",\"assigned_to.name\":\"")
                        .append(assignedTo).append("\"}");
            }
            if (cases.hasNext()) {
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
        Document document = null;
        Contact contact = null;
        Set<CaseInstance> cases = null;
        if ("Document".equals(this.getRelationKey())) {
            document = documentService.getEntityById(Document.class,
                    Integer.valueOf(this.getRelationValue()));
            cases = document.getCases();
        } else if ("Contact".equals(this.getRelationKey())) {
            contact = contactService.getEntityById(Contact.class,
                    Integer.valueOf(this.getRelationValue()));
            cases = contact.getCases();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String selectId = ids[i];
                caseInstance = baseService.getEntityById(CaseInstance.class,
                        Integer.valueOf(selectId));
                cases.add(caseInstance);
            }
        }
        if ("Document".equals(this.getRelationKey())) {
            documentService.makePersistent(document);
        } else if ("Contact".equals(this.getRelationKey())) {
            contactService.makePersistent(contact);
        }
        return SUCCESS;
    }

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */
    public String unselect() throws ServiceException {
        Document document = null;
        Contact contact = null;
        Set<CaseInstance> cases = null;
        if ("Document".equals(this.getRelationKey())) {
            document = documentService.getEntityById(Document.class,
                    Integer.valueOf(this.getRelationValue()));
            cases = document.getCases();
        } else if ("Contact".equals(this.getRelationKey())) {
            contact = contactService.getEntityById(Contact.class,
                    Integer.valueOf(this.getRelationValue()));
            cases = contact.getCases();
        }

        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            Collection<CaseInstance> selectedCases = new ArrayList<CaseInstance>();
            for (int i = 0; i < ids.length; i++) {
                Integer removeId = Integer.valueOf(ids[i]);
                A: for (CaseInstance caseInstance : cases) {
                    if (caseInstance.getId().intValue() == removeId.intValue()) {
                        selectedCases.add(caseInstance);
                        break A;
                    }
                }
            }
            cases.removeAll(selectedCases);
        }
        if ("Document".equals(this.getRelationKey())) {
            documentService.makePersistent(document);
        } else if ("Contact".equals(this.getRelationKey())) {
            contactService.makePersistent(contact);
        }
        return SUCCESS;
    }

    /**
     * Gets the related contacts.
     * 
     * @return null
     */
    public String relateCaseContact() throws Exception {
        caseInstance = baseService.getEntityById(CaseInstance.class, id);
        Set<Contact> contacts = caseInstance.getContacts();
        Iterator<Contact> contactIterator = contacts.iterator();
        long totalRecords = contacts.size();
        ListContactAction.getListJson(contactIterator, totalRecords, null,
                false);
        return null;
    }

    /**
     * Gets the related documents.
     * 
     * @return null
     */
    public String relateCaseDocument() throws Exception {
        caseInstance = baseService.getEntityById(CaseInstance.class, id);
        Set<Document> documents = caseInstance.getDocuments();
        Iterator<Document> documentIterator = documents.iterator();
        long totalRecords = documents.size();
        ListDocumentAction.getListJson(documentIterator, totalRecords, null,
                false);
        return null;
    }

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */
    public String delete() throws Exception {
        UserUtil.permissionCheck("delete_case");
        baseService.batchDeleteEntity(CaseInstance.class, this.getSeleteIDs());
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
                caseInstance = baseService.getEntityById(CaseInstance.class,
                        Integer.valueOf(removeId));
                if ("Account".equals(super.getRemoveKey())) {
                    caseInstance.setAccount(null);
                } else if ("Contact".equals(super.getRemoveKey())) {

                }
                this.baseService.makePersistent(caseInstance);
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
        UserUtil.permissionCheck("create_case");
        if (this.getSeleteIDs() != null) {
            String[] ids = seleteIDs.split(",");
            for (int i = 0; i < ids.length; i++) {
                String copyid = ids[i];
                CaseInstance oriRecord = baseService.getEntityById(
                        CaseInstance.class, Integer.valueOf(copyid));
                CaseInstance targetRecord = oriRecord.clone();
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
        UserUtil.permissionCheck("view_case");
        String fileName = getText("entity.caseInstance.label") + ".csv";
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        File file = new File(fileName);
        ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
                CsvPreference.EXCEL_PREFERENCE);
        try {
            final String[] header = new String[] { getText("entity.id.label"),
                    getText("entity.priority_id.label"),
                    getText("entity.priority_name.label"),
                    getText("entity.status_id.label"),
                    getText("entity.status_name.label"),
                    getText("case.type_id.label"),
                    getText("case.type_name.label"),
                    getText("case.origin_id.label"),
                    getText("case.origin_name.label"),
                    getText("case.reason_id.label"),
                    getText("case.reason_name.label"),
                    getText("entity.subject.label"),
                    getText("entity.notes.label"),
                    getText("case.resolution.label"),
                    getText("entity.assigned_to_id.label"),
                    getText("entity.assigned_to_name.label") };
            writer.writeHeader(header);
            if (!isTemplate) {
                String[] ids = seleteIDs.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String id = ids[i];
                    CaseInstance caseInstance = baseService.getEntityById(
                            CaseInstance.class, Integer.parseInt(id));
                    final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
                    data1.put(header[0], caseInstance.getId());
                    CasePriority casePriority = caseInstance.getPriority();
                    if (casePriority != null) {
                        data1.put(header[1], casePriority.getId());
                    } else {
                        data1.put(header[1], "");
                    }
                    data1.put(header[2],
                            CommonUtil.getOptionLabel(casePriority));
                    CaseStatus caseStatus = caseInstance.getStatus();
                    if (caseStatus != null) {
                        data1.put(header[3], caseStatus.getId());
                    } else {
                        data1.put(header[3], "");
                    }
                    data1.put(header[4], CommonUtil.getOptionLabel(caseStatus));
                    CaseType caseType = caseInstance.getType();
                    if (caseType != null) {
                        data1.put(header[5], caseType.getId());
                    } else {
                        data1.put(header[5], "");
                    }
                    data1.put(header[6], CommonUtil.getOptionLabel(caseType));
                    CaseOrigin caseOrigin = caseInstance.getOrigin();
                    if (caseOrigin != null) {
                        data1.put(header[7], caseOrigin.getId());
                    } else {
                        data1.put(header[7], "");
                    }
                    data1.put(header[8], CommonUtil.getOptionLabel(caseOrigin));
                    CaseReason caseReason = caseInstance.getReason();
                    if (caseReason != null) {
                        data1.put(header[9], caseReason.getId());
                    } else {
                        data1.put(header[9], "");
                    }
                    data1.put(header[10], CommonUtil.getOptionLabel(caseReason));
                    data1.put(header[11], CommonUtil
                            .fromNullToEmpty(caseInstance.getSubject()));
                    data1.put(header[12],
                            CommonUtil.fromNullToEmpty(caseInstance.getNotes()));
                    data1.put(header[13], CommonUtil
                            .fromNullToEmpty(caseInstance.getResolution()));
                    if (caseInstance.getAssigned_to() != null) {
                        data1.put(header[14], caseInstance.getAssigned_to()
                                .getId());
                        data1.put(header[15], caseInstance.getAssigned_to()
                                .getName());
                    } else {
                        data1.put(header[14], "");
                        data1.put(header[15], "");
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
                CaseInstance caseInstance = new CaseInstance();
                try {
                    String id = row.get(getText("entity.id.label"));
                    if (!CommonUtil.isNullOrEmpty(id)) {
                        caseInstance.setId(Integer.parseInt(id));
                        UserUtil.permissionCheck("update_case");
                    } else {
                        UserUtil.permissionCheck("create_case");
                    }
                    String priorityID = row
                            .get(getText("entity.priority_id.label"));
                    if (CommonUtil.isNullOrEmpty(priorityID)) {
                        caseInstance.setPriority(null);
                    } else {
                        CasePriority priority = casePriorityService
                                .getEntityById(CasePriority.class,
                                        Integer.parseInt(priorityID));
                        caseInstance.setPriority(priority);
                    }
                    String statusID = row
                            .get(getText("entity.status_id.label"));
                    if (CommonUtil.isNullOrEmpty(statusID)) {
                        caseInstance.setStatus(null);
                    } else {
                        CaseStatus status = caseStatusService.getEntityById(
                                CaseStatus.class, Integer.parseInt(statusID));
                        caseInstance.setStatus(status);
                    }
                    String typeID = row.get(getText("case.type_id.label"));
                    if (CommonUtil.isNullOrEmpty(typeID)) {
                        caseInstance.setType(null);
                    } else {
                        CaseType type = caseTypeService.getEntityById(
                                CaseType.class, Integer.parseInt(typeID));
                        caseInstance.setType(type);
                    }
                    String originID = row.get(getText("case.origin_id.label"));
                    if (CommonUtil.isNullOrEmpty(originID)) {
                        caseInstance.setOrigin(null);
                    } else {
                        CaseOrigin origin = caseOriginService.getEntityById(
                                CaseOrigin.class, Integer.parseInt(originID));
                        caseInstance.setOrigin(origin);
                    }
                    String reasonID = row.get(getText("case.reason_id.label"));
                    if (CommonUtil.isNullOrEmpty(reasonID)) {
                        caseInstance.setReason(null);
                    } else {
                        CaseReason reason = caseReasonService.getEntityById(
                                CaseReason.class, Integer.parseInt(reasonID));
                        caseInstance.setReason(reason);
                    }
                    caseInstance.setSubject(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.subject.label"))));
                    caseInstance.setNotes(CommonUtil.fromNullToEmpty(row
                            .get(getText("entity.notes.label"))));
                    caseInstance.setResolution(CommonUtil.fromNullToEmpty(row
                            .get(getText("case.resolution.label"))));
                    String assignedToID = row
                            .get(getText("entity.assigned_to_id.label"));
                    if (CommonUtil.isNullOrEmpty(assignedToID)) {
                        caseInstance.setAssigned_to(null);
                    } else {
                        User assignedTo = userService.getEntityById(User.class,
                                Integer.parseInt(assignedToID));
                        caseInstance.setAssigned_to(assignedTo);
                    }
                    baseService.makePersistent(caseInstance);
                    successfulNum++;
                } catch (Exception e) {
                    failedNum++;
                    failedMsg.put(String.valueOf(caseInstance.getSubject()),
                            e.getMessage());
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
    public String execute() throws Exception {
        return SUCCESS;
    }

    @Override
    protected String getEntityName() {
        return CaseInstance.class.getSimpleName();
    }

    public IBaseService<CaseInstance> getbaseService() {
        return baseService;
    }

    public void setbaseService(IBaseService<CaseInstance> baseService) {
        this.baseService = baseService;
    }

    public CaseInstance getCase() {
        return caseInstance;
    }

    public void setCase(CaseInstance caseInstance) {
        this.caseInstance = caseInstance;
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
     * @return the caseStatusService
     */
    public IBaseService<CaseStatus> getCaseStatusService() {
        return caseStatusService;
    }

    /**
     * @param caseStatusService
     *            the caseStatusService to set
     */
    public void setCaseStatusService(IBaseService<CaseStatus> caseStatusService) {
        this.caseStatusService = caseStatusService;
    }

    /**
     * @return the casePriorityService
     */
    public IBaseService<CasePriority> getCasePriorityService() {
        return casePriorityService;
    }

    /**
     * @param casePriorityService
     *            the casePriorityService to set
     */
    public void setCasePriorityService(
            IBaseService<CasePriority> casePriorityService) {
        this.casePriorityService = casePriorityService;
    }

    /**
     * @return the caseTypeService
     */
    public IBaseService<CaseType> getCaseTypeService() {
        return caseTypeService;
    }

    /**
     * @param caseTypeService
     *            the caseTypeService to set
     */
    public void setCaseTypeService(IBaseService<CaseType> caseTypeService) {
        this.caseTypeService = caseTypeService;
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
     * @return the caseInstance
     */
    public CaseInstance getCaseInstance() {
        return caseInstance;
    }

    /**
     * @param caseInstance
     *            the caseInstance to set
     */
    public void setCaseInstance(CaseInstance caseInstance) {
        this.caseInstance = caseInstance;
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
     * @return the caseOriginService
     */
    public IBaseService<CaseOrigin> getCaseOriginService() {
        return caseOriginService;
    }

    /**
     * @param caseOriginService
     *            the caseOriginService to set
     */
    public void setCaseOriginService(IBaseService<CaseOrigin> caseOriginService) {
        this.caseOriginService = caseOriginService;
    }

    /**
     * @return the caseReasonService
     */
    public IBaseService<CaseReason> getCaseReasonService() {
        return caseReasonService;
    }

    /**
     * @param caseReasonService
     *            the caseReasonService to set
     */
    public void setCaseReasonService(IBaseService<CaseReason> caseReasonService) {
        this.caseReasonService = caseReasonService;
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

}
