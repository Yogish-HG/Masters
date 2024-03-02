package com.project.accomatch.Repository;

import com.project.accomatch.Model.ChatRoomModel;

public interface ChatRoomOperationsInterface {
    public int createChatRoom(ChatRoomModel chatRoomModel);
    public int getRoomId(int application_id,int user_id);
}
