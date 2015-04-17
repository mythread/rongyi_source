package com.gcrm.domain;

import java.io.Serializable;

/**
 * 类AccountPojo.java的实现描述：TODO 类实现描述
 * 
 * @author jiejie 2014年4月19日 上午8:56:25
 */
public class SimpleAccount implements Serializable {

    private static final long serialVersionUID = -2525360278247521335L;

    private Integer           id;
    private String            name;
    private User              assigned_to;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(User assigned_to) {
        this.assigned_to = assigned_to;
    }

}
