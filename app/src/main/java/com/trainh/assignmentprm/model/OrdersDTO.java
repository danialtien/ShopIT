package com.trainh.assignmentprm.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OrdersDTO implements Serializable {
    private Integer id;
    private Integer customerId;
    private LocalDate orderDate;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private String status;

    public OrdersDTO() {
    }

    public OrdersDTO(Integer id, Integer customerId, LocalDate orderDate, String shippingAddress, BigDecimal totalPrice, String status) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.status = status;
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrdersDTO(Integer customerId, LocalDate orderDate, String shippingAddress, BigDecimal totalPrice, String status) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
