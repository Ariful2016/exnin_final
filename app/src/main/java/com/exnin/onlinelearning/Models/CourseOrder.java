package com.exnin.onlinelearning.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CourseOrder {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("course_id")
    @Expose
    private String courseId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("selected_day")
    @Expose
    private String selectedDay;
    @SerializedName("selected_time")
    @Expose
    private String selectedTime;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_installments")
    @Expose
    private List<OrderInstallment> orderInstallments = null;
    @SerializedName("course")
    @Expose
    private CourseOrderInfo courseOrderInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
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

    public List<OrderInstallment> getOrderInstallments() {
        return orderInstallments;
    }

    public void setOrderInstallments(List<OrderInstallment> orderInstallments) {
        this.orderInstallments = orderInstallments;
    }

    public CourseOrderInfo getCourseOrderInfo() {
        return courseOrderInfo;
    }

    public void setCourse(CourseOrderInfo courseOrderInfo) {
        this.courseOrderInfo = courseOrderInfo;
    }

}


