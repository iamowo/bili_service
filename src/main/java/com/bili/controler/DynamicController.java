package com.bili.controler;

import com.bili.entity.Dyimgs;
import com.bili.entity.Dynamic;
import com.bili.entity.Video;
import com.bili.entity.outEntity.UpDynamicimgs;
import com.bili.service.DynamicService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;

    @GetMapping("/getDynamic/{did}")
    public Response getDynamic (@PathVariable Integer did) {
        try {
            Dynamic res = dynamicService.getDynamic(did);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    @GetMapping("/getDyanmciList/{uid}/{flag}")
    public Response getDyanmciList (@PathVariable Integer uid, @PathVariable Integer flag) {
        try {
            List<Dynamic> res = dynamicService.getDyanmciList(uid, flag);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getDyanmciListWidthImg/{uid}")
    public Response getDyanmciList (@PathVariable Integer uid) {
        try {
            List<Dynamic> res = dynamicService.getDyanmciListWidthImg(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }


    @PostMapping("/sendDynamic")
    public Response sendDynamic (@RequestBody Dynamic dynamic) {
        try {
            Integer did = dynamicService.sendDynamic(dynamic);
            return Response.success(did);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @PostMapping("/sendDyimgs")
    public Response sendDyimgs (UpDynamicimgs upDynamicimgs) {
        try {
            dynamicService.sendDyimgs(upDynamicimgs);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/shareDynamic/{uid}")
    public Response shareDynamic (@PathVariable Integer uid) {
        try {
            dynamicService.shareDynamic(uid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/likeDynamic/{uid}")
    public Response likeDynamic (@PathVariable Integer uid) {
        try {
            dynamicService.likeDynamic(uid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @PostMapping("/commentDynamic")
    public Response commentDynamic (@RequestBody Dynamic dynamic) {
        try {
            dynamicService.commentDynamic(dynamic);
//            Map<String, Object> res = messageService.getWhisperList(whisper.getUid1());
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
}
