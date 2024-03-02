package com.project.accomatch.Service;

import com.project.accomatch.Model.Posts;

import java.util.List;

public interface DashboardInterface {
    public List<Posts> getListOfPosts();

    public List<Posts> getListOfPostsByStatus(int status);

    public List<String> getListOfgenderPreferencesByApplicationId(int applicantionId);
    public List<String> getListOfFoodPreferencesByApplicationId(int applicantionId);
    public List<String> getListOfImagesByApplicationId(int applicantionId);
    public Posts getPostByApplicationId(int applicationId);
    public List<Posts> getListOfPersonalPosts(int userID);
}
