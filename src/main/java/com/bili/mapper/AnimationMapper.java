package com.bili.mapper;

import com.bili.entity.PO.Animation;
import com.bili.entity.PO.AnimationList;
import com.bili.entity.PO.AnimationSublist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnimationMapper {
    List<AnimationSublist> getAnimationList(Integer uid);

    List<AnimationList> getAllByAid(Integer aid);

    void subthisAnimation(Integer uid, Integer aid);

    void cnacleAnimation(Integer uid, Integer aid);

    Integer hadSubed(Integer uid, Integer aid);

    void updateingfo(AnimationSublist animationSublist);

    Animation getAnimationByAid(Integer aid);

    List<Animation> getAnimationByKeyword(String keyword);

    Integer userLikeIt(Integer uid, Integer aid);

    Integer getSubed(Integer aid);

    List<Animation> getUploadAniList(Integer uid);

    List<Animation> getAnimations(Integer type);
}
