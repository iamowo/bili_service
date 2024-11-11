package com.bili.mapper;

import com.bili.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Objects;

@Mapper
public interface DynamicMapper {
    List<Dynamic> getDyanmciList(Integer uid);

    void sendDynamic(Dynamic dynamic);

    void addDyimgs(Dyimgs dyimgs);

    List<Dyimgs> getImgs(Integer id);

    List<Integer> getFollowlist(Integer uid);

    Dynamic getOneDynamic(Integer did);

    List<Integer> getImgssDid(Integer uid);

    Dynamic getDynamic(Integer did);

    void commentDynamic(Dynamic dynamic);

    void addDynamicLike(LikeInfo likeInfo);

    void addLikeNum(Integer did, Integer num);

    Integer getLiked(Integer did, Integer uid);

    void deleteOneDynamicLike(LikeInfo likeInfo);

    List<Topical> getAllTopical();

    void addTopical(Topical topical);

    void addTopicalCount(Integer tid, Integer num);

    void addTopicalWatch(Integer tid, String topical, Integer num);

    List<Dynamic> getDynamicByTopical(String topical, Integer sort);

    Topical getOneTopical(String topical);

    List<Dynamic> getDynamicByKeyword(Integer uid, String keyword);

    List<Dynamic> getAllDynamic();
}
