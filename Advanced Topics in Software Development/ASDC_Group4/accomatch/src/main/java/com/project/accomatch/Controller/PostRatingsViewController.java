package com.project.accomatch.Controller;

import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.Model.Ratings;
import com.project.accomatch.Model.Review;
import com.project.accomatch.Service.Implementation.ReviewServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/reviews")
public class PostRatingsViewController {
    @Autowired
    public ReviewServiceImplementation reviewServiceImplementation;

    /**
     * It will return all the ratings people have given to the Post
     * @author -- Gowri Kanagaraj
     * @param application_id -- Id of application
     * @return -- List of all reviews
     */
    @GetMapping("/getListOfAllRatings/{application_id}")
    public List<Review> getListOfAllRatings(@PathVariable int application_id){
        if (application_id <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }
        return reviewServiceImplementation.getAllReviews(application_id);
    }
    /**
     * It will return average rating of the have given to the Post
     * @author -- Gowri Kanagaraj
     * @param application_id -- Id of application
     * @return -- List of all average ratings
     */
    @GetMapping("/getAverageRatings/{application_id}")
    public List<Ratings> getAverageRatings(@PathVariable int application_id){
        if (application_id <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }
        return reviewServiceImplementation.getRatingsAverage(application_id);
    }

    /**
     * Get all the reviews of the posts
     * @author -- Gowri Kanagaraj
     * @return -- List of all reviews
     */
    @GetMapping("/getAllPostReviews")
    public List<Review> getAllPostReviews(){
        return reviewServiceImplementation.getAllPostReviews();
    }

    /**
     * Get all average ratings
     * @author -- Gowri Kanagaraj
     * @return -- List of all ratings
     */
    @GetMapping("/getAllAverageRatings")
    public List<Ratings> getAllAverageRatings(){
        return reviewServiceImplementation.getAllRatingsAverage();
    }
}
