package com.example.nft.entity;

import lombok.Data;

/**
 * @Author wangyixiong
 * @Date 2022/11/27 下午10:03
 * @PackageName:com.example.nft.entity
 * @ClassName: Operator
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class Operator {
    private Integer operatorId;

    private String operatorBelong;

    private String operatorLevel;

    private String operatorPhone;

    private String operatorSalt;

    private String operatorPassword;

    private String operatorName;

    private Integer operatorStatus;

    private String createTime;

}
