package com.project.accomatch.Model;

public class Applicant {
    //private int applicationId;
    private int userId;
    private String name;
    private String email;
    private int age;
    private String gender;
    private String mobile;

    public Applicant(int userId, String name, String email , int age , String gender , String mobile ){
        this.userId = userId;
        this.name=name;
        this.email=email;
        this.age=age;
        this.gender=gender;
        this.mobile=mobile;
    }

    public int getUserId() {
        return userId;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public int getAge() {
        return age;
    }
    public String getGender(){
        return gender;
    }
    public String getMobile(){
        return mobile;
    }
}
