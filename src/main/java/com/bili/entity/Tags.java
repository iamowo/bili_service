package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Tags {
    private Integer id;
    private String tagName;
    private Integer type;
    private Integer rId;
    private Timestamp time;
}
