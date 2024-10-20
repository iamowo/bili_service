package com.bili.controler;

import com.bili.entity.HotKeyword;
import com.bili.service.SearchService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/getAllKeyword/{uid}")
    public Response getAllKeyword(@PathVariable Integer uid) {
        try {
            List<HotKeyword> res = searchService.getAllKeyword(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }
    @GetMapping("/addKeyword/{uid}/{keyword}")
    public Response addKeyword(@PathVariable Integer uid, @PathVariable String keyword) {
        try {
            searchService.addKeyword(uid, keyword);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/deleteKeyword/{kid}")
    public Response addKeyword(@PathVariable Integer kid) {
        try {
            searchService.deleteKeyword(kid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/deleteAllKeyword/{uid}")
    public Response deleteAllKeyword(@PathVariable Integer uid) {
        try {
            searchService.deleteAllKeyword(uid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    // 热搜榜
    @GetMapping("/getHotRanking")
    public Response getHotRanking() {
        try {
            List<String> res = searchService.getHotRanking();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }
}
