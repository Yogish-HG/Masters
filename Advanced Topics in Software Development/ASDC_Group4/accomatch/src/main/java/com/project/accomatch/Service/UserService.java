package com.project.accomatch.Service;

import com.project.accomatch.Model.UserModel;

import java.util.Map;

public interface UserService {

        String SignUp(UserModel usermodel);

        Map<String, String> Login(UserModel usermodel);

        String ForgotPassword(UserModel usermodel);

        String CheckEmailID(String mailID);

        UserModel getUserInfo(int id);

}