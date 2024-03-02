package com.project.accomatch.Controller;

import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Review;
import com.project.accomatch.Service.ReviewService;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    Logger logger = LoggerClass.getLogger();
    /**
     * Handles post request to create a new review for a user.
     * @auther Dhrumil Vimalbhai Gosaliya
     * @param review review containing the details of the review to be created.
     */

    @PostMapping("/createReview")
    public void createReview(@RequestBody Review review) {
        int user_id = review.getUserId();
        int application_id = review.getApplicationId();
        logger.info("review controller active");
        reviewService.createReview(review);
    }

    /*@GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }*/

}
