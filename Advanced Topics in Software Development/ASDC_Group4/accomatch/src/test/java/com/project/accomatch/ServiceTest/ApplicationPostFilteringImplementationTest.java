package com.project.accomatch.ServiceTest;

import com.project.accomatch.Repository.Implementation.ApplicantPostFilteringOperation;
import com.project.accomatch.Service.Implementation.ApplicantPostFilterImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ApplicationPostFilteringImplementationTest {

    @InjectMocks
    ApplicantPostFilterImplementation applicantPostFilterImplementation;

    @Mock
    ApplicantPostFilteringOperation applicantPostFilteringOperation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void FilterPost() {
        String[] a = {"Mock1", "Mock2"};
        String ab = "MOck";
        when(applicantPostFilteringOperation.filterPosts(any(String[].class), any(String[].class), any(String.class), any(String.class))).thenReturn(new ArrayList<>());
        assertEquals(applicantPostFilterImplementation.filterPost(a, a, ab, ab), new ArrayList<>());
    }
}
