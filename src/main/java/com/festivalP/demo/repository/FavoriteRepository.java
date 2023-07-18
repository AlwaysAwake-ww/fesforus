package com.festivalP.demo.repository;


import com.festivalP.demo.domain.Favorite;
import com.festivalP.demo.domain.FavoritePK;
import com.festivalP.demo.domain.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class FavoriteRepository {

    private final EntityManager em;


    public void save(Favorite favorite) {

        em.persist(favorite);
    }

    public void delete(Favorite favorite){

        FavoritePK favoritePK = new FavoritePK();
        favoritePK.setMemberIndex(favorite.getMemberIndex());
        favoritePK.setPostNum(favorite.getPostNum());

        Favorite resFavorite = em.find(Favorite.class, favoritePK);
        em.remove(resFavorite);
    }

    public Favorite existCheck(Long member_index, Long post_num){

        FavoritePK favoritePK = new FavoritePK();
        favoritePK.setMemberIndex(member_index);
        favoritePK.setPostNum(post_num);
        try{
            Favorite favorite =em.find(Favorite.class,favoritePK);
            return favorite;
        }
        catch(Exception e){
            return null;
        }
    }



    public List<Posts> findFavoritePosts(Long memberIndex) {
        return em.createQuery("select p from Posts p JOIN Favorite f on p.postNum = f.postNum and f.memberIndex = :memberIndex", Posts.class).setParameter("memberIndex", memberIndex)
                .getResultList();
    }
}
