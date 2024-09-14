package com.bili.entity.outEntity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterUser {
    private String name;
    private MultipartFile avatarfile;
    private String account;
    private String password;
    private String intro;
    private Integer type;  // 0 只注册账号  1 带有信息
    private String filetype;
    private String birthday;
}
