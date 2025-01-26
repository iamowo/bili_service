package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ImgPath {
    private Integer id;
    private Integer imgId;
    private String path;
    private Integer height;
    private Timestamp time;
    private Integer ind;
}
