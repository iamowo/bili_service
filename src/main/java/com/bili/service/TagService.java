package com.bili.service;

import com.bili.entity.TagMainList;
import com.bili.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    public void insertTag(String tagName, Integer type, Integer rId) {
        Integer exist = tagMapper.existTag(tagName);
        if (exist == 0) {
            tagMapper.insertTag(tagName, type, rId);
        }
    }

    public List<TagMainList> getOneTagType(Integer type) {
        return tagMapper.getOneTagType(type);
    }

    public void insertOneTag(String tagName, Integer type) {
        tagMapper.insertOneTag(tagName, type);
    }
}
