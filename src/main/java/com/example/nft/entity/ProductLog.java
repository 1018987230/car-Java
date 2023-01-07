package com.example.nft.entity;

import lombok.Data;


@Data
public class ProductLog {

    private String storeUuid;
    private String productUuid;
    private String productName;
    private Integer productLogQuantity;
    private String productUnit;
    private Integer productLogPrice;
    private Integer productLogType;
    private String createTime;

}
