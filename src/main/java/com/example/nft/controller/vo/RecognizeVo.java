package com.example.nft.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2023/11/30 下午10:33
 * @PackageName:com.example.nft.controller.vo
 * @ClassName: RecognizeVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class RecognizeVo implements Serializable {

    private String carNumber;

    private String consumerUuid;

    private String consumerName;

    private String consumerPhone;

    private Integer balanceMoney;

    private Integer balanceService1;

    private Integer balanceService2;

    private Integer balanceService3;

    private Integer balanceService4;

    private Integer balanceService5;
}
