package com.bili.mapper;

import com.bili.entity.Animation;
import com.bili.entity.AnimationList;
import com.bili.entity.AnimationSublist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnimationMapper {
    List<Animation> getAnimationList();

    List<AnimationList> getAllByAid(Integer aid);

    void subthisAnimation(Integer uid, Integer uid1);

    void cnacleAnimation(Integer id, Integer deleted);

    Integer hadSubed(Integer uid, Integer aid);

    void updateingfo(AnimationSublist animationSublist);

    Animation getAnimationByAid(Integer aid);

    List<Animation> getAnimationByKeyword(String keyword);
}
