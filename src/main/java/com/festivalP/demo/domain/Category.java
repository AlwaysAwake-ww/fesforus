package com.festivalP.demo.domain;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity

@Table(name = "CATEGORY")
@Getter
@Setter
@NoArgsConstructor

@IdClass(CategoryPK.class)
public class Category {

    @Id
    @Column(name = "member_index")
    private Long memberIndex;

    @Id
    @Column(name = "categoryClass")
    private String categoryClass;

}
