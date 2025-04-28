package com.bili.service;

import com.bili.entity.Tags;
import com.bili.entity.TagsMain;
import com.bili.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void insertTagMain(String tagName, Integer type, Integer rId) {
        Integer exist = tagMapper.existTagMain(tagName);
        if (exist == 0) {
            tagMapper.insertTagMain(tagName, type, rId);
        }
    }
}
