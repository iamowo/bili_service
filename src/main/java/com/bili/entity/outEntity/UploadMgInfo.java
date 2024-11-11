package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadMgInfo {
    private Integer uid;
    private String cover;
    private String title;
    private String author;
    private String intro;
    private String tags;
    private String[] taglist;
    private Integer done;  // 0 未完结  1 已完结
    private Integer chapters;
    private String name;  // 章节名
    private Integer number; // 章节index
    private String mtag;  // 主标签
    private String coverfile;
    private Integer type;
    // type == 1
    private Integer season;   // 第几季
    private Integer chapter;
    private Integer chaptertitle;
    // 要返回的mid
    private Integer mid;
}
