package com.festivalP.demo.repository;

import com.festivalP.demo.domain.Posts;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class FestivalRepository {
    private final EntityManager em;


    @Autowired
    PageRepository pageRepository;




    public List<Posts> findAll() {

       return em.createQuery("select p from Posts p", Posts.class).getResultList();
    }


    public Page<Posts> findAll2(Pageable pageable) {
        return pageRepository.findAll(pageable);

    }


    public void save(Posts posts) {em.persist(posts);}

    public List<Posts> findBypostNum(Long postNum) {
        return em.createQuery("select p from Posts p where p.postNum = :postNum", Posts.class).setParameter("postNum",postNum).getResultList();
    }

    public Posts findBypostNum3(Long postNum) {
        return em.createQuery("select p from Posts p where p.postNum = :postNum", Posts.class).setParameter("postNum",postNum).getSingleResult();
    }

    public int deleteBypostNum(Long postNum){

        int result = em.createQuery ("delete from Posts p where p.postNum = :postNum")
                .setParameter("postNum",postNum).executeUpdate();
        return result;
    }


    public List<Posts> findByfestivalTitle(String keyword) {

        return em.createQuery("select p from Posts p where p.festivalTitle LIKE concat('%',:keyword,'%')",Posts.class).setParameter("keyword",keyword).getResultList();
    }



    public Posts findOne(Long postNum) {
        return em.find(Posts.class, postNum);
    }

    public List<Posts> findByboardLocAddr(Long local) {
        return em.createQuery("select p from Posts p where p.boardLocAddr LIKE :boardLocAddr", Posts.class).setParameter("boardLocAddr",local).getResultList();
    }



    public List<Posts> findOneOrderByFestival_contentViews() {
       return em.createQuery("select p from Posts p order by p.contentViews desc", Posts.class).setFirstResult(0).setMaxResults(3).getResultList();
    }


    public List<Posts> findOndOrderByUpload_Date() {
        return em.createQuery("select p from Posts p order by p.festivalUploadDate desc", Posts.class).setFirstResult(0).setMaxResults(3).getResultList();
    }







}


