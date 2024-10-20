package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WhisperList {
    private Integer wid;
    private Integer uid1;
    private Integer uid2;
    private Integer deleted;
    private Timestamp time;
}
