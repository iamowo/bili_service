package com.bili.controler;

import com.bili.entity.Mg;
import com.bili.entity.MgChapters;
import com.bili.entity.MgImgs;
import com.bili.entity.MgList;
import com.bili.entity.outEntity.UploadMgImg;
import com.bili.entity.outEntity.UploadMgInfo;
import com.bili.service.MgService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mg")
public class MgController {
    @Autowired
    private MgService mgService;

    @GetMapping("/getByTitle/{keyword}")
    public Response getByTitle (@PathVariable String keyword) {
        try {
            List<Mg> res = mgService.getByTitle(keyword);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @PostMapping("/uploadMgInfo")
    public Response uploadMgInfo (@RequestBody UploadMgInfo uploadMgInfo) {
        try {
            Integer mid = mgService.uploadMgInfo(uploadMgInfo);
            return Response.success(mid);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    // 上传续集
    @PostMapping("/updateMg")
    public Response updateMg (@RequestBody UploadMgInfo uploadMgInfo) {
        try {
            mgService.updateMg(uploadMgInfo);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @PostMapping("/uploadMgImg")
    public Response uploadMgImg (UploadMgImg uploadMgImg) {
        try {
            mgService.uploadimgs(uploadMgImg);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/getMgs/{num}/{type}")
    public Response getMgs (@PathVariable Integer num, @PathVariable Integer type) {
        try {
            List<Mg> res = mgService.getMgs(num, type);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    // 获取 收藏  历史
    @GetMapping("/getMgList/{uid}/{type}")
    public Response getMgList (@PathVariable Integer uid, @PathVariable Integer type) {
        try {
            List<Mg> res = mgService.getMgList(uid, type);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/getClassify/{type1}/{type2}/{type3}")
    public Response getClassify (@PathVariable String type1, @PathVariable Integer type2, @PathVariable Integer type3) {
        try {
            List<Mg> res = mgService.getClassify(type1, type2, type3);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    // 添加 历史 收藏
    @PostMapping("/addMgList")
    public Response addMgList (@RequestBody MgList mgList) {
        try {
            mgService.addMgList(mgList);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    // 删除历史收藏
    @PostMapping("/updateMgStatus")
    public Response updateMgStatus (@RequestBody MgList mgList) {
        try {
            mgService.updateMgStatus(mgList);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/getOneMg/{mid}/{uid}")
    public Response getOneMg (@PathVariable Integer mid, @PathVariable Integer uid) {
        try {
            Mg res = mgService.getOneMg(mid, uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/getChapters/{mid}/{page}/{num}")
    public Response getChapters (@PathVariable Integer mid, @PathVariable Integer page, @PathVariable Integer num) {
        try {
            List<MgChapters> res = mgService.getChapters(mid, page, num);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/getMgImgs/{mid}/{number}")
    public Response getMgImgs (@PathVariable Integer mid, @PathVariable Integer number) {
        try {
            List<MgImgs> res = mgService.getMgImgs(mid, number);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/getMgImgsRandom/{mid}/{number}/{num}")
    public Response getMgImgsRandom (@PathVariable Integer mid, @PathVariable Integer number, @PathVariable Integer num) {
        try {
            List<MgImgs> res = mgService.getMgImgsRandom(mid, number, num);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/getMgRanking/{type}")
    public Response getMgRanking (@PathVariable Integer type) {
        try {
            List<Mg> res = mgService.getMgRanking(type);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }

    @GetMapping("/searchMg/{keyword}")
    public Response searchMg (@PathVariable String keyword) {
        try {
            List<Mg> res = mgService.searchMg(keyword);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error:" + e);
        }
    }
}
