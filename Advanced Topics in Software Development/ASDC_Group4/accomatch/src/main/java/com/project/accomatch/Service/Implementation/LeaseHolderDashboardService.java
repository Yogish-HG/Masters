package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Exception.DataAccessException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.Implementation.LeaseholderAdsDao;
import com.project.accomatch.Repository.LeaseHolderFoodTableOperationsInterface;
import com.project.accomatch.Repository.LeaseHolderGenderTableOperationsInterface;
import com.project.accomatch.Repository.LeaseHolderImagesTableOperationsInterface;
import com.project.accomatch.Service.DashboardInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class LeaseHolderDashboardService implements DashboardInterface {

    @Autowired
    LeaseholderAdsDao leaseholderAdsDao;

    @Autowired
    LeaseHolderFoodTableOperationsInterface leaseHolderFoodTableOperations;
    @Autowired
    LeaseHolderGenderTableOperationsInterface leaseHolderGenderTableOperations;
    @Autowired
    LeaseHolderImagesTableOperationsInterface leaseHolderImagesTableOperations;

    Logger logger = LoggerClass.getLogger();

    /**
     * Retrieves the list of all posts.
     *
     * @return The list of posts.
     * @throws DataAccessException If there is a failure to retrieve the list of posts from the database.
     */
    public List<Posts> getListOfPosts() {
        try {
            return leaseholderAdsDao.getListOfPosts();
        } catch (SQLException e) {
            logger.error("Failed to retrieve the list of posts.", e);
            throw new DataAccessException("Failed to retrieve the list of posts.", e);
        }
    }


    public List<Posts> getListOfPostsByStatus(int status) {
        return leaseholderAdsDao.getListOfPostsByStatus( status);
    }

    /**
     * Retrieves the list of gender preferences for a post based on the application ID.
     *
     * @param applicationId The application ID.
     * @return The list of gender preferences.
     */
    public List<String> getListOfgenderPreferencesByApplicationId(int applicationId){
      return   leaseHolderGenderTableOperations.getGenderPreferencesByApplicationId(applicationId);
    }

    public List<String> getListOfFoodPreferencesByApplicationId(int applicationId){
        return   leaseHolderFoodTableOperations.getFoodPreferencesByApplicationId(applicationId);
    }
    /**
     * Retrieves the list of images for a post based on the application ID.
     *
     * @param applicationId The application ID.
     * @return The list of images.
     */
    public List<String> getListOfImagesByApplicationId(int applicationId){
        return   leaseHolderImagesTableOperations.getImagesByApplicationId(applicationId);
    }
    /**
     * Retrieves the details of a post based on the application ID.
     *
     * @param applicationId The application ID.
     * @return The post details.
     */
    public Posts getPostByApplicationId(int applicationId){
        return leaseholderAdsDao.getPostByApplicationId(applicationId);
    }

    public List<Posts> getListOfPersonalPosts(int userID){
        return leaseholderAdsDao.getListOfPersonalPosts(userID);
    }
}
