package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Exception.ApplicantNotFound;
import com.project.accomatch.Exception.PostCreationException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.HouseSeekerModel;
import com.project.accomatch.Repository.HouseSeekerFoodTableOperationsInterface;
import com.project.accomatch.Repository.HouseSeekerGenderTableOperationsInterface;
import com.project.accomatch.Repository.HouseSeekerTableOperationsInterface;
import com.project.accomatch.Repository.Implementation.HouseSeekerFoodTableOperations;
import com.project.accomatch.Repository.Implementation.HouseSeekerGenderTableOperations;
import com.project.accomatch.Repository.Implementation.HouseSeekerTableOperations;
import com.project.accomatch.Service.HouseSeekerService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HouseSeekerServiceImplementation implements HouseSeekerService {
    private final HouseSeekerTableOperationsInterface houseSeekerTableOperations;
    private final HouseSeekerFoodTableOperationsInterface houseSeekerFoodTableOperations;
    private final HouseSeekerGenderTableOperationsInterface houseSeekerGenderTableOperations;

    Logger logger = LoggerClass.getLogger();
    @Autowired
    public HouseSeekerServiceImplementation(
            HouseSeekerTableOperations houseSeekerTableOperations,
            HouseSeekerFoodTableOperations houseSeekerFoodTableOperations,
            HouseSeekerGenderTableOperations houseSeekerGenderTableOperations) {
        this.houseSeekerTableOperations = houseSeekerTableOperations;
        this.houseSeekerFoodTableOperations = houseSeekerFoodTableOperations;
        this.houseSeekerGenderTableOperations = houseSeekerGenderTableOperations;
    }
    @Override
    public String createAD(Map<String, Object>  requestBody) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String user_idStr = (String) requestBody.get("user_id");
            int user_id = Integer.parseInt(user_idStr);
            String location_city = (String) requestBody.get("location_city");
            String room_type = (String) requestBody.get("room_type");
            String other_preferences = (String) requestBody.get("other_preferences");
            String start_dateStr = (String) requestBody.get("start_date");
            Date start_date = sdf.parse(start_dateStr);
            ArrayList<String> food_preferences = (ArrayList<String>) requestBody.get("food_preferences");
            ArrayList<String> gender_preferences = (ArrayList<String>) requestBody.get("gender_preferences");
            HouseSeekerModel houseSeekerModel =  HouseSeekerModel
                    .builder(user_id,location_city,room_type,start_date)
                    .otherPreferences(other_preferences)
                    .foodPreferences(food_preferences)
                    .genderPreferences(gender_preferences)
                    .build();

            int houseseeker_application_id = houseSeekerTableOperations.createAD(houseSeekerModel);
            boolean isFoodPreferencesAdded = houseSeekerFoodTableOperations.createFoodReferences(houseSeekerModel,houseseeker_application_id);
            boolean isGenderPreferencesAdded = houseSeekerGenderTableOperations.createGenderReferences(houseSeekerModel,houseseeker_application_id);

            return "Success";
        } catch (Exception e){
            logger.error("Error during post creation: {}", e.getMessage(), e);
            throw new PostCreationException("Error during post creation.", e);
        }
    }

    @Override
    public List<HouseSeekerModel> getListOfAllApplicantPosts() {
        try {

            return houseSeekerTableOperations.getListOfAllApplicantPosts();
        } catch (Exception e){
            logger.error("Error during retrieving applicants: {}", e.getMessage(), e);
            throw new ApplicantNotFound("Error during retrieving applicants.");
        }
    }
}

