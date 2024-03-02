package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Exception.ApplicantNotFound;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.Applicant;
import com.project.accomatch.Repository.LeaseApplicationRepositoryInterface;
import com.project.accomatch.Service.LeaseApplicationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LeaseApplicationImplementation implements LeaseApplicationService {

    @Autowired
    LeaseApplicationRepositoryInterface leaseApplcationRepository;
    Logger logger = LoggerClass.getLogger();
    
    public List<Applicant> getListOfApplicants(int application_id) {
        try {
            return leaseApplcationRepository.getListOfApplicant(application_id);
        }
        catch (Exception e) {
            logger.error("Error while retrieving list of applicants", e.getMessage(), e);
            throw new ApplicantNotFound("Failed to retrieve the list of Applicants.");
        }
    }

    @Override
    public boolean changeStatusofApplicant(int application_id, int user_id, String status) {
        try{
            return leaseApplcationRepository.changeStatusofApplication(application_id,user_id,status);
        } catch (Exception e){
            logger.error("Error while updating Status", e.getMessage(), e);
            throw new ApplicantNotFound("Failed to update the date");
        }
    }

}
