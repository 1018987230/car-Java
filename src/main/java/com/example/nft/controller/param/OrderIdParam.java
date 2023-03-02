package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 上午4:17
 * @PackageName:com.example.nft.controller.param
 * @ClassName: OrderIdParam
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class OrderIdParam implements Serializable {
    private String orderUuid;
    private Integer orderId;
    private String orderConsumerUuid;
    private String orderStoreUuid;
    private String orderCarNumber;
    private Integer orderStatus;
}
