package com.project.accomatch.Repository;

import com.project.accomatch.Model.Posts;

import java.util.List;

public interface LeasePostsLoggedinRepositoryInterface {
    public List<Posts> getListOfLoggedinApplicant(int application_id);

}
