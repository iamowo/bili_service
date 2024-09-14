package com.bili.entity.outEntity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Audit {
    private Integer vid;
    private Integer uid;
    private Timestamp rtime;  // 审核结果时间
    private String content;
    private Integer pass; // 2 不通过  1通过
    private String title;
}
