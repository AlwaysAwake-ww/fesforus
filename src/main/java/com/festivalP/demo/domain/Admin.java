package com.festivalP.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "ADMIN")
@Getter
@Setter
public class Admin {


    @Id
    @GeneratedValue
    @Column (name="admin_index")
    private Long adminIndex;
    
    @Column (name="admin_id")
    private String adminId;

    @Column (name="admin_pw")
    private String adminPw;
}
