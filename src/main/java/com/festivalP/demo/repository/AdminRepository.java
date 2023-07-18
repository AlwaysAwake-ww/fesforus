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
public class

AdminRepository {

    private final EntityManager em;


    public void save(Admin admin) {
        em.persist(admin);
    }


    public Admin findById(String id){

        try{
            return em.createQuery("select m from Admin m where m.adminId = :id", Admin.class).setParameter("id", id).getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }



}