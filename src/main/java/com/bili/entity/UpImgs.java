package com.bili.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
    
import java.util.List;

@Data
public class UpImgs {
    private Integer uid;
    private Integer imgId;
    private String type;
    private MultipartFile file;
    private Integer height;
    // ===
    private String netPath;
    private Integer ind;
}
