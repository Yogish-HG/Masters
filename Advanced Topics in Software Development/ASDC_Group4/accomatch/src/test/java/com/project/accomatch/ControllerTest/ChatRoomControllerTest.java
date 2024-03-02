package com.project.accomatch.ControllerTest;

import com.project.accomatch.Exception.InvalidInputException;
import com.project.accomatch.Service.ChatRoomService;
import com.project.accomatch.Controller.ChatRoomController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ChatRoomControllerTest {

    @Mock
    private ChatRoomService chatRoomService;

    @InjectMocks
    private ChatRoomController chatRoomController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoomId() {
        int user_id = -1;
        int application_id = 0;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", user_id);
        requestBody.put("application_id", application_id);

        when(chatRoomService.getRoomId(application_id, user_id)).thenReturn(0);

        try {
            int roomId = chatRoomController.getRoomId(requestBody);
            fail("Expected InvalidInputException but got roomId: " + roomId);
        } catch (InvalidInputException e) {
        }
        verifyNoInteractions(chatRoomService);
    }
}
