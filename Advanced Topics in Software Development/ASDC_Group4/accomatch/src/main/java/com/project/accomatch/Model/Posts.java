package com.project.accomatch.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Posts {
    private int leaseholderApplicationId;
    private int userId;
    private String title;
    private String subtitle;
    private String address;
    private String locationCity;
    private int size;
    private String roomType;
    private String document;
    private double rent;
    private String otherPreferences;
    private Date startDate;
    private int startAge;
    private int endAge;
    @JsonProperty("isVerified")
    private int isVerified;
    private Date createdAt;
    private Date updatedAt;

    public Posts(int leaseholderApplicationId, int userId, String title, String subtitle,
                 String address, String locationCity, int size, String roomType, String document,
                 double rent, String otherPreferences, Date startDate, int startAge, int endAge,
                 int isVerified, Date createdAt, Date updatedAt) {
        this.leaseholderApplicationId = leaseholderApplicationId;
        this.userId = userId;
        this.title = title;
        this.subtitle = subtitle;
        this.address = address;
        this.locationCity = locationCity;
        this.size = size;
        this.roomType = roomType;
        this.document = document;
        this.rent = rent;
        this.otherPreferences = otherPreferences;
        this.startDate = startDate;
        this.startAge = startAge;
        this.endAge = endAge;
        this.isVerified = isVerified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Posts() {

    }


    // Getters and Setters

    public int getLeaseholderApplicationId() {
        return leaseholderApplicationId;
    }

    public void setLeaseholderApplicationId(int leaseholderApplicationId) {
        this.leaseholderApplicationId = leaseholderApplicationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public String getOtherPreferences() {
        return otherPreferences;
    }

    public void setOtherPreferences(String otherPreferences) {
        this.otherPreferences = otherPreferences;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getStartAge() {
        return startAge;
    }

    public void setStartAge(int startAge) {
        this.startAge = startAge;
    }

    public int getEndAge() {
        return endAge;
    }

    public void setEndAge(int endAge) {
        this.endAge = endAge;
    }

    public int isVerified() {
        return isVerified;
    }

    public void setVerified(int verified) {
        isVerified = verified;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
