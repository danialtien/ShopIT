package com.trainh.assignmentprm.model;

public class ShopDTO {
    private Integer id;
    private String shopName;
    private String address;
    private String phone;
    private Byte status;

    public ShopDTO() {
    }

    public ShopDTO(Integer id, String shopName, String address, String phone, Byte status) {
        this.id = id;
        this.shopName = shopName;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
