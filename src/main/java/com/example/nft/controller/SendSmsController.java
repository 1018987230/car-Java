package com.example.nft.controller;

import com.aliyuncs.utils.StringUtils;
import com.example.nft.service.SendSms;
import com.example.nft.utils.RandomUtil;
import com.example.nft.utils.RedisUtil;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author wangyixiong
 * @Date 2023/1/31 下午10:04
 * @PackageName:com.example.nft.controller
 * @ClassName: SendSmsController
 * @Description: TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class SendSmsController {

    @Resource
    private SendSms sendSms;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("/sms/{phone}")
    public Result sendSms(@PathVariable("phone") String phone) {
        //得到电话，先看查一下redis中有无存放验证码
        String code = (String) redisUtil.get(phone);
        //有则返回已存在
        if (!StringUtils.isEmpty(code)) {
            return ResultGenerator.genFailResult("验证码已经存在，请等待60秒验证码过期。");
        } else {
            //没有则生成验证码，uuid随机生成四位数验证码
            code = RandomUtil.generateRandom(6);   //随机生成6个数形成验证码
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", code);
            //调用方法发送信息 传入电话，模板，验证码
            boolean send = sendSms.addSendSms(phone,  map);

            //返回ture则发送成功
            if (send) {
                //存入redis中并设置过期时间，这里设置5分钟过期
                redisUtil.set(phone, code, 300);
                return ResultGenerator.genSuccessResult();
            } else {
                //返回false则发送失败
                return ResultGenerator.genFailResult("验证码生成错误！");
            }
        }
    }

    @GetMapping("/sms/notice/{phone}")
    public Result sendNoticeSms(@PathVariable("phone") String phone) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("date", "2022-11-11 10:10:10");
        map.put("store", "宜城市牧车人汽车服务中心");
        map.put("service", " 洗车 ");
        //调用方法发送信息 传入电话，模板，验证码
        boolean send = sendSms.addSendSms(phone,  map);
        return  ResultGenerator.genSuccessResult("success");
    }
}
