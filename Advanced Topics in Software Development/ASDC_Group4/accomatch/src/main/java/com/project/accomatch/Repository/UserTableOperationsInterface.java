package com.project.accomatch.Repository;

import com.project.accomatch.Model.UserModel;

import java.util.Map;

public interface UserTableOperationsInterface {
    public String signUpUser(UserModel model);
    public Map<String, String> LoginUser(UserModel model);
    public String CheckMailID(String Mail);
    public String ForgotPassword(UserModel model);
    public UserModel getUserInfo(int id);

}
