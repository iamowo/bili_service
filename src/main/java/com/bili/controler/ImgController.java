package com.bili.controler;

import com.bili.entity.FavoristList;
import com.bili.entity.Img;
import com.bili.entity.ImgBoard;
import com.bili.entity.UpImgs;
import com.bili.entity.outEntity.ImgInfos;
import com.bili.service.ImgService;
import com.bili.util.Response;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("imgs")
public class ImgController {
    @Autowired
    private ImgService imgService;
    @PostMapping("uploadImgInfon")
    public Response uploadImgInfon(@RequestBody Img img) {
        try {
            Integer id = imgService.uploadImgInfon(img);
            return Response.success(id);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }
    @PostMapping("uploadImgs")
    public Response uploadImgs(UpImgs upImgs) {
        try {
            imgService.uploadImgs(upImgs);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("getAllImg/{uid}")
    public Response getAllImg(@PathVariable Integer uid) {
        try {
            List<ImgInfos> res = imgService.getAllImg(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("getOneById/{imgid}/{uid}")
    public Response getAllImg(@PathVariable Integer imgid, @PathVariable Integer uid) {
        try {
            ImgInfos res = imgService.getOneById(imgid, uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("getAllBoards/{uid}")
    public Response getAllBoards(@PathVariable Integer uid) {
        try {
            List<ImgBoard> res = imgService.getAllBoards(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    // 创建一个新的收藏夹
    // 收藏一个图片
    @GetMapping("collectOneImg/{uid}/{imgid}/{boardid}")
    public Response collectOneImg(@PathVariable Integer uid, @PathVariable Integer imgid, @PathVariable Integer boardid) {
        try {
            imgService.collectOneImg(uid, imgid, boardid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @PostMapping("createNewBoard")
    public Response createNewBoard(@RequestBody Map<String, Object> data) {
        try {
            imgService.createNewBoard(data);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("getOneBoard/{boardid}")
    public Response getOneBoard(@PathVariable Integer boardid) {
        try {
            Map<String, Object> res = imgService.getOneBoard(boardid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }
}
