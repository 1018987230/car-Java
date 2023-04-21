package com.example.nft.controller;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2022/11/6 下午4:22
 * @PackageName:com.example.nft.controller
 * @ClassName: RegisterParam
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class RegisterParam implements Serializable {

    private String account;
    private String password;
    private String valid;
}
