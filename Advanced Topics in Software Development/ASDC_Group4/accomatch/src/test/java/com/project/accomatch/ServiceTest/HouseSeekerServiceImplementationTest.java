package com.project.accomatch.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.project.accomatch.Model.HouseSeekerModel;
import com.project.accomatch.Repository.Implementation.HouseSeekerFoodTableOperations;
import com.project.accomatch.Repository.Implementation.HouseSeekerGenderTableOperations;
import com.project.accomatch.Repository.Implementation.HouseSeekerTableOperations;
import com.project.accomatch.Service.Implementation.HouseSeekerServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HouseSeekerServiceImplementationTest {

    private HouseSeekerServiceImplementation houseSeekerService;
    private HouseSeekerTableOperations houseSeekerTableOperations;
    private HouseSeekerFoodTableOperations houseSeekerFoodTableOperations;
    private HouseSeekerGenderTableOperations houseSeekerGenderTableOperations;

    @BeforeEach
    void setUp() {
        houseSeekerTableOperations = mock(HouseSeekerTableOperations.class);
        houseSeekerFoodTableOperations = mock(HouseSeekerFoodTableOperations.class);
        houseSeekerGenderTableOperations = mock(HouseSeekerGenderTableOperations.class);

        houseSeekerService = new HouseSeekerServiceImplementation(
                houseSeekerTableOperations,
                houseSeekerFoodTableOperations,
                houseSeekerGenderTableOperations);
    }


    @Test
    void testCreateAD_Success() throws Exception {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", "1");
        requestBody.put("location_city", "Sample City");
        requestBody.put("room_type", "Single Room");
        requestBody.put("other_preferences", "Sample Preferences");
        requestBody.put("start_date", "2023-07-01");
        requestBody.put("food_preferences", new ArrayList<String>());
        requestBody.put("gender_preferences", new ArrayList<String>());

        // Mocking the houseSeekerTableOperations.createAD method to return a houseseeker_application_id
        int houseseekerApplicationId = 123;
        when(houseSeekerTableOperations.createAD(any(HouseSeekerModel.class))).thenReturn(houseseekerApplicationId);
        when(houseSeekerFoodTableOperations.createFoodReferences(any(HouseSeekerModel.class), eq(houseseekerApplicationId))).thenReturn(true);
        when(houseSeekerGenderTableOperations.createGenderReferences(any(HouseSeekerModel.class), eq(houseseekerApplicationId))).thenReturn(true);

        // Act
        String result = houseSeekerService.createAD(requestBody);

        // Assert
        assertEquals("Success", result);
        verify(houseSeekerTableOperations, times(1)).createAD(any(HouseSeekerModel.class));
        verify(houseSeekerFoodTableOperations, times(1)).createFoodReferences(any(HouseSeekerModel.class), eq(houseseekerApplicationId));
        verify(houseSeekerGenderTableOperations, times(1)).createGenderReferences(any(HouseSeekerModel.class), eq(houseseekerApplicationId));
    }

}
