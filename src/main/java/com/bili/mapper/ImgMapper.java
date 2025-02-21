package com.bili.mapper;

import com.bili.entity.Img;
import com.bili.entity.ImgBoard;
import com.bili.entity.UpImgs;
import com.bili.entity.outEntity.ImgInfos;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImgMapper {
    void uploadImgInfon(Img img);

    void uploadImgs(UpImgs upImgs);

    List<ImgInfos> getAllImg();

    ImgInfos getOneById(Integer imgid);

    Integer judgeCollected(Integer imgid, Integer uid, Integer boardid);

    List<ImgBoard> getAllBoards(Integer uid);

    void collectOneImg(Integer uid, Integer imgid, Integer boardid);

    List<String> getBoardCovers(Integer boardid);

    void cancleCollectOneImg(Integer uid, Integer imgid, Integer boardid);

    void changeBoardData(Integer boardid, int num1, int num2, int num3);
}
