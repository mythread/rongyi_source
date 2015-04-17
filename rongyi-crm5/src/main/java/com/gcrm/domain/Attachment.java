package com.gcrm.domain;

import java.io.Serializable;

public class Attachment extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8250950813769457555L;

    private String name;
    private byte[] content;

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
     * @return the content
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

}
