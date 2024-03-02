package com.project.accomatch.Controller;

import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Posts;
import com.project.accomatch.Service.LeasePostsLoggedinService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/api/leaseholder/loggedinapplicant")
public class LeasePostsAppliedPostsController {
    @Autowired
    public LeasePostsLoggedinService applicantService;

    Logger logger = LoggerClass.getLogger();

    /**
     * This api will provide the user all the posts, user has applied already
     * @author -- Gowri Kanagaraj
     * @param user_id-- userId of the user
     * @return -- List of Posts on which the user has applied
     */
    @GetMapping("/get/list/applicant/{user_id}")
    public List<Posts> getListofAppliedPosts(@PathVariable int user_id) {
        if (user_id <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }
        logger.info("Lease Post logged in controller active");
        return applicantService.getListOfLoggedinApplicants(user_id);
    }

}
