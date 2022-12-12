package com.festivalP.demo.domain;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Entity // 테이블과 링크 될 클래스
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;



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
