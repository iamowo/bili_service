package com.bili.service;

import com.bili.entity.Banner;
import com.bili.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.bili.util.Functions;
@Service
public class BannerService {
    @Autowired
    private BannerMapper bannerMapper;
    @Value("${files.sysPath}")
    private String sysPath;

    @Value("${files.sysNet}")
    private String sysNet;
    public List<Banner> getBanner() {
        List<Banner> res = bannerMapper.getBanner();
        return res;
    }

    public void addBanner(Banner banner) {
        bannerMapper.addBanner(banner);
    }

    public List<Banner> getBannerUnselected() {
        List<Banner> res = bannerMapper.getBannerUnselected();
        return res;
    }

    public void setBanner(Integer ind) {
        bannerMapper.setBanner(ind);
    }

    public void addNewBanner(Banner banner) {
        // base64
        String fileName = UUID.randomUUID() + ".png";
        String savePath = sysPath + "banner/";
        Functions.base64ToFile(banner.getCoverfile(), fileName, savePath);
        banner.setCover(sysNet + "banner/" + fileName);
        bannerMapper.addNewBanner(banner);
    }

    public void updateOneBanner(Banner banner) {
        bannerMapper.updateOneBanner(banner);
    }

    public void deleteThisBanner(Integer ind) {
        bannerMapper.deleteThis(ind);
        bannerMapper.sortBanner(ind);
    }

    public void addBannerToList(Integer id, Integer len) {
        bannerMapper.addBannerToList(id, len);
    }
}
