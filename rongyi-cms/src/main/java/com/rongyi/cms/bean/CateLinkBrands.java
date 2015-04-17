package com.rongyi.cms.bean;

public class CateLinkBrands {
    private Integer id;

    private String categoriesId;

    private Integer brandsId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(String categoriesId) {
        this.categoriesId = categoriesId == null ? null : categoriesId.trim();
    }

    public Integer getBrandsId() {
        return brandsId;
    }

    public void setBrandsId(Integer brandsId) {
        this.brandsId = brandsId;
    }
}