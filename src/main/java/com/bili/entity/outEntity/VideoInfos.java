package com.bili.entity.outEntity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VideoInfos {
    private Integer type; // 0 watch  1 like  2 icon  3 fav  4 share
    private Integer vid;
    private Integer uid; // up的uid
    private Integer hisuid; // 观看的人的uid
    //private Timestamp time1; // watch
    private Integer deleted;
    private Integer lastwatched;
    private Integer done;

    private Integer listid;
    private Integer icons;
    //private Timestamp time2;  // like

    //private Timestamp time3;  // icon

    private Integer fid;
    //private Timestamp time4;  // favorite
}
