package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VideoList {
    private Integer listid;
    private Integer uid;
    private String title;
    private String intro;
    private Timestamp time;
    //append
    private Integer nums;
    private String cover;
}
