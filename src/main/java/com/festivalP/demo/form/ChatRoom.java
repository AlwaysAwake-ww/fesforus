package com.festivalP.demo.form;


import com.festivalP.demo.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {

    private String roomId;
    private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        

        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            // chatMessage 의 mMessageType 이 'ENTER' 라면
            // Session 을 관리하는 HashSet 의 sessions 에 add(session)을 통해 해당 세션 추가
            // 중복 방지 위해 SET사용?
            sessions.add(session);

            // 전달받은 chatMessage의 Message를 sender+님이 입장했습니다. 로 변경
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        // 메세지 전송
        sendMessage(chatMessage, chatService);
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session, message));
    }


}
