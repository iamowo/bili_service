package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OneChunk {
    private Integer vid;
    private Integer uid;
    private String filename;
    // append
    private MultipartFile file;
    private String path;
}
