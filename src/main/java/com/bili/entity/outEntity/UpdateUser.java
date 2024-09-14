package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUser {
    private Integer uid;
    private MultipartFile avatarfile;
    private String avatar;
    private String birthday;
    private String intro;
    private String password;
    private String gonggao;
}
