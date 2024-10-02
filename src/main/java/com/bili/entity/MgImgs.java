package com.bili.entity;

import lombok.Data;

@Data
public class MgImgs {
    private Integer id;
    private Integer mid;
    private String path;
    private Integer number; // 章节号码
    private String name;
    private Integer ind;   // 图片顺序
}
