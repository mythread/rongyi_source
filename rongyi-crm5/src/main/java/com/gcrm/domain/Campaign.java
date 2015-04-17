package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Campaign extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457556L;

    private String name;
    private CampaignStatus status;
    private Date start_date;
    private Date end_date;
    private CampaignType type;
    private Currency currency;
    private double impressions;
    private double budget;
    private double expected_cost;
    private double actual_cost;
    private double expected_revenue;
    private double expected_respone;
    private String objective;
    private String notes;
    private User assigned_to;
    private Set<TargetList> targetLists = new HashSet<TargetList>(0);

    @Override
    public Campaign clone() {
        Campaign o = null;
        try {
            o = (Campaign) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * @hibernate.property length="50" not-null="true"
     */
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the status
     */
    public CampaignStatus getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(CampaignStatus status) {
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
     * @return the type
     */
    public CampaignType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(CampaignType type) {
        this.type = type;
    }

    /**
     * @return the currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * @param currency
     *            the currency to set
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * @return the impressions
     */
    public double getImpressions() {
        return impressions;
    }

    /**
     * @param impressions
     *            the impressions to set
     */
    public void setImpressions(double impressions) {
        this.impressions = impressions;
    }

    /**
     * @return the budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * @param budget
     *            the budget to set
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    /**
     * @return the expected_cost
     */
    public double getExpected_cost() {
        return expected_cost;
    }

    /**
     * @param expected_cost
     *            the expected_cost to set
     */
    public void setExpected_cost(double expected_cost) {
        this.expected_cost = expected_cost;
    }

    /**
     * @return the actual_cost
     */
    public double getActual_cost() {
        return actual_cost;
    }

    /**
     * @param actual_cost
     *            the actual_cost to set
     */
    public void setActual_cost(double actual_cost) {
        this.actual_cost = actual_cost;
    }

    /**
     * @return the expected_revenue
     */
    public double getExpected_revenue() {
        return expected_revenue;
    }

    /**
     * @param expected_revenue
     *            the expected_revenue to set
     */
    public void setExpected_revenue(double expected_revenue) {
        this.expected_revenue = expected_revenue;
    }

    /**
     * @return the objective
     */
    public String getObjective() {
        return objective;
    }

    /**
     * @param objective
     *            the objective to set
     */
    public void setObjective(String objective) {
        this.objective = objective;
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
     * @return the expected_respone
     */
    public double getExpected_respone() {
        return expected_respone;
    }

    /**
     * @param expected_respone
     *            the expected_respone to set
     */
    public void setExpected_respone(double expected_respone) {
        this.expected_respone = expected_respone;
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
     * @return the targetLists
     */
    public Set<TargetList> getTargetLists() {
        return targetLists;
    }

    /**
     * @param targetLists
     *            the targetLists to set
     */
    public void setTargetLists(Set<TargetList> targetLists) {
        this.targetLists = targetLists;
    }

}
