package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SysInfo {
    private Integer id;
    private Timestamp time;
    private String content;
    private Integer uid;
    private String title;
}
