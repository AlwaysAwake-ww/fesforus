package com.festivalP.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="Review")
@Table(name="Festival_Review")
@Getter @Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    @Column(name="review_index")
    private Long reviewIndex;

    @Column(name="post_num")
    private Long postNum;

    @Column(name="member_index")
    private String memberIndex;

    @Column(name="review_text")
    private String reviewText;


    @Column(name="review_score")
    private Long reviewScore;


}
