package com.bili.controler;

import com.bili.entity.LikeInfo;
import com.bili.entity.Message.Whisper;
import com.bili.entity.SysInfo;
import com.bili.service.MessageService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    // 获取全部私信
    @GetMapping("/getWhisperList/{uid}")
    public Response getWhisperList (@PathVariable Integer uid) {
        try {
            Map<String, Object> res = messageService.getWhisperList(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @PostMapping("/sendMessage")
    public Response sendMessage (@RequestBody Whisper whisper) {
        try {
            messageService.sendMessage(whisper);
            Map<String, Object> res = messageService.getWhisperList(whisper.getUid1());
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @PostMapping("/sendImg")
    public Response sendImg (Whisper whisper) {
        try {
            messageService.snedImg(whisper);
            Map<String, Object> res = messageService.getWhisperList(whisper.getUid1());
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 系统通知  稿件通过。活动。。。
    @GetMapping("/getSysinfo/{uid}")
    public Response getSysinfo (@PathVariable Integer uid) {
        try {
            List<SysInfo> res = messageService.getSysinfo(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    @GetMapping("/getLikeinfo/{uid}")
    public Response getLikeinfo (@PathVariable Integer uid) {
        try {
            List<LikeInfo> res = messageService.getLikeinfo(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
}
