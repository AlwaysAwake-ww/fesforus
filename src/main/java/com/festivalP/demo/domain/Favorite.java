package com.festivalP.demo.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FAVORITE")
@Getter
@Setter
@NoArgsConstructor
@IdClass(FavoritePK.class)
public class Favorite {

    @Id
    @Column(name="member_index")
    private Long memberIndex;

    @Id
    @Column(name="post_num")
    private Long postNum;


}



