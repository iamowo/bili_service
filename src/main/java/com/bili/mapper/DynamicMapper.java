package com.bili.mapper;

import com.bili.entity.Dyimgs;
import com.bili.entity.Dynamic;
import com.bili.entity.Follow;
import com.bili.entity.Video;
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
}
