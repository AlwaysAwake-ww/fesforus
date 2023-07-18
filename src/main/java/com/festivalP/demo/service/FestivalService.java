package com.festivalP.demo.service;


import com.festivalP.demo.domain.Posts;
import com.festivalP.demo.domain.Review;
import com.festivalP.demo.repository.FestivalRepository;
import com.festivalP.demo.repository.PageRepository;
import com.festivalP.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class FestivalService{

    private final FestivalRepository festivalRepository;
    private final ReviewRepository reviewRepository;
    private final PageRepository pageRepository;

    @Transactional
    public Long join(Posts posts) {

        festivalRepository.save(posts);
        return posts.getPostNum();
    }

    public Posts findOne3(Long postNum) {
        return festivalRepository.findBypostNum3(postNum);
    }


    public List<Posts> findFestivals() {
        return festivalRepository.findAll();
    }


    @Transactional
    public int updateView(Long postNum) {
        return pageRepository.updateView(postNum);
    }

    public  Page<Posts> paging(String keyword, Pageable pageable) {
        Page<Posts> Pages= pageRepository.findByFestivalTitleContaining(keyword, pageable);
        return Pages;
    }

    public  Page<Posts> paging2(Pageable pageable) {
        Page<Posts> Pages= pageRepository.findAll(pageable);
        return Pages;
    }

    public Page<Posts> pagingView(String keyword,Pageable pageable) {
        Page<Posts> Pages = pageRepository.findByFestivalTitleContaining(keyword,pageable);
        Pages = sortView(pageable);
        return Pages;
    }

    public Page<Posts> sortOld(Pageable pageable) {
        Page<Posts> Pages = pageRepository.findAllByOrderByFestivalUploadDate(pageable);
        return Pages;
    }
    public Page<Posts> sortNew(Pageable pageable) {
        Page<Posts> Pages = pageRepository.findAllByOrderByFestivalUploadDateDesc(pageable);
        return Pages;
    }

    public Page<Posts> sortView(Pageable pageable) {
        Page<Posts> Pages = pageRepository.findAllByOrderByContentViewsDesc(pageable);

        return Pages;
    }

    public List<Posts> findOne(Long postNum) {
        return festivalRepository.findBypostNum(postNum);
    }

    public List<Review> findReviewOne(Long postNum) {
        return reviewRepository.findReviewBypostNum(postNum);
    }

    public List<Posts> findOne2(Long local){ return festivalRepository.findByboardLocAddr(local);}

    public List<Posts> sort3ViewFestivals() {
        List<Posts> posts = festivalRepository.findOneOrderByFestival_contentViews();
        return posts;
    }

    public List<Posts> sort3NewFestivals() {
        List<Posts> date = festivalRepository.findOndOrderByUpload_Date();
        return date;
    }


    @Transactional
    public Long saveReview(Review review) {
        reviewRepository.save(review);
        return review.getReviewIndex();
    }

    public List<Review> findReviews(Long postNum) {
        return reviewRepository.findAllReview(postNum);}



    @Transactional
    public List<Posts> searchPosts(String keyword) {
        List<Posts> posts = festivalRepository.findByfestivalTitle(keyword);
        if(keyword.isEmpty()) return findFestivals();
        else {
            return posts;
        }
    }


    public int deleteBypostNum(Long postNum) {
        return festivalRepository.deleteBypostNum(postNum);
    }


    @Transactional
    public void updatePosts(Long postNum, Posts posts ) {
        Posts post = festivalRepository.findOne(postNum);
        post.setContentText(posts.getContentText());
        post.setFestivalTitle(posts.getFestivalTitle());
        post.setContentImage(posts.getContentImage());
        post.setFestivalUploadDate(posts.getFestivalUploadDate());
        post.setBoardAddr(posts.getBoardAddr());
        post.setBoardLocAddr(posts.getBoardLocAddr());
        post.setFestivalCategory(posts.getFestivalCategory());
    }


}