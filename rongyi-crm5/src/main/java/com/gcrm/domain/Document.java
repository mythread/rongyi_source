package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Document extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private Attachment attachment;
    private DocumentStatus status;
    private String fileName;
    private String name;
    private int revision;
    private Date publish_date;
    private Date expiration_date;
    private DocumentCategory category;
    private DocumentSubCategory sub_category;
    private String notes;
    private Document related_document;
    private User assigned_to;
    private Set<Account> accounts = new HashSet<Account>(0);
    private Set<Contact> contacts = new HashSet<Contact>(0);
    private Set<Opportunity> opportunities = new HashSet<Opportunity>(0);
    private Set<CaseInstance> cases = new HashSet<CaseInstance>(0);

    @Override
    public Document clone() {
        Document o = null;
        try {
            o = (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * @return the status
     */
    public DocumentStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the revision
     */
    public int getRevision() {
        return revision;
    }

    /**
     * @param revision
     *            the revision to set
     */
    public void setRevision(int revision) {
        this.revision = revision;
    }

    /**
     * @return the publish_date
     */
    public Date getPublish_date() {
        return publish_date;
    }

    /**
     * @param publish_date
     *            the publish_date to set
     */
    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    /**
     * @return the expiration_date
     */
    public Date getExpiration_date() {
        return expiration_date;
    }

    /**
     * @param expiration_date
     *            the expiration_date to set
     */
    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }

    /**
     * @return the category
     */
    public DocumentCategory getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(DocumentCategory category) {
        this.category = category;
    }

    /**
     * @return the sub_category
     */
    public DocumentSubCategory getSub_category() {
        return sub_category;
    }

    /**
     * @param sub_category
     *            the sub_category to set
     */
    public void setSub_category(DocumentSubCategory sub_category) {
        this.sub_category = sub_category;
    }

    /**
     * @return the related_document
     */
    public Document getRelated_document() {
        return related_document;
    }

    /**
     * @param related_document
     *            the related_document to set
     */
    public void setRelated_document(Document related_document) {
        this.related_document = related_document;
    }

    /**
     * @return the assigned_to
     */
    public User getAssigned_to() {
        return assigned_to;
    }

    /**
     * @param assigned_to
     *            the assigned_to to set
     */
    public void setAssigned_to(User assigned_to) {
        this.assigned_to = assigned_to;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the accounts
     */
    public Set<Account> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts
     *            the accounts to set
     */
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the contacts
     */
    public Set<Contact> getContacts() {
        return contacts;
    }

    /**
     * @param contacts
     *            the contacts to set
     */
    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    /**
     * @return the opportunities
     */
    public Set<Opportunity> getOpportunities() {
        return opportunities;
    }

    /**
     * @param opportunities
     *            the opportunities to set
     */
    public void setOpportunities(Set<Opportunity> opportunities) {
        this.opportunities = opportunities;
    }

    /**
     * @return the cases
     */
    public Set<CaseInstance> getCases() {
        return cases;
    }

    /**
     * @param cases
     *            the cases to set
     */
    public void setCases(Set<CaseInstance> cases) {
        this.cases = cases;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the attachment
     */
    public Attachment getAttachment() {
        return attachment;
    }

    /**
     * @param attachment
     *            the attachment to set
     */
    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
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

}
