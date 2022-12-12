package com.festivalP.demo.controller;

import com.festivalP.demo.domain.*;
import com.festivalP.demo.form.AuthInfo;
import com.festivalP.demo.form.FestivalForm;
import com.festivalP.demo.repository.FestivalRepository;
import com.festivalP.demo.repository.PageRepository;
import com.festivalP.demo.service.CategoryService;
import com.festivalP.demo.service.FestivalService;
import com.festivalP.demo.service.MemberService;
import com.festivalP.demo.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final FestivalService festivalService;
    private final MemberService memberService;
    //
    private final NoticeService noticeService;

    private final CategoryService categoryService;


    @GetMapping("/festivalManagement")
    public String festivalManagement(Model model, @PageableDefault(size = 5, page = 0, direction = Sort.Direction.DESC, sort="postNum") Pageable pageable, String keyword) {

        System.out.println(pageable.getPageNumber());


        Page<Posts> festivals = festivalService.paging(keyword, pageable);


        model.addAttribute("keyword", keyword);
        model.addAttribute("posts", festivals);
        model.addAttribute("maxPage", 5);
        return "festivalManagement";
    }


    public String festivalManagement(Model model, HttpSession session) {
        // 관리자 메인 페이지 (페스티벌 글 목록)
        List<Posts> festivals = festivalService.findFestivals();
        model.addAttribute("posts", festivals);

        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");

        if (authInfo.getState() == 2) {
            return "festivalManagement";
        } else {
            return "redirect:/";
        }

    }

    @RequestMapping("/festivalWrite")
    public String festivalWrite(Model model) {
        // 축제 작성 페이지
//        List<Category> category=categoryService.findCategory();
//        model.addAttribute("category", category);
        return "festivalWrite";
    }

//    @ResponseBody
    @PostMapping("/festivalWrite")
    public String fes_create(MultipartHttpServletRequest multi) throws ParseException {
//        if (result.hasErrors()) {
//            return "/admin/festivalWrite";
//        }
        //List<Category> festivalcategories= multi.getParameter("festivalCategory");
        System.out.println(multi.getParameter("festivalCategory"));
        Posts posts = new Posts();
        HttpSession session= multi.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        posts.setAdminIndex(admin.getAdminIndex());
        posts.setContentText(multi.getParameter("contentText"));
        posts.setFestivalTitle(multi.getParameter("festivalTitle"));
        posts.setFestivalCategory(multi.getParameter("festivalCategory").toString());


        posts.setBoardAddr(multi.getParameter("address"));
        posts.setBoardLocAddr(Long.parseLong(multi.getParameter("BoardLocAddr")));



        MultipartFile pic = multi.getFile("contentImage");
//        if(pic.getContentType().startsWith("image") == false){
//            return "redirect:/admin/festivalWrite";
//        }
        UUID uuid=UUID.randomUUID();
        String filename =uuid+"_"+pic.getOriginalFilename();


        String uploadDir = "C:\\Users\\kitri\\Desktop\\project2\\src\\main\\resources\\static\\assets\\img\\image" + File.separator;


        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdir();
        }

        String fullPath = uploadDir + filename;
        try {
            pic.transferTo(new File(fullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        posts.setContentImage(filename);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 문자열 -> Date
        Date date = formatter.parse(multi.getParameter("festivalUploadDate"));

        posts.setFestivalUploadDate(date);

//        posts.setContentViews(0L);
        posts.setReviewScoreAvg(0L);


        festivalService.join(posts);


        return "redirect:/admin/festivalManagement";
    }


    @RequestMapping("/noticeManagement")
    public String noticeManagement(Model model, @PageableDefault(size = 5, page = 0, direction = Sort.Direction.DESC) Pageable pageable, String keyword) {


        Page<Notice> notice = noticeService.paging(keyword, pageable);

        model.addAttribute("maxPage", 5);
        model.addAttribute("notice", notice);

        // 공지관리
        return "noticeManagement";
    }

    @RequestMapping("/noticeManagementDetail")
    public String noticeManagementDetail() {
        // 공지 관리 상세 사항
        return "noticeManagementDetail";
    }

    @RequestMapping("/memberManagement")
    public String memberManagement(Model model, @PageableDefault(size = 10, page = 0, direction = Sort.Direction.DESC) Pageable pageable, String keyword) {
        // 회원관리

        Page<Member> members = memberService.paging(keyword, pageable);
        model.addAttribute("maxPage", 5);
        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword);
        return "memberManagement";
    }


    @GetMapping("/memberManagement/{memberState}")
    public String findMemberState(@PathVariable("memberState")int memberState, @PageableDefault(size = 10, page = 0, direction = Sort.Direction.DESC)Pageable pageable, Model model){
        System.out.println("memberState!!!!!!!");
        System.out.println(memberState);

        Page<Member> members= memberService.findByMemberState(memberState, pageable);

        model.addAttribute("maxPage", 5);
        model.addAttribute("members", members);

        return "memberManagement";
    }

    @ResponseBody
    @PostMapping("/memberStateModify")
    public Member memberStateModify(@RequestParam("memberIndex")Long memberIndex){
        Member member= memberService.updateMemberState(memberIndex);
        return member;
    }


    @RequestMapping("/noticeWrite")
    public String noticeWrite() {
        return "noticeWrite";
    }

    @PostMapping("/noticeWrite")
    public String noticeCreate(Notice form, BindingResult result, HttpSession session) {
        Notice notice = new Notice();


        Admin admin = (Admin) session.getAttribute("admin");

        notice.setPostNum(form.getPostNum());
//        notice.setAdminIndex(1L);
        notice.setAdminIndex(admin.getAdminIndex());
        notice.setContentTitle(form.getContentTitle());
        notice.setContentText(form.getContentText());

        noticeService.join(notice);

        return "redirect:/admin/noticeManagement";
    }


    @GetMapping("/noticeManagement/delete_notice/{postNum}")
    public String del_notice_num(@PathVariable("postNum") Long postNum) {
        System.out.println(postNum);
        int result = noticeService.deleteByNotice_num(postNum);




        return "redirect:/admin/noticeManagement";
    }

    @GetMapping("/noticeManagement/modify_notice/{postNum}")
    public String modify_notice(Model model, @PathVariable("postNum") Long postNum) {
        Notice notice = noticeService.findOneNotice(postNum);
        model.addAttribute("notice", notice);
        //System.out.println("festivaltitle============="+notice.);

        return "noticeModify";
    }

    @PostMapping("/noticeManagement/modify_notice/{postNum}")
    public String not_modify(Notice notice,HttpSession session,  @PathVariable("postNum") Long postNum){
        //Notice notice = new Notice();

        Admin admin = (Admin) session.getAttribute("admin");

        notice.setPostNum(notice.getPostNum());
        notice.setAdminIndex(admin.getAdminIndex());
        notice.setContentTitle(notice.getContentTitle());
        notice.setContentText(notice.getContentText());

        noticeService.updatePosts(postNum, notice);
        return "redirect:/admin/noticeManagement";
    }

    @GetMapping("/admin/delete/{postNum}")
    public String del_postNum(@PathVariable("postNum") Long postNum) {
        System.out.println(postNum);
        int result = festivalService.deleteBypostNum(postNum);
        System.out.println("result : " + result);
        //        File file = new File("C:\\Users\\kitri\\Desktop\\new\\Festival-Project\\src\\main\\resources\\static\\assets/" + srcFileName);
//        file.delete();


        return "redirect:/admin/festivalManagement";
    }

    @GetMapping("/admin/modify/{postNum}")
    public String modify_postNum(Model model, @PathVariable("postNum") Long postNum) {

//        List<Posts> festivals = festivalService.findOne(postNum);
        model.addAttribute("posts", festivalService.findOne(postNum));

        Posts festivals = festivalService.findOne3(postNum);
        model.addAttribute("posts", festivals);


        return "festivalModify";
    }


    @PostMapping("/admin/modify/{postNum}")
    public String fes_Modify(@PathVariable("postNum") Long postNum, MultipartHttpServletRequest multi) throws ParseException {

        Posts posts = new Posts();
        posts.setPostNum(postNum);

        HttpSession session= multi.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        //posts.setAdminIndex(Long.parseLong(multi.getParameter("adminIndex")));
        posts.setContentText(multi.getParameter("contentText"));
        posts.setFestivalTitle(multi.getParameter("festivalTitle"));
        posts.setFestivalCategory(multi.getParameter("festivalCategory"));
        posts.setBoardAddr(multi.getParameter("address"));

        posts.setBoardLocAddr(Long.parseLong(multi.getParameter("BoardLocAddr")));
        MultipartFile pic = multi.getFile("contentImage");




        String srcFileName = multi.getParameter("contentImage0");



        if(pic.getOriginalFilename().length()!=0){
            UUID uuid = UUID.randomUUID();
            String filename = uuid + "_" + pic.getOriginalFilename();
            // 랜덤 이름 생성


            String uploadDir = "C:\\Users\\kitri\\Desktop\\project2\\src\\main\\resources\\static\\assets\\img\\image" + File.separator;



            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }

            String fullPath = uploadDir + filename;
            try {
                pic.transferTo(new File(fullPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            posts.setContentImage(filename);
            System.out.println("## posts.getContentImage() :; "+posts.getContentImage());

            //delete

            File file = new File("C:\\Users\\kitri\\Desktop\\project2\\src\\main\\resources\\static\\assets\\img\\image/" + srcFileName); // ex. [D:/test/image/testImage.jpg]

            System.out.println("## srcFileName ::" + srcFileName);

            file.delete();
        }

        else{
            posts.setContentImage(srcFileName);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(multi.getParameter("festivalUploadDate"));

        posts.setFestivalUploadDate(date);


//        posts.setContentViews(0L);
        posts.setReviewScoreAvg(0L);


        festivalService.updatePosts(postNum, posts);


        return "redirect:/admin/festivalManagement";

    }


}


