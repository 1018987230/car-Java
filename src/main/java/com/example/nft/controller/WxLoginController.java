package com.example.nft.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nft.utils.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author wangyixiong
 * @Date 2023/2/25 上午2:18
 * @PackageName:com.example.nft.controller
 * @ClassName: WxLoginController
 * @Description: d488b91cba5ffce6d8febda4f388e433,wx30daae2411af79a8
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/wx")
public class WxLoginController {


    @Resource
    private RedisUtil redisUtil;

    /**
     * 微信登录获取openid，但不是服务器登录
     * @param code
     * @return
     */
    @GetMapping("/login")
    public Result login(@RequestParam("code") String code){


        String wxspAppid = "wx30daae2411af79a8";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "d488b91cba5ffce6d8febda4f388e433";
        //授权（必填）
        String grant_type = "authorization_code";

        //1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session",params);
        //解析相应内容（转换成json对象）
        JSONObject json =JSON.parseObject(sr);

        //获取会话密钥（session_key）json.get("session_key").toString();
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //生成token验证
        String token =  JwtUtil.genToken(openid);
        // 生成的token保存在redis中，key为店铺uuid拼接上用户手机号
        redisUtil.set(openid,token,60*60*24);

        HashMap<String, String> map = new HashMap<>();
        map.put("session_key", session_key);
        map.put("openid", openid);
        map.put("token",token);
        return ResultGenerator.genSuccessResult(map);
    }

    @GetMapping("/sendMessage")
    public Result sendMessage(@RequestParam("openid") String openid,
                              @RequestParam("template_id") String template_id){

        String wxspAppid = "wx30daae2411af79a8";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "d488b91cba5ffce6d8febda4f388e433";

        String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
        String params = "grant_type=client_credential&appid=" + wxspAppid + "&secret=" + wxspSecret;
        String result = HttpRequest.sendGet(tokenUrl, params);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String token = jsonObject.get("access_token").toString();

        String msgUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
        String params2 = "?access_token="+ token;
        JSONObject paramMap = new JSONObject();
        paramMap.put("touser", openid);
        paramMap.put("template_id", "fw4UwYL8cbJeVUthVYQ4nGnYuzk51U8HubcSTR4AUl8");
        paramMap.put("page","/pages/login");


        JSONObject data = new JSONObject();

        JSONObject thing1 = new JSONObject();
        thing1.put("value","你好");
        JSONObject name3 = new JSONObject();
        name3.put("value","王游戏");

        data.put("thing1", thing1);
        data.put("name3", name3);

        paramMap.put("data",data);
        System.out.println(paramMap);

        String post = HttpRequest.sendPost(msgUrl + params2, paramMap, "");
        System.out.println(post);
        return ResultGenerator.genSuccessResult();
    }

}
