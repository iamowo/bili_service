package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LikeInfo {
    private Integer id;
    private Integer uid;
    private Integer hisuid;
    private Integer type;   // 0 对视频点赞  1 对动态带你赞    2 对别人的评论点赞
    private Integer vid;
    private Integer did;
    private Integer cid;  // 评论id
    private Integer mid;  // 漫画
    private Timestamp time;

    private String title; // 点赞的视频
    private String text;  // 点赞的评论

    private String name;
    private String avatar;
}
