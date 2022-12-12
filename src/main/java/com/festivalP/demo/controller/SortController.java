package com.festivalP.demo.controller;

import com.festivalP.demo.domain.Posts;
import com.festivalP.demo.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SortController {

    private final FestivalService festivalService;

//    //오래된 순 정렬
//    @GetMapping("/allfestival/old")
//    public String oldList(Model model,  @PageableDefault(size =9,page=0, direction = Sort.Direction.DESC) Pageable pageable, String keyword){
//        Page<Posts> oldPostsList = festivalService.sortOld(pageable);
//        System.out.println("old keyword");
//        System.out.println(keyword);
//        model.addAttribute("posts", oldPostsList);
//        return "every_festival_board";
//    }
//
//    //최신 순 정렬
//    @GetMapping("/allfestival/new")
//    public String newList(Model model, @PageableDefault(size =9,page=0, direction = Sort.Direction.DESC) Pageable pageable){
////        List<Posts> newPostsList = festivalService.sortNewFestivals();
//        Page<Posts> newPostsList = festivalService.sortNew(pageable);
//        model.addAttribute("posts", newPostsList);
//        System.out.println("여기다");
////        System.out.println(newPostsList.size());
//        return "every_festival_board";
//    }
//
//    //조회수 정렬
//    @GetMapping("/allfestival/view")
//    public String viewList(Model model,@PageableDefault(size =9,page=0, direction = Sort.Direction.DESC) Pageable pageable){
////        List<Posts> viewPostsList = festivalService.sortViewFestivals();
//        Page<Posts> viewPostsList = festivalService.sortView(pageable);
//        model.addAttribute("posts", viewPostsList);
//        System.out.println("여기다");
////        System.out.println(viewPostsList.size());
//        return "every_festival_board";
//    }
}
