package com.project.accomatch.ControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.accomatch.Controller.LeaseHolderDashboardController;
import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.Exception.PostNotFoundException;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Service.DashboardInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaseHolderDashboardControllerTest {

    @Mock
    private DashboardInterface dashboardService;

    @InjectMocks
    private LeaseHolderDashboardController dashboardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListOfPosts_Success() {
        // Arrange
        List<Posts> expectedPostsList = new ArrayList<>();
        expectedPostsList.add(new Posts());
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUserDetails(), null);

        when(dashboardService.getListOfPosts()).thenReturn(expectedPostsList);

        // Act
        List<Posts> result = dashboardController.getListOfPosts(authentication);

        // Assert
        assertEquals(expectedPostsList, result);
        verify(dashboardService, times(1)).getListOfPosts();
    }

    @Test
    void testGetPostDetails_Success() {
        // Arrange
        int applicationId = 123;
        Posts expectedPost = new Posts();
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUserDetails(), null);

        when(dashboardService.getPostByApplicationId(applicationId)).thenReturn(expectedPost);

        // Act
        Posts result = dashboardController.getPostDetails(applicationId, authentication);

        // Assert
        assertEquals(expectedPost, result);
        verify(dashboardService, times(1)).getPostByApplicationId(applicationId);
    }

    @Test
    void testGetPostDetails_InvalidInputException() {
        // Arrange
        int invalidApplicationId = -1;
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUserDetails(), null);

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> dashboardController.getPostDetails(invalidApplicationId, authentication));
        verifyNoInteractions(dashboardService);
    }

    @Test
    void testGetListOfImages_Success() {
        // Arrange
        int applicationId = 123;
        List<String> expectedImages = Arrays.asList("image1.jpg", "image2.jpg");
        Posts mockedPost = new Posts();

        // Mocking the behavior of the dashboardService.getListOfImagesByApplicationId method
        when(dashboardService.getListOfImagesByApplicationId(applicationId)).thenReturn(expectedImages);
        // Mocking the behavior of the dashboardService.getPostByApplicationId method
        when(dashboardService.getPostByApplicationId(applicationId)).thenReturn(mockedPost);

        // Act
        List<String> result = dashboardController.getListOfImages(applicationId);

        // Assert
        // Verifying that the result obtained from the controller is equal to the expectedImages
        assertEquals(expectedImages, result);

        // Verifying that the dashboardService.getListOfImagesByApplicationId method is called exactly once with the provided applicationId
        verify(dashboardService, times(1)).getListOfImagesByApplicationId(applicationId);
        // Verifying that the dashboardService.getPostByApplicationId method is called exactly once with the provided applicationId
        verify(dashboardService, times(1)).getPostByApplicationId(applicationId);
    }

    @Test
    void testGetListOfImages_InvalidInputException() {
        // Arrange
        int invalidApplicationId = -1;

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> dashboardController.getListOfImages(invalidApplicationId));
        verifyNoInteractions(dashboardService);
    }

    @Test
    void testGetListOfFoodPreferences_Success() {
        // Arrange
        int applicationId = 123;
        List<String> expectedFoodPreferences = Arrays.asList("Vegetarian", "Non-Vegetarian");
        Posts mockedPost = new Posts();

        // Mocking the behavior of the dashboardService.getListOfFoodPreferencesByApplicationId method
        when(dashboardService.getListOfFoodPreferencesByApplicationId(applicationId)).thenReturn(expectedFoodPreferences);
        // Mocking the behavior of the dashboardService.getPostByApplicationId method
        when(dashboardService.getPostByApplicationId(applicationId)).thenReturn(mockedPost);

        // Act
        List<String> result = dashboardController.getListOfFoodPreferences(applicationId);

        // Assert
        // Verifying that the result obtained from the controller is equal to the expectedFoodPreferences
        assertEquals(expectedFoodPreferences, result);

        // Verifying that the dashboardService.getListOfFoodPreferencesByApplicationId method is called exactly once with the provided applicationId
        verify(dashboardService, times(1)).getListOfFoodPreferencesByApplicationId(applicationId);
        // Verifying that the dashboardService.getPostByApplicationId method is called exactly once with the provided applicationId
        verify(dashboardService, times(1)).getPostByApplicationId(applicationId);
    }


    @Test
    void testGetListOfGenderPreferences_Success() {
        // Arrange
        int applicationId = 123;
        List<String> expectedGenderPreferences = Arrays.asList("Male", "Female");
        Posts mockedPost = new Posts();

        // Mocking the behavior of the dashboardService.getListOfgenderPreferencesByApplicationId method
        when(dashboardService.getListOfgenderPreferencesByApplicationId(applicationId)).thenReturn(expectedGenderPreferences);
        // Mocking the behavior of the dashboardService.getPostByApplicationId method
        when(dashboardService.getPostByApplicationId(applicationId)).thenReturn(mockedPost);

        // Act
        List<String> result = dashboardController.getListOfGenderPreferences(applicationId);

        // Assert
        // Verifying that the result obtained from the controller is equal to the expectedGenderPreferences
        assertEquals(expectedGenderPreferences, result);

        // Verifying that the dashboardService.getListOfgenderPreferencesByApplicationId method is called exactly once with the provided applicationId
        verify(dashboardService, times(1)).getListOfgenderPreferencesByApplicationId(applicationId);
        // Verifying that the dashboardService.getPostByApplicationId method is called exactly once with the provided applicationId
        verify(dashboardService, times(1)).getPostByApplicationId(applicationId);
    }



    @Test
    void testGetListOfGenderPreferences_InvalidInputException() {
        // Arrange
        int invalidApplicationId = -1;

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> dashboardController.getListOfGenderPreferences(invalidApplicationId));
        verifyNoInteractions(dashboardService);
    }

    @Test
    void testGetListOfGenderPreferences_ZeroApplicationId_InvalidInputException() {
        // Arrange
        int zeroApplicationId = 0;

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> dashboardController.getListOfGenderPreferences(zeroApplicationId));
        verifyNoInteractions(dashboardService);
    }

    @Test
    void testGetListOfPersonalPosts_Success() {
        // Arrange
        int userId = 123;
        List<Posts> expectedPosts = Arrays.asList(new Posts());

        when(dashboardService.getListOfPersonalPosts(userId)).thenReturn(expectedPosts);

        // Act
        List<Posts> result = dashboardController.getListOfPersonalPosts(userId);

        // Assert
        assertEquals(expectedPosts, result);
        verify(dashboardService, times(1)).getListOfPersonalPosts(userId);
    }

    @Test
    void testGetListOfPersonalPosts_InvalidInputException() {
        // Arrange
        int invalidUserId = -1;

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> dashboardController.getListOfPersonalPosts(invalidUserId));
        verifyNoInteractions(dashboardService);
    }

    @Test
    void testGetListOfPersonalPosts_ZeroUserId_InvalidInputException() {
        // Arrange
        int zeroUserId = 0;

        // Act & Assert
        assertThrows(InvalidInputException.class, () -> dashboardController.getListOfPersonalPosts(zeroUserId));
        verifyNoInteractions(dashboardService);
    }

    @Test
    void testGetPostDetails_PostNotFoundException() {
        // Arrange
        int nonExistentApplicationId = 999;
        Authentication authentication = new UsernamePasswordAuthenticationToken(createUserDetails(), null);

        when(dashboardService.getPostByApplicationId(nonExistentApplicationId)).thenReturn(null);

        // Act & Assert
        assertThrows(PostNotFoundException.class, () -> dashboardController.getPostDetails(nonExistentApplicationId, authentication));
        verify(dashboardService, times(1)).getPostByApplicationId(nonExistentApplicationId);
    }

    private UserDetails createUserDetails() {
        return User.builder()
                .username("testUser")
                .password("testPassword")
                .roles("USER")
                .build();
    }
}
