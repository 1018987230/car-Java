package com.example.nft.entity;

import lombok.Data;

@Data
public class BalanceLog {

    private Integer balanceLogId;
    private String balanceLogStore;
    private String balanceLogPhone;
    private Integer balanceLogMoney;
    private Integer balanceLogService1;
    private Integer balanceLogService2;
    private Integer balanceLogService3;
    private Integer balanceLogService4;
    private Integer balanceLogService5;
    private String createTime;
}
