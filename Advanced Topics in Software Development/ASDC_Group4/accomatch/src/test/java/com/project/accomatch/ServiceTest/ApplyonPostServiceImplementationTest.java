package com.project.accomatch.ServiceTest;

import com.project.accomatch.Exception.ApplicationException;
import com.project.accomatch.Model.ChatRoomModel;
import com.project.accomatch.Model.LeaseHolderApplicantModel;
import com.project.accomatch.Repository.Implementation.ApplyonPostTableOperations;
import com.project.accomatch.Repository.Implementation.ChatRoomOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderTableOperations;
import com.project.accomatch.Service.Implementation.ApplyonPostServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplyonPostServiceImplementationTest {

    @Mock
    private ApplyonPostTableOperations applyonPostTableOperations;

    @Mock
    private LeaseHolderTableOperations leaseHolderTableOperations;

    @Mock
    private ChatRoomOperations chatRoomOperations;

    @InjectMocks
    private ApplyonPostServiceImplementation applyonPostService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testApply_Successful() throws ApplicationException {
        LeaseHolderApplicantModel leaseHolderApplicantModel = new LeaseHolderApplicantModel();
        ChatRoomModel chatRoomModel = new ChatRoomModel();
        chatRoomModel.setUser_2_id(1);

        when(leaseHolderTableOperations.getLeaseHolderId(anyInt())).thenReturn(2);
        when(chatRoomOperations.createChatRoom(any(ChatRoomModel.class))).thenReturn(123);

        String result = applyonPostService.apply(leaseHolderApplicantModel, chatRoomModel);

        assertEquals("Applied successfully", result);
        assertEquals(123, leaseHolderApplicantModel.getRoom_id());
        verify(applyonPostTableOperations, times(1)).applyOnPost(leaseHolderApplicantModel);
    }

    @Test
    public void testApply_Exception() throws ApplicationException {
        LeaseHolderApplicantModel leaseHolderApplicantModel = new LeaseHolderApplicantModel();
        ChatRoomModel chatRoomModel = new ChatRoomModel();
        chatRoomModel.setUser_2_id(1);

        when(leaseHolderTableOperations.getLeaseHolderId(anyInt())).thenReturn(2);
        when(chatRoomOperations.createChatRoom(any(ChatRoomModel.class))).thenThrow(new ApplicationException("Some error"));

        assertThrows(ApplicationException.class, () -> {
            applyonPostService.apply(leaseHolderApplicantModel, chatRoomModel);
        });
        verify(applyonPostTableOperations, never()).applyOnPost(leaseHolderApplicantModel);
    }

    @Test
    public void testIsApplied_True() throws ApplicationException {
        LeaseHolderApplicantModel leaseHolderApplicantModel = new LeaseHolderApplicantModel();

        when(applyonPostTableOperations.isAlreadyApplied(any(LeaseHolderApplicantModel.class))).thenReturn(true);

        boolean result = applyonPostService.isApplied(leaseHolderApplicantModel);

        assertEquals(true, result);
        verify(applyonPostTableOperations, times(1)).isAlreadyApplied(leaseHolderApplicantModel);
    }

    @Test
    public void testIsApplied_False() throws ApplicationException {
        LeaseHolderApplicantModel leaseHolderApplicantModel = new LeaseHolderApplicantModel();

        when(applyonPostTableOperations.isAlreadyApplied(any(LeaseHolderApplicantModel.class))).thenReturn(false);

        boolean result = applyonPostService.isApplied(leaseHolderApplicantModel);

        assertEquals(false, result);
        verify(applyonPostTableOperations, times(1)).isAlreadyApplied(leaseHolderApplicantModel);
    }
}
