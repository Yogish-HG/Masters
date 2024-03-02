package com.project.accomatch.Repository;

import com.project.accomatch.Model.Posts;

import java.sql.SQLException;
import java.util.List;

public interface LeaseHolderDashboardInterface {

    public List<Posts> getListOfPosts() throws SQLException ;
    public Posts getPostByApplicationId(int applicationId) ;

    public List<Posts> getListOfPersonalPosts(int user_id);
    public List<Posts> getListOfPostsByStatus(int status) ;
}
