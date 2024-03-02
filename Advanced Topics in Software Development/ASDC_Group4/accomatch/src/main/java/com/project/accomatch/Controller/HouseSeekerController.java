package com.project.accomatch.Controller;

import com.project.accomatch.Model.HouseSeekerModel;
import com.project.accomatch.Service.HouseSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/houseSeeker")
public class HouseSeekerController {
    @Autowired
    private HouseSeekerService houseSeekerService;

    /**
     *
     * Retrieves a list of all applicant posts.
     * @auther Dhrumil Vimalbhai Gosaliya
     * @return list of HouseSeekerModel objects representing the applicant posts.
     */
    @GetMapping("/getListOfAllApplicantPosts")
    public List<HouseSeekerModel> getListOfAllApplicantPosts(){
        return houseSeekerService.getListOfAllApplicantPosts();
    }
}
