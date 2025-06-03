package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Animation {
    private Integer aid;
    private Integer uid;
    private String title;
    private Timestamp publish;
    private Timestamp time;
    private String intro;
    private String cover;
    private Integer chapters;
    private Integer deleted;

    // append
    private List<Integer> vids;
    private Boolean liked;

    private Integer plays;  // 播放次数
    private Integer dms;    // 弹幕数
    private Integer subs;   // 追番人数
}
