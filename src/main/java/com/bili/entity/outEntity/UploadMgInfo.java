package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadMgInfo {
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
    // 要返回的mid
    private Integer mid;
}