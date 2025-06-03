package com.bili.entity.PO;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ImgLike {
    private Integer id;
    private Integer imgId;
    private Integer uid;
    private Timestamp time;
}
