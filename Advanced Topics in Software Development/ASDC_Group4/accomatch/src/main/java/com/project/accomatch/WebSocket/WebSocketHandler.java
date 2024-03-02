package com.project.accomatch.WebSocket;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin("http://localhost:3000")
public class WebSocketHandler extends TextWebSocketHandler {
        private final Map<WebSocketSession, Integer> sessions = new HashMap<>();
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                int room_id= extractRoomId(session);
                sessions.put(session,room_id);

        }

        @Override
        public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
                int room_id = extractRoomId(session);
                for(Map.Entry<WebSocketSession,Integer> entry: sessions.entrySet()){
                        if(room_id== entry.getValue() && session!= entry.getKey()) {
                                entry.getKey().sendMessage(message);
                        }
                }
        }
        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        }
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                sessions.remove(session);
        }

        @Override
        public boolean supportsPartialMessages() {
            return false;
        }

        int extractRoomId(WebSocketSession session) {
                String uri = Objects.requireNonNull(session.getUri()).getPath();
                return Integer.parseInt(uri.substring(uri.lastIndexOf('/') + 1));
        }
}
