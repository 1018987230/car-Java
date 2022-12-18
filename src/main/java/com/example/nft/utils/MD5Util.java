package com.example.nft.utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.util.UUID;

public class MD5Util {

    // 生成盐值
    public static String generateSalt(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,5);
    }


    // md5加密
    public static String passwordMD5(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
