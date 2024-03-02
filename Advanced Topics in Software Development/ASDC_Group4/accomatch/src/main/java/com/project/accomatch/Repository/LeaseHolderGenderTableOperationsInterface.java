package com.project.accomatch.Repository;

import com.project.accomatch.Model.LeaseHolderModel;

import java.util.List;

public interface LeaseHolderGenderTableOperationsInterface {
    public boolean createGenderReferences(LeaseHolderModel leaseHolderModel, int leaseholder_application_id);
    public List<String> getGenderPreferencesByApplicationId(int applicationId);

}
