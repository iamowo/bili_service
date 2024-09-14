package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VideoListInfo {
    private Integer id;
    private Integer listid;
    private Integer uid;
    private Integer vid;
    private Timestamp time;
}
