package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadMgImg {
    private String id;
    private String path;
    private String title;
    private String type;
    private Integer mid;
    private String name;  // 章节名
    private Integer number; // 章节号码
    private MultipartFile imgfile;
    private Integer ind;  // 
}
