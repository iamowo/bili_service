package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Mg {
    private Integer mid;
    private Integer uid;
    private String title;
    private String author;
    private String tags;
    private String mtag;
    private String intro;
    private Integer chapters;  // 章节个数
    private Timestamp time;
    private String cover;
    private Integer done;
    private Integer favorited;
    private Integer reads;
    private Integer deleted;

    // append
    private String[] taglist;
    private boolean collected;  // 是否收藏
}
