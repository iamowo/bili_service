package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OneChunk {
    // 一个切片
    private Integer vid;
    private Integer uid;
    private String filename;
    private MultipartFile file;
    private String path;
    private Integer index;
    private Integer chunsnum;
}
