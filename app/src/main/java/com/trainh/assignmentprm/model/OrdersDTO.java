package com.trainh.assignmentprm.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class OrdersDTO implements Serializable {
    private Integer id;
    private Integer customerId;
    private String orderDate;
    private String shippingAddress;
    private BigDecimal totalPrice;
    private String status;

    public OrdersDTO(Integer id, Integer customerId, String orderDate, String shippingAddress, BigDecimal totalPrice, String status) {
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
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

}
