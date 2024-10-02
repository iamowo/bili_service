package com.bili.entity.Message;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WhisperCover {
    private Integer uid2;
    private String name;
    private String avatar;
    private String lastContent;
    private Timestamp lastTime;
}
