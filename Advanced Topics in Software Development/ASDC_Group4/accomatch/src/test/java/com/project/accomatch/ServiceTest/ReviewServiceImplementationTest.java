package com.project.accomatch.ServiceTest;


import com.project.accomatch.Exception.ApplicantNotFound;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Model.Ratings;
import com.project.accomatch.Model.Review;
import com.project.accomatch.Repository.LeaseholderAdsDaoInterface;
import com.project.accomatch.Repository.Implementation.ReviewRepository;
import com.project.accomatch.Service.Implementation.ReviewServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ReviewServiceImplementationTest {

    private ReviewRepository reviewRepository;
    private LeaseholderAdsDaoInterface leaseholderAdsDao;
    private ReviewServiceImplementation reviewService;

    @BeforeEach
    void setUp() {
        reviewRepository = mock(ReviewRepository.class);
        leaseholderAdsDao = mock(LeaseholderAdsDaoInterface.class);
        reviewService = new ReviewServiceImplementation();
        reviewService.reviewRepository = reviewRepository;
        reviewService.leaseholderAdsDao = leaseholderAdsDao;
    }

    @Test
    void testCreateReview() {
        Review review = new Review(4, 20,3, "rahul", 6, "fine.");
        reviewService.createReview(review);
        verify(reviewRepository, times(1)).createReview(review);
    }

    @Test
    void testGetAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(4, 20,3, "rahul", 6, "fine."));
        when(reviewRepository.getAllReviews(anyInt())).thenReturn(reviewList);
        List<Review> result = reviewService.getAllReviews(1);
        verify(reviewRepository, times(1)).getAllReviews(1);
        assertEquals(reviewList, result);
    }

    @Test
    void testGetAllPostReviews() {
        List<Review> postReviewList = new ArrayList<>();
        postReviewList.add(new Review(4, 20,3, "rahul", 6, "fine."));
        when(reviewRepository.getAllPostReviews()).thenReturn(postReviewList);
        List<Review> result = reviewService.getAllPostReviews();
        verify(reviewRepository, times(1)).getAllPostReviews();
        assertEquals(postReviewList, result);
    }

  /*  @Test
    void testGetAllPostReviewsWithException() {
        when(reviewRepository.getAllPostReviews()).thenThrow(new ApplicantNotFound("Failed to retrieve the list of Applicants."));
        assertThrows(ApplicantNotFound.class, reviewService::getAllPostReviews);
        verify(reviewRepository, times(1)).getAllPostReviews();
    }*/

    @Test
    void testGetRatingsAverage() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(4, 20,3, "rahul", 6, "fine."));
        when(reviewRepository.getAllReviews(anyInt())).thenReturn(reviewList);
        List<Ratings> result = reviewService.getRatingsAverage(1);
        verify(reviewRepository, times(1)).getAllReviews(1);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testGetAllRatingsAverage() throws SQLException {
        List<Posts> postsList = new ArrayList<>();
        Posts post1 = new Posts();
        post1.setLeaseholderApplicationId(1);
        Posts post2 = new Posts();
        post2.setLeaseholderApplicationId(2);
        postsList.add(post1);
        postsList.add(post2);

        when(leaseholderAdsDao.getListOfPosts()).thenReturn(postsList);

        List<Review> reviewList1 = new ArrayList<>();
        reviewList1.add(new Review(4, 20,3, "rahul", 6, "fine."));
        when(reviewRepository.getAllReviews(1)).thenReturn(reviewList1);
        List<Review> reviewList2 = new ArrayList<>();
        reviewList2.add(new Review(4, 20,3, "rahul", 6, "fine."));
        when(reviewRepository.getAllReviews(1)).thenReturn(reviewList2);
        List<Ratings> result = reviewService.getAllRatingsAverage();
        verify(leaseholderAdsDao, times(1)).getListOfPosts();
        verify(reviewRepository, times(1)).getAllReviews(2);
        verify(reviewRepository, times(1)).getAllReviews(2);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }
}
