package com.bili.entity.PO;

import lombok.Data;

@Data
// 分裂
public class TagMainList {
    private Integer id;
    private String tagName;
    // 0视频 1漫画 2图片
    private Integer type;
}
