package com.bili.service;

import com.bili.entity.LikeInfo;
import com.bili.entity.Message.Whisper;
import com.bili.entity.Message.WhisperCover;
import com.bili.entity.SysInfo;
import com.bili.entity.User;
import com.bili.mapper.MessageMapper;
import com.bili.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class MessageService {
    @Value("${files.videoPath}")
    private String videoPath;
    @Value("${files.videoNet}")
    private String videoNet;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;

    public Map<String, Object> getWhisperList(Integer uid) {
        Integer uid1 = uid;  // 自己的id
        Map<String, Object> res = new HashMap<>();
        List<Whisper> smallList = messageMapper.getCoverWhisperList(uid);
        List<WhisperCover> coverlist = new ArrayList<>();
        for (int i = 0; i < smallList.size(); i++) {
            Integer uid2 = -1;  // 对方的id
            if (smallList.get(i).getUid1() == uid1) {
                uid2 = smallList.get(i).getUid2();
            } else {
                uid2 = smallList.get(i).getUid1();
            }
            if (i > 0 && (uid2 + uid1 == smallList.get(i - 1).getUid1() + smallList.get(i - 1).getUid2())) {
                continue;
            }

            WhisperCover temp = new WhisperCover();
            User user = userMapper.getByUid(uid2);
            temp.setUid2(uid2);
            temp.setAvatar(user.getAvatar());
            temp.setName(user.getName());

//            temp.setLastContent(smallList.get(i).getContent());
            coverlist.add(temp);
        }
        res.put("whisperlist", coverlist);
        List<List<Whisper>> contentlist = new ArrayList<>();
        for (int i = 0; i < coverlist.size(); i++) {
            List<Whisper> temp = new ArrayList<>();
            Integer uid2 = coverlist.get(i).getUid2();
            List<Whisper> list = messageMapper.getByTwoUid(uid1, uid2);
            contentlist.add(list);

            Whisper lasewhisper = list.getLast();  // 最后一条评论
            if (lasewhisper.getType() == 1) {
                coverlist.get(i).setLastContent("图片");
            } else {
                coverlist.get(i).setLastContent(list.getLast().getContent());
            }
        }
        res.put("contentlist", contentlist);
        return res;
    }

    public void sendMessage(Whisper whisper) {
        messageMapper.sendMessage(whisper);
    }

    public void snedImg(Whisper whisper) throws IOException {
        Integer uid = whisper.getUid1();
        UUID uuid = UUID.randomUUID();
        File path = new File(videoPath + uid + "/imgs/" + uuid + "." + whisper.getFiletype());
        whisper.getImg().transferTo(path);
        String netpath = videoNet + uid + "/imgs/" + uuid + "." + whisper.getFiletype();

        whisper.setContent(netpath);
        messageMapper.sendMessage(whisper);
    }

    public List<SysInfo> getSysinfo(Integer uid) {
        List<SysInfo> res = messageMapper.getSysinfo(uid);
        return res;
    }

    public List<LikeInfo> getLikeinfo(Integer uid) {
        List<LikeInfo> res = messageMapper.getLikeinfo(uid);
        adduserinfo(res);
        return res;
    }

    private void adduserinfo(List<LikeInfo> res) {
        for (int i = 0; i < res.size(); i++) {
            Integer uid = res.get(i).getHisuid();
            User user = userMapper.getByUid(uid);

            res.get(i).setName(user.getName());
            res.get(i).setAvatar(user.getAvatar());
        }
    }
}
