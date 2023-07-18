package com.festivalP.demo.service;

import com.festivalP.demo.domain.Favorite;
import com.festivalP.demo.domain.Posts;
import com.festivalP.demo.repository.FavoritePageRepository;
import com.festivalP.demo.repository.FavoriteRepository;
import com.festivalP.demo.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoritePageRepository favoritePageRepository;
    private final PageRepository pageRepository;

    @Transactional
    public String insert(Favorite favorite){

        favoriteRepository.save(favorite);

        return "";
    }

    @Transactional
    public boolean favoriteExist(Long member_index, Long post_num){

        Favorite favorite =favoriteRepository.existCheck(member_index, post_num);


        if(favorite==null){
            return false;
        }
        return true;
    }

    @Transactional
    public void delete(Favorite favorite) {
        favoriteRepository.delete(favorite);

    }

    @Transactional
    public List<Posts> findFestivals(Long memberIndex){

        return favoriteRepository.findFavoritePosts(memberIndex);
    }

    @Transactional
    public Page<Posts> paging(Long memberId,String keyword, Pageable pageable) {
        Page<Posts> Pages= favoritePageRepository.findByFestivalTitle(memberId ,keyword, pageable);
        return Pages;
    }

    @Transactional
    public  Page<Posts> paging2(Long memberId, Pageable pageable) {
        Page<Posts> Pages= favoritePageRepository.findByMemberIndex(memberId, pageable);
        return Pages;
    }

    public Page<Posts> sortOld(Long memberIndex, Pageable pageable) {
        Page<Posts> Pages = favoritePageRepository.findAllByOrderByFestivalUploadDate(memberIndex, pageable);
        return Pages;
    }
    public Page<Posts> sortNew(Long memberIndex, Pageable pageable) {
        Page<Posts> Pages = favoritePageRepository.findAllByOrderByFestivalUploadDateDesc(memberIndex, pageable);
        return Pages;
    }

    public Page<Posts> sortView(Long memberIndex, Pageable pageable) {
        Page<Posts> Pages = favoritePageRepository.findAllByOrderByContentViewsDesc(memberIndex, pageable);

        return Pages;
    }

}



