package com.project.accomatch.ServiceTest;

import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.Implementation.AdminRepository;
import com.project.accomatch.Service.Implementation.AdminImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminImplementationTest {

    @InjectMocks
    AdminImplementation adminImplementation;
    @Mock
    AdminRepository adminRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void VerifyOneTestSuccess(){
        Posts posts = new Posts();
        when(adminRepository.verifyOneAd(any(Posts.class))).thenReturn("Success");
        assertEquals("Success", adminImplementation.VerifyOneAd(posts));
    }

    @Test
    public void VerifyOneTestFailure(){
        Posts posts = new Posts();
        when(adminRepository.verifyOneAd(any(Posts.class))).thenReturn("Failure");
        assertEquals("Failure", adminImplementation.VerifyOneAd(posts));
    }

    @Test
    public void VerifyAllTestSuccess(){
        Posts posts = new Posts();
        when(adminRepository.verifyAllAd(any(Posts.class))).thenReturn("Success");
        assertEquals("Success", adminImplementation.VerifyAllAd(posts));
    }

    @Test
    public void VerifyAllTestFailure(){
        Posts posts = new Posts();
        when(adminRepository.verifyAllAd(any(Posts.class))).thenReturn("Fail");
        assertEquals("Fail", adminImplementation.VerifyAllAd(posts));
    }
}
