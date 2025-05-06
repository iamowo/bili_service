package com.bili.controler;

import com.bili.entity.Mg;
import com.bili.entity.TagMainList;
import com.bili.entity.Tags;
import com.bili.entity.Video;
import com.bili.service.TagService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    // 获取主标签
    @GetMapping("/getMainTag/{type}/{limit}")
    public Response getMainTag(@PathVariable Integer type,@PathVariable Integer limit) {
        try {
            List<TagMainList> res = tagService. getMainTag(type, limit);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 获取标签
    @GetMapping("/getTags/{fid}/{type}/{name}/{flag}/{page}/{limit}")
    public Response getTags(@PathVariable Integer fid, @PathVariable Integer type, @PathVariable String name, @PathVariable Integer flag, @PathVariable Integer page, @PathVariable Integer limit ) {
        try {
            List<Tags> res = tagService. getTags(fid, type, name, flag, page, limit);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 插入标签
    @GetMapping("/insertOneTag/{tagName}/{type}")
    public Response insertOneTag(@PathVariable String tagName, @PathVariable Integer type) {
        try {
            tagService.insertOneTag(tagName, type);
            return Response.success("success");
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 根据表亲啊获取资源
    @GetMapping("/getResourceByTag/{type}/{tag}/{page}/{limit}")
    public Response getResourceByTag(@PathVariable Integer type, @PathVariable String tag, @PathVariable Integer page, @PathVariable Integer limit) {
        try {
            if(type == 0) {
                List<Video> res = tagService.getVideosByTag(tag, page, limit);
                return Response.success(res);
            } else if(type == 1) {
                List<Mg> res = tagService.getMgsByTag(tag, page, limit);
                return Response.success(res);
            } else if(type == 2) {
                List<Image> res = tagService.getImagesByTag(tag, page, limit);
                return Response.success(res);
            }
            return Response.success("200");
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }
}
