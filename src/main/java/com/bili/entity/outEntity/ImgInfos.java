package com.bili.entity.outEntity;

import lombok.Data;

import java.util.List;

@Data
public class ImgInfos {
    // 图片id
    // 图片信息
    private Integer id;
    private String path;
    private Integer height;
    private String title;
    private Integer likes;
    private Integer collects;
    private String maintag;
    private String tags;
    // 是否点赞
    private Boolean liked;
    // 是否搜藏
    private Boolean collected;
    // 用户信息
    private Integer uid;
    private String name;
    private String avatar;
    private List<String> taglist;
}
