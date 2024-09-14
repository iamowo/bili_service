package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer uid;
    private String name;
    private String avatar;
    private String birthday;
    private Timestamp time;
    private String account;
    private String password;
    // 数据信息
    private Integer icons;
    private Integer lv;
    private Integer fans;
    private Integer follows;
    private Integer likes;
    private Integer plays;
    private Integer videos;
    private Integer dynamics;
    private Integer favlists;
    private Integer animas;
    // 默认收藏夹id，需要手动创建
    private Integer defaultfid;
    private String intro;
    private String gonggao;
    private Integer permissions;  // 权限等级

    // append
    private String token;
    private Boolean followed;  // 是否关注了
}
