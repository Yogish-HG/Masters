package com.project.accomatch.ControllerTest;

import com.project.accomatch.Controller.AdminController;
import com.project.accomatch.Exception.InvalidPostStatusException;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Service.AdminInterface;
import com.project.accomatch.Service.DashboardInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AdminControllerTest {

    @Mock
    private DashboardInterface dashboardService;
    @Mock
    AdminInterface adminInterface;
    @InjectMocks
    AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    public void testVerifySingleAdSuccess() {
        // Mock the input data
        Map<String, String> posts = new HashMap<>();
        posts.put("isVerified", "1");
        posts.put("leaseholderApplicationId", "123");

        // Mock the behavior of adminInterface.VerifyOneAd()
        when(adminInterface.VerifyOneAd(any(Posts.class))).thenReturn("Success");

        // Call the API method
        String result = adminController.verifySingleAd(posts);

        // Verify that the adminInterface.VerifyOneAd() method was called with the expected Posts object
        verify(adminInterface).VerifyOneAd(argThat(postss -> Integer.parseInt(posts.get("isVerified")) == 1 && Integer.parseInt(posts.get("LeaseholderApplicationId")) == 123));

        // Assert the result is as expected
        assertEquals("Success", result);
    }

    @Test
    public void testVerifySingleAdNullInput() {
        // Mock the input data as null
        Map<String, String> posts = null;

        // Call the API method
        String result = adminController.verifySingleAd(posts);


        // Assert the result is as expected
        assertEquals("Posts object cannot be null", result);
    }

    @Test
    public void verifyAllTestSuccess(){
        Posts posts = mock(Posts.class);
        when(adminInterface.VerifyAllAd(posts)).thenReturn("Success");
        assertEquals("Success", adminController.verifyAllAd(posts));
        verify(adminInterface, times(1)).VerifyAllAd(posts);
    }

    @Test
    public void verifyAllTestFailure(){
        Posts posts = mock(Posts.class);
        when(adminInterface.VerifyAllAd(posts)).thenReturn("Fail");
        assertEquals("Fail", adminController.verifyAllAd(posts));
        verify(adminInterface, times(1)).VerifyAllAd(posts);
    }

    @Test
    void testGetListOfPosts_Success() {
        // Arrange
        List<Posts> expectedPostsList = Arrays.asList(new Posts());
        when(dashboardService.getListOfPosts()).thenReturn(expectedPostsList);

        // Act
        List<Posts> result = adminController.getListOfPosts();

        // Assert
        assertEquals(expectedPostsList, result);
        verify(dashboardService, times(1)).getListOfPosts();
    }

    @Test
    void testGetListOfPostsByStatus_Success() {
        // Arrange
        int status = 1;
        HashMap<String, String> map = new HashMap<>();
        map.put("status", Integer.toString(status));
        List<Posts> expectedPostsList = Arrays.asList(new Posts());
        when(dashboardService.getListOfPostsByStatus(status)).thenReturn(expectedPostsList);

        // Act
        List<Posts> result = adminController.getListOfPostsByStatus(map);

        // Assert
        assertEquals(expectedPostsList, result);
        verify(dashboardService, times(1)).getListOfPostsByStatus(status);
    }

    @Test
    void testGetListOfPostsByStatus_InvalidPostStatusException() {
        // Arrange
        String invalidStatus = "invalid_status";
        HashMap<String, String> map = new HashMap<>();
        map.put("status", invalidStatus);

        // Act & Assert
        assertThrows(InvalidPostStatusException.class, () -> adminController.getListOfPostsByStatus(map));
        verifyNoInteractions(dashboardService);
    }
}
