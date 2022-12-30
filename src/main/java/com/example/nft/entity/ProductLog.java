package com.example.nft.entity;

import lombok.Data;

/**
 * @author ：bbdxgg
 * @date ：Created By 2022/12/30 下午1:34
 * @description：
 * @modified By：
 * @version: $
 */
@Data
public class ProductLog {

    private  Integer productLogId;
    private  String storeUuid;
    private  String productLogUuid;
    private  String productLogName;
    private  Integer productLogQuantity;
    private  String productLogUnit;
    private  Float productLogPrice;
    private  String productLogIn;
    private  String createTime;
}
