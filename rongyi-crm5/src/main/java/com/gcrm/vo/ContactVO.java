package com.gcrm.vo;

import java.io.Serializable;

public class ContactVO implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private String first_name;
    private String last_name;
    private String title;
    private String email;
    private String primary_street;
    private String primary_city;
    private String primary_state;
    private String primary_postal_code;

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name
     *            the first_name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name
     *            the last_name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the primary_street
     */
    public String getPrimary_street() {
        return primary_street;
    }

    /**
     * @param primary_street
     *            the primary_street to set
     */
    public void setPrimary_street(String primary_street) {
        this.primary_street = primary_street;
    }

    /**
     * @return the primary_city
     */
    public String getPrimary_city() {
        return primary_city;
    }

    /**
     * @param primary_city
     *            the primary_city to set
     */
    public void setPrimary_city(String primary_city) {
        this.primary_city = primary_city;
    }

    /**
     * @return the primary_state
     */
    public String getPrimary_state() {
        return primary_state;
    }

    /**
     * @param primary_state
     *            the primary_state to set
     */
    public void setPrimary_state(String primary_state) {
        this.primary_state = primary_state;
    }

    /**
     * @return the primary_postal_code
     */
    public String getPrimary_postal_code() {
        return primary_postal_code;
    }

    /**
     * @param primary_postal_code
     *            the primary_postal_code to set
     */
    public void setPrimary_postal_code(String primary_postal_code) {
        this.primary_postal_code = primary_postal_code;
    }

}
