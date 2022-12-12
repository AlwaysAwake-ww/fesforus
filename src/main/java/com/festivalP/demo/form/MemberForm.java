package com.festivalP.demo.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Date;

@Getter
@Setter
public class MemberForm {

    private String id;
    private String pw;
    private String nickname;
    private Date birth;
    private String email;
    private String addr;
    private String category;
    private int state;
}

