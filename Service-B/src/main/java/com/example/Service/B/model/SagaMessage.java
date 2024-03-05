package com.example.Service.B.model;

public class SagaMessage {
    private Long orderId;
    private String status;


    public SagaMessage() {
    }

    public SagaMessage(String status) {
        this.status = status;
    }

    public SagaMessage(String status, Long orderId) {
        this.status = status;
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
