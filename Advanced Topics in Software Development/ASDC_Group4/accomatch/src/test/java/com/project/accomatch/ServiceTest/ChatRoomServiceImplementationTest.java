package com.project.accomatch.ServiceTest;

import com.project.accomatch.Repository.ChatRoomOperationsInterface;
import com.project.accomatch.Service.Implementation.ChatRoomServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ChatRoomServiceImplementationTest {

    @Mock
    private ChatRoomOperationsInterface chatRoomOperations;

    @InjectMocks
    private ChatRoomServiceImplementation chatRoomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRoomId_Success() {
        int application_id = 1;
        int user_id = 2;
        int expectedRoomId = 123;

        when(chatRoomOperations.getRoomId(application_id, user_id)).thenReturn(expectedRoomId);

        int actualRoomId = chatRoomService.getRoomId(application_id, user_id);

        assertEquals(expectedRoomId, actualRoomId);
        verify(chatRoomOperations, times(1)).getRoomId(application_id, user_id);
    }

    @Test
    public void testGetRoomId_Exception() {
        int application_id = 1;
        int user_id = 2;

        when(chatRoomOperations.getRoomId(application_id, user_id)).thenThrow(new RuntimeException("Error"));

        try {
            chatRoomService.getRoomId(application_id, user_id);
        } catch (RuntimeException e) {
            assertEquals("Error", e.getMessage());
        }

        verify(chatRoomOperations, times(1)).getRoomId(application_id, user_id);
    }
}
