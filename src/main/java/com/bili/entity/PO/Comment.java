package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Comment {
    private Integer id;
    private Integer uid;
    private Integer hisuid;
    private Integer vid;  // 视频id
    private Integer mid;  // 漫画id
    private Integer cid;  //
    private Integer did;  // 动态id
    private String content;
    private Timestamp time;
    private Integer topid;  // 二级评论 顶层id
    private Integer fid;    // 二级评论父uid
    private String replaycontent;
    private Integer likes;
    private Integer type;   // 0视频   1 动态   2评论  3 漫画
    private Integer atid;
    private Integer replaycid;
    // append
    private boolean liked;        // 是都点过攒了
    private String name;
    private String avatar;
    private String fname;         // 回复的人的名字
    private List<Comment> lists;
    private Integer listslength;
    // input
    private Integer atuid;    // @的人的uid
    private String atname;    // @的人的name
}
