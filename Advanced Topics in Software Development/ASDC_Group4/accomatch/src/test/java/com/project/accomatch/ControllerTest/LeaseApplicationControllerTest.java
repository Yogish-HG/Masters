package com.project.accomatch.ControllerTest;

import com.project.accomatch.Controller.LeaseApplicationController;
import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.Model.Applicant;
import com.project.accomatch.Service.Implementation.MailSenderClass;
import com.project.accomatch.Service.LeaseApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaseApplicationControllerTest {

    private LeaseApplicationService applicantService;
    private LeaseApplicationController leaseApplicationController;
    MailSenderClass mailSenderClass2 = mock(MailSenderClass.class);

    @BeforeEach
    void setUp() {
        applicantService = mock(LeaseApplicationService.class);
        leaseApplicationController = new LeaseApplicationController();
        leaseApplicationController.applicantService = applicantService;
        leaseApplicationController.mailSenderClass = mailSenderClass2;
    }

    @Test
    void testGetListOfPosts() {
        List<Applicant> applicantList = new ArrayList<>();
        applicantList.add(new Applicant(2,"dhrumil","dhrumil.gosaliya@gmail.com",25,"male","1234457894"));
        when(applicantService.getListOfApplicants(anyInt())).thenReturn(applicantList);
        List<Applicant> result = leaseApplicationController.getListofApplicants(1);
        verify(applicantService, times(1)).getListOfApplicants(1);
        assertEquals(applicantList, result);
    }

    @Test
    void testGetListOfPostsWithInvalidApplicationId() {
        int invalidApplicationId = -1;
        assertThrows(InvalidInputException.class,
                () -> leaseApplicationController.getListofApplicants(invalidApplicationId));
    }

    @Test
    void testChangeStatusofApplication() {
        int applicationId = 1;
        int userId = 123;
        String status = "approved";

        // Prepare the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("application_id", applicationId);
        requestBody.put("user_id", userId);
        requestBody.put("status", status);
        doNothing().when(mailSenderClass2).sendEmail(any(String.class), any(String.class), anyString());

        // Mock the service method
        when(applicantService.changeStatusofApplicant(applicationId, userId, status)).thenReturn(true);

        // Perform the test
        boolean result = leaseApplicationController.chanegStatusofApplication(requestBody);

        // Verify the method was called once with the correct parameters
        verify(applicantService, times(1)).changeStatusofApplicant(applicationId, userId, status);

        // Check the result
        assertTrue(result);
    }

    @Test
    void testChangeStatusofApplicationWithInvalidInput() {
        // Prepare an invalid request body with negative IDs
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("application_id", -1);
        requestBody.put("user_id", -1);
        requestBody.put("status", "pending");

        // Perform the test and assert that it throws an InvalidInputException
        assertThrows(InvalidInputException.class, () -> leaseApplicationController.chanegStatusofApplication(requestBody));

        // Verify that the service method was not called with invalid input
        verify(applicantService, never()).changeStatusofApplicant(anyInt(), anyInt(), anyString());
    }
}
