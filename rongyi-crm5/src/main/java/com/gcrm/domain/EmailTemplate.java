package com.gcrm.domain;

import java.io.Serializable;

public class EmailTemplate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private String name;
    private String type;
    private String description;
    private String notes;
    private String subject;
    boolean text_only;
    private String html_body;
    private String text_body;
    private User assigned_to;

    public EmailTemplate() {
        super();
    }

    public EmailTemplate(Integer id, String name) {
        super();
        this.setId(id);
        this.name = name;
    }

    @Override
    public EmailTemplate clone() {
        EmailTemplate o = null;
        try {
            o = (EmailTemplate) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return the text_only
     */
    public boolean isText_only() {
        return text_only;
    }

    /**
     * @param text_only
     *            the text_only to set
     */
    public void setText_only(boolean text_only) {
        this.text_only = text_only;
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
     * @return the html_body
     */
    public String getHtml_body() {
        return html_body;
    }

    /**
     * @param html_body
     *            the html_body to set
     */
    public void setHtml_body(String html_body) {
        this.html_body = html_body;
    }

    /**
     * @return the text_body
     */
    public String getText_body() {
        return text_body;
    }

    /**
     * @param text_body
     *            the text_body to set
     */
    public void setText_body(String text_body) {
        this.text_body = text_body;
    }

}
