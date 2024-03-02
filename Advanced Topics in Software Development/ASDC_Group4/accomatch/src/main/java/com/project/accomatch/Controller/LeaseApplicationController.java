package com.project.accomatch.Controller;

import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Applicant;
import com.project.accomatch.Service.Implementation.MailSenderClass;
import com.project.accomatch.Service.LeaseApplicationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/leaseholder/applicant")
public class LeaseApplicationController {
    @Autowired
    public LeaseApplicationService applicantService;

    @Autowired
    public MailSenderClass mailSenderClass;
    Logger logger = LoggerClass.getLogger();

    /**
     * This api will give list of applicants on the Post
     * @author -- Gowri kanagaraj
     * @param application_id -- Id of the Application
     * @return -- List of all applicants
     */
    @GetMapping("/get/list/applicant/{application_id}")
    public List<Applicant> getListofApplicants(@PathVariable int application_id) {
        if (application_id <= 0) {
            throw new InvalidInputException("Invalid application ID provided.");
        }
        logger.info("Lease Applicant controller active");
        return applicantService.getListOfApplicants(application_id);
    }

    /**
     * This api will be used to change the status of the applicant on any leaseholder application
     * @author -- Bhargav kanodiya
     * @param requestBody-- Body of the Request
     * @return -- true if status has been changed successfully
     */
    @PostMapping("/changeStatus")
    public boolean chanegStatusofApplication(@RequestBody Map<String,Object> requestBody){
        int application_id= (Integer) requestBody.get("application_id");
        int user_id = (Integer) requestBody.get("user_id");
        String status = (String) requestBody.get("status");
        String email = (String) requestBody.get("email");
        if(application_id<=0 || user_id<=0){
            throw new InvalidInputException("Invalid application Id or user Id provided.");
        }
        String Subject = "Status Change";
        String Body = "Your status of your Application id "+application_id+" has been changed to "+status;
        mailSenderClass.sendEmail( email, Subject, Body);
        logger.info("Lease Applicant Controller Active");
        return applicantService.changeStatusofApplicant(application_id,user_id,status);
    }

}
