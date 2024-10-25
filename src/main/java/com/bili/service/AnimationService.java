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
    public List<Animation> getAnimationList(Integer uid) {
        // 带uid，获得个人收藏的
        List<AnimationSublist> lists = animationMapper.getAnimationList(uid);
        List<Animation> res =  new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            Integer aid = lists.get(i).getAid();
            Animation temp = animationMapper.getAnimationByAid(aid);
            temp.setLiked(UserLikeIt(uid, aid));
            // 获取每个视频的vid
            addAnimationVid(temp);
            res.add(temp);
        }
        return res;
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

    public void cnacleAnimation(Integer uid, Integer aid) {
        animationMapper.cnacleAnimation(uid, aid);
    }

    public Animation getAnimationByVid(Integer vid, Integer uid) {
        Video v = videoMapper.getByVid(vid);
        Integer aid = v.getAid();
        Animation res = animationMapper.getAnimationByAid(aid);
        if (uid == -1) {
            res.setLiked(false);
        } else {
            res.setLiked(UserLikeIt(uid, vid));
        }
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

    // 添加所有视频的vid
    private void addAnimationVid (Animation animation) {
        Integer aid = animation.getAid();
        List<AnimationList> anis = animationMapper.getAllByAid(aid);
        List<Integer> vids = new ArrayList<>();
        for (int j = 0; j < anis.size(); j++) {
            vids.add(anis.get(j).getVid());
        }
        animation.setVids(vids);
    }
    // 判断是否追剧
    private boolean UserLikeIt(Integer uid, Integer aid) {
        Integer exist = animationMapper.userLikeIt(uid, aid);
        if (exist == 0) {
            return false;
        }
        return true;
    }
}
