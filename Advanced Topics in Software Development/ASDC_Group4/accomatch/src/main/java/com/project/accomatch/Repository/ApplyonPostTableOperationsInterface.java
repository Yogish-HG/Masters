package com.project.accomatch.Repository;

import com.project.accomatch.Model.LeaseHolderApplicantModel;

public interface ApplyonPostTableOperationsInterface {
    public void applyOnPost(LeaseHolderApplicantModel leaseHolderApplicantModel);
    public boolean isAlreadyApplied(LeaseHolderApplicantModel leaseHolderApplicantModel);

}
