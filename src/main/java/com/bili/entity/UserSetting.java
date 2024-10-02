package com.bili.entity;

import lombok.Data;

@Data
public class UserSetting {
    private Integer uid;
    // 空间设置
    private Integer favlist;
    private Integer followlist;
    private Integer likerecently;
    private Integer fanslist;
    private Integer birthday;
    private Integer donate;

    // 其他设置
    private Integer history;   // 记录观看历史
}
