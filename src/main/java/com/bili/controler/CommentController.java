package com.bili.controler;

import com.bili.entity.Comment;
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

    @GetMapping("/getAllComment/{vid}")
    public Response getAllComment(@PathVariable Integer vid) {
        try {
            List<Comment> res = commentService.getAllComment(vid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: " + e);
        }
    }

    @PostMapping("/addComment")
    public Response addComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
            return Response.success(200);
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
}
