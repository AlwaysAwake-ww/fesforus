package com.festivalP.demo.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavoritePK implements Serializable {
    private Long postNum;
    private Long memberIndex;

}
