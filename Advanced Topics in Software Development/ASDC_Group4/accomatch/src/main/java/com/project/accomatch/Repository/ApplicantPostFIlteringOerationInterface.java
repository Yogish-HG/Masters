package com.project.accomatch.Repository;

import com.project.accomatch.Model.Posts;

import java.util.List;

public interface ApplicantPostFIlteringOerationInterface {
    public List<Posts> filterPosts(String[] gp, String[] fp, String age, String rt);

}
