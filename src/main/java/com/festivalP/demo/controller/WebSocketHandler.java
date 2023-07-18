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



        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());

        chatRoom.handlerActions(session, chatMessage, chatService);
    }
}

