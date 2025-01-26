package com.bili.mapper;

import com.bili.entity.User;
import com.bili.entity.UserSetting;
import com.bili.entity.Video;
import com.bili.entity.outEntity.UpdateUser;
import com.bili.entity.outEntity.UserData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User findAccount(String account);

    void addUser(User user);

    User findUid(Integer uid);

    User getByUid(Integer uid);

    void addFollow(Integer uid1, Integer uid2);

    void addFollowNum1(Integer uid1, Integer num);

    void addFollowNum2(Integer uid2, Integer num);

    void updateinfo(UpdateUser updateUser);

    void updateUser1(UserData userData);

    List<Integer> getUidByKeyword(String keyword);

    void touseIocn(Integer uid, Integer icons);

    List<User> getUserByKeyword(String keyword);

    Integer judgeFollow(Integer uid1, Integer uid2);

    void changeFollow(Integer uid1, Integer uid2, Integer num);

    Integer findINFollow(Integer uid1, Integer uid2);

    void updateFamous(Integer vid, int num);

    List<Integer> finfAllFollows(Integer uid, Integer page, Integer nums, String keyword);
    List<Integer> getFans(Integer uid, Integer page, Integer nums, String keyword);

    void userdeletevideo(Integer vid);

    void userChnageInfo(Video video);

    void giveInfo(User user);

    Integer findAccount2(String account);

    void addUserSettnig(Integer uid);

    UserSetting getSetting(Integer uid);

    void changeSetting(UserSetting userSetting);

    void updateTempToken(String token, Integer uid);

    Integer getOneMounthFans(Integer uid);

    Integer getFansLength(Integer uid, String keyword);

    Integer getFollowsLength(Integer uid, String keyword);

    List<User> AllUser();
}
