package com.example.nft.entity;


import lombok.Data;

import java.util.List;

@Data
public class Store {
    private Integer storeId;
    private String storeUuid;
    private String storeName;
    private String storeOwner;
    private String storePhone;
    private String storeAddress;
    private String storeBusinessScope;
    private String storeRemark;
    private Integer storeStatus;
    private String createTime;

    private List<Consumer> consumers;
}
