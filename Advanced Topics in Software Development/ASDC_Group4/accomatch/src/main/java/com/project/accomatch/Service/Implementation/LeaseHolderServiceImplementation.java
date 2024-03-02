package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Exception.PostCreationException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.LeaseHolderModel;
import com.project.accomatch.Repository.Implementation.LeaseHolderFoodTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderGenderTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderImagesTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderTableOperations;
import com.project.accomatch.Repository.LeaseHolderFoodTableOperationsInterface;
import com.project.accomatch.Repository.LeaseHolderGenderTableOperationsInterface;
import com.project.accomatch.Repository.LeaseHolderImagesTableOperationsInterface;
import com.project.accomatch.Repository.LeaseHolderTableOperationsInterface;
import com.project.accomatch.Service.LeaseHolderService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
/**
 * Service class for the leaseholder post creation.
 * @author Bhargav
 */
@Service
public class LeaseHolderServiceImplementation implements LeaseHolderService {
    @Autowired
    LeaseHolderTableOperationsInterface leaseHolderTableOperations;
    @Autowired
    LeaseHolderFoodTableOperationsInterface leaseHolderFoodTableOperations;
    @Autowired
    LeaseHolderGenderTableOperationsInterface leaseHolderGenderTableOperations;
    @Autowired
    LeaseHolderImagesTableOperationsInterface leaseHolderImagesTableOperations;

    Logger logger = LoggerClass.getLogger();
    /**
     * Creates a new advertisement (post) for the leaseholder.
     *
     * @param requestBody The request body containing the post details.
     * @return A success message if the post is created successfully.
     * @throws PostCreationException If there is an error during post creation.
     */
    @Override
    public String createAD(Map<String, Object> requestBody) {
        try {
            LeaseHolderModel leaseHolderModel = new LeaseHolderModel();

            leaseHolderModel.setUser_id(Integer.parseInt(requestBody.get("user_id").toString()));
            leaseHolderModel.setSize(Integer.parseInt(requestBody.get("size").toString()));
            leaseHolderModel.setTitle((String) requestBody.get("title"));
            leaseHolderModel.setSubtitle((String) requestBody.get("subtitle"));
            leaseHolderModel.setAddress((String) requestBody.get("address"));
            leaseHolderModel.setLocation_city((String) requestBody.get("location_city"));
            leaseHolderModel.setRoom_type((String) requestBody.get("room_type"));
            leaseHolderModel.setDocument((String) requestBody.get("document"));
            leaseHolderModel.setOther_preferences((String) requestBody.get("other_preferences"));
            leaseHolderModel.setRent(Double.parseDouble(requestBody.get("rent").toString()));
            leaseHolderModel.setIs_verified(0);
            leaseHolderModel.setStart_age(Integer.parseInt(requestBody.get("start_age").toString()));
            leaseHolderModel.setEnd_age(Integer.parseInt(requestBody.get("end_age").toString()));


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start_dateStr = (String) requestBody.get("start_date");
            Date start_date = sdf.parse(start_dateStr);

            leaseHolderModel.setStart_date(start_date);
            leaseHolderModel.setFood_preferences((ArrayList<String>) requestBody.get("food_preferences"));
            leaseHolderModel.setImages((ArrayList<String>) requestBody.get("images"));
            leaseHolderModel.setGender_preferences((ArrayList<String>) requestBody.get("gender_preferences"));

            int leaseholder_application_id = leaseHolderTableOperations.createAD(leaseHolderModel);
            boolean isFoodPreferencesAdded = leaseHolderFoodTableOperations.createFoodReferences(leaseHolderModel,leaseholder_application_id);
            boolean isImagesAdded = leaseHolderImagesTableOperations.addImages(leaseHolderModel,leaseholder_application_id);
            boolean isGenderPreferencesAdded = leaseHolderGenderTableOperations.createGenderReferences(leaseHolderModel,leaseholder_application_id);
            logger.error("Post created Successfully with PostID: {}",leaseholder_application_id);
            return "Success";
        } catch (Exception e){
            logger.error("Error during post creation: {}", e.getMessage(), e);
            throw new PostCreationException("Error during post creation.", e);
        }
    }


}
