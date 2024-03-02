package com.project.accomatch.Model;

import java.util.ArrayList;
import java.util.Date;

public class HouseSeekerModel {
    public int getUser_id() {
        return user_id;
    }

    public int getHousekeeper_application_Id() {
        return housekeeper_application_Id;
    }

    public String getLocation_city() {
        return location_city;
    }

    public String getRoom_type() {
        return room_type;
    }

    public String getOther_preferences() {
        return other_preferences;
    }

    public String getName() {
        return name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public ArrayList<String> getFood_preferences() {
        return food_preferences;
    }

    public ArrayList<String> getGender_preferences() {
        return gender_preferences;
    }

    public int user_id, housekeeper_application_Id;
    public String location_city, room_type, other_preferences, name;
    public Date start_date, created_At, updated_At;
    public ArrayList<String> food_preferences, gender_preferences;

    public HouseSeekerModel() {
    }

    //  method to obtain the builder instance
    public static HouseSeekerModelBuilder builder(int user_id, String location_city, String room_type, Date start_date) {
        return new HouseSeekerModelBuilderImpl(user_id, location_city, room_type, start_date);
    }
    
}
