package com.bili.service;

import com.bili.entity.FavoristList;
import com.bili.entity.User;
import com.bili.entity.UserSetting;
import com.bili.entity.Video;
import com.bili.entity.outEntity.RegisterUser;
import com.bili.entity.outEntity.UpdateUser;
import com.bili.entity.outEntity.UserData;
import com.bili.mapper.CommentMapper;
import com.bili.mapper.FavlistMapper;
import com.bili.mapper.UserMapper;
import com.bili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {
    @Value("${files.avatarPath}")
    private String avatarPath;
    @Value("${files.avatarNet}")
    private String avatarNet;
    @Value("${files.videoPath}")
    private String videoPath;
    @Value("${url2}")
    private String neturl;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FavlistMapper favlistMapper;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private CommentMapper commentMapper;

    public User getByUid(Integer uid) {
        User res = userMapper.getByUid(uid);
        return res;
    }

    public User getByUidFollowed(Integer uid, Integer myuid) {
        User res = userMapper.getByUid(uid);
        if (uid == myuid) {
            res.setFollowed(false);
            return res;
        }
        if (myuid >= 0 && !Objects.equals(uid, myuid)) {
            Integer exist = userMapper.judgeFollow(uid, myuid);
            if (exist >= 1) {
                res.setFollowed(true);
            } else {
                res.setFollowed(false);
            }
        }
        return res;
    }
    public Integer login(String account, String password) {
        User res1 = userMapper.findAccount(account);
        if (res1 != null) {
            if (Objects.equals(res1.getPassword(), password)) {
                return 2; // 密码正确
            } else {
                return 1; // 错误
            }
        } else {
            // 没有此账号
            return 0;
        }
    }

    public Integer register(RegisterUser registerUser) throws IOException {
        Integer r = userMapper.findAccount2(registerUser.getAccount());
        if (r > 0) {
            return 201;    // 存在此账号
        }
        User user = new User();
        user.setAccount(registerUser.getAccount());
        user.setPassword(registerUser.getPassword());
        userMapper.addUser(user);
        Integer uid = user.getUid();  // xml 配置，可以获取uid

        userMapper.addUserSettnig(uid);  // 添加user_setting表
        FavoristList favoristList = new FavoristList();
        favoristList.setPub(0);
        favoristList.setTitle("默认收藏夹");
        favoristList.setUid(uid);
        // 创建默认视频收藏夹
        favlistMapper.addDefaultFavlist(favoristList);
        Integer fid = favoristList.getFid();   // 获得生成的fid
        user.setDefaultfid(fid);   // 默认收藏夹fid

        // 创建默认图片收藏夹（喜欢）
        userMapper.createLikeBoard(uid);
        if (registerUser.getType() == 0) {
            user.setName("bili_user#" + uid);
//            user.setAvatar("http://127.0.0.1:8082/avatar/default.png");
            user.setAvatar(neturl + "/avatar/default.png");
        } else {
            user.setName(registerUser.getName());
            user.setIntro(registerUser.getIntro());
            UUID uuid = UUID.randomUUID();
            String avataPath = avatarPath + uuid + "." + registerUser.getFiletype();
            String netPath = avatarNet + uuid + "." + registerUser.getFiletype();
            user.setAvatar(netPath);
            File file = new File(avataPath);
            registerUser.getAvatarfile().transferTo(file);
        }
        userMapper.giveInfo(user);            // 用户添加信息

        String resourcePath = videoPath + uid;
        File coverFile = new File(resourcePath + "/cover");
        coverFile.mkdirs();
        File tempFile = new File(resourcePath + "/temp");
        tempFile.mkdirs();
        File videoFile = new File(resourcePath + "/video");
        videoFile.mkdirs();
        File imgFile = new File(resourcePath + "/imgs");
        imgFile.mkdirs();
        return 100;
    }

    public void toFollow(Integer uid1, Integer uid2) {
        Integer exist = userMapper.findINFollow(uid1, uid2);
        if (exist == 1) {
            userMapper.changeFollow(uid1, uid2, 0);
        } else {
            userMapper.addFollow(uid1, uid2);
        }
        userMapper.addFollowNum1(uid1, 1);   // 给uid加一个粉丝
        userMapper.addFollowNum2(uid2, 1);   //  给uid2借一个关注
    }

    public void toUnfollow(Integer uid1, Integer uid2) {
        userMapper.changeFollow(uid1, uid2, 1);
        userMapper.addFollowNum1(uid1, -1);
        userMapper.addFollowNum2(uid2, -1);
    }

    public void updateUserinfo(UpdateUser updateUser) throws IOException {
        if (updateUser.getAvatarfile() != null) {
            UUID uuid = UUID.randomUUID();
            String type = updateUser.getAvatarfile().getOriginalFilename().substring(updateUser.getAvatarfile().getOriginalFilename().lastIndexOf("."));
            File avatarFile = new File(avatarPath + uuid + type);
            updateUser.getAvatarfile().transferTo(avatarFile);
            updateUser.setAvatar(avatarNet + uuid + type);
        }
        userMapper.updateinfo(updateUser);
    }

    public void updateUserdata(UserData userData) {
        userMapper.updateUser1(userData);
    }

    public List<User> searchUser(String keyword, Integer uid, Integer sort) {
        List<User> res = userMapper.getUserByKeyword(keyword);
        if (uid >= 0) {
            followinfos(uid, res);
        }
        if (sort == 1) {
            Collections.sort(res, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getFans() - o1.getFans();
                }
            });
        } else if (sort == 2) {
            Collections.sort(res, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getFans() - o2.getFans();
                }
            });
        } else if (sort == 3) {
            Collections.sort(res, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.getLv() - o1.getLv();
                }
            });
        } else if (sort == 4) {
            Collections.sort(res, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.getLv() - o2.getLv();
                }
            });
        }
        return res;
    }

    // 是否关注
    private void followinfos(Integer uid, List<User> res) {
        for (int i = 0; i < res.size(); i++) {
            Integer uid1 = res.get(i).getUid();
            Integer temp = userMapper.judgeFollow(uid1, uid);
            if (temp == 1) {
                res.get(i).setFollowed(true);
            } else {
                res.get(i).setFollowed(false);
            }
        }
    }

    public Map<String, Object> getFollow(Map<String, Object> map) {
        Integer uid = (Integer) map.get("uid");
        Integer page = (Integer) map.get("page");
        Integer nums = (Integer) map.get("nums");
        String keyword = (String) map.get("keyword");

        Map<String, Object> res = new HashMap<>();
        Integer len = userMapper.getFollowsLength(uid, keyword);
        List<Integer> uids = userMapper.finfAllFollows(uid, (page - 1) * nums, nums, keyword);
        List<User> users = new ArrayList<>();
        for (Integer uid2 : uids) {
            User u = userMapper.getByUid(uid2);
            users.add(u);
        }
        res.put("list", users);
        res.put("len", len);
        return res;
    }

    public Map<String ,Object> getFans(Map<String, Object> map) {
        Integer uid = (Integer) map.get("uid");
        Integer page = (Integer) map.get("page");
        Integer nums = (Integer) map.get("nums");
        String keyword = (String) map.get("keyword");

        Map<String, Object> res = new HashMap<>();
        Integer len = userMapper.getFansLength(uid, keyword);
        List<Integer> uids = userMapper.getFans(uid, (page - 1) * nums, nums, keyword);
        List<User> users = new ArrayList<>();
        for (Integer uid2 : uids) {
            User u = userMapper.getByUid(uid2);
            users.add(u);
        }
        res.put("list", users);
        res.put("len", len);
        return res;
    }

    public Integer findAccount(String account) {
        Integer r = userMapper.findAccount2(account);
        if (r == 1) {
            return 201;
        } else {
            return 202;
        }
    }

    public UserSetting getSetting(Integer uid) {
        return userMapper.getSetting(uid);
    }

    public void changeSetting(UserSetting userSetting) {
        userMapper.changeSetting(userSetting);
    }

    public void updateTempToken(String token, Integer uid) {
        userMapper.updateTempToken(token, uid);
    }

    public Map<String, Integer> getUserData(Integer uid) {
        Map<String, Integer> res = new HashMap<>();
        res.put("fans", userMapper.getOneMounthFans(uid));
        res.put("plays", videoMapper.getOneMounthPlays(uid));
        res.put("comments", commentMapper.getOneMounthComments(uid));
        res.put("likes", videoMapper.getOneMounthLikes(uid));

        List<Video> vids = videoMapper.getAllv(uid);
        int dms = 0, shares = 0, collects = 0, icons = 0;
        for (int i = 0; i < vids.size(); i++) {
            Integer thisvid = vids.get(i).getVid();
            dms += videoMapper.getOneMounthDms(thisvid);
            shares += videoMapper.getOneMounthShares(thisvid);
            collects += videoMapper.getOneMounthCollects(thisvid);
            icons += videoMapper.getOneMounthIcons(thisvid);
        }
        res.put("dms", dms);
        res.put("shares", shares);
        res.put("collects", collects);
        res.put("icons", icons);
        return res;
    }

    public List<User> AllUser() {
        List<User> res = userMapper.AllUser();
        return res;
    }
}
