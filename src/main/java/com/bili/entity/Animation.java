package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Animation {
    private Integer aid;
    private Integer uid;
    private String title;
    private Timestamp publish;
    private Timestamp time;
    private String intro;
    private String cover;
    private Integer chapters;

    // append
    private List<Integer> vids;
}
