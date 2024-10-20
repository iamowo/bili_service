package com.bili.service;

import com.bili.entity.Animation;
import com.bili.entity.AnimationList;
import com.bili.entity.AnimationSublist;
import com.bili.entity.Video;
import com.bili.mapper.AnimationMapper;
import com.bili.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AnimationService {
    @Autowired
    private AnimationMapper animationMapper;

    @Autowired
    private VideoMapper videoMapper;
    public List<Animation> getAnimationList() {
        return animationMapper.getAnimationList();
    }

    public List<List<AnimationList>> getSeasons(Integer aid) {
        List<AnimationList> anis = animationMapper.getAllByAid(aid);
        List<List<AnimationList>> res = new ArrayList<>();
        int left = 0, right = 0;
        while (left < anis.size() && left <= right) {
            List<AnimationList> temp = new ArrayList<>();
            while (right < anis.size() && anis.get(right).getSeason() == anis.get(left).getSeason()) {
                temp.add(anis.get(right));
                right++;
            }
            left = right;
            res.add(temp);
        }
        return res;
    }

    public void subthisAnimation(Integer uid, Integer aid) {
        Integer r = animationMapper.hadSubed(uid, aid);
        if (r == 0) {
            animationMapper.subthisAnimation(uid, aid);
        } else {
            AnimationSublist animationSublist = new AnimationSublist();
            animationSublist.setUid(uid);
            animationSublist.setAid(aid);
            animationSublist.setDeleted(0);
            animationMapper.updateingfo(animationSublist);
        }
    }

    public void cnacleAnimation(Integer id, Integer deleted) {
        animationMapper.cnacleAnimation(id, deleted);
    }

    public Animation getAnimationByVid(Integer vid) {
        Video v = videoMapper.getByVid(vid);
        Integer aid = v.getAid();
        Animation res = animationMapper.getAnimationByAid(aid);
        return res;
    }

    public List<Animation> getAnimationByKeyword(String keyword) {
        List<Animation> res = animationMapper.getAnimationByKeyword(keyword);
        // 添加章节信息
        for (int i = 0; i < res.size(); i++) {
            List<AnimationList> cps = animationMapper.getAllByAid(res.get(i).getAid());
            List<Integer> vids = new ArrayList<>();
            for (int j = 0; j < cps.size(); j++) {
                vids.add(cps.get(j).getVid());
            }
            res.get(i).setVids(vids);
        }
        return res;
    }
}