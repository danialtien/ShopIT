package com.trainh.assignmentprm.model;


import java.io.Serializable;
import java.math.BigDecimal;

public class ProductDTO implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String productName;
    private Byte status;
    private Integer unitInStock;
    private BigDecimal unitPrice;
    private String urlImage;
    private String description;

    public ProductDTO(Integer id, Integer categoryId, String productName, Byte status, Integer unitInStock, BigDecimal unitPrice, String urlImage, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.status = status;
        this.unitInStock = unitInStock;
        this.unitPrice = unitPrice;
        this.urlImage = urlImage;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(Integer unitInStock) {
        this.unitInStock = unitInStock;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
