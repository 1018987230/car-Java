package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2022/11/27 下午11:06
 * @PackageName:com.example.nft.controller.param
 * @ClassName: OperatorParam
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class OperatorParam implements Serializable {

    private String operatorBelong;

    private String operatorPhone;

    private String operatorPassword;

    private String newPassword;

    private Integer operatorStatus;
}
