package com.festivalP.demo.repository;

import com.festivalP.demo.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Override
    List<Category> findAll(Sort sort);

}
