package com.example.nft.entity;

import lombok.Data;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 上午1:29
 * @PackageName:com.example.nft.entity
 * @ClassName: Order
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class Order {
    private Integer orderId ;
    private String orderUuid;
    private String orderConsumerUuid;
    private String orderStoreUuid;
    private String orderCarNumber;
    private String orderService;
    private String orderStatus;
    private String orderNote;
    private String orderPreordainTime;
    private String updateTime;
    private String createTime;
}
