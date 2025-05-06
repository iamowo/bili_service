package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Tags {
    private Integer id;
    private String tagName;
    private Integer type;
    // 资源id
    private Integer rId;
    private Timestamp time;
    private Integer fid;
}
