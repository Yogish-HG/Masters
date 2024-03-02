package com.project.accomatch.Service;

import com.project.accomatch.Model.ChatMessageModel;

import java.util.ArrayList;

public interface ChatService {
    String sendMessage(ChatMessageModel chatMessageModel);
    ArrayList<ChatMessageModel> getMessages(int room_id);
}
