package com.bili.entity.VO.video;

import lombok.Data;
/*
*   视频概略信息
* */
@Data
public class VideoInfo {
    private Integer id;
    private String title;
    private String introduction;
    private Integer uid;
    // 用户名称
    private String name;
    private String cover;
    private Integer views;
    private Integer dms;
}
