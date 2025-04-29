package com.bili.mapper;

import com.bili.entity.TagMainList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    Integer existTag(String tagName);

    void insertTag(String tagName, Integer type, Integer rId);

    List<TagMainList> getOneTagType(Integer type);

    void insertOneTag(String tagName, Integer type);
}
