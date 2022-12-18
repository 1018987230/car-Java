package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2022/11/21 上午12:20
 * @PackageName:com.example.nft.controller.param
 * @ClassName: PasswordParam
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class PasswordParam implements Serializable {
    private String consumerPhone;
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
}
