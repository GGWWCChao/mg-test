package com.majiang.mgtest.model;

import lombok.Data;

import java.time.Period;
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long mgtModified;
    private Long mgtModifie;
    private Long create;
    private Integer commontCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
}
