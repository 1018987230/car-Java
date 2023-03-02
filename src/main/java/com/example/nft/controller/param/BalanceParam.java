package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class BalanceParam implements Serializable {
    private String balanceOwnerPhone;
    private String consumerUuid;
    private String storeUuid;
    private Integer costMoney;
    private Integer costService1;
    private Integer costService2;
    private Integer costService3;
    private Integer costService4;
    private Integer costService5;
}
