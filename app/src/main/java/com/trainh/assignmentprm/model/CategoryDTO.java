package com.trainh.assignmentprm.model;


import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private Integer id;
    private String categoryName;

    public CategoryDTO(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
