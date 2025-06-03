package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Follow {
    private Integer id;
    private Integer uid1;
    private Integer uid2;
    private Timestamp time;
    private Integer deleted;
}
