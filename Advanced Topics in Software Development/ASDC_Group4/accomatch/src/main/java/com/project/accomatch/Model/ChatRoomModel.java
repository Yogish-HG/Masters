package com.project.accomatch.Model;

public class ChatRoomModel {
    private int user_1_id,user_2_id;

    public int getUser_1_id() {
        return user_1_id;
    }

    public void setUser_1_id(int user_1_id) {
        this.user_1_id = user_1_id;
    }

    public int getUser_2_id() {
        return user_2_id;
    }

    public void setUser_2_id(int user_2_id) {
        this.user_2_id = user_2_id;
    }

    public ChatRoomModel(int user_1_id, int user_2_id) {
        this.user_1_id = user_1_id;
        this.user_2_id = user_2_id;
    }
    public ChatRoomModel(){}
}
