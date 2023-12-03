package com.example.nft.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.nft.service.SendSms;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author wangyixiong
 * @Date 2023/1/30 下午11:31
 * @PackageName:com.example.nft.service.impl
 * @ClassName: SendSmsImpl
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class SendSmsImpl implements SendSms {
    @Override
    public boolean addSendSms(String PhoneNumbers,  Map date) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tF9AbG4ojYnT1KYiTRb", "IlIdi1PgKP0VxiX5HoN033lf5V389r");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //自定义信息
        request.putQueryParameter("PhoneNumbers", PhoneNumbers); //发送至手机号
        request.putQueryParameter("SignName", "宜城市牧车人汽车服务中心");  //自己配置的短信签名
        request.putQueryParameter("TemplateCode", "SMS_464041144"); //自己配置的模板 模版CODE

        //构建一个短信验证码

        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(date));   //转换成json字符串
        try {
            CommonResponse response = client.getCommonResponse(request); //发送至客户端
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();//返回是否发送成功
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;
    }
}
