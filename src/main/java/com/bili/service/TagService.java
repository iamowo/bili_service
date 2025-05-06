package com.bili.service;

import com.bili.entity.*;
import com.bili.mapper.TagMapper;
import com.bili.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserMapper userMapper;

    public void insertTag(String tagName, Integer type, Integer rId, Integer fid) {
//        Integer exist = tagMapper.existTag(tagName, type, fid);
//        if (exist == 0) {
//            tagMapper.insertTag(tagName, type, rId, fid);
//        }
        tagMapper.insertTag(tagName, type, rId, fid);
    }

    public List<TagMainList> getMainTag(Integer type, Integer limit) {
        return tagMapper.getMainTag(type, limit);
    }

    public void insertOneTag(String tagName, Integer type) {
        tagMapper.insertOneTag(tagName, type);
    }

    public List<Tags> getTags(Integer fid,  Integer type, String name, Integer flag, Integer page, Integer limit) {
        return tagMapper.getTags(fid, type, name, flag, page, limit);
    }

    public List<Video> getVideosByTag(String tag, Integer page, Integer limit) {
        List<Tags> tags = tagMapper.getTags(0, 0, "", 1, 0, 0);
        List<Integer> idList = tags.stream()
                               .map(Tags::getRId) // 提取id
                              .toList(); // 收集到list
        List<Video> res =  tagMapper.getVideos(idList);
        adduserinfo(res);
        return res;
    }

    public List<Mg> getMgsByTag(String tag, Integer page, Integer limit) {
        List<Tags> tags = tagMapper.getTags(0, 0, "", 1, 0, 0);
        List<Integer> idList = tags.stream()
                .map(Tags::getRId) // 提取id
                .toList(); // 收集到list
        return tagMapper.getMgs(idList);
    }

    public List<Image> getImagesByTag(String tag, Integer page, Integer limit) {
        List<Tags> tags = tagMapper.getTags(0, 0, "", 1, 0, 0);
        List<Integer> idList = tags.stream()
                .map(Tags::getRId) // 提取id
                .toList(); // 收集到list
        return tagMapper.getImages(idList);
    }

    // 加工视频信息
    private void adduserinfo(List<Video> video) {
        for (int i = 0; i < video.size(); i++) {
            Integer uid = video.get(i).getUid();
            User user = userMapper.findUid(uid);
            video.get(i).setName(user.getName());
            video.get(i).setAvatar(user.getAvatar());
            video.get(i).setUserintro(user.getIntro());
        }
    }
}
