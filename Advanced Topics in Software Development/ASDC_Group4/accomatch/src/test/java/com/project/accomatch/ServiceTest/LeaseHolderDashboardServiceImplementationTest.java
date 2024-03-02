package com.project.accomatch.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.project.accomatch.Exception.DataAccessException;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.Implementation.LeaseholderAdsDao;
import com.project.accomatch.Repository.Implementation.LeaseHolderFoodTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderGenderTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderImagesTableOperations;
import com.project.accomatch.Service.Implementation.LeaseHolderDashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LeaseHolderDashboardServiceImplementationTest {
    @Mock
    private LeaseholderAdsDao leaseholderAdsDao;

    @Mock
    private LeaseHolderFoodTableOperations leaseHolderFoodTableOperations;

    @Mock
    private LeaseHolderGenderTableOperations leaseHolderGenderTableOperations;

    @Mock
    private LeaseHolderImagesTableOperations leaseHolderImagesTableOperations;

    @InjectMocks
    private LeaseHolderDashboardService leaseHolderDashboardService;

    @BeforeEach
    void setUp() {
        reset(leaseholderAdsDao);
        reset(leaseHolderFoodTableOperations);
        reset(leaseHolderGenderTableOperations);
        reset(leaseHolderImagesTableOperations);
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getListOfPosts()}.
     * It verifies that the method returns the correct list of posts from the DAO.
     *
     * @throws SQLException if an error occurs while accessing the data.
     */
    @Test
    void testGetListOfPosts_Success() throws SQLException {
        // Arrange
        List<Posts> expectedPostsList = new ArrayList<>();
        expectedPostsList.add(new Posts(/* post parameters */));
        when(leaseholderAdsDao.getListOfPosts()).thenReturn(expectedPostsList);

        // Act
        List<Posts> result = leaseHolderDashboardService.getListOfPosts();

        // Assert
        assertEquals(expectedPostsList, result);
        verify(leaseholderAdsDao, times(1)).getListOfPosts();
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getListOfPosts()}.
     * It verifies that the method throws a {@link DataAccessException} when an SQL exception occurs in the DAO.
     *
     * @throws SQLException if an error occurs while accessing the data.
     */
    @Test
    void testGetListOfPosts_DataAccessException() throws SQLException {
        // Arrange
        when(leaseholderAdsDao.getListOfPosts()).thenThrow(new SQLException());

        // Act & Assert
        assertThrows(DataAccessException.class, () -> leaseHolderDashboardService.getListOfPosts());
        verify(leaseholderAdsDao, times(1)).getListOfPosts();
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getListOfPostsByStatus(int)}.
     * It verifies that the method returns the correct list of posts for the given status from the DAO.
     */
    @Test
    void testGetListOfPostsByStatus_Success() {
        // Arrange
        int status = 1;
        List<Posts> expectedPostsList = new ArrayList<>();
        expectedPostsList.add(new Posts(/* post parameters */));
        when(leaseholderAdsDao.getListOfPostsByStatus(status)).thenReturn(expectedPostsList);

        // Act
        List<Posts> result = leaseHolderDashboardService.getListOfPostsByStatus(status);

        // Assert
        assertEquals(expectedPostsList, result);
        verify(leaseholderAdsDao, times(1)).getListOfPostsByStatus(status);
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getListOfgenderPreferencesByApplicationId(int)}.
     * It verifies that the method returns the correct list of gender preferences for the given application ID from the DAO.
     */
    @Test
    void testGetListOfgenderPreferencesByApplicationId_Success() {
        int applicationId = 1;
        List<String> expectedGenderPreferences = new ArrayList<>();
        expectedGenderPreferences.add("Male");
        expectedGenderPreferences.add("Female");
        when(leaseHolderGenderTableOperations.getGenderPreferencesByApplicationId(applicationId)).thenReturn(expectedGenderPreferences);

        List<String> result = leaseHolderDashboardService.getListOfgenderPreferencesByApplicationId(applicationId);

        assertEquals(expectedGenderPreferences, result);
        verify(leaseHolderGenderTableOperations, times(1)).getGenderPreferencesByApplicationId(applicationId);
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getListOfFoodPreferencesByApplicationId(int)}.
     * It verifies that the method returns the correct list of food preferences for the given application ID from the DAO.
     */
    @Test
    void testGetListOfFoodPreferencesByApplicationId_Success() {
        int applicationId = 1;
        List<String> expectedFoodPreferences = new ArrayList<>();
        expectedFoodPreferences.add("Vegetarian");
        expectedFoodPreferences.add("Non-Vegetarian");
        when(leaseHolderFoodTableOperations.getFoodPreferencesByApplicationId(applicationId)).thenReturn(expectedFoodPreferences);

        List<String> result = leaseHolderDashboardService.getListOfFoodPreferencesByApplicationId(applicationId);

        assertEquals(expectedFoodPreferences, result);
        verify(leaseHolderFoodTableOperations, times(1)).getFoodPreferencesByApplicationId(applicationId);
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getListOfImagesByApplicationId(int)}.
     * It verifies that the method returns the correct list of images for the given application ID from the DAO.
     */
    @Test
    void testGetListOfImagesByApplicationId_Success() {
        int applicationId = 1;
        List<String> expectedImages = new ArrayList<>();
        expectedImages.add("image1.jpg");
        expectedImages.add("image2.jpg");
        when(leaseHolderImagesTableOperations.getImagesByApplicationId(applicationId)).thenReturn(expectedImages);

        List<String> result = leaseHolderDashboardService.getListOfImagesByApplicationId(applicationId);

        assertEquals(expectedImages, result);
        verify(leaseHolderImagesTableOperations, times(1)).getImagesByApplicationId(applicationId);
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getPostByApplicationId(int)}.
     * It verifies that the method returns the correct post for the given application ID from the DAO.
     */
    @Test
    void testGetPostByApplicationId_Success() {
        int applicationId = 1;
        Posts expectedPost = new Posts(/* post parameters */);
        when(leaseholderAdsDao.getPostByApplicationId(applicationId)).thenReturn(expectedPost);

        Posts result = leaseHolderDashboardService.getPostByApplicationId(applicationId);

        assertEquals(expectedPost, result);
        verify(leaseholderAdsDao, times(1)).getPostByApplicationId(applicationId);
    }

    /**
     * Test case for {@link LeaseHolderDashboardService#getListOfPersonalPosts(int)}.
     * It verifies that the method returns the correct list of personal posts for the given user ID from the DAO.
     */
    @Test
    void testGetListOfPersonalPosts_Success() {
        int userID = 123;
        List<Posts> expectedPostsList = new ArrayList<>();
        expectedPostsList.add(new Posts(/* post parameters */));
        when(leaseholderAdsDao.getListOfPersonalPosts(userID)).thenReturn(expectedPostsList);

        List<Posts> result = leaseHolderDashboardService.getListOfPersonalPosts(userID);

        assertEquals(expectedPostsList, result);
        verify(leaseholderAdsDao, times(1)).getListOfPersonalPosts(userID);
    }
}
