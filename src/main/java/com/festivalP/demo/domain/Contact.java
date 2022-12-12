package com.festivalP.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="Contact")
@Table
@Getter @Setter
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue
    @Column(name="contact_index")
    private Long contactIndex;

    @Column(name="contact_name")
    private String contactName;

    @Column(name="contact_email")
    private String contactEmail;

//    private String contact_number;
    @Column(name="contact_text")
    private String contactText;
}
