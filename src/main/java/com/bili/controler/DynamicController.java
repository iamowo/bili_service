package com.bili.controler;

import com.bili.entity.*;
import com.bili.entity.outEntity.UpDynamicimgs;
import com.bili.service.DynamicService;
import com.bili.util.Response;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;

    @GetMapping("/getDynamic/{did}/{uid}")
    public Response getDynamic (@PathVariable Integer did, @PathVariable Integer uid) {
        try {
            Dynamic res = dynamicService.getDynamic(did, uid);
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

    // topnav 历史动态
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

    @PostMapping("/updateDyinfo")
    public Response updateDyinfo (@RequestBody Dynamic dynamic) {
        try {
            dynamicService.updateDyinfo(dynamic);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @PostMapping("/addDynamicLike")
    public Response addDynamicLike (@RequestBody LikeInfo likeInfo) {
        try {
            dynamicService.addDynamicLike(likeInfo);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getAllTopical")
    public Response getAllTopical () {
        try {
            List<Topical> res = dynamicService.getAllTopical();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @PostMapping("/addTopical")
    public Response addTopical (@RequestBody Topical topical) {
        try {
            dynamicService.addTopical(topical);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/addTopicalCount/{tid}")
    public Response addTopicalCount (@PathVariable Integer tid) {
        try {
            dynamicService.addTopicalCount(tid);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/addTopicalWatchs/{tid}/{topical}")
    public Response addTopicalWatch (@PathVariable Integer tid, @PathVariable String topical) {
        try {
            dynamicService.addTopicalWatch(tid, topical);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getOneTopical/{topical}")
    public Response getOneTopical (@PathVariable String topical) {
        try {
            Topical res = dynamicService.getOneTopical(topical);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
    @GetMapping("/getDynamicByTopical/{topical}/{uid}/{sort}")
    public Response getDynamicByTopical (@PathVariable String topical, @PathVariable Integer uid, @PathVariable Integer sort) {
        try {
            List<Dynamic> res = dynamicService.getDynamicByTopical(topical, uid, sort);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getDynamicByKeyword")
    public Response getDynamicByKeyword (@RequestParam ("uid") Integer uid, @RequestParam ("keyword") String keyword) {
        try {
            List<Dynamic> res = dynamicService.getDynamicByKeyword(uid, keyword);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
}
