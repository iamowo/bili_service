package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadVideoInfos {
    private String title;
    private Integer duration;
    private String intro;
    private Integer uid;
    private MultipartFile cover;
    private String covertype;

    private String maintag;
    private String othertags;
}
