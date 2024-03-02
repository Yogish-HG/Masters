package com.project.accomatch.Service;

import com.project.accomatch.Model.Ratings;
import com.project.accomatch.Model.Review;

import java.util.List;

public interface ReviewService {
    void createReview(Review review);

    List<Review> getAllReviews(int application_id);

    List<Review> getAllPostReviews();
    // Other methods for retrieving, updating, deleting reviews
    List<Ratings> getRatingsAverage(int appliciation_id);
}
