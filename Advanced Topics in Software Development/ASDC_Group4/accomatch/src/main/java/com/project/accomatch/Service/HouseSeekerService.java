package com.project.accomatch.Service;

import com.project.accomatch.Model.HouseSeekerModel;


import java.util.List;
import java.util.Map;

public interface HouseSeekerService {
    String createAD(Map<String, Object> requestBody);

    List<HouseSeekerModel> getListOfAllApplicantPosts();
}
