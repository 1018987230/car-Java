package com.example.nft.entity;

import lombok.Data;

@Data
public class ConsumerBalance {

    private Integer  balanceId;
    private String  balanceStoreUuid ;
    private String  balanceOwnerPhone;
    private Integer balanceMoney;
    private Integer balanceService1;
    private Integer balanceService2;
    private Integer balanceService3;
    private Integer balanceService4;
    private Integer balanceService5;
    private Integer balanceStatus;
    private String createTime;
}
