package com.festivalP.demo.controller;


import com.festivalP.demo.form.ChatRoom;
import com.festivalP.demo.form.MemberForm;
import com.festivalP.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;


    @GetMapping("/admin/chatManagement")
    public String loadChat(Model model){

        List<ChatRoom> chatRoom = chatService.findAllRoom();

        model.addAttribute("chatroom", chatRoom);
        return "chatManagementForm";
    }

    @PostMapping("/admin/chatlist")
    public List<ChatRoom> chatList(){

        return chatService.findAllRoom();
    }

    @ResponseBody
    @PostMapping("/admin/createroom")
    public ChatRoom createRoom(String name) {
//        String name = postNum.toString()+"번 게시물 채팅방";
        System.out.println("@@@@#### createRoom called!! :: "+name);

        // /chat 주소로 POST 요청 시 JSON 데이터에서 name 값을 받아 해당 이름으로 된 채팅방 생성
        return chatService.createRoom(name);
    }



    @GetMapping("/member/chatlist")
    public String loadchatlist(){


        return "";
    }
}


