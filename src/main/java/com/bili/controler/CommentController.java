package com.bili.controler;

import com.bili.entity.Comment;
import com.bili.entity.LikeInfo;
import com.bili.service.CommentService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getAllComment/{vid}/{uid}/{type}")
    public Response getAllComment(@PathVariable Integer vid, @PathVariable Integer uid,@PathVariable Integer type) {
        try {
            List<Comment> res = commentService.getAllComment(vid, uid, type);
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

    @GetMapping("/deleteComment/{id}/{vid}")
    public Response deleteComment(@PathVariable Integer id, @PathVariable Integer vid) {
        try {
            commentService.deleteComment(id, vid);
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
}
