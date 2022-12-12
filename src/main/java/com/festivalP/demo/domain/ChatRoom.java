package com.festivalP.demo.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CHAT_ROOM")
@Getter
@Setter
@NoArgsConstructor
//@IdClass(FavoritePK.class)
public class ChatRoom {

    @Id
    @GeneratedValue
    @Column(name="chatroom_id")
    private Long chatroomId;

    @Column(name="post_num")
    private String postNum;

}



