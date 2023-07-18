package com.festivalP.demo.controller;

import com.festivalP.demo.domain.Member;
import com.festivalP.demo.domain.Posts;
import com.festivalP.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/categoryfestival")
    public String list(Model model, @PageableDefault(size =6,page=0, sort="contentViews", direction = Sort.Direction.DESC) Pageable pageable, String keyword, HttpSession session) {
        String[] sortDirection  = String.valueOf(pageable.getSort()).split(":");


        String sort = sortDirection[0].trim();
        String direction = sortDirection[1].trim();

        Member member = (Member)session.getAttribute("member");
        Page<Posts> categoryPosts = null;

        if(member==null)
            return "redirect:/";
        if(keyword == null) {

            categoryPosts = categoryService.listPaging(member.getMemberIndex(), pageable);

        } else {
            categoryPosts = categoryService.paging(member.getMemberIndex(), keyword, pageable);
        }
        model.addAttribute("posts",categoryPosts);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageable", pageable);
        model.addAttribute("sort",sort);
        model.addAttribute("direction",direction);
        return "category_festival_board";
    }

    @PostMapping("/categoryfestival/scroll")
    @ResponseBody
    public Page<Posts> list(Model model, String keyword, @PageableDefault(size =6,page=0, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String direction, String sort, HttpSession session) {

        Member member = (Member)session.getAttribute("member");
        Page<Posts> categoryPosts=null;


        if(keyword == null) {
            if(direction.equals("DESC") && sort.equals("contentViews")) {
                categoryPosts =categoryService.sortView(member.getMemberIndex(), pageable);
            } else if (direction.equals("DESC") && sort.equals("festivalUploadDate")) {
                categoryPosts = categoryService.sortNew(member.getMemberIndex(), pageable);
            } else {
                categoryPosts = categoryService.sortOld(member.getMemberIndex(), pageable);
            }

        } else {
            categoryPosts = categoryService.paging(member.getMemberIndex(), keyword, pageable);
        }
        model.addAttribute("posts",categoryPosts);
        model.addAttribute("keyword", keyword);
        return categoryPosts;
    }
}