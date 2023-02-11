package com.example.nft.service;

import java.util.Map;

/**
 * @Author wangyixiong
 * @Date 2023/1/30 下午11:29
 * @PackageName:com.example.nft.service
 * @ClassName: SendSms
 * @Description: TODO
 * @Version 1.0
 */
public interface SendSms {

    boolean addSendSms(String PhoneNumber, String TemplateCode, Map code);
}
