package com.festivalP.demo.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name="Posts")
@Table
//@Table(name = "POSTS")

@Getter
@Setter
@NoArgsConstructor
public class Posts extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name="post_num")
    private Long postNum;

    @Column(name="admin_index")
    private Long adminIndex;

    @Column(name="content_text")
    private String contentText;

    @Column(name="content_views",columnDefinition = "integer default 0", nullable = false)
    private int contentViews;

    @Column(name = "festival_title")
    private String festivalTitle;

    @Column(name="reviewScore_avg")
    private Long reviewScoreAvg;

    @Column(name="board_addr")
    private String boardAddr; //주소

    @Column(name="board_loc_addr")
    private Long boardLocAddr; //지역주소
    
    @Column(name="content_image")
    private String contentImage;

    @Column(name="progress_state")
    private String progressState; //진행상태

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="festival_upload_date")
    private Date festivalUploadDate;

    @Column(name="festival_category")
    private String festivalCategory;


    @OneToMany(fetch= FetchType.LAZY)
    @JoinColumn(name = "post_num")
    private List<Favorite> favorite;
}
