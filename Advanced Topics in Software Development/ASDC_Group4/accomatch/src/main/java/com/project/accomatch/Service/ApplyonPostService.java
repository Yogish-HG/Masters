package com.project.accomatch.Service;

import com.project.accomatch.Model.ChatRoomModel;
import com.project.accomatch.Model.LeaseHolderApplicantModel;

public interface ApplyonPostService {
    String apply(LeaseHolderApplicantModel leaseHolderApplicantModel, ChatRoomModel chatRoomModel);
    boolean isApplied(LeaseHolderApplicantModel leaseHolderApplicantModel);
}
