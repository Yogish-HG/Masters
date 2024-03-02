package com.project.accomatch.Repository;

import com.project.accomatch.Model.LeaseHolderModel;

import java.util.List;

public interface LeaseHolderImagesTableOperationsInterface {
    public boolean addImages(LeaseHolderModel leaseHolderModel, int leaseholder_application_id);
    public List<String> getImagesByApplicationId(int applicationId);


}
