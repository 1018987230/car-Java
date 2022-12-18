package com.example.nft.controller.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author wangyixiong
 * @Date 2022/10/23 上午2:11
 * @PackageName:com.example.nft.controller.param
 * @ClassName: OrderParam
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class OrderParam implements Serializable {
    private String orderConsumerUuid;
    private String orderStoreUuid;
    private String orderCarNumber;
    private String orderService;
    private String orderNote;
    private String orderPreordainTime;
}
