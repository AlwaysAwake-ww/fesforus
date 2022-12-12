package com.festivalP.demo.controller;

import com.festivalP.demo.domain.Notice;
import com.festivalP.demo.domain.Posts;
import com.festivalP.demo.service.FestivalService;
import com.festivalP.demo.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FestivalService festivalService;
    private final NoticeService noticeService;


    //가장 많이 찾는 축제, 최근공지 모달
    @GetMapping("/")
    public String home(Model model) {
        List<Posts> topThree = festivalService.sort3ViewFestivals();
        List<Posts> topDate =  festivalService.sort3NewFestivals();

        model.addAttribute("topview",topThree);
        model.addAttribute("topdate",topDate);

        return "home";
    }


    @ResponseBody
    @PostMapping ("/noticerefresh")
    public List<Notice> noticerefresh(HttpSession session){

        System.out.println("sdfasdfsadfasdfsadf@!");

        List<Notice> notice = noticeService.NewNotice();
        return notice;
    }
}
