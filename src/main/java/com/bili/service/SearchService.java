package com.bili.service;

import com.bili.entity.HotKeyword;
import com.bili.mapper.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    @Autowired
    private SearchMapper searchMapper;

    public List<HotKeyword> getAllKeyword(Integer uid) {
        List<HotKeyword> res = searchMapper.getAllKeyword(uid);
        return res;
    }

    public void addKeyword(Integer uid, String keyword) {
        Integer count = searchMapper.getSearched(uid, keyword);
        if (count == 0) {
            searchMapper.addKeyword(uid, keyword);
        }
    }

    public void deleteKeyword(Integer kid) {
        searchMapper.deleteKeyword(kid);
    }

    public void deleteAllKeyword(Integer uid) {
        searchMapper.deleteAllKeyword(uid);
    }

    public List<String> getHotRanking() {
        List<String> res = searchMapper.getHotRanking();
        return res;
    }
}
