package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AnimationSublist {
    private Integer id;
    private Integer uid;
    private Integer aid;
    private Integer deleted;
    private Timestamp time;
}
