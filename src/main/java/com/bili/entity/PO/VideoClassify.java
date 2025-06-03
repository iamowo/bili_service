package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VideoClassify {
    private Integer id;
    private String value;
    private Integer type;
    private Integer topid;
    private Integer ind;
    private Timestamp time;
}
