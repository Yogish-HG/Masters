package com.project.accomatch.Service;

import com.project.accomatch.Model.Applicant;

import java.util.List;

public interface LeaseApplicationService {
    public List<Applicant> getListOfApplicants(int application_id);
    public boolean changeStatusofApplicant(int application_id,int user_id,String status);
}
