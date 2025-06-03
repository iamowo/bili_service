package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Banner {
    private Integer id;
    private Integer type;
    private Integer targetId;
    private String cover;
    private String title;
    private String bgc;
    private Integer ind;
    private Timestamp time;

    // append
    private String coverfile;
}
