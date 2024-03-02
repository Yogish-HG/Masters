package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Service.HouseSeekerService;
import com.project.accomatch.Service.LeaseHolderService;
import com.project.accomatch.Utlity.ResponseStatus;
import com.project.accomatch.Utlity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateApplicationFactory {

    @Autowired
    LeaseHolderService leaseHolderService;

    @Autowired
    HouseSeekerService houseSeekerService;

    public String createAD(Map<String, Object> requestBody) {
        try {
            String userTypeStr = (String) requestBody.get("type");
            UserType userType = UserType.fromString(userTypeStr);

            if (userType.equals(UserType.AP)) {
                houseSeekerService.createAD(requestBody);
            } else if (userType.equals(UserType.LH)) {
                leaseHolderService.createAD(requestBody);
            }

            return ResponseStatus.SUCCESS.getMessage();
        } catch (Exception e) {
            return ResponseStatus.ERROR.getMessage();
        }
    }



}

