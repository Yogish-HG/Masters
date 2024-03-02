package com.project.accomatch.Repository;

import com.project.accomatch.Model.HouseSeekerModel;

import java.util.List;

public interface HouseSeekerTableOperationsInterface {
    public int createAD(HouseSeekerModel houseSeekerModel);
    public List<HouseSeekerModel> getListOfAllApplicantPosts();
}
