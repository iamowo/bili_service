package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class At {
    private Integer atid;
    private String atname;
    private String attitle;      // 在视频中at的话，为视频标题， 在动态中at的话为动态内容（太长的话部分），在评论中at的话评论内容（部分）
    private Integer uid1;
    private Integer uid2;
    private Integer vid;
    private Integer cid;
    private Integer did;
    private Timestamp time;
    private Integer type;       // 0视频评论  1动态评论  2二级评论（视频和动态）  3 动态内容
    // append
    private String name;
    private String avatar;
}
