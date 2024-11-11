package com.bili.entity;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class Video{
    private Integer vid;
    private String cover;
    private String path;
    private String title;
    private String intro;
    private Timestamp time;
    private Timestamp passtime;
    private String vidlong;
    private Integer duration;
    private Integer uid;
    private Integer plays;
    private Integer danmus;
    private Integer likes;
    private Integer icons;
    private Integer favorites;
    private Integer shares;
    private Integer comments;
    private Integer pass;   // 0 审核中  1 通过  2 审核失败   3 删除
    private String maintag;
    private String othertags;
    private String hashValue;
    private Integer listid;    // 属于那个列表
    private Integer type;      // 0 自上传    1 连续剧
    private Integer aid;

    // append info
//    private List<String> tags;
    private String[] tags;
    private String name;
    private String avatar;
    private String userintro;

    // userinfoappend
    private Integer lastweatched;
    private Integer done;
    private boolean liked;
    private boolean iconed;
    private boolean faved;
}
