package com.project.accomatch.Repository;

import com.project.accomatch.Model.LeaseHolderModel;

public interface LeaseHolderTableOperationsInterface {
    public int createAD(LeaseHolderModel leaseHolderModel);
    public int getLeaseHolderId(int applicationId);


}
