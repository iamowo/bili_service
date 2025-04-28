package com.bili.mapper;

import com.bili.entity.Tags;
import com.bili.entity.TagsMain;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    Integer existTag(String tagName);

    Integer existTagMain(String tagName);

    void insertTag(String tagName, Integer type, Integer rId);

    void insertTagMain(String tagName, Integer type, Integer rId);
}
