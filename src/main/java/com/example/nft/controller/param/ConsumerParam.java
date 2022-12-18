package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConsumerParam implements Serializable {
    private Integer consumerId;
    private String consumerName;
    private String consumerSex;
    private String consumerBirthday;
    private String consumerLevel;
    private String consumerPhone;
    private String consumerPassword;
    private Integer consumerStatus;
    // 根据车牌号查车主信息
    private String carNumber;
}
