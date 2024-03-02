package com.project.accomatch.Controller;

import com.project.accomatch.Exception.ChatMessageException;
import com.project.accomatch.LoggerPack.LoggerClass;
import com.project.accomatch.Model.ChatMessageModel;
import com.project.accomatch.Service.ChatService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    Logger logger = LoggerClass.getLogger();

    /**
     * Endpoint to send a chat message.
     * @author Bhargav Kanodiya
     * @param requestBody JSON object containing user_id, room_id, and message.
     * @return A success message or an error message.
     * @throws ChatMessageException if an error occurs while sending the chat message.
     */
    @PostMapping("/send")
    public String sendMessage(@RequestBody Map<String, Object> requestBody) {
        try {
            int user_id = (Integer) requestBody.get("user_id");
            int room_id = (Integer) requestBody.get("room_id");
            String message = (String) requestBody.get("message");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            ChatMessageModel chatMessageModel = new ChatMessageModel(message, room_id, user_id, currentTimestamp);
            return chatService.sendMessage(chatMessageModel);
        } catch (Exception e) {
            logger.error("Error while sending a chat message: {}", e.getMessage());
            throw new ChatMessageException("Error while sending a chat message: " + e.getMessage());
        }
    }

    /**
     * Endpoint to get all chat messages for a specific room.
     * @author Bhargav Kanodiya
     * @param room_id -- RoomId to get the messages of room
     * @return An ArrayList of ChatMessageModel containing chat messages for the specified room.
     * @throws ChatMessageException if an error occurs while fetching chat messages.
     */
    @GetMapping("/get/{room_id}")
    public ArrayList<ChatMessageModel> getMessages(@PathVariable int room_id) {
        try {
            logger.info("Fetching chat messages for room_id: {}", room_id);
            return chatService.getMessages(room_id);
        } catch (Exception e) {
            logger.error("Error while fetching chat messages: {}", e.getMessage());
            throw new ChatMessageException("Error while fetching chat messages: " + e.getMessage());
        }
    }

}
