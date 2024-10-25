package com.bili.controler;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Donate {
    private Integer id;
    private Float cache;
    private Timestamp time;
    private Integer uid1;
    private Integer uid2;
}
