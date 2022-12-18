package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class BalanceLogParam implements Serializable {
    private String balanceLogPhone;

    private String balanceLogStore;

    private String startTime;

    private String endTime;

    private Integer currentPage;
}
