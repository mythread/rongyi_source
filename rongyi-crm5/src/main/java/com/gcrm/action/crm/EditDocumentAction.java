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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.gcrm.domain.Attachment;
import com.gcrm.domain.CaseInstance;
import com.gcrm.domain.ChangeLog;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Document;
import com.gcrm.domain.DocumentCategory;
import com.gcrm.domain.DocumentStatus;
import com.gcrm.domain.DocumentSubCategory;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.service.IOptionService;
import com.gcrm.util.BeanUtil;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.util.security.UserUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Document
 * 
 */
public class EditDocumentAction extends BaseEditAction implements Preparable {

    private static final long serialVersionUID = -2404576552417042445L;

    private IBaseService<Document> baseService;
    private IOptionService<DocumentStatus> documentStatusService;
    private IOptionService<DocumentCategory> documentCategoryService;
    private IOptionService<DocumentSubCategory> documentSubCategoryService;
    private IBaseService<Account> accountService;
    private IBaseService<Contact> contactService;
    private IBaseService<Opportunity> opportunityService;
    private IBaseService<CaseInstance> caseService;
    private IBaseService<User> userService;
    private IBaseService<ChangeLog> changeLogService;
    private TaskExecutor taskExecutor;
    private Document document;
    private List<DocumentStatus> statuses;
    private List<DocumentCategory> categories;
    private List<DocumentSubCategory> subCategories;
    private Integer statusID = null;
    private Integer categoryID = null;
    private Integer subCategoryID = null;
    private Integer relatedDocumentID = null;
    private String relatedDocumentText = null;
    private String publishDateS = null;
    private String expirationDateS = null;
    private File upload;
    private String uploadFileName;
    private String uploadContentType;
    private String fileName;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */
    public String save() throws Exception {
        Document originalDocument = saveEntity();
        final Collection<ChangeLog> changeLogs = changeLog(originalDocument,
                document);
        document = getBaseService().makePersistent(document);
        this.setId(document.getId());
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
     * Creates change log
     * 
     * @param originalDocument
     *            original document record
     * @param document
     *            current document record
     * @return change log collections
     */
    private Collection<ChangeLog> changeLog(Document originalDocument,
            Document document) {
        Collection<ChangeLog> changeLogs = null;
        if (originalDocument != null) {
            ActionContext context = ActionContext.getContext();
            Map<String, Object> session = context.getSession();
            String entityName = Document.class.getSimpleName();
            Integer recordID = document.getId();
            User loginUser = (User) session
                    .get(AuthenticationSuccessListener.LOGIN_USER);
            changeLogs = new ArrayList<ChangeLog>();

            String oldName = CommonUtil.fromNullToEmpty(originalDocument
                    .getName());
            String newName = CommonUtil.fromNullToEmpty(document.getName());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.name.label", oldName, newName, loginUser);

            String oldFileName = CommonUtil.fromNullToEmpty(originalDocument
                    .getFileName());
            String newFileName = CommonUtil.fromNullToEmpty(document
                    .getFileName());

            createChangeLog(changeLogs, entityName, recordID,
                    "document.file.label", oldFileName, newFileName, loginUser);

            String oldRevision = String.valueOf(originalDocument.getRevision());
            String newRevision = String.valueOf(document.getRevision());
            createChangeLog(changeLogs, entityName, recordID,
                    "document.revision.label", oldRevision, newRevision,
                    loginUser);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_EDIT_FORMAT);
            String oldPublishDateValue = "";
            Date oldPublishDate = originalDocument.getPublish_date();
            if (oldPublishDate != null) {
                oldPublishDateValue = dateFormat.format(oldPublishDate);
            }
            String newPublishDateValue = "";
            Date newPublishDate = document.getPublish_date();
            if (newPublishDate != null) {
                newPublishDateValue = dateFormat.format(newPublishDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "document.publish_date.label", oldPublishDateValue,
                    newPublishDateValue, loginUser);

            String oldExpirationDateValue = "";
            Date oldExpirationDate = originalDocument.getExpiration_date();
            if (oldExpirationDate != null) {
                oldExpirationDateValue = dateFormat.format(oldExpirationDate);
            }
            String newExpirationDateValue = "";
            Date newExpirationDate = document.getExpiration_date();
            if (newExpirationDate != null) {
                newExpirationDateValue = dateFormat.format(newExpirationDate);
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "document.expiration_date.label", oldExpirationDateValue,
                    newExpirationDateValue, loginUser);

            String oldCategory = getOptionValue(originalDocument.getCategory());
            String newCategory = getOptionValue(document.getCategory());
            createChangeLog(changeLogs, entityName, recordID,
                    "document.category.label", oldCategory, newCategory,
                    loginUser);

            String oldSubCategory = getOptionValue(originalDocument
                    .getSub_category());
            String newSubCategory = getOptionValue(document.getSub_category());
            createChangeLog(changeLogs, entityName, recordID,
                    "document.sub_category.label", oldSubCategory,
                    newSubCategory, loginUser);

            String oldRelatedDocumentName = "";
            Document oldRelatedDocument = originalDocument
                    .getRelated_document();
            if (oldRelatedDocument != null) {
                oldRelatedDocumentName = CommonUtil
                        .fromNullToEmpty(oldRelatedDocument.getName());
            }
            String newRelatedDocumentName = "";
            Document newRelatedDocument = document.getRelated_document();
            if (newRelatedDocument != null) {
                newRelatedDocumentName = CommonUtil
                        .fromNullToEmpty(newRelatedDocument.getName());
            }
            createChangeLog(changeLogs, entityName, recordID,
                    "document.related_document.label", oldRelatedDocumentName,
                    newRelatedDocumentName, loginUser);

            String oldNotes = CommonUtil.fromNullToEmpty(originalDocument
                    .getNotes());
            String newNotes = CommonUtil.fromNullToEmpty(document.getNotes());
            createChangeLog(changeLogs, entityName, recordID,
                    "entity.notes.label", oldNotes, newNotes, loginUser);

            String oldAssignedToName = "";
            User oldAssignedTo = originalDocument.getAssigned_to();
            if (oldAssignedTo != null) {
                oldAssignedToName = oldAssignedTo.getName();
            }
            String newAssignedToName = "";
            User newAssignedTo = document.getAssigned_to();
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
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
    public String get() throws Exception {
        if (this.getId() != null) {
            document = baseService.getEntityById(Document.class, this.getId());
            DocumentStatus status = document.getStatus();
            if (status != null) {
                statusID = status.getId();
            }
            DocumentCategory category = document.getCategory();
            if (category != null) {
                categoryID = category.getId();
            }

            DocumentSubCategory subCategory = document.getSub_category();
            if (subCategory != null) {
                subCategoryID = subCategory.getId();
            }

            User assignedTo = document.getAssigned_to();
            if (assignedTo != null) {
                this.setAssignedToID(assignedTo.getId());
                this.setAssignedToText(assignedTo.getName());
            }
            Document relatedDocument = document.getRelated_document();
            if (relatedDocument != null) {
                relatedDocumentID = relatedDocument.getId();
                relatedDocumentText = relatedDocument.getName();
            }
            Date publishDate = document.getPublish_date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    Constant.DATE_EDIT_FORMAT);
            if (publishDate != null) {
                publishDateS = dateFormat.format(publishDate);
            }
            Date expirationDate = document.getExpiration_date();
            if (expirationDate != null) {
                expirationDateS = dateFormat.format(expirationDate);
            }
            this.getBaseInfo(document, Document.class.getSimpleName(),
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
            Collection<Document> documents = new ArrayList<Document>();
            User loginUser = this.getLoginUser();
            User user = userService
                    .getEntityById(User.class, loginUser.getId());
            Collection<ChangeLog> allChangeLogs = new ArrayList<ChangeLog>();
            for (String IDString : selectIDArray) {
                int id = Integer.parseInt(IDString);
                Document documentInstance = this.baseService.getEntityById(
                        Document.class, id);
                Document originalDocument = documentInstance.clone();
                for (String fieldName : fieldNames) {
                    Object value = BeanUtil.getFieldValue(document, fieldName);
                    BeanUtil.setFieldValue(documentInstance, fieldName, value);
                }
                documentInstance.setUpdated_by(user);
                documentInstance.setUpdated_on(new Date());
                Collection<ChangeLog> changeLogs = changeLog(originalDocument,
                        documentInstance);
                allChangeLogs.addAll(changeLogs);
                documents.add(documentInstance);
            }
            final Collection<ChangeLog> changeLogsForSave = allChangeLogs;
            if (documents.size() > 0) {
                this.baseService.batchUpdate(documents);
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
     * @return original document record
     * @throws ParseException
     */
    private Document saveEntity() throws Exception {
        Document originalDocument = null;
        if (document.getId() == null) {
            UserUtil.permissionCheck("create_document");
        } else {
            UserUtil.permissionCheck("update_document");
            originalDocument = baseService.getEntityById(Document.class,
                    document.getId());
            document.setAttachment(originalDocument.getAttachment());
            document.setContacts(originalDocument.getContacts());
            document.setCases(originalDocument.getCases());
            document.setAccounts(originalDocument.getAccounts());
            document.setOpportunities(originalDocument.getOpportunities());
            document.setCreated_on(originalDocument.getCreated_on());
            document.setCreated_by(originalDocument.getCreated_by());
        }

        DocumentStatus status = null;
        if (statusID != null) {
            status = documentStatusService.getEntityById(DocumentStatus.class,
                    statusID);
        }
        document.setStatus(status);

        DocumentCategory category = null;
        if (categoryID != null) {
            category = documentCategoryService.getEntityById(
                    DocumentCategory.class, categoryID);
        }
        document.setCategory(category);

        DocumentSubCategory subCategory = null;
        if (subCategoryID != null) {
            subCategory = documentSubCategoryService.getEntityById(
                    DocumentSubCategory.class, subCategoryID);
        }
        document.setSub_category(subCategory);
        User assignedTo = null;
        if (this.getAssignedToID() != null) {
            assignedTo = userService.getEntityById(User.class,
                    this.getAssignedToID());
        }
        document.setAssigned_to(assignedTo);
        User owner = null;
        if (this.getOwnerID() != null) {
            owner = userService.getEntityById(User.class, this.getOwnerID());
        }
        document.setOwner(owner);
        Document relatedDocument = null;
        if (relatedDocumentID != null) {
            relatedDocument = baseService.getEntityById(Document.class,
                    relatedDocumentID);
        }
        document.setRelated_document(relatedDocument);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                Constant.DATE_EDIT_FORMAT);
        Date publishDate = null;
        if (!CommonUtil.isNullOrEmpty(publishDateS)) {
            publishDate = dateFormat.parse(publishDateS);
        }
        document.setPublish_date(publishDate);
        Date expirationDate = null;
        if (!CommonUtil.isNullOrEmpty(expirationDateS)) {
            expirationDate = dateFormat.parse(expirationDateS);
        }
        document.setExpiration_date(expirationDate);

        File file = this.getUpload();
        if (file != null) {
            InputStream stream = null;
            byte[] input = null;
            try {
                stream = new BufferedInputStream(new FileInputStream(file));
                input = new byte[stream.available()];
                stream.read(input);
            } finally {
                stream.close();
            }
            Attachment attachment = null;
            attachment = document.getAttachment();
            if (attachment == null) {
                attachment = new Attachment();
                document.setAttachment(attachment);
            }
            attachment.setName(this.uploadFileName);
            document.setFileName(this.uploadFileName);
            attachment.setContent(input);
        }

        if ("Account".equals(this.getRelationKey())) {
            Account account = accountService.getEntityById(Account.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Account> accounts = document.getAccounts();
            if (accounts == null) {
                accounts = new HashSet<Account>();
            }
            accounts.add(account);
        } else if ("Contact".equals(this.getRelationKey())) {
            Contact contact = contactService.getEntityById(Contact.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<Contact> contacts = document.getContacts();
            if (contacts == null) {
                contacts = new HashSet<Contact>();
            }
            contacts.add(contact);
        } else if ("Opportunity".equals(this.getRelationKey())) {
            Opportunity opportunity = opportunityService
                    .getEntityById(Opportunity.class,
                            Integer.valueOf(this.getRelationValue()));
            Set<Opportunity> opportunities = document.getOpportunities();
            if (opportunities == null) {
                opportunities = new HashSet<Opportunity>();
            }
            opportunities.add(opportunity);
        } else if ("CaseInstance".equals(this.getRelationKey())) {
            CaseInstance caseInstance = caseService.getEntityById(
                    CaseInstance.class,
                    Integer.valueOf(this.getRelationValue()));
            Set<CaseInstance> cases = document.getCases();
            if (cases == null) {
                cases = new HashSet<CaseInstance>();
            }
            cases.add(caseInstance);
        }
        super.updateBaseInfo(document);
        return originalDocument;
    }

    /**
     * Gets Document Relation Counts
     * 
     * @return null
     */
    public String getDocumentRelationCounts() throws Exception {
        long accountNumber = this.baseService
                .countsByParams(
                        "select count(*) from Document document join document.accounts where document.id = ?",
                        new Integer[] { this.getId() });
        long contactNumber = this.baseService
                .countsByParams(
                        "select count(*) from Document document join document.contacts where document.id = ?",
                        new Integer[] { this.getId() });
        long opportunitiyNumber = this.baseService
                .countsByParams(
                        "select count(*) from Document document join document.opportunities where document.id = ?",
                        new Integer[] { this.getId() });
        long caseNumber = this.baseService
                .countsByParams(
                        "select count(*) from Document document join document.cases where document.id = ?",
                        new Integer[] { this.getId() });

        StringBuilder jsonBuilder = new StringBuilder("");
        jsonBuilder.append("{\"accountNumber\":\"").append(accountNumber)
                .append("\",\"contactNumber\":\"").append(contactNumber)
                .append("\",\"opportunitiyNumber\":\"")
                .append(opportunitiyNumber).append("\",\"caseNumber\":\"")
                .append(caseNumber).append("\"}");
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
        this.statuses = documentStatusService.getOptions(
                DocumentStatus.class.getSimpleName(), local);
        this.categories = documentCategoryService.getOptions(
                DocumentCategory.class.getSimpleName(), local);
        this.subCategories = documentSubCategoryService.getOptions(
                DocumentSubCategory.class.getSimpleName(), local);
    }

    /**
     * Imports the document
     * 
     * @return document inputStream
     * 
     */
    public InputStream getInputStream() throws Exception {
        byte[] fileBytes = null;
        if (this.getId() != null) {
            Document document = baseService.getEntityById(Document.class,
                    this.getId());
            Attachment attachment = document.getAttachment();
            if (attachment != null) {
                fileBytes = attachment.getContent();
            }
        }
        InputStream in = new ByteArrayInputStream(fileBytes);
        return in;
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
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document
     *            the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * @return the statuses
     */
    public List<DocumentStatus> getStatuses() {
        return statuses;
    }

    /**
     * @param statuses
     *            the statuses to set
     */
    public void setStatuses(List<DocumentStatus> statuses) {
        this.statuses = statuses;
    }

    /**
     * @return the categories
     */
    public List<DocumentCategory> getCategories() {
        return categories;
    }

    /**
     * @param categories
     *            the categories to set
     */
    public void setCategories(List<DocumentCategory> categories) {
        this.categories = categories;
    }

    /**
     * @return the subCategories
     */
    public List<DocumentSubCategory> getSubCategories() {
        return subCategories;
    }

    /**
     * @param subCategories
     *            the subCategories to set
     */
    public void setSubCategories(List<DocumentSubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    /**
     * @return the statusID
     */
    public Integer getStatusID() {
        return statusID;
    }

    /**
     * @param statusID
     *            the statusID to set
     */
    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    /**
     * @return the categoryID
     */
    public Integer getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID
     *            the categoryID to set
     */
    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the subCategoryID
     */
    public Integer getSubCategoryID() {
        return subCategoryID;
    }

    /**
     * @param subCategoryID
     *            the subCategoryID to set
     */
    public void setSubCategoryID(Integer subCategoryID) {
        this.subCategoryID = subCategoryID;
    }

    /**
     * @return the relatedDocumentID
     */
    public Integer getRelatedDocumentID() {
        return relatedDocumentID;
    }

    /**
     * @param relatedDocumentID
     *            the relatedDocumentID to set
     */
    public void setRelatedDocumentID(Integer relatedDocumentID) {
        this.relatedDocumentID = relatedDocumentID;
    }

    /**
     * @return the publishDate
     */
    public String getPublishDateS() {
        return publishDateS;
    }

    /**
     * @param publishDate
     *            the publishDate to set
     */
    public void setPublishDate(String publishDateS) {
        this.publishDateS = publishDateS;
    }

    /**
     * @return the expirationDate
     */
    public String getExpirationDateS() {
        return expirationDateS;
    }

    /**
     * @param expirationDate
     *            the expirationDate to set
     */
    public void setExpirationDateS(String expirationDateS) {
        this.expirationDateS = expirationDateS;
    }

    /**
     * @return the upload
     */
    public File getUpload() {
        return upload;
    }

    /**
     * @param upload
     *            the upload to set
     */
    public void setUpload(File upload) {
        this.upload = upload;
    }

    /**
     * @return the uploadFileName
     */
    public String getUploadFileName() {
        return uploadFileName;
    }

    /**
     * @param uploadFileName
     *            the uploadFileName to set
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * @return the uploadContentType
     */
    public String getUploadContentType() {
        return uploadContentType;
    }

    /**
     * @param uploadContentType
     *            the uploadContentType to set
     */
    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
     * @return the relatedDocumentText
     */
    public String getRelatedDocumentText() {
        return relatedDocumentText;
    }

    /**
     * @return the documentStatusService
     */
    public IOptionService<DocumentStatus> getDocumentStatusService() {
        return documentStatusService;
    }

    /**
     * @param documentStatusService
     *            the documentStatusService to set
     */
    public void setDocumentStatusService(
            IOptionService<DocumentStatus> documentStatusService) {
        this.documentStatusService = documentStatusService;
    }

    /**
     * @return the documentCategoryService
     */
    public IOptionService<DocumentCategory> getDocumentCategoryService() {
        return documentCategoryService;
    }

    /**
     * @param documentCategoryService
     *            the documentCategoryService to set
     */
    public void setDocumentCategoryService(
            IOptionService<DocumentCategory> documentCategoryService) {
        this.documentCategoryService = documentCategoryService;
    }

    /**
     * @return the documentSubCategoryService
     */
    public IOptionService<DocumentSubCategory> getDocumentSubCategoryService() {
        return documentSubCategoryService;
    }

    /**
     * @param documentSubCategoryService
     *            the documentSubCategoryService to set
     */
    public void setDocumentSubCategoryService(
            IOptionService<DocumentSubCategory> documentSubCategoryService) {
        this.documentSubCategoryService = documentSubCategoryService;
    }

    public IBaseService<ChangeLog> getChangeLogService() {
        return changeLogService;
    }

    public void setChangeLogService(IBaseService<ChangeLog> changeLogService) {
        this.changeLogService = changeLogService;
    }

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @return the baseService
     */
    public IBaseService<Document> getBaseService() {
        return baseService;
    }

    /**
     * @param baseService
     *            the baseService to set
     */
    public void setBaseService(IBaseService<Document> baseService) {
        this.baseService = baseService;
    }
}
