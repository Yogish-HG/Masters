package com.project.accomatch.ControllerTest;


import com.project.accomatch.Controller.ChatController;
import com.project.accomatch.Exception.ChatMessageException;
import com.project.accomatch.Model.ChatMessageModel;
import com.project.accomatch.Service.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @InjectMocks
    private ChatController chatController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", 1);
        requestBody.put("room_id", 2);
        requestBody.put("message", "Hello");

        when(chatService.sendMessage(any(ChatMessageModel.class))).thenThrow(new ChatMessageException("Error"));

        assertThrows(ChatMessageException.class, () -> {
            chatController.sendMessage(requestBody);
        });

        verify(chatService, times(1)).sendMessage(any(ChatMessageModel.class));
    }

    @Test
    public void testGetMessages() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("room_id", 2);

        when(chatService.getMessages(anyInt())).thenThrow(new ChatMessageException("Error"));

        assertThrows(ChatMessageException.class, () -> {
            chatController.getMessages(requestBody.size());
        });

        verify(chatService, times(1)).getMessages(anyInt());
    }
}
