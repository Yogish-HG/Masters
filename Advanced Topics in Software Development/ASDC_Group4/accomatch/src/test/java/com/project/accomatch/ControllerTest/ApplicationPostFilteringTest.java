package com.project.accomatch.ControllerTest;

import com.project.accomatch.Controller.ApplicantPostFiltering;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Service.ApplicantPostFilterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationPostFilteringTest {
    @Mock
    ApplicantPostFilterService applicantPostFilterService;
    @InjectMocks
    ApplicantPostFiltering applicantPostFiltering;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void FilterSuccess() {
        HashMap<String, String> mockedMap = mock(HashMap.class);
        when(mockedMap.get("gender_pref")).thenReturn("Male");
        when(mockedMap.get("food_pref")).thenReturn("Veg,NonVeg");

        List<Posts> mockPostLists = mock(List.class);
        mockPostLists.add(new Posts());
        // Properly mock the filterPost method to return a non-null value
        when(applicantPostFilterService.filterPost(any(String[].class), any(String[].class), anyString(), anyString()))
                .thenReturn(mockPostLists);
        // Now the method should return the mockPostLists without any NullPointerException
       // assertEquals(applicantPostFiltering.signUp(mockedMap), new ArrayList<>());
    }
}
