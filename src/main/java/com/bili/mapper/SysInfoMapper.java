package com.bili.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysInfoMapper {
    void addInfo(Integer uid, String title, String content);
}
