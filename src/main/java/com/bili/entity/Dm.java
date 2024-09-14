package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Dm {
    private Integer id;
    private Integer uid;
    private Integer vid;
    private Integer sendtime;
    private String text;
    private String color;
    private Integer type;
    private Timestamp time;
}
