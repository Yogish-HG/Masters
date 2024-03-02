package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Repository.ChatRoomOperationsInterface;
import com.project.accomatch.Service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomServiceImplementation implements ChatRoomService {
    @Autowired
    ChatRoomOperationsInterface chatRoomOperations;
    @Override
    public int getRoomId(int application_id,int user_id) {
        return chatRoomOperations.getRoomId(application_id,user_id);
    }
}
