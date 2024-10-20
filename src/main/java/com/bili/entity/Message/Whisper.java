package com.bili.entity.Message;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
public class Whisper {
    private Integer id;
    private Integer uid1;
    private Integer uid2;
    private Integer type;
    private Timestamp time;
    private String content;
    private Integer wid;

    private MultipartFile img;
    private String filetype;
}
