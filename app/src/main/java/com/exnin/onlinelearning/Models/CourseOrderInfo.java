package com.exnin.onlinelearning.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseOrderInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("total_class")
    @Expose
    private String totalClass;
    @SerializedName("class_info")
    @Expose
    private String classInfo;
    @SerializedName("course_fee")
    @Expose
    private String courseFee;
    @SerializedName("usdeuro")
    @Expose
    private String usdeuro;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("class_days")
    @Expose
    private String classDays;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(String totalClass) {
        this.totalClass = totalClass;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }

    public String getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(String courseFee) {
        this.courseFee = courseFee;
    }

    public String getUsdeuro() {
        return usdeuro;
    }

    public void setUsdeuro(String usdeuro) {
        this.usdeuro = usdeuro;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
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

    public String getClassDays() {
        return classDays;
    }

    public void setClassDays(String classDays) {
        this.classDays = classDays;
    }

}
