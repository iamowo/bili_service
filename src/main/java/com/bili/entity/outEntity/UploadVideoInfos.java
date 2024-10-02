package com.bili.entity.outEntity;

import lombok.Data;

@Data
public class UploadVideoInfos {
    private String title;
    private Integer duration;
    private String intro;
    private Integer uid;
    private String hashValue;
//    private MultipartFile cover;
//    private String covertype;
    private String cover;    // 截取后时base64格式
    private Integer listid;

    private String maintag;
    private String othertags;
}
