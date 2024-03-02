package com.project.accomatch.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LeaseHolderModel {
    private int user_id,size;
    private String title,subtitle,address,location_city,room_type,document,other_preferences;
    private double rent;
    private int is_verified,start_age,end_age;
    private Date start_date;
    private ArrayList<String> food_preferences,images,gender_preferences;

    public ArrayList<String> getFood_preferences() {
        return food_preferences;
    }

    public void setFood_preferences(ArrayList<String> food_preferences) {
        this.food_preferences = food_preferences;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getGender_preferences() {
        return gender_preferences;
    }

    public void setGender_preferences(ArrayList<String> gender_preferences) {
        this.gender_preferences = gender_preferences;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public String getLocation_city() {
        return location_city;
    }

    public void setLocation_city(String location_city) {
        this.location_city = location_city;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getOther_preferences() {
        return other_preferences;
    }

    public void setOther_preferences(String other_preferences) {
        this.other_preferences = other_preferences;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }

    public int getStart_age() {
        return start_age;
    }

    public void setStart_age(int start_age) {
        this.start_age = start_age;
    }

    public int getEnd_age() {
        return end_age;
    }

    public void setEnd_age(int end_age) {
        this.end_age = end_age;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date=start_date;
    }
    public LeaseHolderModel(){}

    public LeaseHolderModel(int user_id, int size, String title, String subtitle, String address, String location_city, String room_type, String document, String other_preferences, double rent, int start_age, int end_age, String start_date, ArrayList<String> food_preferences, ArrayList<String> images, ArrayList<String> gender_preferences) throws ParseException {
        this.user_id = user_id;
        this.size = size;
        this.title = title;
        this.subtitle = subtitle;
        this.address = address;
        this.location_city = location_city;
        this.room_type = room_type;
        this.document = document;
        this.other_preferences = other_preferences;
        this.rent = rent;
        this.is_verified = 0;
        this.start_age = start_age;
        this.end_age = end_age;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.start_date = simpleDateFormat.parse(start_date);
        System.out.println(this.start_date);
        this.food_preferences = food_preferences;
        this.images = images;
        this.gender_preferences = gender_preferences;
    }
}
