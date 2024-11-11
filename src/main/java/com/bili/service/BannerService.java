package com.bili.service;

import com.bili.entity.Banner;
import com.bili.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerMapper bannerMapper;

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
}
