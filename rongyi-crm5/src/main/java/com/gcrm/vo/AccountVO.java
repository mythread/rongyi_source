package com.gcrm.vo;

import java.io.Serializable;

public class AccountVO implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private String name;
    private String office_phone;
    private String email;
    private String bill_street;
    private String bill_city;
    private String bill_state;
    private String bill_country;
    private String assigned_to;

    /**
     * @return the name
     */
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
     * @return the office_phone
     */
    public String getOffice_phone() {
        return office_phone;
    }

    /**
     * @param office_phone
     *            the office_phone to set
     */
    public void setOffice_phone(String office_phone) {
        this.office_phone = office_phone;
    }

    /**
     * @return the bill_street
     */
    public String getBill_street() {
        return bill_street;
    }

    /**
     * @param bill_street
     *            the bill_street to set
     */
    public void setBill_street(String bill_street) {
        this.bill_street = bill_street;
    }

    /**
     * @return the bill_city
     */
    public String getBill_city() {
        return bill_city;
    }

    /**
     * @param bill_city
     *            the bill_city to set
     */
    public void setBill_city(String bill_city) {
        this.bill_city = bill_city;
    }

    /**
     * @return the bill_state
     */
    public String getBill_state() {
        return bill_state;
    }

    /**
     * @param bill_state
     *            the bill_state to set
     */
    public void setBill_state(String bill_state) {
        this.bill_state = bill_state;
    }

    /**
     * @return the bill_country
     */
    public String getBill_country() {
        return bill_country;
    }

    /**
     * @param bill_country
     *            the bill_country to set
     */
    public void setBill_country(String bill_country) {
        this.bill_country = bill_country;
    }

    /**
     * @return the assigned_to
     */
    public String getAssigned_to() {
        return assigned_to;
    }

    /**
     * @param assigned_to
     *            the assigned_to to set
     */
    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
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

}
