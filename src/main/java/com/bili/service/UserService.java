package com.bili.service;

import com.bili.entity.FavoristList;
import com.bili.entity.User;
import com.bili.entity.UserSetting;
import com.bili.entity.Video;
import com.bili.entity.outEntity.RegisterUser;
import com.bili.entity.outEntity.UpdateUser;
import com.bili.entity.outEntity.UserData;
import com.bili.mapper.FavlistMapper;
import com.bili.mapper.UserMapper;
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

    public User getByUid(Integer uid) {
        User res = userMapper.getByUid(uid);
        return res;
    }

    public User getByUidFollowed(Integer uid, Integer myuid) {
        User res = userMapper.getByUid(uid);
        if (myuid >= 0 && !Objects.equals(uid, myuid)) {
            Integer exist = userMapper.judgeFollow(uid, myuid);
            if (exist == 1) {
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
        favlistMapper.addDefaultFavlist(favoristList);
        Integer fid = favoristList.getFid();   // 获得生成的fid
        user.setDefaultfid(fid);   // 默认收藏夹fid

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

    public List<User> getFollow(Integer uid) {
        List<Integer> uids = userMapper.finfAllFollows(uid);
        List<User> res = new ArrayList<>();
        for (Integer uid2 : uids) {
            User u = userMapper.getByUid(uid2);
            res.add(u);
        }
        return res;
    }

    public List<User> getFans(Integer uid) {
        List<Integer> uids = userMapper.getFans(uid);
        List<User> res = new ArrayList<>();
        for (Integer uid2 : uids) {
            User u = userMapper.getByUid(uid2);
            res.add(u);
        }
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

    public void updateTempToken(String token) {
        userMapper.updateTempToken(token);
    }
}
