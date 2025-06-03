package com.bili.entity.PO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
