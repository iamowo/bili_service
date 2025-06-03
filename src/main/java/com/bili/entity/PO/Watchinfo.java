package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Watchinfo {
    private Integer id;
    private Integer uid;  // 观看人的id
    private Integer vid;
    private Integer deleted;
    private Integer lastwatched;
    private Integer done;
    private Timestamp time;

    private String watchtype;  // 格式化后的时间
    // append
    private String cover;
    private String title;
    private Integer upuid;  // 视频up的id
    private String tag;   // main tag
    private String upname;
    private String upavatar;

}
