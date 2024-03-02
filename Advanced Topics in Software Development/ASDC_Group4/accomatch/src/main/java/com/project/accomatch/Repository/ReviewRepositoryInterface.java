package com.project.accomatch.Repository;

import com.project.accomatch.Model.Review;

import java.util.List;

public interface ReviewRepositoryInterface {
    public int createReview(Review review);
    public List<Review> getAllReviews(int application_id);
    public List<Review> getAllPostReviews();


}
