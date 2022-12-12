package com.festivalP.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;


@Entity // 테이블과 링크 될 클래스
@Table(name = "ADMIN")
@Getter
@Setter
public class Admin {


    @Id // 해당 테이블의 PK 항목
    @GeneratedValue // PK 생성 규칙
    @Column (name="admin_index")// 테이블의 칼럼,
    private Long adminIndex;
    
    @Column (name="admin_id")
    private String adminId;

    @Column (name="admin_pw")
    private String adminPw;
}
