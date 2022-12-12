package com.festivalP.demo.repository;

import com.festivalP.demo.domain.Admin;
import com.festivalP.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
// 롬복 사용하여 생성자 의존성 주입
public class

AdminRepository {

    private final EntityManager em;


    public void save(Admin admin) {
        // JPA에서 save 함수는 매개변수를 insert 해주는 동작
        System.out.println("AdminRepository.save");
        em.persist(admin);
    }


    public Admin findById(String id){
        System.out.println("#####this is ID : "+id);
        System.out.println("#####this is ID : "+id.getClass().getName());

        try{
            return em.createQuery("select m from Admin m where m.adminId = :id", Admin.class).setParameter("id", id).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }



}