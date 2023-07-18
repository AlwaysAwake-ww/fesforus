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

        return chatService.createRoom(name);
    }



    @GetMapping("/member/chatlist")
    public String loadchatlist(){


        return "";
    }
}


