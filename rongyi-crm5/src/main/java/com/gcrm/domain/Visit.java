package com.gcrm.domain;

/**
 * @author ShaoYanbin
 * */
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Visit extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -1570286768466081807L;
	private String subject;
    private VisitStatus status;
    private Date start_date;
    private Date end_date;
    private String related_object;
    private Integer related_record;
    private String location;
    private boolean reminder_email;
    private ReminderOption reminder_option_email;
    private EmailTemplate reminder_template;
    private String notes;
    private User assigned_to;
    private Set<Lead> leads = new HashSet<Lead>(0);
    private Set<User> users = new HashSet<User>(0);
    private Set<Contact> contacts = new HashSet<Contact>(0);

    @Override
    public Visit clone() {
        Visit o = null;
        try {
            o = (Visit) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
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
     * @return the status
     */
    public VisitStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    /**
     * @return the start_date
     */
    public Date getStart_date() {
        return start_date;
    }

    /**
     * @param start_date
     *            the start_date to set
     */
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    /**
     * @return the end_date
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * @param end_date
     *            the end_date to set
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * @return the related_object
     */
    public String getRelated_object() {
        return related_object;
    }

    /**
     * @param related_object
     *            the related_object to set
     */
    public void setRelated_object(String related_object) {
        this.related_object = related_object;
    }

    /**
     * @return the related_record
     */
    public Integer getRelated_record() {
        return related_record;
    }

    /**
     * @param related_record
     *            the related_record to set
     */
    public void setRelated_record(Integer related_record) {
        this.related_record = related_record;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
        this.location = location;
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
     * @return the reminder_email
     */
    public boolean isReminder_email() {
        return reminder_email;
    }

    /**
     * @param reminder_email
     *            the reminder_email to set
     */
    public void setReminder_email(boolean reminder_email) {
        this.reminder_email = reminder_email;
    }

    /**
     * @return the reminder_option_email
     */
    public ReminderOption getReminder_option_email() {
        return reminder_option_email;
    }

    /**
     * @param reminder_option_email
     *            the reminder_option_email to set
     */
    public void setReminder_option_email(ReminderOption reminder_option_email) {
        this.reminder_option_email = reminder_option_email;
    }

    /**
     * @return the leads
     */
    public Set<Lead> getLeads() {
        return leads;
    }

    /**
     * @param leads
     *            the leads to set
     */
    public void setLeads(Set<Lead> leads) {
        this.leads = leads;
    }

    /**
     * @return the users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param users
     *            the users to set
     */
    public void setUsers(Set<User> users) {
        this.users = users;
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

    /**
     * @return the reminder_template
     */
    public EmailTemplate getReminder_template() {
        return reminder_template;
    }

    /**
     * @param reminder_template
     *            the reminder_template to set
     */
    public void setReminder_template(EmailTemplate reminder_template) {
        this.reminder_template = reminder_template;
    }

}
