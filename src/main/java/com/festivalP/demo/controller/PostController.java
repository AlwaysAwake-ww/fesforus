package com.festivalP.demo.controller;

//import com.festivalP.demo.domain.Contact;
import com.festivalP.demo.domain.Contact;
import com.festivalP.demo.domain.Member;
import com.festivalP.demo.domain.Posts;
import com.festivalP.demo.domain.Review;
import com.festivalP.demo.form.PostForm;
import com.festivalP.demo.service.FavoriteService;
import com.festivalP.demo.service.FestivalService;
//import com.festivalP.demo.service.MailService;
//import com.festivalP.demo.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
@Log4j2
@Controller
@RequiredArgsConstructor
public class PostController {

    private final FestivalService festivalService;

    private final FavoriteService favoriteService;


    //전체 축제리스트 불러오는 부분
    @GetMapping("/allfestival")
    public String list(Model model, @PageableDefault(size =6,page=0, sort="contentViews", direction = Sort.Direction.DESC) Pageable pageable,String keyword) {

        String[] sortDirection  = String.valueOf(pageable.getSort()).split(":");
        String sort = sortDirection[0].trim();
        String direction = sortDirection[1].trim();

        Page<Posts> festivals = null;
        if(keyword == null) {
            festivals = festivalService.paging2(pageable);
        } else {
            festivals = festivalService.paging(keyword,pageable);
        }
        model.addAttribute("posts",festivals);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageable", pageable);
        model.addAttribute("sort",sort);
        model.addAttribute("direction",direction);
        return "every_festival_board";
    }

    //페이지 ajax
    @PostMapping("/allfestival/scroll")
    @ResponseBody
    public Page<Posts> list(Model model, String keyword, @PageableDefault(size =6,page=0,direction = Sort.Direction.DESC) Pageable pageable ,@RequestParam String direction,String sort) {

        Sort sort1 = Sort.by("postNum").descending();
        Page<Posts> festivals = null;
        if(keyword.length() == 0) {
            if(direction.equals("DESC") && sort.equals("contentViews")) {
                festivals =festivalService.sortView(pageable);
            } else if (direction.equals("DESC") && sort.equals("festivalUploadDate")) {
                festivals = festivalService.sortNew(pageable);
            } else {
                festivals = festivalService.sortOld(pageable);
            }
        }else {
            festivals = festivalService.paging(keyword, pageable);
        }

        return festivals;
    }

    //각 축제별 정보와 리뷰리스트 불러오는 부분
    @GetMapping("/festival/{postNum}")
    public String list(Model model, @PathVariable("postNum") Long postNum, HttpServletRequest request) {
        List<Posts> post = festivalService.findOne(postNum);
        model.addAttribute("post", post);
        List<Review> reviews = festivalService.findReviewOne(postNum);
        model.addAttribute("reviews",reviews);

        PostForm postForm = new PostForm();

        postForm.setPostNum(post.get(0).getPostNum());
        postForm.setAdminIndex(post.get(0).getAdminIndex());
        postForm.setContentText(post.get(0).getContentText());
        postForm.setContentViews(post.get(0).getContentViews());
        postForm.setFestivalTitle(post.get(0).getFestivalTitle());
        postForm.setReviewScoreAvg(post.get(0).getReviewScoreAvg());
        postForm.setBoardAddr(post.get(0).getBoardAddr());
        postForm.setBoardLocAddr(post.get(0).getBoardLocAddr());
        postForm.setContentImage(post.get(0).getContentImage());
        postForm.setFestivalUploadDate(post.get(0).getFestivalUploadDate());
        postForm.setProgressState(post.get(0).getProgressState());
        postForm.setFestivalCategory(post.get(0).getFestivalCategory());

        HttpSession session = request.getSession();

        Member member = (Member) session.getAttribute("member");
        if(member!=null){
            postForm.setFavoriteFlag(favoriteService.favoriteExist(member.getMemberIndex(), postNum));
        }else{
            postForm.setFavoriteFlag(false);
        }
        model.addAttribute("post", postForm);
        festivalService.updateView(postNum);
        return "Each_Festival_board";
    }

    //후기 제출했을 때 데이터 저장되는 부분
    @PostMapping("/festival/review")
    @ResponseBody
    public HashMap<String, Object> Review(@RequestBody Review data) {
        festivalService.saveReview(data);
        return null;
    }



    //지역별 축제
    @GetMapping("/localFestival")
    public String local() {
        return "Local_Festival_board";
    }

    //지역주소 받아오고 지역별 축제 보여주기
    @PostMapping("/local")
    @ResponseBody
    public List local_Addr(Long local) {
        List<Posts> addr = festivalService.findOne2(local);
        return addr;
    }


}
