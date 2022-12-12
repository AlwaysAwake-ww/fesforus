package com.festivalP.demo.repository;

import com.festivalP.demo.domain.Member;
import com.festivalP.demo.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageMemberRepository extends PagingAndSortingRepository<Member, Long> {

    Page<Member> findByMemberIdContaining(String keyword, Pageable pageable);



    Page<Member> findByMemberState( int memberState ,Pageable pageable);
}
