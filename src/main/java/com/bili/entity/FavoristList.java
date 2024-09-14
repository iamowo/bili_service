package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FavoristList {
    private Integer fid;
    private Integer uid;
    private String title;
    private Timestamp time;
    private Integer pub;  // 0 公开   1 私密

    // append
    private String cover; // 第一个视频的封面
    private Integer nums;  // 数据库不记录
    private Boolean collected; // 是否已经收藏了
}
