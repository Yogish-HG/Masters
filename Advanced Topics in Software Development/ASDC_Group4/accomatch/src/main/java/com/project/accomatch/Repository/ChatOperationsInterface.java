package com.project.accomatch.Repository;

import com.project.accomatch.Model.ChatMessageModel;

import java.util.ArrayList;

public interface ChatOperationsInterface {
    public String createMessage(ChatMessageModel chatMessageModel);
    public ArrayList<ChatMessageModel> getMessages(int room_id);

}
