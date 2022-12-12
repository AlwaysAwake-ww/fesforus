package com.festivalP.demo.form;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class AuthInfo {

    private String id;
    private int state;

}
