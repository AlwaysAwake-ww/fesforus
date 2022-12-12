package com.festivalP.demo.service;


import com.festivalP.demo.domain.Notice;
import com.festivalP.demo.domain.Posts;
import com.festivalP.demo.repository.MemberRepository;
import com.festivalP.demo.repository.NoticeRepository;
import com.festivalP.demo.repository.PageNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final PageNoticeRepository pageNoticeRepository;

    public Notice findOneNotice(Long postNum){return noticeRepository.findByPostNum2(postNum);};

    @Transactional
    public Long join(Notice notice) {

        noticeRepository.save(notice);
        return notice.getPostNum();
    }

    public List<Notice> findNotice() {
        return noticeRepository.findAll();
    }

    public Page<Notice> paging(String keyword, Pageable pageable) {
        // Page<Posts> Pages= pageRepository.findAll(pageable);
        return pageNoticeRepository.findByContentTitleContaining(keyword, pageable);
    }


    @Transactional
    public int deleteByNotice_num(Long postNum) {
        return noticeRepository.deleteByNotice_num(postNum);
    }

    public List<Notice> findOne(Long postNum) {
        return noticeRepository.findBypostNum(postNum);
    }

    //검색
//    @Transactional
//    public List<Notice> searchNotice(String keyword) {
//        List<Notice> notices = pageNoticeRepository.findByContentTitleContaining(keyword);
//        if(keyword.isEmpty()) return findNotice();
//        else {
//            return notices;
//        }
//
//    }

    //최근공지
    public List<Notice> NewNotice() {


        List<Notice> notice = noticeRepository.findByNotice_Date();
        return notice;
    }


    @Transactional
    public void updatePosts(Long postNum, Notice notice){
        Notice notices =noticeRepository.findOne(postNum);

        notices.setAdminIndex(notice.getAdminIndex());
        notices.setContentTitle(notice.getContentTitle());
        notices.setContentText(notice.getContentText());
    }


}
