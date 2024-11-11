package com.bili.controler;

import com.bili.entity.Banner;
import com.bili.entity.Comment;
import com.bili.service.BannerService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @PostMapping("/addBanner")
    public Response addBanner(@RequestBody Banner banner) {
        try {
            bannerService.addBanner(banner);
            // 新插入生成的id
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("/getBanner")
    public Response getBanner() {
        try {
            List<Banner> res = bannerService.getBanner();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("/getBannerUnselected")
    public Response getBannerUnselected() {
        try {
            List<Banner> res = bannerService.getBannerUnselected();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("/setBanner/{ind}")
    public Response setBanner(@PathVariable Integer ind) {
        try {
            bannerService.setBanner(ind);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }
}
