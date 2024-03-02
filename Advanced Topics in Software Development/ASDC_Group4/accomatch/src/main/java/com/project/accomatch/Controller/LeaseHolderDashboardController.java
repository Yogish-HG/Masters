
package com.project.accomatch.Controller;
import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.Exception.PostNotFoundException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Service.DashboardInterface;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
/**
 * Controller class for the leaseholder dashboard.
 * @author Ramandeep Kaur
 */
@RestController
@CrossOrigin
@RequestMapping("/api/leaseholder/dashboard")
public class LeaseHolderDashboardController {
    @Autowired
    private DashboardInterface dashboardService;

    Logger logger = LoggerClass.getLogger();
    /**
     * Retrieves the list of posts.
     * @author Ramandeep kaur
     * @return The list of posts.
     */
    @GetMapping("/get/list/post")
    public List<Posts> getListOfPosts(Authentication authentication) {
        // Getting the currently authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        logger.info("Getting list of posts for user: {}", username);
        return dashboardService.getListOfPosts();
    }


    /**
     * Retrieves the details of a post based on the application ID.
     * @author Ramandeep Kaur
     * @param applicationId The application ID.
     * @return The post details.
     */
    @GetMapping("/get/post/details/{applicationId}")
    public Posts getPostDetails(@PathVariable int applicationId, Authentication authentication) {
        if (applicationId <= 0) {
            logger.error("No details of post were found with application ID {}:", applicationId);

            throw new InvalidInputException("Invalid application ID provided.");
        }

        // Getting the currently authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        logger.info("Getting details of post with application ID {} for user: {}", applicationId, username);

        // Checking if the post with the given application ID exists
        Posts post = dashboardService.getPostByApplicationId(applicationId);
        if (post == null) {
            logger.error("No details of post were found with application ID {} for user: {}", applicationId, username);
            throw new PostNotFoundException("Post not found with application ID: " + applicationId);
        }

        return post;
    }

    /**
     * Retrieves the list of images for a post based on the application ID.
     *@author Ramandeep Kaur
     * @param applicationId The application ID.
     * @return The list of images.
     */
    @GetMapping("/get/list/images/{applicationId}")
    public List<String> getListOfImages(@PathVariable int applicationId) {
        if (applicationId <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }

        logger.info("Getting list of images for post with application ID: {}", applicationId);

        // Checking if the post with the given application ID exists
        Posts post = dashboardService.getPostByApplicationId(applicationId);
        if (post == null) {
            logger.error("No details of post were found with application ID: {}", applicationId);
            throw new PostNotFoundException("Post not found with application ID: " + applicationId);
        }

        return dashboardService.getListOfImagesByApplicationId(applicationId);
    }

    /**
     * Retrieves the list of food preferences for a post based on the application ID.
     *@author Ramandeep Kaur
     * @param applicationId The application ID.
     * @return The list of food preferences.
     */
    @GetMapping("/get/list/food/{applicationId}")
    public List<String> getListOfFoodPreferences(@PathVariable int applicationId) {
        if (applicationId <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }
        logger.info("Getting list of food preference for post with application ID: {}", applicationId);
        Posts post = dashboardService.getPostByApplicationId(applicationId);
        if (post == null) {
            logger.error("No details of post were found with application ID: {}", applicationId);
            throw new PostNotFoundException("Post not found with application ID: " + applicationId);
        }
        return dashboardService.getListOfFoodPreferencesByApplicationId(applicationId);
    }

    /**
     * Retrieves the list of gender preferences for a post based on the application ID.
     *@author Ramandeep Kaur
     * @param applicationId The application ID.
     * @return The list of gender preferences.
     */
    @GetMapping("/get/list/gender/{applicationId}")
    public List<String> getListOfGenderPreferences(@PathVariable int applicationId) {
        if (applicationId <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }
        logger.info("Getting list of gender preference for post with application ID: {}", applicationId);
        Posts post = dashboardService.getPostByApplicationId(applicationId);
        if (post == null) {
            logger.error("No details of post were found with application ID: {}", applicationId);
            throw new PostNotFoundException("Post not found with application ID: " + applicationId);
        }
        return dashboardService.getListOfgenderPreferencesByApplicationId(applicationId);
    }

    /**
     * Retrieves a list of personal posts for a specific user based on their user ID.
     * @auther Dhrumil Vimalbhai Gosaliya
     * @param user_Id The user id of the user.
     * @return A list of Posts objects representing the personal posts of the user.
     * @throws InvalidInputException if the provided user ID is invalid.
     */
    @GetMapping("/get/list/getListOfPersonalPosts/{user_Id}")
    public List<Posts> getListOfPersonalPosts(@PathVariable int user_Id) {
        if (user_Id <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }
        return dashboardService.getListOfPersonalPosts(user_Id);
    }

}

