package com.festivalP.demo.repository;

import com.festivalP.demo.domain.Notice;
import com.festivalP.demo.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageNoticeRepository extends PagingAndSortingRepository<Notice, Long> {


    Page<Notice> findByContentTitleContaining(String keyword, Pageable pageable);



}
