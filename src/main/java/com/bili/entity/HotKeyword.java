package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class HotKeyword {
    private Integer kid;
    private Timestamp time;
    private String keyword;
    private Integer uid;
}
