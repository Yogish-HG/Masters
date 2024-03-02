package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Exception.ApplicationException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.ChatRoomModel;
import com.project.accomatch.Model.LeaseHolderApplicantModel;
import com.project.accomatch.Repository.ApplyonPostTableOperationsInterface;
import com.project.accomatch.Repository.ChatRoomOperationsInterface;
import com.project.accomatch.Repository.Implementation.ApplyonPostTableOperations;
import com.project.accomatch.Repository.Implementation.ChatRoomOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderTableOperations;
import com.project.accomatch.Repository.LeaseHolderTableOperationsInterface;
import com.project.accomatch.Service.ApplyonPostService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyonPostServiceImplementation implements ApplyonPostService {
    Logger logger = LoggerClass.getLogger();
    @Autowired
    ApplyonPostTableOperationsInterface applyonPostTableOperations;
    @Autowired
    LeaseHolderTableOperationsInterface leaseHolderTableOperations;
    @Autowired
    ChatRoomOperationsInterface chatRoomOperations;

    /**
     * Applies to a post and creates a chat room for communication.
     *
     * @param leaseHolderApplicantModel The LeaseHolderApplicantModel containing application details.
     * @param chatRoomModel             The ChatRoomModel containing chat room details.
     * @return A message indicating the successful application.
     * @throws ApplicationException If there is an issue while applying or creating a chat room.
     */
    @Override
    public String apply(LeaseHolderApplicantModel leaseHolderApplicantModel, ChatRoomModel chatRoomModel) throws ApplicationException {
        try {
            int leaseHolderId = leaseHolderTableOperations.getLeaseHolderId(leaseHolderApplicantModel.getApplication_id());
            chatRoomModel.setUser_1_id(leaseHolderId);
            int roomId = chatRoomOperations.createChatRoom(chatRoomModel);
            leaseHolderApplicantModel.setRoom_id(roomId);
            applyonPostTableOperations.applyOnPost(leaseHolderApplicantModel);
            logger.info("Applying to a post for postId:{} and userID: {}",leaseHolderApplicantModel.getApplication_id(),leaseHolderApplicantModel.getUser_id());
            return "Applied successfully";
        } catch (Exception e) {
            String errorMessage = "Error while applying to the post: " + e.getMessage();
             logger.error(errorMessage, e);
            throw new ApplicationException(errorMessage, e);
        }
    }

    /**
     * Checks if the application has already been submitted.
     *
     * @param leaseHolderApplicantModel The LeaseHolderApplicantModel containing application details.
     * @return True if the application has already been submitted, false otherwise.
     * @throws ApplicationException If there is an issue while checking the application status.
     */
    @Override
    public boolean isApplied(LeaseHolderApplicantModel leaseHolderApplicantModel) throws ApplicationException {
        try {
            return applyonPostTableOperations.isAlreadyApplied(leaseHolderApplicantModel);
        } catch (Exception e) {
            String errorMessage = "Error while checking application status: " + e.getMessage();
            logger.error(errorMessage, e);
            throw new ApplicationException(errorMessage, e);
        }
    }
}
