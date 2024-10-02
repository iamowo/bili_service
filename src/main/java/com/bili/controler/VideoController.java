package com.bili.controler;

import com.bili.entity.Dm;
import com.bili.entity.Video;
import com.bili.entity.VideoList;
import com.bili.entity.Watchinfo;
import com.bili.entity.outEntity.Audit;
import com.bili.entity.outEntity.OneChunk;
import com.bili.entity.outEntity.UploadVideoInfos;
import com.bili.entity.outEntity.VideoInfos;
import com.bili.entity.temp.TagAndNums;
import com.bili.service.VideoService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @GetMapping("/getByVid/{vid}/{uid}")
    public Response getByVid(@PathVariable Integer vid, @PathVariable Integer uid) {
        try {
            Video res = videoService.getByVid(vid, uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    @GetMapping("/getAllVideo")
    public Response getAllVideo() {
        try {
            List<Video> res = videoService.getAll();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获取莫个人的视频
    @GetMapping("/getVideoByUid/{uid}/{nums}")
    public Response getVideoByUid(@PathVariable Integer uid, @PathVariable Integer nums) {
        try {
            List<Video> res = videoService.getVideoByUid(uid, nums);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获取个人推荐（已登录）
    @GetMapping("/getRecommend")
    public Response getRecommend() {
        try {
            List<Video> res = videoService.getRecommend();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 随机推荐（未登录）
    @GetMapping("/getRandom")
    public Response getRandom() {
        try {
            List<Video> res = videoService.getRandom();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 相关视频
    @GetMapping("/getVideoLikely/{vid}")
    public Response getVideoLikely(@PathVariable Integer vid) {
        try {
            List<Video> res = videoService.getVideoLikely(vid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    // 上传视频信息
    @PostMapping("/uploadVideoInfos")
    public Response uploadVideoInfos (@RequestBody UploadVideoInfos uploadVideoInfos) {
        try {
            Integer vid = videoService.uploadVideoInfos(uploadVideoInfos);
            return Response.success(vid);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 观看历史
    @GetMapping("/getHistory/{uid}")
    public Response getHistory (@PathVariable Integer uid) {
        try {
            List<Watchinfo> res = videoService.getHistory(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 删除管看历史
    @GetMapping("/deleteHistory/{uid}/{vid}")
    public Response deleteHistory (@PathVariable Integer uid, @PathVariable Integer vid) {
        try {
            videoService.deleteHistory(uid, vid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/deleteAll/{uid}")
    public Response deleteHistory (@PathVariable Integer uid) {
        try {
            videoService.deleteAll(uid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getRank/{type}")
    public Response getRank (@PathVariable Integer type) {
        try {
            List<Video> res = videoService.getRank(type);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    // 上传分片
    @PostMapping("/uploadChunks")
    public Response uploadChunks (OneChunk oneChunk) {
        try {
            videoService.uploadChunks(oneChunk);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获取已上传切片
    @GetMapping("/getAlready/{hashValue}/{uid}")
    public Response getAlready (@PathVariable String hashValue, @PathVariable Integer uid) {
        try {
            Map<String, Object> res = videoService.getAlready(hashValue, uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    // 合并分片
    @GetMapping("/mergeChunks/{uid}/{vid}")
    public Response mergeChunks (@PathVariable Integer uid, @PathVariable Integer vid) {
        try {
            videoService.mergeChunks(uid, vid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 更新视频信息
    @PostMapping("/updateinfo")
    public Response updateinfo(@RequestBody VideoInfos videoInfos) {
        try {
            videoService.infos(videoInfos);
            return Response.success(videoInfos.getType());
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    // 搜索视频 标题
    @GetMapping("/getByKeywordAll/{keyword}/{sort1}/{sort2}/{sort3}")
    public Response getByKeywordAll (@PathVariable String keyword, @PathVariable Integer sort1, @PathVariable Integer sort2, @PathVariable Integer sort3) {
        try {
            List<Video> res = videoService.getByKeywordAll(keyword, sort1, sort2, sort3);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 搜索视频 作者名字
    @GetMapping("/getByKeywordName/{keyword}")
    public Response getByKeywordName (@PathVariable String keyword) {
        try {
            List<Video> res = videoService.getByKeywordName(keyword);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 首页视频动态，每次获取20个。最多60个
    @GetMapping("/getHomeDynamicVideo/{uid}/{page}/{num}/{size}")
    public Response getHomeDynamicVideo (@PathVariable Integer uid, @PathVariable Integer page, @PathVariable Integer num, @PathVariable Integer size) {
        try {
            List<Video> res = videoService.getHomeDynamicVideo(uid, page, num, size);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // topnav历史记录
    @GetMapping("/getHomHistory/{uid}/{page}/{num}/{size}")
    public Response getHomHistory (@PathVariable Integer uid, @PathVariable Integer page, @PathVariable Integer num, @PathVariable Integer size) {
        try {
            List<Watchinfo> res = videoService.getHomHistory(uid, page, num, size);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获取弹幕
    @GetMapping("/getDm/{vid}")
    public Response getDm (@PathVariable Integer vid) {
        try {
            List<Dm> res = videoService.getDm(vid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 发送弹幕
    @PostMapping("/sendDm")
    public Response sendDm (@RequestBody Dm dm) {
        try {
            videoService.sendDm(dm);
            List<Dm> res = videoService.getDm(dm.getVid());
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 搜索关键词返回标题， 作者
    @GetMapping("/searchKw/{keyword}")
    public Response searchKw (@PathVariable String keyword) {
        try {
            List<String> res = videoService.searchKw(keyword);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 返回代表作
    @GetMapping("/getFamous/{uid}")
    public Response getFamous (@PathVariable Integer uid) {
        try {
            List<Video> res = videoService.getFamous(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 返回代表作以外的作品
    @GetMapping("/getUnfamous/{uid}/{type}")
    public Response getUnfamous (@PathVariable Integer uid, @PathVariable Integer type) {
        try {
            List<Video> res = videoService.getUnfamous(uid, type);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    // 改变代表作
    @GetMapping("/changeFamous/{uid}/{vids}")
    public Response changeFamous (@PathVariable Integer uid, @PathVariable Integer[] vids) {
        try {
            videoService.changeFamous(uid, vids);
            List<Video> res = videoService.getFamous(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 主页动态视频
    @GetMapping("/getHomeDynamic/{uid}")
    public Response getHomeDynamic (@PathVariable Integer uid) {
        try {
            List<Video> res = videoService.getHomeDynamic(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获取待审核视频
    @GetMapping("/getAuditList")
    public Response getAuditList () {
        try {
            List<Video> res = videoService.getAuditList();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 审核结果
    @PostMapping("/oneResult")
    public Response oneResult (@RequestBody Audit audit) {
        try {
            videoService.oneResult(audit);
            List<Video> res = videoService.getAuditList();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获取全部视频， 包括未审核，未通过的
    @GetMapping("/getAllv/{uid}")
    public Response getAllv (@PathVariable Integer uid) {
        try {
            List<Video> res = videoService.getAllv(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 用户删除视频
    @GetMapping("/userdeletevideo/{vid}")
    public Response userdeletevideo (@PathVariable Integer vid) {
        try {
            videoService.userdeletevideo(vid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 用户修改视频，重新审核
    @PostMapping("/userChnageInfo")
    public Response userChnageInfo (@RequestBody Video video) {
        try {
            videoService.userChnageInfo(video);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获得用户的视频列表
    @GetMapping("/getUserVideoList/{uid}")
    public Response getUserVideoList (@PathVariable Integer uid) {
        try {
            List<VideoList> res = videoService.getUserVideoList(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 删除用户是怕你列表
    @GetMapping("/deleteVideoList/{listid}")
    public Response deleteVideoList (@PathVariable Integer listid) {
        try {
            videoService.deleteVideoList(listid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    @PostMapping("/chanegListInfo")
    public Response chanegListInfo (@RequestBody VideoList videoList) {
        try {
            videoService.chanegListInfo(videoList);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getUserListOne/{listid}")
    public Response getUserListOne (@PathVariable Integer listid) {
        try {
            VideoList res = videoService.getUserListOne(listid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 新建视频列表
    @PostMapping("/addVideoList")
    public Response addVideoList (@RequestBody VideoList videoList) {
        try {
            Integer listid = videoService.addVideoList(videoList);
            return Response.success(listid);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    // 向视频列表中添加视频
    @GetMapping("/addVideoToList/{listid}/{uid}/{vids}")
    public Response addVideoToList (@PathVariable Integer listid, @PathVariable Integer uid, @PathVariable List<Integer> vids) {
        try {
            videoService.addVideoToList(listid, uid, vids);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 得到一个列表中的视频(已经添加过的)
    @GetMapping("/getVideoFormList/{listid}")
    public Response getVideoFormList (@PathVariable Integer listid) {
        try {
            List<Video> res = videoService.getVideoFormList(listid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 一个列表中未添加过的视频
    @GetMapping("/getUnaddVideo/{uid}")
    public Response getUnaddVideo (@PathVariable Integer uid) {
        try {
            List<Video> res = videoService.getUnaddVideo(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getByMaintag/{maintag}")
    public Response getByMaintag (@PathVariable String maintag) {
        try {
            List<Video> res = videoService.getByMaintag(maintag);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getAllMainTag")
    public Response getAllMainTag () {
        try {
            List<TagAndNums> res = videoService.getAllMainTag();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
}
