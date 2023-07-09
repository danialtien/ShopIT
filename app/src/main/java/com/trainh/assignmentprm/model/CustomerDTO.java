package com.trainh.assignmentprm.model;


import java.time.LocalDateTime;

public class CustomerDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private String avatar;
    private String address;
    private LocalDateTime createdAt;
    private boolean status;

    public CustomerDTO() {
    }

    public CustomerDTO(String fullName, String email, String phone, String password, String avatar, String address, LocalDateTime createdAt, boolean status) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.createdAt = createdAt;
        this.status = status;
    }

    public CustomerDTO(Integer id, String fullName, String email, String phone, String password, String avatar, String address, LocalDateTime createdAt, boolean status) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
