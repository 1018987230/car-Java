package com.example.nft.entity;

import lombok.Data;


@Data
public class ProductLog {

    private String storeUuid;
    private String productLogUuid;
    private String productLogName;
    private Integer productLogQuantity;
    private String productLogUnit;
    private Float productLogPrice;
    private String productLogType;
    private String createTime;

}
