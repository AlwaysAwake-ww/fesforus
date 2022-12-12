package com.festivalP.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.festivalP.demo.form.ChatMessage;
import com.festivalP.demo.form.ChatRoom;
import com.festivalP.demo.service.ChatService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, @NotNull TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println(payload);



        // 전달받은 Message의 payload를 ObjectMapper 통해 ChatMessage.class 로 변환
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        // JSON 의 roomId를 통해 ChatRoom 찾음
        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());

        // chatRoom 의 handlerActions 을 통해 처음 참여 시 세션 연결, 아닐 시 메세지 전송 
        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}

