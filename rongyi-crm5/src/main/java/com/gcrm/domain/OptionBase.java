package com.gcrm.domain;

import java.io.Serializable;

public class OptionBase implements Serializable {

    private static final long serialVersionUID = 8250950813769457556L;

    private Integer id;
    private String value;
    private String label_en_US;
    private String label_zh_CN;
    private String label;
    private Integer sequence;

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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the sequence
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * @param sequence
     *            the sequence to set
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the label_zh_CN
     */
    public String getLabel_zh_CN() {
        return label_zh_CN;
    }

    /**
     * @param label_zh_CN
     *            the label_zh_CN to set
     */
    public void setLabel_zh_CN(String label_zh_CN) {
        this.label_zh_CN = label_zh_CN;
    }

    /**
     * @return the label_en_US
     */
    public String getLabel_en_US() {
        return label_en_US;
    }

    /**
     * @param label_en_US
     *            the label_en_US to set
     */
    public void setLabel_en_US(String label_en_US) {
        this.label_en_US = label_en_US;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
