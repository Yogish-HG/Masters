package com.project.accomatch.ControllerTest;


import com.project.accomatch.Controller.ReviewController;
import com.project.accomatch.Model.Review;
import com.project.accomatch.Service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReviewSuccess() {
        Review review = new Review(4, 20,3, "rahul", 6, "fine.");
        review.setUserId(1);
        review.setApplicationId(3);
        doNothing().when(reviewService).createReview(review);
        reviewController.createReview(review);
        verify(reviewService, times(1)).createReview(review);
    }
}
