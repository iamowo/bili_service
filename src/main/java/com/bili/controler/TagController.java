package com.bili.controler;

import com.bili.entity.TagMainList;
import com.bili.service.TagService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/getOneTagType/{type}")
    public Response getOneTagType(@PathVariable Integer type) {
        try {
            List<TagMainList> res = tagService. getOneTagType(type);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    @GetMapping("/insertOneTag/{tagName}/{type}")
    public Response insertOneTag(@PathVariable String tagName, @PathVariable Integer type) {
        try {
            tagService.insertOneTag(tagName, type);
            return Response.success("success");
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }
}
