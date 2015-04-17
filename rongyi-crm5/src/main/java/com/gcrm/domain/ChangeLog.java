package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;

public class ChangeLog implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;
    private Integer id;
    private String entityName;
    private Integer recordID;
    private String columnName;
    private String oldValue;
    private String newValue;
    private User updated_by;
    private Date updated_on;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the entityName
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * @param entityName
     *            the entityName to set
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return the recordID
     */
    public Integer getRecordID() {
        return recordID;
    }

    /**
     * @param recordID
     *            the recordID to set
     */
    public void setRecordID(Integer recordID) {
        this.recordID = recordID;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName
     *            the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the oldValue
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * @param oldValue
     *            the oldValue to set
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * @return the newValue
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * @param newValue
     *            the newValue to set
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * @return the updated_by
     */
    public User getUpdated_by() {
        return updated_by;
    }

    /**
     * @param updated_by
     *            the updated_by to set
     */
    public void setUpdated_by(User updated_by) {
        this.updated_by = updated_by;
    }

    /**
     * @return the updated_on
     */
    public Date getUpdated_on() {
        return updated_on;
    }

    /**
     * @param updated_on
     *            the updated_on to set
     */
    public void setUpdated_on(Date updated_on) {
        this.updated_on = updated_on;
    }

}
