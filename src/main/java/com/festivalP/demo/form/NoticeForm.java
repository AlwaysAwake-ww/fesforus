package com.festivalP.demo.form;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeForm {

    private Long postNum;
    private Long adminIndex;
    private String contentTitle;
    private String contentText;
}
