package com.project.accomatch.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LeaseHolderApplicantModel {
    private int user_id,application_id,room_id;
    private String status;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getApplication_id() {
        return application_id;
    }

    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public LeaseHolderApplicantModel(){}
    public LeaseHolderApplicantModel(int user_id,int application_id){
        this.user_id=user_id;
        this.application_id=application_id;
    }
    public LeaseHolderApplicantModel(int user_id,int application_id,String status) {
        this.user_id = user_id;
        this.application_id = application_id;
        this.status = status;
    }

}
