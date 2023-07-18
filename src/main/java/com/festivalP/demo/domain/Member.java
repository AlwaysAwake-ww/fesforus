package com.festivalP.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "MEMBER")
@Getter
@Setter
public class Member {


    @Id
    @GeneratedValue
    @Column(name="member_index")
    private Long memberIndex;

    @Column(name="member_id")
    private String memberId;

    @Column(name="member_pw")
    private String memberPw;

    @Column(name="member_birth")
    private Date memberBirth;

    @Column(name="member_addr")
    private String memberAddr;

    @Column(name="member_email")
    private String memberEmail;

    @Column(name="member_nickname")
    private String memberNickname;

    @Column(name="member_category")
    private String memberCategory;

    @Column(name="member_state")
    private int memberState=1;



}