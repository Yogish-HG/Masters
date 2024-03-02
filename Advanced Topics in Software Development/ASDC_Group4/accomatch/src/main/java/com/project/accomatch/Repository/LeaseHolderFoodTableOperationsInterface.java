package com.project.accomatch.Repository;

import com.project.accomatch.Model.LeaseHolderModel;

import java.util.List;

public interface LeaseHolderFoodTableOperationsInterface {
    public boolean createFoodReferences(LeaseHolderModel leaseHolderModel, int leaseholder_application_id);
    public List<String> getFoodPreferencesByApplicationId(int applicationId);

}
