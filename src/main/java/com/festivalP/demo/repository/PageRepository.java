package com.festivalP.demo.repository;

import com.festivalP.demo.domain.Member;
import com.festivalP.demo.domain.Posts;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface PageRepository extends PagingAndSortingRepository<Posts, Long>{



    @Override
    Page<Posts> findAll( Pageable pageable);


    Page<Posts> findByFestivalTitleContaining(String keyword, Pageable pageable);



    //오래된 순
    Page<Posts> findAllByOrderByFestivalUploadDate(Pageable pageable);

    //최신 순
    Page<Posts> findAllByOrderByFestivalUploadDateDesc(Pageable pageable);

    //조회수 순
    Page<Posts> findAllByOrderByContentViewsDesc(Pageable pageable);

    @Modifying
    @Query("update Posts p set p.contentViews = p.contentViews + 1 where p.postNum = :postNum")
    int updateView(@Param("postNum")Long postNum);




}

