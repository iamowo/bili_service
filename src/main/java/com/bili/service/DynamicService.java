package com.bili.service;

import com.bili.entity.*;
import com.bili.entity.outEntity.UpDynamicimgs;
import com.bili.mapper.DynamicMapper;
import com.bili.mapper.UserMapper;
import com.bili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class DynamicService {
    @Value("${files.videoPath}")
    private String videoPath;
    @Value("${files.videoNet}")
    private String videoNet;
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private VideoMapper videoMapper;

    // 给视频添加信息
    private void adduserinfoone(Video video) {
        Integer uid = video.getUid();
        User user = userMapper.findUid(uid);

        video.setName(user.getName());
        video.setAvatar(user.getAvatar());
        video.setUserintro(user.getIntro());
    }

    // 给动态添加信息
    private void addUserinfo(List<Dynamic> res) {
        for (int i = 0; i < res.size(); i++) {
            Integer uid = res.get(i).getUid();
            User user  = userMapper.getByUid(uid);
            res.get(i).setName(user.getName());
            res.get(i).setAvatar(user.getAvatar());

            List<Dyimgs> list = dynamicMapper.getImgs(res.get(i).getId());
            List<String> imgs = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                imgs.add(list.get(j).getImg());
            }
            res.get(i).setImgs(imgs);
        }
    }

    private void addUserinfoOne(Dynamic res) {
        Integer uid = res.getUid();
        User user  = userMapper.getByUid(uid);
        res.setName(user.getName());
        res.setAvatar(user.getAvatar());

        List<Dyimgs> list = dynamicMapper.getImgs(res.getId());
        List<String> imgs = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            imgs.add(list.get(j).getImg());
        }
        res.setImgs(imgs);
    }

    // 各种类型 图文 转发 视频
    private void dealtype(List<Dynamic> res) {
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).getType() == 1 || res.get(i).getType() == 3) {
                Integer vid = res.get(i).getVordid();
                Video video = videoMapper.getByVid(vid);
                adduserinfoone(video);
                res.get(i).setVideo(video);
            } else if (res.get(i).getType() == 2) {
                Integer did = res.get(i).getVordid();
                Dynamic dynamic = dynamicMapper.getOneDynamic(did);
                addUserinfoOne(dynamic);
                res.get(i).setDy2(dynamic);
            }
        }
    }

    // 实际上按照id 排序， 效果和按照时间排序一样
    private void quickSort(List<Dynamic> list, int low, int high) {
        int i,j;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        Dynamic temp = list.get(low);
        Dynamic t = null;
        while (i<j) {
            //先看右边，依次往左递减
            while (temp.getId() >= list.get(j).getId() && i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp.getId() <= list.get(i).getId() && i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = list.get(j);
                list.set(j, list.get(i));
                list.set(i, t);
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        //arr[low] = arr[i];
        list.set(low, list.get(i));
        //arr[i] = temp;
        list.set(i, temp);
        //递归调用左半数组
        quickSort(list, low, j-1);
        //递归调用右半数组
        quickSort(list, j+1, high);

    }

    public List<Dynamic> getDyanmciList(Integer uid, Integer flag) {
        List<Dynamic> res = dynamicMapper.getDyanmciList(uid);

        if (flag == 1) {
            List<Integer> followlist = dynamicMapper.getFollowlist(uid);   // 获得关注的人的id
            for (int i = 0; i < followlist.size(); i++) {
                Integer followuid = followlist.get(i);
                List<Dynamic> res2 = dynamicMapper.getDyanmciList(followuid);
                res.addAll(res2);
            }
            quickSort(res, 0, res.size() - 1);  // 按时间排序
        }
        addUserinfo(res);
        dealtype(res);
        return res;
    }

    public Integer sendDynamic(Dynamic dynamic) {
        dynamicMapper.sendDynamic(dynamic);
        Integer did = dynamic.getId();
        return did;
    }

    public void sendDyimgs(UpDynamicimgs upDynamicimgs) throws IOException {
        Integer uid = upDynamicimgs.getUid();
        Integer did= upDynamicimgs.getDid();

        UUID uuid = UUID.randomUUID();
        String path = uid + "/imgs/" + uuid + "." + upDynamicimgs.getType();
        File file = new File(videoPath + path);
        upDynamicimgs.getImg().transferTo(file);

        Dyimgs dyimgs = new Dyimgs();
        dyimgs.setUid(uid);
        dyimgs.setDid(did);
        dyimgs.setImg(videoNet + path);
        dynamicMapper.addDyimgs(dyimgs);
    }
    public void shareDynamic(Integer uid) {
    }

    public void likeDynamic(Integer uid) {
    }

    public void commentDynamic(Dynamic dynamic) {

    }

    public List<Dynamic> getDyanmciListWidthImg(Integer uid) {
        List<Integer> dids = dynamicMapper.getImgssDid(uid);
        List<Dynamic> res = new ArrayList<>();
        for (int i = 0; i < dids.size(); i++) {
            Integer did = dids.get(i);
            res.add(dynamicMapper.getOneDynamic(did));
        }
        addUserinfo(res);
        dealtype(res);
        return res;
    }

    public Dynamic getDynamic(Integer did) {
        Dynamic res = dynamicMapper.getDynamic(did);
        addUserinfoOne(res);
        return res;
    }
}
