package com.project.accomatch.ServiceTest;

import com.project.accomatch.Exception.ApplicantNotFound;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.Implementation.LeasePostsLoggedinRepository;
import com.project.accomatch.Service.Implementation.LeasePostLoggedinImplementation;
import com.project.accomatch.Service.LeasePostsLoggedinService;
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

class LeasePostLoggedinImplementationTest {

    @Mock
    LeasePostsLoggedinRepository leasePostsLoggedinRepository;

    @InjectMocks
    LeasePostLoggedinImplementation Lease;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListOfLoggedinApplicants() {
        int mockId = 1;
        when(leasePostsLoggedinRepository.getListOfLoggedinApplicant(anyInt())).thenReturn(new LinkedList<>());
        assertEquals(new LinkedList<>(), Lease.getListOfLoggedinApplicants(mockId));
    }
}
