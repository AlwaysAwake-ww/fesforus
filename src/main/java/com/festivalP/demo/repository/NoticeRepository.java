package com.festivalP.demo.repository;


import com.festivalP.demo.domain.Notice;
import com.festivalP.demo.domain.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeRepository {

    private final EntityManager em;

    public Notice findByPostNum2(Long postNum) {
        return em.createQuery("select n from Notice n where n.postNum = :postNum",Notice.class).setParameter("postNum",postNum).getSingleResult();}


    public void save(Notice notice) {
        em.persist(notice);
    }

    public List<Notice> findAll() {
        return em.createQuery("select p from Notice p", Notice.class).getResultList();
    }


    public int deleteByNotice_num(Long postNum){

        int result = em.createQuery ("delete from Notice p where p.postNum = :postNum")
                .setParameter("postNum",postNum).executeUpdate();
        return result;
    }

    //각 공지 들어갈 때

    public List<Notice> findByPost_num(Long post_num) {
        return em.createQuery("select n from Notice n where n.post_num = :post_num", Notice.class).setParameter("post_num", post_num).getResultList();
    }

    public List<Notice> findBypostNum(Long postNum) {
        return em.createQuery("select n from Notice n where n.postNum = :postNum",Notice.class).setParameter("postNum",postNum).getResultList();}





    public List<Notice> findByNotice_Title(String keyword) {

        return em.createQuery("select n from Notice n where n.contentText LIKE concat('%',:keyword,'%')", Notice.class).setParameter("keyword", keyword).getResultList();
    }




    public List<Notice> findByNotice_Date() {

        return em.createQuery("select n from Notice n order by n.modifiedDate desc", Notice.class).setFirstResult(0).setMaxResults(3).getResultList();




//        return em.createQuery("select n from Notice n order by n.modifiedDate desc", Notice.class).setFirstResult(0).setMaxResults(1).getResultList();
    }

    public Notice findOne(Long postNum) {
        return em.find(Notice.class, postNum);

    }

//    public List<Notice> findByNoticeRecent3(){
//        return em.createQuery("select n from Notice n order by n.noticeDate desc", Notice.class).setMaxResults(1).getResultList();
//    }


}

