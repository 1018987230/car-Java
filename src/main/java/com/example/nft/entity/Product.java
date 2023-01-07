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
public class Product {

    private Integer productId;
    private String storeUuid;
    private String productUuid;
    private String productName;
    private Integer productQuantity;
    private String productUnit;
    private Integer productPrice;
    private String productIn;
    private Integer productStatus;
    private String createTime;

}
