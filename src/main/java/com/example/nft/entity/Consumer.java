package com.example.nft.entity;

import lombok.Data;

import java.util.List;

@Data
public class Consumer {
    private Integer consumerId;
    private String consumerUuid;
    private String consumerSalt;
    private String consumerName;
    private String consumerSex;
    private String consumerBirthday;
    private String consumerLevel;
    private String consumerPhone;
    private String consumerPassword;
    private Integer consumerStatus;
    private String createTime;

    private List<Store> stores;
}
