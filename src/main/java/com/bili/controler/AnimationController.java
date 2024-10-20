package com.bili.controler;

import com.bili.entity.Animation;
import com.bili.entity.AnimationList;
import com.bili.service.AnimationService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/animation")
public class AnimationController {
    @Autowired
    private AnimationService animationService;


    @GetMapping("/getAnimationList")
    public Response getAnimationList () {
        try {
            List<Animation> res = animationService.getAnimationList();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getSeasons/{aid}")
    public Response getSeasons (@PathVariable Integer aid) {
        try {
            List<List<AnimationList>> res = animationService.getSeasons(aid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/subthisAnimation/{uid}/{aid}")
    public Response subthisAnimation (@PathVariable Integer uid, @PathVariable Integer aid) {
        try {
            animationService.subthisAnimation(uid, aid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/cnacleAnimation/{id}/{deleted}")
    public Response cnacleAnimation (@PathVariable Integer id, @PathVariable Integer deleted) {
        try {
            animationService.cnacleAnimation(id, deleted);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getAnimationByVid/{vid}")
    public Response getAnimationByVid (@PathVariable Integer vid) {
        try {
            Animation res = animationService.getAnimationByVid(vid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    @GetMapping("getAnimationByKeyword/{keyword}")
    public Response getAnimationByKeyword (@PathVariable String keyword) {
        try {
            List<Animation> res = animationService.getAnimationByKeyword(keyword);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
}
