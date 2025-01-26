package com.bili.mapper;

import com.bili.entity.Banner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {
    List<Banner> getBanner();

    void addBanner(Banner banner);

    List<Banner> getBannerUnselected();

    void setBanner(Integer ind);

    void addNewBanner(Banner banner);

    void updateOneBanner(Banner banner);

    void sortBanner(Integer ind);

    void deleteThis(Integer ind);

    void addBannerToList(Integer id, Integer len);
}
