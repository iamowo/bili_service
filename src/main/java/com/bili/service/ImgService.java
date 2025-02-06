package com.bili.service;

import com.bili.entity.Img;
import com.bili.entity.ImgBoard;
import com.bili.entity.UpImgs;
import com.bili.entity.outEntity.ImgInfos;
import com.bili.mapper.ImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service

public class ImgService {
    @Autowired
    private ImgMapper imgMapper;
    @Value("${files.videoPath}")
    private String videoPath;
    @Value("${files.videoNet}")
    private String videoNet;

    public Integer uploadImgInfon(Img img) {
        File f = new File(videoPath + img.getUid()+ "/imgs");
        if (!f.exists()) {
            f.mkdir();
        }
        imgMapper.uploadImgInfon(img);
        Integer id = img.getId();
        return id;
    }

    public void uploadImgs(UpImgs upImgs) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String path = videoPath + upImgs.getUid() + "/imgs/" + uuid + "." + upImgs.getType();
        File file = new File(path);
        upImgs.getFile().transferTo(file);
        String netPath = videoNet + upImgs.getUid() + "/imgs/" + uuid + "." + upImgs.getType();
        upImgs.setNetPath(netPath);
        imgMapper.uploadImgs(upImgs);
    }

    public List<ImgInfos> getAllImg(Integer uid) {
        List<ImgInfos> res = imgMapper.getAllImg();
        for (int i = 0; i < res.size(); i++) {
            setTgas(res.get(i));
        }
        return res;
    }

    public ImgInfos getOneById(Integer imgid, Integer uid) {
        ImgInfos res = imgMapper.getOneById(imgid);
        res.setCollected(judgeCollected(imgid, uid));
        setTgas(res);
        return res;
    }

    // 判断是都收藏了
    private boolean judgeCollected(Integer imgid, Integer uid) {
        Integer num = imgMapper.judgeCollected(imgid, uid);
        return num > 0;
    }

    // 添加tags
    private void setTgas (ImgInfos imgInfos) {
        List<String> tags = new ArrayList<>();
        if (imgInfos.getMaintag() != "" && imgInfos.getMaintag() != null) {
            tags.add(imgInfos.getMaintag());
        }
        String[] tagss = imgInfos.getTags().split(" ");
        tags.addAll(Arrays.asList(tagss));
        imgInfos.setTaglist(tags);
    }

    public List<ImgBoard> getAllBoards(Integer uid) {
        List<ImgBoard> res = imgMapper.getAllBoards(uid);
        return res;
    }

    public void collectOneImg(Integer uid, Integer imgid, Integer boardid) {
        imgMapper.collectOneImg(uid, imgid, boardid);
    }

    public void createNewBoard(Map<String, Object> data) {
    }
}
