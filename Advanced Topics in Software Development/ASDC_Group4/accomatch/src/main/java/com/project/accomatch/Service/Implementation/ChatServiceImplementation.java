package com.project.accomatch.Service.Implementation;

import com.project.accomatch.Model.ChatMessageModel;
import com.project.accomatch.Repository.ChatOperationsInterface;
import com.project.accomatch.Repository.Implementation.ChatOperations;
import com.project.accomatch.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ChatServiceImplementation implements ChatService {
    @Autowired
    ChatOperationsInterface chatOperations;
    @Override
    public String sendMessage(ChatMessageModel chatMessageModel) {
        return chatOperations.createMessage(chatMessageModel);
    }

    @Override
    public ArrayList<ChatMessageModel> getMessages(int room_id) {
        return chatOperations.getMessages(room_id);
    }
}
