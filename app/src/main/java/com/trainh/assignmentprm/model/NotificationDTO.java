package com.trainh.assignmentprm.model;


import java.time.LocalDateTime;

public class NotificationDTO {
    private Integer id;
    private Integer customerId;
    private String title;
    private String description;
    private String createdAt;

    public NotificationDTO() {
    }

    public NotificationDTO(Integer id, Integer customerId, String title, String description, String createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
