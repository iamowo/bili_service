package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
// 主标签（分类）
public class TagsMain {
    private Integer id;
    private String tagName;
    private Integer type;
    // 资源id
    private Integer rId;
    private Timestamp time;
}
