package com.bili.mapper;

import com.bili.entity.LikeInfo;
import com.bili.entity.Message.Whisper;
import com.bili.entity.SysInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    List<Whisper> getWhiseperList(Integer uid);

    List<Whisper> getCoverWhisperList(Integer uid);

    List<Whisper> getByTwoUid(Integer uid1, Integer uid2);

    void sendMessage(Whisper whisper);

    List<SysInfo> getSysinfo(Integer uid);

    List<LikeInfo> getLikeinfo(Integer uid);
}
