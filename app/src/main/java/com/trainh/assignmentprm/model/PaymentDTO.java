package com.trainh.assignmentprm.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDTO {
    private Integer id;
    private Integer orderId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String paymentMethod;

    public PaymentDTO() {
    }

    public PaymentDTO(Integer id, Integer orderId, LocalDate paymentDate, BigDecimal amount, String paymentMethod) {
        this.id = id;
        this.orderId = orderId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
