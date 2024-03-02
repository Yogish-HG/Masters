package com.project.accomatch.ServiceTest;

import com.project.accomatch.Exception.ApplicantNotFound;
import com.project.accomatch.Model.Applicant;
import com.project.accomatch.Repository.Implementation.LeaseApplicationRepository;
import com.project.accomatch.Service.Implementation.LeaseApplicationImplementation;
import com.project.accomatch.Service.Implementation.LeasePostLoggedinImplementation;
import com.project.accomatch.Service.LeaseApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class LeaseApplicationImplementationTest {

    @Mock
    private LeaseApplicationRepository leaseApplicationRepository;
    @InjectMocks
    LeaseApplicationImplementation Lease;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListOfApplicants() {
        int mockId = 1;
        when(leaseApplicationRepository.getListOfApplicant(anyInt())).thenReturn(new LinkedList<>() {});
        assertEquals(new LinkedList<>(), leaseApplicationRepository.getListOfApplicant(mockId));
    }
}
