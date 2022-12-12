package com.festivalP.demo.repository;


import com.festivalP.demo.domain.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CategoryPageRepository extends PagingAndSortingRepository<Posts, Long> {

    @Override
    Page<Posts> findAll( Pageable pageable);



    @Query("select p from Posts p JOIN FETCH Favorite f on p.postNum = f.postNum where f.memberIndex = :memberIndex")
    Page<Posts> findByMemberIndex(@Param("memberIndex") Long memberIndex, Pageable pageable);

    @Query("select p from Posts p JOIN FETCH Favorite f on p.postNum = f.postNum where f.memberIndex = :memberIndex and p.festivalTitle like %:keyword%")
    Page<Posts> findByFestivalTitle(@Param("memberIndex") Long memberIndex, @Param("keyword") String keyword, Pageable pageable);


    @Query("select p from Posts p where p.festivalCategory like %:favorite%")
    Page<Posts> findByFavorite(@Param("favorite") String favorite, Pageable pageable);


    @Query("select distinct p from Posts p join fetch Category c ON (LOCATE(c.categoryClass, p.festivalCategory) > 0) and c.memberIndex = :memberIndex ")
    Page<Posts> findByFavoriteCategory(@Param("memberIndex") Long memberIndex, Pageable pageable);



    @Query("select distinct p from Posts p join fetch Category c ON (LOCATE(c.categoryClass, p.festivalCategory) > 0) and c.memberIndex = :memberIndex")
    Page<Posts> findAllByOrderByFestivalUploadDate(@Param("memberIndex") Long memberIndex, Pageable pageable);

    @Query("select distinct p from Posts p join fetch Category c ON (LOCATE(c.categoryClass, p.festivalCategory) > 0) and c.memberIndex = :memberIndex")
    Page<Posts> findAllByOrderByFestivalUploadDateDesc(@Param("memberIndex") Long memberIndex, Pageable pageable);

    @Query("select distinct p from Posts p join fetch Category c ON (LOCATE(c.categoryClass, p.festivalCategory) > 0) and c.memberIndex = :memberIndex")
    Page<Posts> findAllByOrderByContentViewsDesc(@Param("memberIndex") Long memberIndex, Pageable pageable);



}