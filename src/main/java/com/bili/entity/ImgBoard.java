package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ImgBoard {

    private Integer id;
    private Integer nums;
    private Integer uid;
    private Integer type;
    private String title;
    private String tags;
    private String intro;
    private Timestamp time;

    // 最新收藏的作为封面
    private String path;
}
