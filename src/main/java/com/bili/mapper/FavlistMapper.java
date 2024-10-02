package com.bili.mapper;

import com.bili.entity.FavoristList;
import com.bili.entity.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavlistMapper {
    List<FavoristList> getFavlist(Integer uid);

    void addFavlist(Integer uid,String title, Integer pub);

    void addOneFAV(Integer uid, Integer fid, Integer vid);

    List<Integer> getAllVideoVid(Integer fid);

    Video getOneVideo(Integer vid);

    void addNuminlist(Integer fid, int num);

    void addVideoFav(Integer vid, int num);

    Integer hadFavedThisVideo(Integer fid, Integer vid);

    void deleteOneFAV(Integer fid, Integer vid);

    void updateFav(Integer fid, String title, Integer pub);

    void deleteFav(Integer fid);

    void addDefaultFavlist(FavoristList favoristList);

    void deleteVideoFromFav(Integer fid, Integer vid);

    void changeFavnums(Integer fid, int num);

    void deleteAllVideo(Integer fid);
}
