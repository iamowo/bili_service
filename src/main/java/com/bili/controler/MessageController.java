package com.bili.controler;

import com.bili.entity.PO.At;
import com.bili.entity.PO.LikeInfo;
import com.bili.entity.Message.Whisper;
import com.bili.entity.Message.WhisperCover;
import com.bili.entity.PO.SysInfo;
import com.bili.service.MessageService;
import com.bili.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    // 获取全部私信
    @GetMapping("/getWhisperList/{uid}")
    public Response getWhisperList (@PathVariable Integer uid) {
        try {
            List<WhisperCover> res = messageService.getWhisperList(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 获取私信内容
    @GetMapping("/getWhisperConent/{uid}/{hisuid}")
    public Response getWhisperConent (@PathVariable Integer uid, @PathVariable Integer hisuid) {
        try {
            List<Whisper> res = messageService.getWhisperConent(uid, hisuid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 发送私信（文字）
    @PostMapping("/sendMessage")
    public Response sendMessage (@RequestBody Whisper whisper) {
        try {
            messageService.sendMessage(whisper);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 发送私信 （图片）
    @PostMapping("/sendImg")
    public Response sendImg (Whisper whisper) {
        try {
            messageService.snedImg(whisper);
            return Response.success(200);
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

    @GetMapping("/getAtinfo/{uid}")
    public Response getAtinfo (@PathVariable Integer uid) {
        try {
            List<At> res = messageService.getAtinfo(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }

    // 修改deleted
    @GetMapping("/updateWhisperList/{wid}/{deleted}")
    public Response updateWhisperList (@PathVariable Integer wid, @PathVariable Integer deleted) {
        try {
            messageService.updateWhisperList(wid, deleted);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error: "+e);
        }
    }
}
