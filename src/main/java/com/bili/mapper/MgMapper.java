package com.bili.mapper;

import com.bili.entity.Mg;
import com.bili.entity.MgChapters;
import com.bili.entity.MgImgs;
import com.bili.entity.MgList;
import com.bili.entity.outEntity.UploadMgImg;
import com.bili.entity.outEntity.UploadMgInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MgMapper {
    List<Mg> getByTitle(String keyword);

    void uploadMgInfo(UploadMgInfo uploadMgInfo);

    void uploadimgs(UploadMgImg uploadMgImg);

    void addChapter(Integer mid, String name, Integer number);

    void updateMgChapter(Integer mid, int num);

    Mg getOneMg(Integer mid);

    List<MgChapters> getChapters(Integer mid, Integer page, Integer num);

    List<MgImgs> getMgImgs(Integer mid, Integer number);

    List<Mg> getMgs(Integer num, String type);

    List<MgChapters> getAllChapter(Integer mid);

    List<Mg> getClassify(String type1, Integer type2, Integer type3);

    List<Mg> getMgRanking(String type);

    List<Mg> searchMg(String keyword);

    void addMgList(MgList mgList);

    void updateMgStatus(MgList mgList);

    Integer judgeCollected(Integer mid, Integer uid, Integer type, Integer deleted);

    List<Integer> getMgList(Integer uid, Integer type);

    void changeAllList(MgList mgList);

    List<MgImgs> getMgImgsRandom(Integer mid, Integer number, Integer num);
}
