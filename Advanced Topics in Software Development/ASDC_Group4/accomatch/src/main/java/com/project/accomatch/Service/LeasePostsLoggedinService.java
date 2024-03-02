package com.project.accomatch.Service;

import com.project.accomatch.Model.Posts;

import java.util.List;

public interface LeasePostsLoggedinService {
    public List<Posts> getListOfLoggedinApplicants(int application_id);
}
