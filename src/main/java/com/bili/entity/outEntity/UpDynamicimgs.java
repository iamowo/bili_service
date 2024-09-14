package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpDynamicimgs {
    private Integer id;
    private Integer uid;
    private Integer did;
    private MultipartFile img;
    private String type;
}
