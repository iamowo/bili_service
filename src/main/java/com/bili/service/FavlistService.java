package com.bili.service;

import com.bili.entity.FavoristList;
import com.bili.entity.User;
import com.bili.entity.Video;
import com.bili.entity.outEntity.Addfavorite;
import com.bili.mapper.FavlistMapper;
import com.bili.mapper.UserMapper;
import com.bili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavlistService {
    @Autowired
    private FavlistMapper favlistMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;
    public List<FavoristList> getFavlist(Integer uid, Integer vid) {
        List<FavoristList> res = favlistMapper.getFavlist(uid);
        if (vid != -1) {             // 判断某个视频是否已经搜藏了
            judgeFav(res, vid);
        } else {
            for (int i = 0; i < res.size(); i++) {
                Integer fid = res.get(i).getFid();
                List<Integer> vids = favlistMapper.getAllVideoVid(fid);
                String cover = "";
                if (!vids.isEmpty()) {
                    cover = videoMapper.getByVid(vids.getFirst()).getCover();
                } else {
                    cover = "http://127.0.0.1:8082/sys/playlistbg.png";   // 没有视频时，默认的图片
                }
                res.get(i).setCover(cover);
            }
        }
        return res;
    }

    public void addFavlist(Integer uid, String title, Integer pub) {
        favlistMapper.addFavlist(uid, title, pub);
    }

    public void addOneFAV(Addfavorite addfavorite) {
        for (int i = 0; i < addfavorite.getFids().length; i++) {
            Integer uid = addfavorite.getUid();
            Integer vid = addfavorite.getVid();
            Integer fid = addfavorite.getFids()[i];
            if (addfavorite.getType()[i] == 1) {
                // 添加收藏
                favlistMapper.addOneFAV(uid, fid, vid);
                favlistMapper.addNuminlist(fid, 1);     // 给 列表中数量信息增加一个
                favlistMapper.addVideoFav(vid, 1);           // 给 video 中favorites + 1
            } else if (addfavorite.getType()[i] == -1) {
                // 取消收藏
                favlistMapper.deleteOneFAV(fid, vid);
                favlistMapper.addNuminlist(fid, -1);     // 给 列表中数量信息增加一个
                favlistMapper.addVideoFav(vid, -1);
            }
            // addfavorite.getFids()[i] == -0  不变
        }
    }

    public List<Video> getOneList(Integer fid) {
        List<Integer> vids = favlistMapper.getAllVideoVid(fid);
        List<Video> res = new ArrayList<>();
        for (int i = 0; i < vids.size(); i++) {
            Integer vid = vids.get(i);
            Video video = favlistMapper.getOneVideo(vid);
            res.add(video);
        }
        adduserinfo(res);
        return res;
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

    // 判单该视频是否收藏过了
    private void judgeFav(List<FavoristList> res, Integer vid) {
        for (int i = 0; i < res.size(); i++) {
            Integer uid = res.get(i).getUid();
            Integer fid = res.get(i).getFid();
            Integer had = favlistMapper.hadFavedThisVideo(fid, vid);
            if (had >= 1) {
                res.get(i).setCollected(true);
            } else {
                res.get(i).setCollected(false);
            }
        }
    }


    public void updateFav(Integer fid, String title, Integer pub) {
        favlistMapper.updateFav(fid, title, pub);
    }

    public void deleteFav(Integer fid) {
        favlistMapper.deleteFav(fid);
    }
}
