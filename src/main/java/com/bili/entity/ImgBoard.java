package com.bili.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ImgBoard {

    private Integer id;
    private Integer nums;
    private Integer uid;
    // 0 喜欢，每个用户创建账号后默认一个，  1 自建收藏夹
    private Integer type;
    private String title;
    private String tags;
    private String intro;
    private Timestamp time;
    // 喜欢数量
    private Integer likes;
    // 采集数量
    private Integer collects;
    // 最新收藏的作为封面
    private List<String> coverlist;
}
