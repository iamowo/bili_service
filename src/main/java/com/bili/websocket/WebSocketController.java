package com.bili.websocket;

import com.bili.util.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class WebSocketController {
    @GetMapping("/socket/sendmessag/{userId}")
    public Response pushMessage(@PathVariable Integer userId) {
        Map<String, Object> result = new HashMap<>();
        String message = "is ok";
        try {
            HashSet<Integer> userIds = new HashSet<>();
            userIds.add(userId);
            WebSocktService.sendMessage("服务端推送消息：" + message, userIds );
            result.put("code", userId);
            result.put("msg", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.success(result);
    }
}
