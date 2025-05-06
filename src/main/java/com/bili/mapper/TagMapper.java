package com.bili.mapper;

import com.bili.entity.Mg;
import com.bili.entity.TagMainList;
import com.bili.entity.Tags;
import com.bili.entity.Video;
import org.apache.ibatis.annotations.Mapper;

import java.awt.*;
import java.util.List;

@Mapper
public interface TagMapper {
    Integer existTag(String tagName, Integer type, Integer fid);

    void insertTag(String tagName, Integer type, Integer rId, Integer fid);

    List<TagMainList> getMainTag(Integer type, Integer limit);

    void insertOneTag(String tagName, Integer type);


    List<Tags> getTags(Integer fid, Integer type, String name, Integer flag, Integer page, Integer limit);

    List<Video> getVideos(List<Integer> idList);

    List<Mg> getMgs(List<Integer> idList);

    List<Image> getImages(List<Integer> idList);
}
