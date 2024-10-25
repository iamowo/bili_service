package com.bili.mapper;

import com.bili.entity.HotKeyword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {

    List<HotKeyword> getAllKeyword(Integer uid);

    void addKeyword(Integer uid, String keyword);

    void deleteKeyword(Integer kid);

    void deleteAllKeyword(Integer uid);

    List<String> getHotRanking();

    Integer getSearched(Integer uid, String keyword);
}
