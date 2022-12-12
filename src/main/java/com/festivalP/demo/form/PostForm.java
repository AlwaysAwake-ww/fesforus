package com.festivalP.demo.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;



@Getter
@Setter
public class PostForm {

    private Long postNum;

    private Long adminIndex;

    private String contentText;

    private int contentViews;

    private String festivalTitle;

    private Long reviewScoreAvg;

    private String boardAddr; //주소

    private Long boardLocAddr; //지역주소

    private String contentImage;

    private String progressState; //진행상태

    private Date festivalUploadDate;

    private String festivalCategory;

    private boolean favoriteFlag;



}
