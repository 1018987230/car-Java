package com.example.nft.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.nft.utils.HttpRequest;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.*;

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

        HashMap<String, String> map = new HashMap<>();
        map.put("session_key", session_key);
        map.put("openid", openid);

        return ResultGenerator.genSuccessResult(map);
    }



}

