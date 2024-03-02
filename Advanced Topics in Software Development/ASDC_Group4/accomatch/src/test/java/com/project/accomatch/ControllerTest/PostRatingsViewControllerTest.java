package com.project.accomatch.ControllerTest;

import com.project.accomatch.Controller.PostRatingsViewController;
import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.Model.Ratings;
import com.project.accomatch.Model.Review;
import com.project.accomatch.Service.Implementation.ReviewServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PostRatingsViewControllerTest {

    private ReviewServiceImplementation reviewService;
    private PostRatingsViewController postRatingsViewController;

    @BeforeEach
    void setUp() {
        reviewService = mock(ReviewServiceImplementation.class);
        postRatingsViewController = new PostRatingsViewController();
        postRatingsViewController.reviewServiceImplementation = reviewService;
    }

    @Test
    void testGetListOfAllRatings() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(4, 20,3, "rahul", 6, "fine."));
        reviewList.add(new Review(3, 7,3, "manan", 6, "bad"));
        when(reviewService.getAllReviews(anyInt())).thenReturn(reviewList);
        List<Review> result = postRatingsViewController.getListOfAllRatings(2);
        verify(reviewService, times(1)).getAllReviews(2);
        assertEquals(reviewList, result);
    }

    @Test
    void testGetAverageRatings() {
        List<Ratings> ratingsList = new ArrayList<>();
        ratingsList.add(new Ratings());
        ratingsList.add(new Ratings());
        when(reviewService.getRatingsAverage(anyInt())).thenReturn(ratingsList);
        List<Ratings> result = postRatingsViewController.getAverageRatings(4);
        verify(reviewService, times(1)).getRatingsAverage(4);
        assertEquals(ratingsList, result);
    }

    @Test
    void testGetAllPostReviews() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(4, 20,3, "rahul", 6, "fine."));
        reviewList.add(new Review(2, 20,2, "manan", 3, "average."));
        when(reviewService.getAllPostReviews()).thenReturn(reviewList);
        List<Review> result = postRatingsViewController.getAllPostReviews();
        verify(reviewService, times(1)).getAllPostReviews();
        assertEquals(reviewList, result);
    }

    @Test
    void testGetAllAverageRatings() {
        List<Ratings> ratingsList = new ArrayList<>();
        ratingsList.add(new Ratings());
        ratingsList.add(new Ratings());
        when(reviewService.getAllRatingsAverage()).thenReturn(ratingsList);
        List<Ratings> result = postRatingsViewController.getAllAverageRatings();
        verify(reviewService, times(1)).getAllRatingsAverage();
        assertEquals(ratingsList, result);
    }
}
