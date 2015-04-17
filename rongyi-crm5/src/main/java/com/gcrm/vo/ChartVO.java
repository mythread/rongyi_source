package com.gcrm.vo;

import java.io.Serializable;

public class ChartVO implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    public static final String CHART_PIE = "pie";
    public static final String CHART_BAR = "bar";

    private String label;
    private int number;

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number
     *            the number to set
     */
    public void setNumber(int number) {
        this.number = number;
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
