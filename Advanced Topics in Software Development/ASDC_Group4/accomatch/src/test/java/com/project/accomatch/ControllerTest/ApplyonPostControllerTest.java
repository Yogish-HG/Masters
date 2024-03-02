package com.project.accomatch.ControllerTest;

import com.project.accomatch.Controller.ApplyonPostController;
import com.project.accomatch.Model.ChatRoomModel;
import com.project.accomatch.Model.LeaseHolderApplicantModel;
import com.project.accomatch.Service.ApplyonPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

public class ApplyonPostControllerTest {

    @Mock
    ApplyonPostService applyonPostService;

    @InjectMocks
    ApplyonPostController applyonPostController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testApply() throws Exception {
        LeaseHolderApplicantModel leaseHolderApplicantModel = mock(LeaseHolderApplicantModel.class);
        when(leaseHolderApplicantModel.getUser_id()).thenReturn(1);
        when(applyonPostService.apply(any(LeaseHolderApplicantModel.class), any(ChatRoomModel.class))).thenReturn("Success");
        assertEquals(applyonPostController.apply(leaseHolderApplicantModel), "Success");
    }

    @Test
    public void testIsUserApplied() throws Exception {
        Map<String, Object> mockObj = new HashMap<>();
        mockObj.put("user_id", 1);
        mockObj.put("application_id", 1);

        LeaseHolderApplicantModel leaseHolderApplicantModel = new LeaseHolderApplicantModel();
        leaseHolderApplicantModel.setUser_id((int) mockObj.get("user_id"));
        leaseHolderApplicantModel.setApplication_id((int) mockObj.get("application_id"));

        when(applyonPostService.isApplied(leaseHolderApplicantModel)).thenReturn(true);

        assertTrue(applyonPostService.isApplied(leaseHolderApplicantModel));
    }
}
