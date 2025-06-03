package com.bili.mapper;

import com.bili.entity.Message.Whisper;
import com.bili.entity.PO.At;
import com.bili.entity.PO.LikeInfo;
import com.bili.entity.PO.SysInfo;
import com.bili.entity.PO.WhisperList;
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

    Integer existWhisperList(Integer uid1, Integer uid2);

    void addWhisperList(WhisperList whisperList);

    List<WhisperList> getHisAllWhisperList(Integer uid1);

    void updateWhisperListTime(Integer uid1, Integer uid2, String time);

    String getLastMapper(Integer wid);

    List<Integer> findWid(Integer uid1, Integer uid2);

    void updateWhisperList(Integer wid, Integer deleted);

    List<At> getAtinfo(Integer uid);
}
