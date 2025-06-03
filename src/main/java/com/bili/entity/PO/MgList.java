package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MgList {
    private Integer id;
    private Integer mid;
    private Integer uid;
    private Timestamp time;
    private Integer type;
    private Integer deleted;
    private Integer watchpage;
    private Integer watchtype;
    private Integer number;

    // append
    private Integer num;
    private Integer[] midlist;
}
