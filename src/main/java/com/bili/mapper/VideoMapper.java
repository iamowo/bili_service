package com.bili.mapper;

import com.bili.entity.*;
import com.bili.entity.temp.TagAndNums;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {
    List<Video> getAll();

    List<Video> getRecommend();

    void addInfos(Video video);

    void updateinfo(Video video);

    List<Video> getRandom();

    Video getByVid(Integer vid);

    void infos(Video video);


    void addWatch(Integer uid, Integer vid, Integer lastwatched, Integer done);

    void addLike(Integer uid, Integer vid, Integer type, Integer hisuid);

    void addIcon(Integer uid, Integer vid);

    void addFav(Integer uid, Integer vid, Integer fid);

    void addShare(Integer uid, Integer vid);

    Integer getPalys(Integer vid);

    Integer getDanmus(Integer vid);

    Integer getLikes(Integer vid);

    Integer getIcons(Integer vid);

    Integer getFavorites(Integer vid);

    Integer getShares(Integer vid);

    Integer getComments(Integer vid);

    Integer exist(Integer uid, Integer vid);

    void deletedLike(Integer uid, Integer vid);

    void chnageCommentNum(Integer num, Integer vid);

    void chnageCommentNumDynamic(Integer num, Integer did);

    Integer getUserLikes(Integer vid, Integer uid);

    Integer getUserIcon(Integer vid, Integer uid);

    Integer getUserFav(Integer vid, Integer uid);

    Watchinfo getLastWatch(Integer vid, Integer uid);

    Integer existedIcon(Integer uid, Integer vid);

    void updateWatchinfo(Integer id, Integer done, Integer lastwatched, String time, Integer deleted);

    List<Watchinfo> getHistory(Integer uid);

    void deleteHistory(Integer uid, Integer vid);

    void deleteAll(Integer uid);

    List<Video> getVideoByUid(Integer uid);

    List<Video> getVideoByUidNums(Integer uid, Integer nums);

    List<Video> getRank1();

    List<Video> getRank2();

    List<Video> getRank3();

    List<Video> getByKeyword(String keyword);

    List<Video> getHomeDynamicVideo(Integer uid, Integer start, Integer num);

    List<Watchinfo> getHomHistory(Integer uid, Integer start, Integer num);

    void sendDm(Dm dm);

    List<Dm> getDm(Integer vid);

    void updateVideoDm(Integer vid);

    List<String> searchName(String keyword);

    List<String> searchTilte(String keyword);

    List<Integer> searchUserName(String keyword);

    List<Video> getByDuration1(String keyword, Integer time1, Integer time2);

    List<Video> getFamous(Integer uid);

    List<Video> getUnfamous(Integer uid, String type);

    List<Video> getAuditVideo();

    void updatePass(Integer vid, Integer pass);

    List<Video> getAllv(Integer uid);

    List<VideoList> getUserVideoList(Integer uid);

    void addVideoList(VideoList videoList);

    Integer getNumsOfVideoList(Integer listid);

    List<Integer> getAllListVideo(Integer listid);

    List<Video> getByMaintag(String maintag);

    List<String> getAllMainTag();

    Integer videoNumsMainTag(String maintag);

    List<TagAndNums> getTandN();

    Video getVideoByHash(String hashValue);

    List<Video> getUnExitList(Integer uid);

    List<Video> getVideoFormList(Integer listid);

    void addVideoToList(VideoListInfo videoListInfo);

    VideoList getUserListOne(Integer listid);

    void deleteVideoList(Integer listid);

    void updateVideoInfoListid(Integer listid);

    void chanegListInfo(VideoList videoList);

    void insertAnimation(Animation animation);

    void insertAnimationList(AnimationList animationList);

    void uploadAnimationList(AnimationList animationList);

    void updateAnimationChpaters(Integer aid, int num);

    Integer getOneMounthPlays(Integer uid);

    Integer getOneMounthDms(Integer vid);

    Integer getOneMounthLikes(Integer uid);

    Integer getOneMounthShares(Integer vid);

    Integer getOneMounthCollects(Integer vid);

    Integer getOneMounthIcons(Integer vid);

    List<Video> getVideoByKeyword(Integer uid, String keyword);
}
