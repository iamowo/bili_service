package com.bili.controler;

import com.bili.entity.FavoristList;
import com.bili.entity.Video;
import com.bili.entity.outEntity.Addfavorite;
import com.bili.service.FavlistService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favlist")
public class FavController {
    @Autowired
    private FavlistService favlistService;

    // 得到收藏夹列表, vid = -1 时， 只查询列表
    @GetMapping("/getFavlist/{uid}/{vid}")
    public Response getFavlist(@PathVariable Integer uid, @PathVariable Integer vid) {
        try {
            List<FavoristList> res = favlistService.getFavlist(uid, vid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    // 获取一个收藏夹中的内容
    @GetMapping("/getOneList/{fid}")
    public Response getOneList(@PathVariable Integer fid) {
        try {
            List<Video> res = favlistService.getOneList(fid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    // 增加一个收藏夹列表
    @GetMapping("/addOneFavlist/{uid}/{title}/{pub}")
    public Response addOneFavlist(@PathVariable Integer uid, @PathVariable String title, @PathVariable Integer pub) {
        try {
            favlistService.addFavlist(uid, title, pub);
            List<FavoristList> res = favlistService.getFavlist(uid, -1);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    // 增加一个收藏视频
    @PostMapping("/addOneVideo")
    public Response addOneVideo(@RequestBody Addfavorite addfavorite) {
        try {
            favlistService.addOneFAV(addfavorite);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    // 删除
    @GetMapping("/deleteFav/{fid}/{uid}")
    public Response deleteFav(@PathVariable Integer fid, @PathVariable Integer uid) {
        try {
            favlistService.deleteFav(fid);
            List<FavoristList> res = favlistService.getFavlist(uid, -1);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }
    // 修改
    @GetMapping("/updateFav/{uid}/{fid}/{title}/{pub}")
    public Response updateFav(@PathVariable Integer uid,@PathVariable Integer fid, @PathVariable String title, @PathVariable Integer pub) {
        try {
            favlistService.updateFav(fid, title, pub);
            List<FavoristList> res = favlistService.getFavlist(uid, -1);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }
}
