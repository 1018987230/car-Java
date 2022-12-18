package com.example.nft.controller;

import com.example.nft.controller.param.EmailParam;
import com.example.nft.service.EmailService;
import com.example.nft.utils.Result;
import com.example.nft.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Resource
    EmailService emailService;
    /**
     * 发送邮件验证码
     * @param emailParam
     * @return
     * @throws MessagingException
     */

    @PostMapping("/sendCode")
    public Result send(@RequestBody EmailParam emailParam ) throws MessagingException {
        System.out.println(emailParam.getToEmail());
        String result = emailService.sendCode(emailParam.getToEmail());
        System.out.println(result);
        if(result.equals("success")){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(result);
    }


}
