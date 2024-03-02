package com.project.accomatch.Controller;

import com.project.accomatch.Exception.UserNotFoundException;
import com.project.accomatch.Utlity.HasherClass;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.UserModel;
import com.project.accomatch.Service.Implementation.MailSenderClass;
import com.project.accomatch.Service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userservice;
    Logger logger = LoggerClass.getLogger();
    @Autowired
    private MailSenderClass mailSender;

    /**
     * Endpoint for user registration (signup).
     *
     * @param model The UserModel object containing user details for signup.
     * @return A string message indicating the status of the signup process.
     * @author Yogish Honnadevipura Gopalakrishna
     */
    @PostMapping("/signup")
    public String signUp(@RequestBody UserModel model){
        try{
            if(model == null){
                throw new UserNotFoundException("User cannot be null");
            }
            logger.info("signup controller active");
            return userservice.SignUp(model);
        }catch (UserNotFoundException u){
            logger.error("user not found");
            return u.getMessage();
        }
    }

    /**
     * Endpoint for user login.
     *
     * @param model The UserModel object containing user credentials for login.
     * @return A map containing authentication status and user-related data on successful login.
     * @author Yogish Honnadevipura Gopalakrishna
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserModel model){
        try{
            if(model == null){
                throw new UserNotFoundException("User cannot be null");
            }
            logger.info("Login controller active");
            return userservice.Login(model);
        }catch (UserNotFoundException u){
            logger.error("user not found");
            Map<String, String> retMap = new HashMap<>();
            retMap.put("Status", u.getMessage());
            return retMap;
        }
    }

    /**
     * Dummy endpoint to check if login is successful (for testing purposes).
     * @author Yogish Honnadevipura Gopalakrishna
     * @return A string message indicating successful login.
     */
    @GetMapping("/login")
    public String login(){
        return "Login Successful";
    }

    /**
     * Endpoint to retrieve user information by user ID.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param id The user ID whose information is to be retrieved.
     * @return The UserModel object containing user information.
     */
    @GetMapping("/get/{id}")
    public UserModel getUserInformation(@PathVariable int id){
        try{
            if(id < 1){
                throw new UserNotFoundException("User ID cannot be 0 or negative");
            }
            logger.info("get user information controller active");
            return userservice.getUserInfo(id);
        }catch (UserNotFoundException u){
            logger.error("user not found");
            return null;
        }

    }

    /**
     * Endpoint to handle the forgot password feature.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param model The UserModel object containing the user's email for password reset.
     * @return A string message indicating the status of the password reset process or an error message.
     */
    @PostMapping("/forgot/password")
    public String forgotPassword(@RequestBody UserModel model){
        try {
            if(model == null){
                throw new UserNotFoundException("User cannot be null");
            }
            System.out.println(model.getEmail());
            String mailID = model.getEmail();
            String email = HasherClass.hashEmail(mailID);
            String result = userservice.CheckEmailID(mailID);
            if(Objects.equals(result, "Success")){
                String subject = "Password Reset";
                String body = "Click on the Below link to reset your Password\n\n" +
                        "http://localhost:3000/updatepassword?email=" + email;
                mailSender.sendEmail(model.getEmail(), subject, body);
                logger.info("Mail sent to "+ model.getEmail());
                return "Mail Sent";
            }
            else{
                logger.info("Couldn't find the mail "+ model.getEmail());
                return result;
            }
        }catch (UserNotFoundException u){
            logger.error("user not found");
            return u.getMessage();
        }
            catch(Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * Endpoint to handle the password update process after the user clicks the reset password link.
     * @author Yogish Honnadevipura Gopalakrishna
     * @param model The UserModel object containing the user's email and new password.
     * @return A string message indicating the status of the password update process or an error message.
     */
    @PostMapping("/update/password")
    public String updatePassword(@RequestBody UserModel model){
        try {
            if(model == null){
                throw new UserNotFoundException("User cannot be null");
            }
            model.setEmail(HasherClass.unHashEmail(model.getEmail()));
            logger.info("Update password controller active "+ model.getEmail());
            return userservice.ForgotPassword(model);
        }catch (UserNotFoundException u){
            logger.error("user not found");
            return u.getMessage();
        }catch(Exception e){
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }
}
