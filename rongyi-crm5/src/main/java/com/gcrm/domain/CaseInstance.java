package com.gcrm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CaseInstance extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private CasePriority priority;
    private CaseStatus status;
    private Account account;
    private CaseType type;
    private CaseOrigin origin;
    private CaseReason reason;
    private String subject;
    private String notes;
    private String resolution;
    private User assigned_to;
    private Set<Contact> contacts = new HashSet<Contact>(0);
    private Set<Document> documents = new HashSet<Document>(0);

    @Override
    public CaseInstance clone() {
        CaseInstance o = null;
        try {
            o = (CaseInstance) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * @return the priority
     */
    public CasePriority getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(CasePriority priority) {
        this.priority = priority;
    }

    /**
     * @return the status
     */
    public CaseStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * @return the type
     */
    public CaseType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(CaseType type) {
        this.type = type;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the resolution
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * @param resolution
     *            the resolution to set
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
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
     * @return the documents
     */
    public Set<Document> getDocuments() {
        return documents;
    }

    /**
     * @param documents
     *            the documents to set
     */
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    /**
     * @return the origin
     */
    public CaseOrigin getOrigin() {
        return origin;
    }

    /**
     * @param origin
     *            the origin to set
     */
    public void setOrigin(CaseOrigin origin) {
        this.origin = origin;
    }

    /**
     * @return the reason
     */
    public CaseReason getReason() {
        return reason;
    }

    /**
     * @param reason
     *            the reason to set
     */
    public void setReason(CaseReason reason) {
        this.reason = reason;
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

    @Override
    public String getName() {
        return this.subject;
    }

}
