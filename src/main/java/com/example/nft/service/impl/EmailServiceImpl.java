package com.example.nft.service.impl;


import com.example.nft.commons.ServiceResultEnum;
import com.example.nft.service.EmailService;
import com.example.nft.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    RedisService redisService;

    private static long CODE_EXPIRE_SECONDS = 600;    //设置验证码过期时间为600秒

    public String sendCode(String to) throws MessagingException {
        // 设置邮件主题和验证码
        String subject = "Please receive code!";
        int num= (int) ((Math.random()*8999)+1000);
        String code = Integer.toString(num);
        String context="<h1>code:<h1>" + code;
        //保存验证码在redis中
        redisService.set(to, code);
        System.out.println(redisService.get(to) + to);
        redisService.expire(to, CODE_EXPIRE_SECONDS);
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(context, true);
        javaMailSender.send(mimeMailMessage);
        return ServiceResultEnum.SUCCESS.getResult();
    }

}
