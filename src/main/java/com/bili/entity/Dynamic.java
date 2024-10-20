package com.bili.entity;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Dynamic {
    private Integer id;
    private Integer uid;
    private Timestamp time;
    private String content;
    private Integer shares;
    private Integer likes;
    private Integer comments;
    private List<String> imgs;
    private Integer type;     // 0 图文类型  1 视频  2 动态
    private Integer vordid;   // vid or did（动态id）
    private Integer deleted;
    private String topical;   // 话题
    // ..
    private String name;
    private String avatar;

    // 视频类型
    private Video video;

    // 转发别人的动态
    private Dynamic dy2;

    private Boolean liked;   // 点过赞
}
