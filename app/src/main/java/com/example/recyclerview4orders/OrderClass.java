package com.example.recyclerview4orders;

public class OrderClass {

    String refId,orderId,date,orderCost,remaining,paid;
    Boolean isPending;

    public OrderClass() {
    }

    public OrderClass(String refId, String orderId, String date, String orderCost, String remaining, String paid, Boolean isPending) {
        this.refId = refId;
        this.orderId = orderId;
        this.date = date;
        this.orderCost = orderCost;
        this.remaining = remaining;
        this.paid = paid;
        this.isPending = isPending;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public Boolean getPending() {
        return isPending;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }
}
