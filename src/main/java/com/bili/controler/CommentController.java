package com.bili.controler;

import com.bili.entity.Comment;
import com.bili.entity.LikeInfo;
import com.bili.service.CommentService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getAllComment/{id}/{uid}/{sort}/{type}")
    public Response getAllComment(@PathVariable Integer id, @PathVariable Integer uid, @PathVariable Integer sort, @PathVariable Integer type) {
        try {
            List<Comment> res = commentService.getAllComment(id, uid, sort, type);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @PostMapping("/addComment")
    public Response addComment(@RequestBody Comment comment) {
        try {
            Integer res = commentService.addComment(comment);
            // 新插入生成的id
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("/deleteComment/{id}/{deletedid}/{type}")
    public Response deleteComment(@PathVariable Integer id, @PathVariable Integer deletedid, @PathVariable Integer type) {
        try {
            commentService.deleteComment(id, deletedid, type);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @PostMapping("/addLikeinfo")
    public Response addLikeinfo(@RequestBody LikeInfo likeInfo) {
        try {
            commentService.addLikeinfo(likeInfo);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @GetMapping("/deletelikeinfo/{cid}/{uid}")
    public Response deletelikeinfo(@PathVariable Integer cid, @PathVariable Integer uid) {
        try {
            commentService.deletelikeinfo(cid, uid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }
    // 回复的评论
    @GetMapping("/getReplayComment/{uid}")
    public Response updateWhisperList (@PathVariable Integer uid) {
        try {
            List<Map> res = commentService.getReplayComment(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
}
