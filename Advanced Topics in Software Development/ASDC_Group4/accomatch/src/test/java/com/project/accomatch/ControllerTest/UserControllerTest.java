package com.project.accomatch.ControllerTest;

import com.project.accomatch.Controller.UserController;
import com.project.accomatch.Model.UserModel;
import com.project.accomatch.Service.Implementation.MailSenderClass;
import com.project.accomatch.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    UserService userService;
    @Mock
    MailSenderClass mailSenderClass;

    @InjectMocks
    UserController usercontroller;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUpSuccess() {
        when(userService.SignUp(any(UserModel.class))).thenReturn("user added successfully");
        UserModel userModel = new UserModel();
        assertEquals(usercontroller.signUp(userModel), "user added successfully");
        verify(userService, times(1)).SignUp(userModel);
    }

    @Test
    public void testSignUpFailure() {
        when(userService.SignUp(any(UserModel.class))).thenReturn("Error occurred");
        UserModel userModel = new UserModel();
        assertEquals(usercontroller.signUp(userModel), "Error occurred");
        verify(userService, times(1)).SignUp(userModel);
    }
    @Test
    public void testLoginSuccess() {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("Status", "Success");
        when(userService.Login(any(UserModel.class))).thenReturn(returnMap);
        UserModel userModel = new UserModel();
        Map<String, String> result = usercontroller.login(userModel);
        assertEquals("Success", result.get("Status"));
        verify(userService, times(1)).Login(userModel);
    }

    @Test
    public void testLoginFailure() {
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("Status", "Failure");
        when(userService.Login(any(UserModel.class))).thenReturn(returnMap);
        UserModel userModel = new UserModel();
        Map<String, String> result = usercontroller.login(userModel);
        assertEquals("Failure", result.get("Status"));
        verify(userService, times(1)).Login(userModel);
    }

    @Test
    void forgotPassword_whenEmailExists_shouldReturnMailSent() {
        UserModel userModel = mock(UserModel.class);
        when(userModel.getEmail()).thenReturn("abcd");
        doNothing().when(mailSenderClass).sendEmail(anyString(), anyString(), anyString());
        when(userService.CheckEmailID(anyString())).thenReturn("Success");
        assertEquals(usercontroller.forgotPassword(userModel), "Mail Sent");
    }

    @Test
    void forgotPassword_whenEmailDoesNotExist_shouldReturnErrorMessage() {
        UserModel userModel = mock(UserModel.class);
        when(userModel.getEmail()).thenReturn("abcd");
        doNothing().when(mailSenderClass).sendEmail(anyString(), anyString(), anyString());
        when(userService.CheckEmailID(anyString())).thenReturn("ID doesnt Exist");
        assertEquals(usercontroller.forgotPassword(userModel), "ID doesnt Exist");
    }
    @Test
    void updatePassword_whenCalled_shouldReturnResultFromService() {
        when(userService.ForgotPassword(any(UserModel.class))).thenReturn("Password Updated");
        UserModel userModel = mock(UserModel.class);
        assertEquals(usercontroller.updatePassword(userModel), "Password Updated");
        verify(userService, times(1)).ForgotPassword(userModel);
    }

    @Test
    public void getUserInfoTest(){
        int testData = 1;
        UserModel userModel = new UserModel();
        when(userService.getUserInfo(anyInt())).thenReturn(userModel);
        assertEquals(userModel, usercontroller.getUserInformation(testData));
        verify(userService, times(1)).getUserInfo(testData);
    }

}

