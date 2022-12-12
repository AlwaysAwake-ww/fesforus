package com.festivalP.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryPK implements Serializable {
    private Long memberIndex;
    private String categoryClass;

}
