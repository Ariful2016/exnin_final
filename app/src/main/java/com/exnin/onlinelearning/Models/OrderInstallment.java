package com.exnin.onlinelearning.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderInstallment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("installment")
    @Expose
    private String installment;
    @SerializedName("bdt")
    @Expose
    private String bdt;
    @SerializedName("paydate")
    @Expose
    private String paydate;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("send_notification")
    @Expose
    private Object sendNotification;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getBdt() {
        return bdt;
    }

    public void setBdt(String bdt) {
        this.bdt = bdt;
    }

    public String getPaydate() {
        return paydate;
    }

    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(Object sendNotification) {
        this.sendNotification = sendNotification;
    }

}