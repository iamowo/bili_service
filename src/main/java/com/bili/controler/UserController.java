package com.bili.controler;

import com.bili.entity.User;
import com.bili.entity.UserSetting;
import com.bili.entity.outEntity.Login;
import com.bili.entity.outEntity.RegisterUser;
import com.bili.entity.outEntity.UpdateUser;
import com.bili.entity.outEntity.UserData;
import com.bili.mapper.UserMapper;
import com.bili.service.RedisService;
import com.bili.service.UserService;
import com.bili.util.Response;
import com.bili.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 没有token的用户信息
    @GetMapping("/getByUid/{uid}")
    public Response getByUid (@PathVariable Integer uid) {
        try {
            User res = userService.getByUid(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 获取全部User
    @GetMapping("/AllUser")
    public Response AllUser () {
        try {
            List<User> res = userService.AllUser();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    @GetMapping("/getByUidFollowed/{uid}/{myuid}")
    public Response getByUidFollowed (@PathVariable Integer uid, @PathVariable Integer myuid) {
        try {
            User res = userService.getByUidFollowed(uid, myuid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 登录
    @PostMapping("/login")
    public Response login(@RequestBody Login login) {
        try {
            Integer result = userService.login(login.getAccount(), login.getPassword());
            if (result == 0) {
                return Response.success(0);
            } else if (result == 1) {
                return Response.success(1);
            } else {
                User user = userMapper.findAccount(login.getAccount());
                String role = "XIXIX";
                String token = TokenUtil.getToken(user.getName(), role);
                user.setToken(token);
                userService.updateTempToken(token, user.getUid());
                return Response.success(user);
            }
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    @GetMapping("/getByUidWithToken/{uid}")
    public Response getByUidWithToken (@PathVariable Integer uid) {
        try {
            User res = userService.getByUid(uid);
            res.setToken(res.getTemptoken());
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 注册
    @PostMapping("/register")
    public Response Register(RegisterUser registerUser) {
        try {
            Integer res = userService.register(registerUser);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 注册查询，是否有此账号
    @GetMapping("/findAccount/{account}")
    public Response findAccount(@PathVariable String account) {
        try {
            Integer res= userService.findAccount(account);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }
    // 加关注
    @GetMapping("/toFollow/{uid1}/{uid2}")
    public Response toFollow (@PathVariable Integer uid1, @PathVariable Integer uid2) {
        try {
            userService.toFollow(uid1, uid2);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 取消关注
    @GetMapping("/toUnfollow/{uid1}/{uid2}")
    public Response toUnfollow (@PathVariable Integer uid1, @PathVariable Integer uid2) {
        try {
            userService.toUnfollow(uid1, uid2);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 获取关注
    @PostMapping("/getFollow")
    public Response getFollow (@RequestBody Map<String, Object> map) {
        try {
            Map<String, Object> res = userService.getFollow(map);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 获取粉丝
//    @GetMapping("/getFans/{uid}/{page}/{nums}/{keyword}")
    @PostMapping("/getFans")
    public Response getFans (@RequestBody Map<String, Object> map) {
        try {
            Map<String, Object> res = userService.getFans(map);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 更新基础信息
    @PostMapping("/updateUserinfo")
    public Response updateUserinfo(UpdateUser updateUser) {
        try {
            userService.updateUserinfo(updateUser);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 更新数据信息
    @PostMapping("/updateUserdata")
    public Response updateUserdata(@RequestBody UserData userData) {
        try {
            userService.updateUserdata(userData);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 搜索用户， 根据关键词
    @GetMapping("/searchUser/{keyword}/{uid}/{sort}")
    public Response searchUser (@PathVariable String keyword, @PathVariable Integer uid, @PathVariable Integer sort) {
        try {
            List<User> res = userService.searchUser(keyword, uid, sort);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    @GetMapping("/getSetting/{uid}")
    public Response getSetting (@PathVariable Integer uid) {
        try {
            UserSetting res = userService.getSetting(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500,"error:"+e);
        }
    }

    // 更新设置
    @PostMapping("/changeSetting")
    public Response changeSetting(@RequestBody UserSetting userSetting) {
        try {
            userService.changeSetting(userSetting);
            return Response.success(200);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 得到用户数据
    @GetMapping("/getUserData")
    public Response getUserData(@RequestParam Integer uid) {
        try {
            Map<String, Integer> res = userService.getUserData(uid);
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 生成登录二维码
    @GetMapping("/generateQrCode")
    public Response generateQrCode() {
        try {
            String res = userService.generateQrCode();
            return Response.success(res);
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }

    // 验证二维码
    @PostMapping("/verifyQrCode")
    public Response verifyQrCode(@RequestBody Map<String, String> request) {
        try {
            String qrCodeContent = request.get("qrCodeContent");
            // 从Redis或数据库中获取二维码状态
            // String status = redisTemplate.opsForValue().get(qrCodeContent);
            String status = "pending"; // 假设状态为pending
            if ("pending".equals(status)) {
                // 更新二维码状态为已扫描
                // redisTemplate.opsForValue().set(qrCodeContent, "scanned");
                return Response.success("二维码已扫描");
            } else if ("scanned".equals(status)) {
                return Response.success("二维码已使用");
            } else {
                return Response.success("二维码无效");
            }
        } catch (Exception e) {
            return Response.failure(500, "error");
        }
    }
}
