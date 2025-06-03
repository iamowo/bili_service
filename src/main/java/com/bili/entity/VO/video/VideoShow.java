package com.bili.entity.VO.video;

import lombok.Data;

/*
*   视频-概况信息
* */
@Data
public class VideoShow {
    private Integer id;
    private String cover;
    private String title;
    private Integer watch;
    private Integer dm;
    private Integer comment;
}
