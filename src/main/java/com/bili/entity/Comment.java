package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Comment {
    private Integer id;
    private Integer uid;
    private Integer hisuid;
    private Integer vid;
    private String content;
    private Timestamp time;
    private Integer topid;  // 二级评论 顶层id
    private Integer fid;    // 二级评论父id
    private Integer likes;

    // append
    private String name;
    private String avatar;
    private String fname; // 回复的人的名字
    private List<Comment> lists;
    private Integer listslength;
}