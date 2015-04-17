package com.gcrm.vo;

public class UserVO {
    private Integer id;
    private String name;
    
    public UserVO(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    
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
    
}
