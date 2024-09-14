package com.bili.entity.outEntity;

// 上传 番剧 等连续的

import org.springframework.web.multipart.MultipartFile;

public class UpVideo2 {
    private String title;
    private String Intro;
    private String type;  // 0 新上传  1 上传续集
    private String chaptertitle;
    private Integer chapter;

    private MultipartFile coverfile;
    private String covertype;
    private MultipartFile videofile;
    private String videotype;
}
