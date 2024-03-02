package com.project.accomatch.ServiceTest;

import com.project.accomatch.Model.ChatMessageModel;
import com.project.accomatch.Repository.ChatOperationsInterface;
import com.project.accomatch.Service.Implementation.ChatServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChatServiceImplementationTest {

    @Mock
    private ChatOperationsInterface chatOperations;

    @InjectMocks
    private ChatServiceImplementation chatService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMessage_Success() {
        ChatMessageModel chatMessageModel = new ChatMessageModel("hello", 1, 2,new Timestamp(4));
        when(chatOperations.createMessage(any(ChatMessageModel.class))).thenReturn("Message sent successfully");
        String result = chatService.sendMessage(chatMessageModel);
        assertEquals("Message sent successfully", result);
        verify(chatOperations, times(1)).createMessage(chatMessageModel);
    }

    @Test
    public void testGetMessages_Success() {
        int roomId = 1;
        ArrayList<ChatMessageModel> messages = new ArrayList<>();
        messages.add(new ChatMessageModel("Hello", roomId, 1,new Timestamp(3)));
        messages.add(new ChatMessageModel("Hi", roomId, 2,new Timestamp(3)));
        when(chatOperations.getMessages(roomId)).thenReturn(messages);
        ArrayList<ChatMessageModel> result = chatService.getMessages(roomId);
        assertEquals(messages, result);
        verify(chatOperations, times(1)).getMessages(roomId);
    }
}
