package com.project.accomatch.Service;

import com.project.accomatch.Model.Posts;

import java.util.List;

public interface ApplicantPostFilterService {

    List<Posts> filterPost(String[] gp, String[] fp, String age, String rt);
}
