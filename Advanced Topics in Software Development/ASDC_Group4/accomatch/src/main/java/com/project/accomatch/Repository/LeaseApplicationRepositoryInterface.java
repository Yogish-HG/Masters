package com.project.accomatch.Repository;

import com.project.accomatch.Model.Applicant;

import java.util.List;

public interface LeaseApplicationRepositoryInterface {
    public List<Applicant> getListOfApplicant(int application_id);
    public boolean changeStatusofApplication(int application_id,int user_id,String status);

}
