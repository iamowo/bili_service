package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

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
    private Integer favorited; // 收藏个数
    private Integer reads;
    private Integer deleted;
    private Integer comments;
    private Integer score;

    // append
    private List<String> taglist;
    private boolean collected;  // 是否收藏
}
