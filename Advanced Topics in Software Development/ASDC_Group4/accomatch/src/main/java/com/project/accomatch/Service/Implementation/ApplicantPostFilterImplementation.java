package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Model.Posts;
import com.project.accomatch.Repository.ApplicantPostFIlteringOerationInterface;
import com.project.accomatch.Repository.Implementation.ApplicantPostFilteringOperation;
import com.project.accomatch.Service.ApplicantPostFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantPostFilterImplementation implements ApplicantPostFilterService {


    /**
     * Filters the advertisement posts based on the applicant's preferences (gender preference, food preference,
     * age, and room type).
     * @author Yogish Honnadevipura Gopalakrishna
     * @param gp   An array of Strings representing the applicant's gender preferences.
     * @param fp   An array of Strings representing the applicant's food preferences.
     * @param age  The applicant's age as a String.
     * @param rt   The applicant's preferred room type as a String.
     * @return A list of Posts objects representing the filtered advertisement posts matching the applicant's preferences.
     */
    @Autowired
    ApplicantPostFIlteringOerationInterface applicantPostFilteringOperation;
    @Override
    public List<Posts> filterPost(String[] gp, String[] fp, String age, String rt) {
        return applicantPostFilteringOperation.filterPosts(gp, fp, age, rt);
    }
}
