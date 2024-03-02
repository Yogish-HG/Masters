package com.project.accomatch.Model;

import java.util.ArrayList;
import java.util.Date;

public class HouseSeekerModelBuilderImpl implements HouseSeekerModelBuilder {
    private int user_id, housekeeper_application_Id;
    private String location_city, room_type, other_preferences, name;
    private Date start_date, created_At, updated_At;
    private ArrayList<String> food_preferences, gender_preferences;

    public HouseSeekerModelBuilderImpl(int user_id, String location_city, String room_type, Date start_date) {
        this.user_id = user_id;
        this.location_city = location_city;
        this.room_type = room_type;
        this.start_date = start_date;
    }
    @Override
    public HouseSeekerModelBuilder housekeeperApplicationId(int housekeeper_application_Id) {
        this.housekeeper_application_Id = housekeeper_application_Id;
        return this;
    }

    @Override
    public HouseSeekerModelBuilder name(String name) {
        this.name=name;
        return this;
    }

    @Override
    public HouseSeekerModelBuilder otherPreferences(String other_preferences) {
        this.other_preferences = other_preferences;
        return this;
    }

    @Override
    public HouseSeekerModelBuilder createdAt(Date created_At) {
        this.created_At = created_At;
        return this;
    }

    @Override
    public HouseSeekerModelBuilder updatedAt(Date updated_At) {
        this.updated_At = updated_At;
        return this;
    }

    @Override
    public HouseSeekerModelBuilder foodPreferences(ArrayList<String> food_preferences) {
        this.food_preferences = food_preferences;
        return this;
    }

    @Override
    public HouseSeekerModelBuilder genderPreferences(ArrayList<String> gender_preferences) {
        this.gender_preferences = gender_preferences;
        return this;
    }

    @Override
    public HouseSeekerModel build() {
        HouseSeekerModel houseSeekerModel = new HouseSeekerModel();
        houseSeekerModel.user_id = this.user_id;
        houseSeekerModel.housekeeper_application_Id = this.housekeeper_application_Id;
        houseSeekerModel.location_city = this.location_city;
        houseSeekerModel.room_type = this.room_type;
        houseSeekerModel.other_preferences = this.other_preferences;
        houseSeekerModel.name = this.name;
        houseSeekerModel.start_date = this.start_date;
        houseSeekerModel.created_At = this.created_At;
        houseSeekerModel.updated_At = this.updated_At;
        houseSeekerModel.food_preferences = this.food_preferences;
        houseSeekerModel.gender_preferences = this.gender_preferences;
        return houseSeekerModel;
    }
}
