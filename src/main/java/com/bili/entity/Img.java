package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Img {
    private Integer id;
    private String title;
    private String intro;
    // 数量
    private Integer nums;
    private Integer uid;
    private Integer likes;
    private Integer collects;
    private Timestamp time;
}
