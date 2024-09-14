package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Animation {
    private Integer aid;
    private String title;
    private Integer chapters;
    private Timestamp publish;
    private String intro;
    private String cover;
    private Integer season;   // 第几季
    private Integer sid;      // 季 id
}
